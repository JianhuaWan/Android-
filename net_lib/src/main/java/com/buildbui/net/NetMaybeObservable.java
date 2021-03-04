package com.buildbui.net;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

/**
 * 主要是兼容处理 服务器entity返回为null，比如提交数据成功后，给了成功的code，但是无entity数据返回时
 * 会走onComplete这个方法，而为了简化和统一接口的使用，将这个方法映射到onSuccess(null)
 * desc:
 * author: luoh17
 * time: 2018/9/7 14:24
 */
public abstract class NetMaybeObservable<T> implements MaybeObserver<T> {
  @Override public void onSubscribe(Disposable d) {

  }

  @Override public void onComplete() {//服务器返回的entity为null，会走这个方法，这里做转发
    onSuccess(null);
  }

}
