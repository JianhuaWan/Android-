package consultan.vanke.com.fragment;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import consultan.vanke.com.R;
import consultan.vanke.com.bean.DataChatBean;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.widgets.CanvasView;
import consultan.vanke.com.widgets.MyChatView;
import me.yokeyword.fragmentation.ISupportFragment;

public class ChartFragment extends BaseFragment {
    @BindView(R.id.chatview)
    MyChatView chatview;
    List<DataChatBean> mData = new ArrayList<>();
    @BindView(R.id.chart)
    LineChart chart;
    @BindView(R.id.canvas_view)
    CanvasView canvasView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initVariable() {
        super.initVariable();
        List<String> colors_bule = new ArrayList<>();
        colors_bule.add(getString(R.string.char_color52c6f6));
        colors_bule.add(getString(R.color.char_color2995ea));
        List<String> colors_yellow = new ArrayList<>();
        colors_yellow.add(getString(R.color.char_colorffdb4c));
        colors_yellow.add(getString(R.color.char_colorffb631));
        List<String> colors_green = new ArrayList<>();
        colors_green.add(getString(R.color.char_color9eea5e));
        colors_green.add(getString(R.color.char_color81cd42));
        DataChatBean dataChatBean = new DataChatBean();
        dataChatBean.setColors(colors_bule);
        dataChatBean.setValue(0.25f);
        DataChatBean dataChatBean1 = new DataChatBean();
        dataChatBean1.setColors(colors_yellow);
        dataChatBean1.setValue(0.65f);
        DataChatBean dataChatBean2 = new DataChatBean();
        dataChatBean2.setColors(colors_green);
        dataChatBean2.setValue(0.1f);
        mData.add(dataChatBean1);
        mData.add(dataChatBean2);
        mData.add(dataChatBean);
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initDatas() {
        chatview.setmDatas(mData);
        chatview.invalidate();
        chatview.startA();
        canvasView.setDatas(getString(R.string.total), "6");
        canvasView.invalidate();
        //===========================
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis rightYAxis = chart.getAxisRight();
        rightYAxis.setEnabled(false);
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);
        List<Entry> entries = new ArrayList<>();
        Entry entry = new Entry();
        entry.setX(10);
        entry.setY(10);
        Entry entry1 = new Entry();
        entry1.setX(1);
        entry1.setY(3);
        entries.add(entry1);
        entries.add(entry);
        LineDataSet lineDataSet = new LineDataSet(entries, ConstantLibrary.LABLE);
        lineDataSet.setColor(R.color.color_00BA91);
        lineDataSet.setValueTextColor(R.color.color_00BA91);
        LineData lineData = new LineData(lineDataSet);
        chart.setData(lineData);
        chart.invalidate();
    }


    @Override
    protected void initEvents() {

    }


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = View.inflate(_mActivity, R.layout.chart_fragment, null);
        return rootView;
    }


}
