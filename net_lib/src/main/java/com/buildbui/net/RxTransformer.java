package com.buildbui.net;

import android.util.Log;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

import java.util.concurrent.Callable;

public class RxTransformer {

    private static final String TAG = "RxTransformer";
    private static int errCode;

    private static class TransfIoMain {
        private static final MaybeTransformer TRANSFORMER = new MaybeTransformer() {
            @Override
            public MaybeSource apply(Maybe upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> MaybeTransformer<T, T> io_main() {
        return TransfIoMain.TRANSFORMER;
    }

    public static <T> MaybeTransformer<T, T> waitLoading2(final ILoadingView iLoadingView) {

        return new MaybeTransformer<T, T>() {

            @Override
            public MaybeSource<T> apply(final Maybe<T> upstream) {
                return Maybe.using(new Callable<ILoadingView>() {
                    @Override
                    public ILoadingView call() throws Exception {
                        Log.i(TAG, "show: " + Thread.currentThread());
                        iLoadingView.show();
                        return iLoadingView;
                    }
                }, new io.reactivex.functions.Function<ILoadingView, MaybeSource<? extends T>>() {
                    @Override
                    public MaybeSource<? extends T> apply(ILoadingView iLoadingView)
                            throws Exception {
                        Log.i(TAG, "dowork: " + Thread.currentThread());
                        return upstream;
                    }
                }, new Consumer<ILoadingView>() {
                    @Override
                    public void accept(ILoadingView iLoadingView) throws Exception {
                        Log.i(TAG, "dismiss: " + Thread.currentThread());
                        iLoadingView.dismiss();
                    }
                });
            }
        };
    }

    public static WaitingTransformer waitLoading(final ILoadingView iLoadingView) {

        return new WaitingTransformer(iLoadingView);
    }


    public static <T> MaybeTransformer<T, T> retryToken(final Maybe tokenObservable) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> upstream) {

                return upstream.retryWhen(new Function<Flowable<Throwable>, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Flowable<Throwable> throwableFlowable)
                            throws Exception {

                        return throwableFlowable.flatMap(new Function<Throwable, Publisher<?>>() {
                            @Override
                            public Publisher<?> apply(Throwable throwable) throws Exception {
                                if (throwable instanceof ApiStateException) {
                                    int errCode = ((ApiStateException) throwable).getCode();
                                    if (errCode == ApiStateException.ERR_ACCESS_TOKEN) {
                                        Log.i(TAG, "retryToken 等待Token刷新: " + Thread.currentThread());
                                        return TokenRefresh.getInstance()
                                                .refresh(tokenObservable.toObservable())
                                                .toFlowable(BackpressureStrategy.DROP);//等待刷新token
                                    } /*else if (errCode == ApiStateException.ERR_REFRESH_TOKEN) {
                    //退回登录
                  }*/
                                }
                                return Maybe.error(throwable).toFlowable();//直接返回错误
                            }
                        });
                    }
                });
            }
        };
    }

    public static <T, R> MaybeTransformer<T, T> retryToken(
            final TokenActionCallback<R> tokenActionCallback) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> upstream) {

                return upstream.retryWhen(new Function<Flowable<Throwable>, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Flowable<Throwable> throwableFlowable) throws Exception {

                        return throwableFlowable.flatMap(new Function<Throwable, Publisher<?>>() {
                            @Override
                            public Publisher<?> apply(final Throwable throwable) throws Exception {
                                if (throwable instanceof ApiStateException) {
                                    errCode = ((ApiStateException) throwable).getCode();
                                    if (errCode == ApiStateException.ERR_ACCESS_TOKEN
                                            || errCode == ApiStateException.ERR_MISS_INFO) {
                                        Log.i(TAG, "retryToken 等待Token刷新: " + Thread.currentThread());
                                        return TokenRefresh.getInstance().refresh(Maybe.defer(new Callable<MaybeSource<R>>() {
                                            @Override
                                            public MaybeSource<R> call() throws Exception {
                                                return tokenActionCallback.getUpdateTokenMaybe();
                                            }
                                        }).doOnSuccess(new Consumer<R>() {
                                            @Override
                                            public void accept(R r) throws Exception {
                                                tokenActionCallback.onUpdateTokenSuccess(r);
                                            }
                                        }).doOnComplete(new Action() {
                                            @Override
                                            public void run() throws Exception {
                                                //data返回为null
                                                if (errCode == ApiStateException.ERR_ACCESS_TOKEN) {
                                                    throw new ReLoginException();
                                                } else {
                                                    throw new MissInfoException();
                                                }
                                            }
                                        }).toObservable()).toFlowable(BackpressureStrategy.DROP);//等待刷新token
                                    } /*else if (errCode == ApiStateException.ERR_REFRESH_TOKEN) {
                    //退回登录
                  }*/
                                }
                                return Maybe.error(throwable).toFlowable();//直接返回错误
                            }
                        });
                    }
                }).onErrorResumeNext(new Function<Throwable, MaybeSource<? extends T>>() {
                    @Override
                    public MaybeSource<? extends T> apply(Throwable throwable) throws Exception {
                        if (throwable instanceof ReLoginException) {
                            //返回登录
                            tokenActionCallback.onReLogin(errCode);
                        } else if (throwable instanceof MissInfoException) {

                        }
                        return Maybe.error(throwable);
                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> retryToken(final Observable tokenObservable) {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable)
                            throws Exception {
                        Log.i(TAG, "retryToken  retryWhen: " + Thread.currentThread());
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(final Throwable throwable)
                                    throws Exception {
                                if (throwable instanceof ApiStateException) {
                                    int errCode = ((ApiStateException) throwable).getCode();
                                    if (errCode == ApiStateException.ERR_ACCESS_TOKEN) {
                                        Log.i(TAG, "retryToken 等待Token刷新: " + Thread.currentThread());
                                        return TokenRefresh.getInstance()
                                                .refresh(tokenObservable)
                                                .doOnNext(new Consumer() {
                                                    @Override
                                                    public void accept(Object o) throws Exception {
                                                        Log.i(TAG, "retryToken doOnNext accept: " + o);
                                                    }
                                                })
                                                .doOnComplete(new Action() {
                                                    @Override
                                                    public void run() throws Exception {
                                                        Log.i(TAG, "retryToken run: doOnComplete 刷新Token结束==");
                                                    }
                                                })
                                                .doOnError(new Consumer<Throwable>() {
                                                    @Override
                                                    public void accept(Throwable throwable) throws Exception {
                                                        Log.i(TAG, "retryToken run: doOnError 刷新Token结束==");
                                                    }
                                                });//等待刷新token
                                    }/* else if (errCode == ApiStateException.ERR_REFRESH_TOKEN) {
                    //退回登录

                  }*/
                                }
                                return Observable.error(throwable);//直接返回错误
                            }
                        });
                    }
                });
            }
        };
    }

    static class WaitingTransformer
            implements MaybeTransformer, ObservableTransformer, CompletableTransformer,
            FlowableTransformer {

        ILoadingView loadingView;

        public WaitingTransformer(ILoadingView iLoadingView) {
            this.loadingView = iLoadingView;
        }

        @Override
        public CompletableSource apply(Completable upstream) {
            UsingHandle usingHandle = new UsingHandle<>(upstream, loadingView);
            return Completable.using(usingHandle, usingHandle, usingHandle);
        }

        @Override
        public Publisher apply(Flowable upstream) {
            UsingHandle usingHandle = new UsingHandle<>(upstream, loadingView);
            return Flowable.using(usingHandle, usingHandle, usingHandle);
        }

        @Override
        public MaybeSource apply(final Maybe upstream) {
            UsingHandle usingHandle = new UsingHandle<>(upstream, loadingView);
            return Maybe.using(usingHandle, usingHandle, usingHandle);
        }

        @Override
        public ObservableSource apply(Observable upstream) {
            UsingHandle usingHandle = new UsingHandle<>(upstream, loadingView);
            return Observable.using(usingHandle, usingHandle, usingHandle);
        }
    }

    static class UsingHandle<T>
            implements Callable<ILoadingView>, Function<ILoadingView, T>, Consumer<ILoadingView> {

        ILoadingView iLoadingView;

        T upstream;

        public UsingHandle(T upstream, ILoadingView loadingView) {
            this.upstream = upstream;
            iLoadingView = loadingView;
        }

        @Override
        public void accept(ILoadingView loadingView) throws Exception {
            Log.i(TAG, "dismiss: " + Thread.currentThread());
            iLoadingView.dismiss();
        }

        @Override
        public T apply(ILoadingView loadingView) throws Exception {
            Log.i(TAG, "dowork: " + Thread.currentThread());
            return upstream;
        }

        @Override
        public ILoadingView call() throws Exception {
            Log.i(TAG, "show: " + Thread.currentThread());
            iLoadingView.show();
            return iLoadingView;
        }
    }
}
