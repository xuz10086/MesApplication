package com.indusfo.spc.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.indusfo.spc.R;
import com.indusfo.spc.activity.LoginActivity;
import com.indusfo.spc.activity.ProductRightActivity;
import com.indusfo.spc.bean.AspectIdAndValue;
import com.indusfo.spc.bean.DetePro;
import com.indusfo.spc.bean.ProductDeteAspectD;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.controller.BaseController;
import com.indusfo.spc.thread.ListThread;
import com.indusfo.spc.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AspectValueAdapter extends ArrayAdapter<ProductDeteAspectD> {
    private Context context;
    private int resource;
    private List<ProductDeteAspectD> list;
    private BaseController productRightController;
    private List<AspectIdAndValue> aspectItems = new ArrayList<AspectIdAndValue>();
    private List<DetePro> deteProList;
    private ProductRightActivity productRightActivity;

    public AspectValueAdapter(ProductRightActivity _activity, Context _context, BaseController _productRightController, int _resource, List<ProductDeteAspectD> _list, List<DetePro> deteProList) {
        super(_context, _resource, _list);
        this.list = _list;
        this.context = _context;
        this.resource = _resource;
        this.productRightController = _productRightController;
        this.deteProList = deteProList;
        this.productRightActivity = _activity;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public ProductDeteAspectD getItem(int position) {
        return list!=null?list.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 清空数组，不然列表会重复项
        aspectItems.clear();
        final ProductDeteAspectD productDeteAspectD = getItem(position);
        // 内部类
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.spinnerArray = convertView.findViewById(R.id.spinner_arrays);
            viewHolder.sum = convertView.findViewById(R.id.sum);
            viewHolder.sumValue = convertView.findViewById(R.id.sum_value);
            viewHolder.disqualification = convertView.findViewById(R.id.disqualification);
            viewHolder.disqualificationValue = convertView.findViewById(R.id.disqualification_value);
            viewHolder.closeAdapterButton = convertView.findViewById(R.id.close_adapter_button);
            viewHolder.editAdapterButton = convertView.findViewById(R.id.edit_adapter_button);
            viewHolder.rightCreateTime = convertView.findViewById(R.id.right_create_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 设置检测值
        if (productDeteAspectD != null && productDeteAspectD.getlSwatch() != null) {
            viewHolder.sumValue.setText(productDeteAspectD.getlSwatch().toString());
        } else {
            viewHolder.sumValue.setText("");
        }
        if (productDeteAspectD != null && productDeteAspectD.getVcValue() != null) {
            viewHolder.disqualificationValue.setText(productDeteAspectD.getVcValue());
        } else {
            viewHolder.disqualificationValue.setText("");
        }
        if (productDeteAspectD != null && productDeteAspectD.getdCreateTime() != null) {
            viewHolder.rightCreateTime.setText("创建时间：" + productDeteAspectD.getdCreateTime());
        }

        // 当前这条数据的Spinner所在条目位置
        Integer positionSpinner = 0;
        for (int i=0; i<deteProList.size(); i++) {
            DetePro detePro = deteProList.get(i);
            AspectIdAndValue aspectIdAndValue = new AspectIdAndValue(detePro.getlDeteProj(), detePro.getVcDeteName());
            aspectItems.add(aspectIdAndValue);
            Integer integer1 = productDeteAspectD.getlDeteProj();
            Integer integer2 = detePro.getlDeteProj();

            // 当id相等时，取列表此时的位置，没id时，默认为第一行
            if (null != integer1 && integer1.equals(integer2)) {
                positionSpinner = i;
            }
        }

        SpinnerView(position, positionSpinner, viewHolder);

        // 删除
        viewHolder.closeAdapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 弹框
                AlertDialog alertDialog = new AlertDialog.Builder(productRightActivity)
                        .setTitle("删除外观检测值").setMessage("是否删除外观检测值").setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                list.remove(position);
                                AspectValueAdapter.this.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                ProductRightActivity.weatherSave = true;
            }
        });

        // 修改外观检测值
        viewHolder.editAdapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                inputConsole(position, viewHolder);

                alertDialogInput(viewHolder, position);

                ProductRightActivity.weatherSave = true;
            }
        });

        return convertView;
    }

    private void alertDialogInput(final ViewHolder viewHolder, final int position) {
        View edit = LayoutInflater.from(getContext()).inflate(R.layout.alertdialog_edit, null);
        final EditText edit1 = edit.findViewById(R.id.edit_sum);
        final EditText edit2 = edit.findViewById(R.id.edit_err);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("外观检测值输入");
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String input1 = edit1.getText().toString();
                String input2 = edit2.getText().toString();

                if (input1.isEmpty() || input2.isEmpty()) {
                    Toast.makeText(context, "请保证输入信息完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!StringUtils.isInteger(input1.toString()) || !StringUtils.isInteger(input2.toString())) {
                    Toast.makeText(context, "输入值格式错误：不为整数", Toast.LENGTH_SHORT).show();
                    return;
                }

                viewHolder.sumValue.setText(input1);
                viewHolder.disqualificationValue.setText(input2);

                // 将修改的数据保存到list中
                HashMap<String,List> hashMap = ListThread.get();
                List<ProductDeteAspectD> deteAspectList = hashMap.get("deteAspectList");
                ProductDeteAspectD productDeteAspectD = deteAspectList.get(position);
                productDeteAspectD.setlSwatch(Integer.valueOf(input1));
                productDeteAspectD.setVcValue(input2);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    /**
     * Spinner列表展现，点击事件
     *
     * @author xuz
     * @date 2019/3/1 1:02 PM
     * @param [viewHolder]
     * @return void
     */
    private void SpinnerView(final Integer postionDete, Integer positionSpinner, final ViewHolder viewHolder) {

        ArrayAdapter<AspectIdAndValue> adapter =
                new ArrayAdapter<AspectIdAndValue>(context, android.R.layout.simple_spinner_item, aspectItems);
        // 设置下拉列表风格
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // 将adapter添加到spinner中
        viewHolder.spinnerArray.setAdapter(adapter);
        // 设置默认值
        viewHolder.spinnerArray.setVisibility(View.VISIBLE);

        // 给Spinner设置值，和Id
        viewHolder.spinnerArray.setSelection(positionSpinner,true);
        viewHolder.spinnerArray.setId(aspectItems.get(positionSpinner).getId());

        // 选项选择监听
        viewHolder.spinnerArray.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position2, long id) {
                // 获取当前选择的检测项目id和name
                int lDeteProj = deteProList.get(position2).getlDeteProj();
                String vcDeteName = deteProList.get(position2).getVcDeteName();

                // 选项不能重复
                HashMap<String, List> hashMap = ListThread.get();
                List<ProductDeteAspectD> deteAspectList = hashMap.get("deteAspectList");

                // 设置id
                viewHolder.spinnerArray.setId(lDeteProj);

                // 给新选择的检测项目设置id
                ProductDeteAspectD productDeteAspectD = deteAspectList.get(postionDete);
                productDeteAspectD.setlDeteProj(lDeteProj);
                productDeteAspectD.setVcDeteName(vcDeteName);
                deteAspectList.set(postionDete, productDeteAspectD);
                hashMap.put("deteAspectList", deteAspectList);

//                Toast.makeText(context, lDeteProj+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    class ViewHolder {
        Spinner spinnerArray;
        TextView sum;
        TextView sumValue;
        TextView disqualification;
        TextView disqualificationValue;
        TextView editAdapterButton;
        TextView closeAdapterButton;
        TextView rightCreateTime;
    }
}
