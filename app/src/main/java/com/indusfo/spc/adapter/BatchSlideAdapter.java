package com.indusfo.spc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.indusfo.spc.utils.DialogUtils;
import com.indusfo.spc.utils.StringUtils;
import com.roamer.slidelistview.SlideBaseAdapter;
import com.roamer.slidelistview.SlideListView;

import java.util.List;
import java.util.Locale;

public class BatchSlideAdapter extends SlideBaseAdapter {

    private Context context;
    private List<Batch> batchList;
    private int resourceId;
    private MainActivity mainActivity;
    private MainController mainController;

    private long start = 0;
    private long end = 0;

    public BatchSlideAdapter(MainActivity activity, Context context, int resource, List<Batch> objects) {
        super(context);
        this.context = context;
        batchList = objects;
        resourceId = resource;
        this.mainActivity = activity;
        mainController = new MainController(context);
        mainController.setIModeChangeListener(activity);
    }

    @Override
    public int getCount() {
        return batchList!=null?batchList.size():0;
    }

    @Override
    public Batch getItem(int position) {
        return batchList!=null?batchList.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Batch batch = getItem(position);
        // 内部类
        BatchSlideAdapter.ViewHolder viewHolder;

        /* getView()方法中的converView参数表示之前加载的布局 */
        if (convertView == null) {
            /* 如果converView参数值为null，则使用LayoutInflater去加载布局 */
            convertView = createConvertView(position);
            viewHolder = new ViewHolder();

            viewHolder.theFirst = (TextView) convertView.findViewById(R.id.the_first);
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
            viewHolder = (BatchSlideAdapter.ViewHolder) convertView.getTag();
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

        updatePercent(percent, viewHolder.percentage, viewHolder.pBotIv, viewHolder.pCoverIv);


        final LinearLayout listViewItem = convertView.findViewById(R.id.main_item_line);

        // 事件监听
//        viewHolder.taskLinear = (LinearLayout) convertView.findViewById(R.id.task_linear);
        /*viewHolder.theFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDeteByVcEquipment(batch);
            }
        });*/
        // 点击进入
        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDeteByVcEquipment(batch);
            }
        });

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

                        // 界面删除这项
                        batchList.remove(position);
                        notifyDataSetChanged();
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
                        // 界面删除这项
                        batchList.remove(position);
                        notifyDataSetChanged();
                    }
                };
                dialogUtils.showDialog(context, "产品检测单判定", "是否保存判定结果", "确定", "取消");

            }
        });
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

    @Override
    public int getFrontViewId(int position) {
        return R.layout.activity_main_list_view_item;
    }

    @Override
    public int getLeftBackViewId(int position) {
        /*if (position % 2 == 0) {
            return R.layout.activity_main_list_view_item_right;
        }*/
        return R.layout.activity_main_list_view_item_left;
    }

    @Override
    public int getRightBackViewId(int position) {
        return R.layout.activity_main_list_view_item_right;
    }

}
