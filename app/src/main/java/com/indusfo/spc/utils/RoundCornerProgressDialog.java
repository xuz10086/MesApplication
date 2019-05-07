package com.indusfo.spc.utils;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.indusfo.spc.R;

import java.util.Locale;

/**
 * 进度条
 *  
 * @author xuz
 * @date 2019/3/20 12:37 PM
 */
public class RoundCornerProgressDialog extends DialogFragment {
    private static final String TAG = "rustApp";
    public static final String F_TAG = "f_tag_ChangeUserProgressDialog";
    private int mDialogWid = -1;
    private int mDialogHeight = -1;
    int mPercent = 0; // 百分比
    private TextView mPercentTv;
    private RoundCornerImageView mProgressIv;
    private ImageView mBotIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_list_view_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPercentTv = view.findViewById(R.id.percentage);
        mProgressIv = view.findViewById(R.id.p_cover_iv);
        mBotIv = view.findViewById(R.id.p_bot_iv);
        mProgressIv.setRadiusDp(5);
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (null != window) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            if (mDialogWid < 0) {
                mDialogHeight = (int) (dm.heightPixels * 0.4);
                mDialogWid = (int) (dm.widthPixels * 0.86);
            }
            window.setLayout(mDialogWid, mDialogHeight);
        }
    } // 调整dialog的宽高

    public void updatePercent(int percent) {
        mPercent = percent;
        mPercentTv.setText(String.format(Locale.CHINA, "%2d%%", mPercent));
        float percentFloat = mPercent / 100.0f;
        final int ivWidth = mBotIv.getWidth();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mProgressIv.getLayoutParams();
        int marginEnd = (int) ((1 - percentFloat) * ivWidth);
        lp.width = ivWidth - marginEnd;
        mProgressIv.setLayoutParams(lp);
        mProgressIv.postInvalidate();
    }

}