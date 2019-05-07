package com.indusfo.spc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.indusfo.spc.R;
import com.indusfo.spc.activity.MainActivity;
import com.indusfo.spc.activity.ProductLeftActivity;
import com.indusfo.spc.bean.Batch;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.controller.MainController;
import com.indusfo.spc.utils.ActivityUtils;
import com.indusfo.spc.utils.DialogUtils;
import com.indusfo.spc.utils.RoundCornerProgressDialog;
import com.indusfo.spc.utils.StringUtils;

import java.util.List;
import java.util.Locale;

public class BatchAdapter extends ArrayAdapter<Batch> {

    private Context context;
    private List<Batch> batchList;
    private int resourceId;
    private MainActivity mainActivity;
    private MainController mainController;

    private long start = 0;
    private long end = 0;

    public BatchAdapter(MainActivity activity, Context context, int resource, List<Batch> objects) {
        super(context, resource, objects);
        this.context = context;
        batchList = objects;
        resourceId = resource;
        this.mainActivity = activity;
        mainController = new MainController(context);
        mainController.setIModeChangeListener(activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Batch batch = getItem(position);
        // 内部类
        ViewHolder viewHolder;

        /* getView()方法中的converView参数表示之前加载的布局 */
        if (convertView == null) {
            /* 如果converView参数值为null，则使用LayoutInflater去加载布局 */
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.theFirst = (TextView) convertView.findViewById(R.id.the_first);
//            viewHolder.theSecond = (TextView) convertView.findViewById(R.id.the_second);
//            viewHolder.theThrid = (TextView) convertView.findViewById(R.id.the_thrid);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
            viewHolder.person = (TextView) convertView.findViewById(R.id.person);
            viewHolder.batchCode = (TextView) convertView.findViewById(R.id.batch_code);
            viewHolder.ok = convertView.findViewById(R.id.pass_ok);
            viewHolder.exception = convertView.findViewById(R.id.pass_exception);
            viewHolder.pBotIv = convertView.findViewById(R.id.p_bot_iv);
            viewHolder.pCoverIv = convertView.findViewById(R.id.p_cover_iv);
            viewHolder.percentage = convertView.findViewById(R.id.percentage);
            convertView.setTag(viewHolder);
        } else {
            /* 否则，直接对converView进行重用 */
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.theFirst.setText("机台号:" + batch.getVcEquipment() + "      " + batch.getVcModel());
//        viewHolder.theSecond.setText("批号:" + batch.getVcBatchCode() + "  状态:" + batch.getlBatchStateName());
//        viewHolder.theThrid.setText("操作工:" + batch.getVcUserName() + "  生产完工比例:" + batch.getProportion());
        viewHolder.status.setText(batch.getlBatchStateName());
        viewHolder.batchCode.setText(batch.getVcBatchCode());
        viewHolder.person.setText(batch.getVcUserName());
        String proportion = "";
        if (null != batch.getProportion() && !batch.getProportion().isEmpty()) {
            proportion = batch.getProportion();
            String[] list = proportion.split("\\.");
            proportion = list[0];
        }
        final int percent = StringUtils.convertToInt(proportion, -1);


       /* final RoundCornerProgressDialog mRoundCornerProgressDialog = new RoundCornerProgressDialog();
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRoundCornerProgressDialog.updatePercent(percent);
            }
        });*/
       updatePercent(percent, viewHolder.percentage, viewHolder.pBotIv, viewHolder.pCoverIv);


        final LinearLayout listViewItem = convertView.findViewById(R.id.main_item_line);

        // 事件监听
//        viewHolder.taskLinear = (LinearLayout) convertView.findViewById(R.id.task_linear);
        viewHolder.theFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDeteByVcEquipment(batch);
            }
        });
/*        viewHolder.theSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDeteByVcEquipment(batch);
            }
        });
        viewHolder.theThrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDeteByVcEquipment(batch);
            }
        });*/

