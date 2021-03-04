package consultan.vanke.com.fragment;

import android.os.Bundle;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import consultan.vanke.com.R;
import consultan.vanke.com.adapter.CommonAdapter;
import consultan.vanke.com.adapter.ViewHolder;
import consultan.vanke.com.bean.HomeJumpBean;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.event.MessageEvent;
import consultan.vanke.com.event.MessageResult;
import consultan.vanke.com.utils.ImageSelector;
import consultan.vanke.com.widgets.SpaceItemDecoration;

public class BlockThreeFragment extends BaseFragment {


    @BindView(R.id.bt_camera)
    Button btCamera;
    @BindView(R.id.bt_webView)
    Button btWebView;
    @BindView(R.id.recy_view)
    RecyclerView recyView;
    CommonAdapter adapter;
    @BindView(R.id.bt_dialog_choise)
    Button btDialogChoise;
    private List<String> photoList;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void initViews() {
        bottomSheetDialog = new BottomSheetDialog(_mActivity);
        recyView.setLayoutManager(new GridLayoutManager(_mActivity, 3));
        recyView.addItemDecoration(new SpaceItemDecoration(3));
        adapter = new CommonAdapter<String>(_mActivity, photoList, R.layout.item_photo) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }

            @Override
            public void convert(ViewHolder holder, String photourl) {
                ImageView imageView = holder.getView(R.id.photo);
                ImageView photo_del = holder.getView(R.id.photo_del);
                //=======================================
                Glide.with(getActivity())
                        .load(photourl)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
                photo_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.remove(holder.getPosition());
                    }
                });
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //查看
//                        PreviewActivity.openActivity(_mActivity, new ArrayList<>(adapter.getDatas()),
//                                new ArrayList<>(adapter.getDatas()), false, 9, holder.getPosition());
                    }
                });
            }

        };
        recyView.setAdapter(adapter);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(_mActivity, R.layout.block_three_fragment, null);
    }

    public static BlockThreeFragment getInstance() {
        BlockThreeFragment blockThreeFragment = new BlockThreeFragment();
        return blockThreeFragment;
    }


    @OnClick({R.id.bt_camera, R.id.bt_webView, R.id.bt_dialog_choise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_camera:
                bottomSheetDialog.setContentView(R.layout.common_dialog_photo);
                //给布局设置透明背景色
                bottomSheetDialog.getDelegate().findViewById(R.id.design_bottom_sheet)
                        .setBackgroundColor(getResources().getColor(android.R.color.transparent));
                bottomSheetDialog.findViewById(R.id.cutter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        ImageSelector.builder()
                                .setCrop(true) // 设置是否使用图片剪切功能。
                                .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                                .onlyTakePhoto(true)  // 仅拍照，不打开相册
                                .start(_mActivity, ConstantLibrary.IMAGESELECTOR_CROP);
                    }
                });
                bottomSheetDialog.findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        ImageSelector.builder().useCamera(true).onlyTakePhoto(true).start(_mActivity, ConstantLibrary.IMAGESELECTOR_ONLYTAKE);
                    }
                });
                bottomSheetDialog.findViewById(R.id.albums).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        //限数量的多选(比如最多9张)
                        ImageSelector.builder()
                                .useCamera(true) // 设置是否使用拍照
                                .setSingle(false)  //设置是否单选
                                .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                                .canPreview(false) //是否可以预览图片，默认为true
                                .start(_mActivity, ConstantLibrary.IMAGESELECTOR_FILE); // 打开相册
                    }
                });
                bottomSheetDialog.findViewById(R.id.albumsingle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        ImageSelector.builder()
                                .useCamera(true) // 设置是否使用拍照
                                .setCrop(true)  // 设置是否使用图片剪切功能。
                                .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                                .setSingle(true)  //设置是否单选
                                .canPreview(true) //是否可以预览图片，默认为true
                                .start(_mActivity, ConstantLibrary.IMAGESELECTOR_FILE_ONE); // 打开相册

                    }
                });
                bottomSheetDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.show();
                break;
            case R.id.bt_webView:
                MessageEvent<HomeJumpBean> messageEvent = new MessageEvent<>();
                messageEvent.setData(new HomeJumpBean(ConstantLibrary.WEBVIEWFRAGMENT, ConstantLibrary.TYPE_TIME_OUT));
                EventBus.getDefault().post(messageEvent);
                break;
            case R.id.bt_dialog_choise:
                bottomSheetDialog.setContentView(R.layout.common_dialog);
                //给布局设置透明背景色
                bottomSheetDialog.getDelegate().findViewById(R.id.design_bottom_sheet)
                        .setBackgroundColor(getResources().getColor(android.R.color.transparent));
                bottomSheetDialog.show();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onActivityResult(MessageResult messageResult) {
        if (messageResult.getRequestCode() == ConstantLibrary.IMAGESELECTOR_CROP || messageResult.getRequestCode() == ConstantLibrary.IMAGESELECTOR_ONLYTAKE || messageResult.getRequestCode() == ConstantLibrary.IMAGESELECTOR_FILE || messageResult.getRequestCode() == ConstantLibrary.IMAGESELECTOR_FILE_ONE && messageResult.getData() != null) {
            //获取选择器返回的数据
            ArrayList<String> images = messageResult.getData().getStringArrayListExtra(
                    ImageSelector.SELECT_RESULT);

            /**
             * 是否是来自于相机拍照的图片，
             * 只有本次调用相机拍出来的照片，返回时才为true。
             * 当为true时，图片返回的结果有且只有一张图片。
             */
            boolean isCameraImage = messageResult.getData().getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
            adapter.setDatas(images);
        }
    }
}
