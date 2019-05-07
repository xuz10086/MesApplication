package com.indusfo.spc.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.indusfo.spc.R;
import com.indusfo.spc.activity.ProductLeftActivity;
import com.indusfo.spc.bean.ProcessTargetD;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.controller.BaseController;

import java.util.HashMap;
import java.util.List;

public class GridViewAdapter extends ArrayAdapter<ProcessTargetD> {
    private Context context;
    private int resource;
    private List<ProcessTargetD> list;
    private BaseController productLeftController;
    private String vcBatchCode;
    private String lEquipment;
    private String vcEquipment;
    private int mounts;

    // 根据id来限制不能重复点击
    private long exitTime = 0;
    private int id = -1;

    public GridViewAdapter(Context _context, int _resource, BaseController _productLeftController, List<ProcessTargetD> _list, HashMap<String, String> hashMap, int _mounts) {
        super(_context, _resource, _list);
        this.list = _list;
        this.context = _context;
        this.resource = _resource;
        this.productLeftController = _productLeftController;
        this.vcBatchCode = hashMap.get("vcBatchCode");
        this.lEquipment = hashMap.get("lEquipment");
        this.vcEquipment = hashMap.get("vcEquipment");
        this.mounts = _mounts;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public ProcessTargetD getItem(int position) {
        return list!=null?list.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ProcessTargetD processTargetD = getItem(position);
        // 内部类
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.product_left_dete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(processTargetD.getVcDeteName());
        viewHolder.textView.setHint(processTargetD.getlProTarget().toString());
        // 给第一个设置为黄色
        if (position == 0) {
            viewHolder.textView.setBackgroundColor(Color.RED);
//            id = processTargetD.getlProTarget();
        }

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* long currentTime = System.currentTimeMillis();
                if (currentTime - exitTime < 2000) {
                    return;
                }
                exitTime = currentTime;*/
               if (id == processTargetD.getlProTarget()) {
                   return;
               }
                // 拿到ID
                SharedPreferences dataSet = context.getSharedPreferences("dataSet", 0);
                boolean weatherSave = ProductLeftActivity.weatherSave;
                if (weatherSave) {
                    final int lProTarget = dataSet.getInt("lProTarget", 0);
                    final int lDeteId = dataSet.getInt("lDeteId", 0);
                    final String timeValues = dataSet.getString("timeValues", "");
                    final String deteValues = dataSet.getString("deteValues", "");
                    // 弹框
                    int d = deteValues.split(",").length;
                    if (d % mounts != 0) {
                        alertDialog2(processTargetD);
                    } else {
                        alertDialog(lProTarget, lDeteId, parent, timeValues, deteValues, processTargetD);

                        /*if (id != processTargetD.getlProTarget()) {
                            id = processTargetD.getlProTarget();
                            // 展示其他检测项目的值
                            productLeftController.sendAsynMessage(IdiyMessage.QUERY_TARGET_BY_ID, processTargetD.getlProTarget(), vcBatchCode);
                        }
                        ProductLeftActivity.weatherSave = false;*/
                    }
                } else {
//                    if (id != processTargetD.getlProTarget()) {
                        id = processTargetD.getlProTarget();
                        // 展示其他检测项目的值
                        productLeftController.sendAsynMessage(IdiyMessage.QUERY_TARGET_BY_ID, processTargetD.getlProTarget(), vcBatchCode);
                        ProductLeftActivity.weatherSave = false;
//                    }

                }
            }
        });
        return convertView;
    }

    /**
     * 弹框，提示是否保存
     *
     * @author xuz
     * @date 2019/3/5 4:34 PM
     * @param [lProTarget, lDeteId, parent]
     * @return void
     */
    private void alertDialog(final int lProTarget, final int lDeteId,
                             final ViewGroup parent, final String timeValues, final String deteValues, final ProcessTargetD processTargetD) {

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("保存").setMessage("是否保存当前检测项目检测值").setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (lDeteId == -1) {
                            Toast.makeText(context, "检测单不存在", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // 如果检测的数目不是每组样本数，则不能保存
//                        int d = deteValues.split(",").length;
//                        if (d % mounts != 0) {
//                            ActivityUtils.showDialog(context, "检测数目不符合", "每组样本数应为：*"+mounts+"，保存失败！");
//                            return;
//                        }
                        productLeftController.sendAsynMessage(IdiyMessage.SAVE_DETE_VALUE, lProTarget, lDeteId, timeValues, deteValues);
//                        if (id != processTargetD.getlProTarget()) {
                            id = processTargetD.getlProTarget();
                            // 展示其他检测项目的值
                            productLeftController.sendAsynMessage(IdiyMessage.QUERY_TARGET_BY_ID, processTargetD.getlProTarget(), vcBatchCode);
//                        }
                        ProductLeftActivity.weatherSave = false;
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (id != processTargetD.getlProTarget()) {
                            id = processTargetD.getlProTarget();
                            // 展示其他检测项目的值
                            productLeftController.sendAsynMessage(IdiyMessage.QUERY_TARGET_BY_ID, processTargetD.getlProTarget(), vcBatchCode);
//                        }
                        ProductLeftActivity.weatherSave = false;
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * 弹框，提示是否保存
     *
     * @author xuz
     * @date 2019/3/5 4:34 PM
     * @param [lProTarget, lDeteId, parent]
     * @return void
     */
    private void alertDialog2(final ProcessTargetD processTargetD) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("检测数目不符合").setMessage("当前每组样本数少于" + mounts + "个，是否放弃当前检测值！").setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("继续检测", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       return;
                    }
                })
                .setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (id != processTargetD.getlProTarget()) {
                            id = processTargetD.getlProTarget();
                            // 展示其他检测项目的值
                            productLeftController.sendAsynMessage(IdiyMessage.QUERY_TARGET_BY_ID, processTargetD.getlProTarget(), vcBatchCode);
//                        }
                        ProductLeftActivity.weatherSave = false;
                    }
                }).create();
        alertDialog.show();
    }

    class ViewHolder {
        TextView textView;
    }
}