        viewHolder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils dialogUtils = new DialogUtils() {
                    @Override
                    public void leftButtonMethod() {

                    }

                    @Override
                    public void rightButtonMethod() {
                        mainController.sendAsynMessage(IdiyMessage.SET_DETE_STATUE, batch.getVcBatchCode(), "1");
                    }
                };
                dialogUtils.showDialog(context, "产品检测单判定", "是否保存判定结果", "确定", "取消");

            }
        });

        viewHolder.exception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils dialogUtils = new DialogUtils() {
                    @Override
                    public void leftButtonMethod() {

                    }

                    @Override
                    public void rightButtonMethod() {
                        mainController.sendAsynMessage(IdiyMessage.SET_DETE_STATUE, batch.getVcBatchCode(), "2");
                    }
                };
                dialogUtils.showDialog(context, "产品检测单判定", "是否保存判定结果", "确定", "取消");

            }
        });

               /*listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDeteByVcEquipment(batch);
            }
        });*/

        /*listViewItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                *//*switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        start = System.currentTimeMillis();
                        Log.e("onTouch", "按下。。。。。。。。。。。。。。。。。。。。。");
                        return true;
                    case MotionEvent.ACTION_UP:
                        end = System.currentTimeMillis();
                        Log.e("onTouch", "抬起。。。。。。。。。。。。。。。。。。。。。");
                        if (end - start < 1000) {
                            Intent intent = new Intent(context, ProductLeftActivity.class);
                            // 传输机台id，机台号，批次编码
                            intent.putExtra("lEquipment", batch.getlEquipment());
                            intent.putExtra("vcEquipment", batch.getVcEquipment());
                            intent.putExtra("vcBatchCode", batch.getVcBatchCode());
                            context.startActivity(intent);
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("onScroll", "滑动。。。。。。。。。。。。。。。。。。。。");
                        listViewItem.setFocusable(false);
                        listViewItem.setFocusableInTouchMode(false);
                        listViewItem.clearFocus();

                        listViewItem2.setFocusable(true);
                        listViewItem2.setFocusableInTouchMode(true);
                        boolean flag2 = listViewItem2.isActivated();
                        boolean flag = listViewItem.isActivated();
                        listViewItem2.setActivated(true);
                        Log.e("isActivited", flag + "....................");
                        Log.e("isActivited2:", flag2 + ".................");
                        return false;
                    default:
                        break;
                }
                return true;*//*
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    start = System.currentTimeMillis();
                    Log.e("onTouch", "按下。。。。。。。。。。。。。。。。。。。。。");

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    end = System.currentTimeMillis();
                    Log.e("onTouch", "抬起。。。。。。。。。。。。。。。。。。。。。");
                    if (end - start < 1000) {
                        Intent intent = new Intent(context, ProductLeftActivity.class);
                        // 传输机台id，机台号，批次编码
                        intent.putExtra("lEquipment", batch.getlEquipment());
                        intent.putExtra("vcEquipment", batch.getVcEquipment());
                        intent.putExtra("vcBatchCode", batch.getVcBatchCode());
                        context.startActivity(intent);
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.e("onScroll", "滑动。。。。。。。。。。。。。。。。。。。。");
                    listViewItem.setFocusable(false);
                    listViewItem.setFocusableInTouchMode(false);
                    listViewItem.clearFocus();

                    return false;
                }
                return true;
            }
        });*/

        return convertView;
    }

    /**
     * 点击条目后页面跳转
     *
     * @author xuz
     * @date 2019/3/18 7:08 PM
     * @param [batch]
     * @return void
     */
    private void queryDeteByVcEquipment(Batch batch) {
        Intent intent = new Intent(context, ProductLeftActivity.class);
        // 传输机台id，机台号，批次编码
        intent.putExtra("lEquipment", batch.getlEquipment());
        intent.putExtra("vcEquipment", batch.getVcEquipment());
        intent.putExtra("vcBatchCode", batch.getVcBatchCode());
        context.startActivity(intent);
    }

    /**
     * 设置进度条长度
     *
     * @author xuz
     * @date 2019/3/20 11:22 AM
     * @param [percent, mPercentTv, mBotIv, mProgressIv]
     * @return void
     */
    public void updatePercent(int percent, TextView mPercentTv, ImageView mBotIv, ImageView mProgressIv) {
        mPercentTv.setText(String.format(Locale.CHINA, "%2d%%", percent));
        // 将百分数的值变为小数
        float percentFloat = percent / 100.0f;
        // 获取底部长度
        final int ivWidth = 600;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mProgressIv.getLayoutParams();
        // 计算出进度条的结束位置
        int marginEnd = (int) ((1 - percentFloat) * ivWidth);
        // 计算出进度条的长度
        lp.width = ivWidth - marginEnd;
        // 设置进度条
        mProgressIv.setLayoutParams(lp);
        mProgressIv.postInvalidate();
    }


    class ViewHolder {
        TextView theFirst;
//        TextView theSecond;
//        TextView theThrid;
        TextView status;
        TextView batchCode;
        TextView person;
        TextView ok;
        TextView exception;

        ImageView pBotIv;
        ImageView pCoverIv;
        TextView percentage;
    }
}
