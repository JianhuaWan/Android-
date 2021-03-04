package consultan.vanke.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import consultan.vanke.com.R;
import consultan.vanke.com.base.BaseApplication;
import consultan.vanke.com.bean.TimerItem;
import consultan.vanke.com.bean.TimerItemUtil;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.utils.TimeTools;

import java.util.List;

public class CountTimeFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private View inflate;

    private MyAdapter mAdapter;


    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        List<TimerItem> timerItems = TimerItemUtil.getTimerItemList();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new MyAdapter(_mActivity, timerItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = View.inflate(_mActivity, R.layout.activity_recycler_view, null);
        return inflate;
    }

    //适配器
    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<TimerItem> mDatas;
        //用于退出activity,避免countdown，造成资源浪费。
        private SparseArray<CountDownTimer> countDownMap;

        public MyAdapter(Context context, List<TimerItem> datas) {
            mDatas = datas;
            countDownMap = new SparseArray<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_common, parent, false);
            return new ViewHolder(view);
        }

        /**
         * 清空资源
         */
        public void cancelAllTimers() {
            if (countDownMap == null) {
                return;
            }
            Log.e("TAG", "size :  " + countDownMap.size());
            for (int i = 0, length = countDownMap.size(); i < length; i++) {
                CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
                if (cdt != null) {
                    cdt.cancel();
                }
            }
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final TimerItem data = mDatas.get(position);
            holder.statusTv.setText(data.name);
            long time = data.expirationTime;
            time = time - System.currentTimeMillis();
            //将前一个缓存清除
            if (holder.countDownTimer != null) {
                holder.countDownTimer.cancel();
            }
            if (time > 0) {
                holder.countDownTimer = new CountDownTimer(time, 1000) {
                    public void onTick(long millisUntilFinished) {
                        holder.timeTv.setText(TimeTools.getCountTimeByLong(millisUntilFinished));
                    }

                    public void onFinish() {
                        holder.timeTv.setText(ConstantLibrary.TIMESTAR);
                        holder.statusTv.setText(data.name + ConstantLibrary.TIMEOVER);
                    }
                }.start();

                countDownMap.put(holder.timeTv.hashCode(), holder.countDownTimer);
            } else {
                holder.timeTv.setText(ConstantLibrary.TIMESTAR);
                holder.statusTv.setText(data.name + ConstantLibrary.TIMEOVER);
            }

        }

        @Override
        public int getItemCount() {
            if (mDatas != null && !mDatas.isEmpty()) {
                return mDatas.size();
            }
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView statusTv;
            public TextView timeTv;
            public CountDownTimer countDownTimer;

            public ViewHolder(View itemView) {
                super(itemView);
                statusTv = (TextView) itemView.findViewById(R.id.tv_status);
                timeTv = (TextView) itemView.findViewById(R.id.tv_time);
                timeTv.setTypeface(BaseApplication.getInstance().tf);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAdapter != null) {
            mAdapter.cancelAllTimers();
        }
    }
}
