package com.indusfo.spc.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.indusfo.spc.R;
import com.indusfo.spc.activity.ProductRight2Activity;
import com.indusfo.spc.bean.AspectIdAndValue;
import com.indusfo.spc.bean.DetePro;
import com.indusfo.spc.bean.ProductDeteAspectD;
import com.indusfo.spc.controller.BaseController;
import com.indusfo.spc.thread.ListThread;
import com.indusfo.spc.utils.StringUtils;
import com.roamer.slidelistview.SlideBaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AspectValue2Adapter extends SlideBaseAdapter {
    private Context context;
    private int resource;
    private List<ProductDeteAspectD> list;
    private BaseController productRightController;
    private List<AspectIdAndValue> aspectItems = new ArrayList<AspectIdAndValue>();
    private List<DetePro> deteProList;
    private ProductRight2Activity productRightActivity;

    public AspectValue2Adapter(ProductRight2Activity _activity, Context _context, BaseController _productRightController, int _resource, List<ProductDeteAspectD> _list, List<DetePro> deteProList) {
        super(_context);
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
            convertView = createConvertView(position);
            viewHolder = new ViewHolder();
//            viewHolder.spinnerArray = (Spinner) convertView.findViewById(R.id.spinner_arrays);
            viewHolder.rightCreateTime = (TextView) convertView.findViewById(R.id.right_create_time);
            viewHolder.deleteAspectItem = (TextView) convertView.findViewById(R.id.delete_aspect_item);
            viewHolder.aspectResult = (TextView) convertView.findViewById(R.id.aspect_result);
            viewHolder.tongGuo = (TextView) convertView.findViewById(R.id.tong_guo);
            viewHolder.buTongGuo = (TextView) convertView.findViewById(R.id.bu_tong_guo);
            viewHolder.aspectItem = (TextView) convertView.findViewById(R.id.aspect_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        switch (productDeteAspectD.getlResult()) {
            case 1:
                viewHolder.aspectResult.setText("通过");
                viewHolder.aspectResult.setTextColor(Color.GREEN);
                break;
            case 2:
                viewHolder.aspectResult.setText("不通过");
                viewHolder.aspectResult.setTextColor(Color.RED);
                break;
            case 3:
                viewHolder.aspectResult.setText("未检测");
                viewHolder.aspectResult.setTextColor(Color.GRAY);
                break;

        }

        long t = System.currentTimeMillis();
        String time = StringUtils.timeStamp2Date(t+"", "yyyy-MM-dd HH:mm:SS");
        productDeteAspectD.setdCreateTime(time);
        viewHolder.rightCreateTime.setText("创建时间："+time);

        String vcDeteName = productDeteAspectD.getVcDeteName();
        Integer deteProj = productDeteAspectD.getlDeteProj();
        if (null!=vcDeteName && !vcDeteName.isEmpty()) {
            viewHolder.aspectItem.setText(vcDeteName);
        }

        // 新增一行的同时，deteProList中减少一行，避免重复选择
        for (Iterator<DetePro> iterator=deteProList.iterator(); iterator.hasNext();) {
            DetePro detePro = iterator.next();
            if (detePro.getlDeteProj().equals(deteProj)) {
                iterator.remove();
            }
        }

        // 删除
        viewHolder.deleteAspectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 弹框
                AlertDialog alertDialog = new AlertDialog.Builder(productRightActivity)
                        .setTitle("删除外观检测值").setMessage("是否删除外观检测值").setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 删除后，deteProList新增一行删除的外观检测项目
                                Integer lDeteProj = productDeteAspectD.getlDeteProj();
                                String vcDeteName1 = productDeteAspectD.getVcDeteName();
                                DetePro detePro = new DetePro();
                                detePro.setlDeteProj(lDeteProj);
                                detePro.setVcDeteName(vcDeteName1);
                                deteProList.add(detePro);

                                list.remove(position);

                                AspectValue2Adapter.this.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                ProductRight2Activity.weatherSave = true;
            }
        });

        // 修改外观检测值
        viewHolder.tongGuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alertDialogInput(viewHolder, position);
                productDeteAspectD.setlResult(1);
                viewHolder.aspectResult.setText("通过");
                viewHolder.aspectResult.setTextColor(Color.GREEN);

                ProductRight2Activity.weatherSave = true;
            }
        });
        viewHolder.buTongGuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alertDialogInput(viewHolder, position);
                productDeteAspectD.setlResult(2);
                viewHolder.aspectResult.setText("不通过");
                viewHolder.aspectResult.setTextColor(Color.RED);
                viewHolder.aspectResult.setTextSize(16);
                ProductRight2Activity.weatherSave = true;
            }
        });

        return convertView;
    }

    private void alertDialogInput(final ViewHolder viewHolder, final int position) {
        View edit = LayoutInflater.from(context).inflate(R.layout.alertdialog_edit, null);
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
     * Spinner列表展现，点击事件，现在不使用Spinner，改为TextView
     *
     * @author xuz
     * @date 2019/3/1 1:02 PM
     * @param [viewHolder]
     * @return void
     */
  /*  private void SpinnerView(final Integer postionDete, Integer positionSpinner, final ViewHolder viewHolder) {

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
//                HashMap<String, List> hashMap = ListThread.get();
//                List<ProductDeteAspectD> deteAspectList = hashMap.get("deteAspectList");

                // 设置id
                viewHolder.spinnerArray.setId(lDeteProj);

                // 给新选择的检测项目设置id
                ProductDeteAspectD productDeteAspectD = list.get(postionDete);
                productDeteAspectD.setlDeteProj(lDeteProj);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
*/
    @Override
    public int getFrontViewId(int position) {
        return R.layout.activity_product_right2_list_view_item;
    }

    @Override
    public int getLeftBackViewId(int position) {
        return R.layout.activity_product_right2_list_view_item_left;
    }

    @Override
    public int getRightBackViewId(int position) {
        return R.layout.activity_product_right2_list_view_item_right;
    }

    class ViewHolder {
//        Spinner spinnerArray;
        TextView aspectItem,rightCreateTime,deleteAspectItem,aspectResult,tongGuo,buTongGuo;
    }
}
