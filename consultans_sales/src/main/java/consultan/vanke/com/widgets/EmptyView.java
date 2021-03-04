package consultan.vanke.com.widgets;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import consultan.vanke.com.R;
import consultan.vanke.com.constant.ConstantLibrary;

public class EmptyView extends FrameLayout implements View.OnClickListener {

    private ImageView iv_empty;//获取参数

    private TextView tv_empty;//是否为空

    private RelativeLayout rl_content;//获取内容


    private int defaultEmptyPicture = R.mipmap.temp_content_empty;

    private String[] mDataDiffText = new String[]{ConstantLibrary.NETERROR};

    private ImageView mLoadingImage;

    //设置网络监听
    public OnAgainData getOnAgainData() {
        return onAgainData;
    }

    public void setOnAgainData(OnAgainData onAgainData) {
        this.onAgainData = onAgainData;
    }

    private OnAgainData onAgainData;//再一次获取请求

    private int type;

    private ImageView iv_loading;

    private AnimationDrawable mAnimationDrawable;


    private BitmapDrawable topDrawable;


    private String emptyDesc;


    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.loading_empty, this, true);
        iv_empty = findViewById(R.id.iv_empty);
        tv_empty = findViewById(R.id.tv_empty);
        rl_content = findViewById(R.id.rl_content);
        iv_loading = findViewById(R.id.iv_loading);
        if (null == topDrawable) {
            iv_empty.setBackgroundResource(defaultEmptyPicture);
        } else {
            iv_empty.setBackground(topDrawable);
        }
        if (TextUtils.isEmpty(emptyDesc)) {
            tv_empty.setText(R.string.no_data);
        } else {
            tv_empty.setText(emptyDesc);
        }
        rl_content.setVisibility(VISIBLE);
        Glide.with(getContext()).load(R.drawable.loading).into(iv_loading);
        initView();

    }

    private void initView() {
        switch (type) {
            case 0://5高度
                RelativeLayout.LayoutParams layoutParamsZero = (RelativeLayout.LayoutParams) iv_empty.getLayoutParams();
                int topZero = (int) getResources().getDimension(5);
                layoutParamsZero.setMargins(0, topZero, 0, 0);
                iv_empty.setLayoutParams(layoutParamsZero);
                break;
            case 1://176高度
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_empty.getLayoutParams();
                int top = (int) getResources().getDimension(88);
                layoutParams.setMargins(0, top, 0, 0);
                iv_empty.setLayoutParams(layoutParams);
                break;
            case 2://156高度
                RelativeLayout.LayoutParams layoutParamTwo = (RelativeLayout.LayoutParams) iv_empty.getLayoutParams();
                int topTwo = (int) getResources().getDimension(78);
                layoutParamTwo.setMargins(0, topTwo, 0, 0);
                iv_empty.setLayoutParams(layoutParamTwo);
                break;
            case 3://256高度
                RelativeLayout.LayoutParams layoutParamThree = (RelativeLayout.LayoutParams) iv_empty.getLayoutParams();
                int topThree = (int) getResources().getDimension(128);
                layoutParamThree.setMargins(0, topThree, 0, 0);
                iv_empty.setLayoutParams(layoutParamThree);
                break;
            case 4://256高度
                RelativeLayout.LayoutParams layoutParamFour = (RelativeLayout.LayoutParams) iv_empty.getLayoutParams();
                int topFour = (int) getResources().getDimension(156);
                layoutParamFour.setMargins(0, topFour, 0, 0);
                iv_empty.setLayoutParams(layoutParamFour);
                break;
        }
        tv_empty.setOnClickListener(this);
        iv_empty.setOnClickListener(this);
    }

    public void setText(int resouceId) {
        tv_empty.setText(resouceId);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_empty || view.getId() == R.id.iv_empty) {
            if (onAgainData != null) {
                rl_content.setVisibility(VISIBLE);
                onAgainData.getAgain();
            }
        }

    }

    public interface OnAgainData {
        void getAgain();
    }

    /**
     * 数据加载失败
     * NETERROR  网络错误 没有网络
     * SERVERERRO 服务器异常
     *
     * @param type
     */
    public void setDataError(int type) {
        rl_content.setVisibility(View.GONE);
        iv_empty.setBackgroundResource(defaultEmptyPicture);
        tv_empty.setText(mDataDiffText[0]);
        setVisibility(VISIBLE);
    }


    public String getEmptyDesc() {
        return emptyDesc;
    }

    public void setEmptyDesc(String emptyDesc) {
        this.emptyDesc = emptyDesc;
    }


    /**
     * 加载过程中为空
     *
     * @param bitmapRes
     * @param txtRes
     */
    public void setEmpty(int bitmapRes, int txtRes) {
        rl_content.setVisibility(View.GONE);
        iv_empty.setBackgroundResource(bitmapRes);
        tv_empty.setText(txtRes);
        setVisibility(VISIBLE);
    }

    /**
     * 布局的空白页
     */
    public void setEmpty() {
        rl_content.setVisibility(View.GONE);
        if (topDrawable != null) {
            iv_empty.setBackground(topDrawable);
        }
        if (!TextUtils.isEmpty(emptyDesc)) {
            tv_empty.setText(emptyDesc);
        }
        setVisibility(VISIBLE);
    }

    /**
     * 成功后消失
     */
    public void success() {
        setVisibility(View.GONE);

    }


    /**
     * 开启加载
     */
    public void startLoading() {
        if (this.getVisibility() == View.GONE) {
            rl_content.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(R.drawable.loading).into(iv_loading);
            setVisibility(View.VISIBLE);
        }
    }
}
