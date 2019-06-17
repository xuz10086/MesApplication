package com.indusfo.spc.activity;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dou361.dialogui.DialogUIUtils;
import com.google.gson.Gson;
import com.indusfo.spc.R;
import com.indusfo.spc.adapter.AspectValue2Adapter;
import com.indusfo.spc.adapter.AspectValueAdapter;
import com.indusfo.spc.bean.AspectIdAndValue;
import com.indusfo.spc.bean.DetePro;
import com.indusfo.spc.bean.ProductDeteAspectD;
import com.indusfo.spc.bean.RResult;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.controller.ProductRightController;
import com.indusfo.spc.thread.ListThread;
import com.indusfo.spc.utils.ActivityUtils;
import com.indusfo.spc.utils.AnimUtil;
import com.indusfo.spc.utils.StringUtils;
import com.indusfo.spc.utils.TopBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ProductRight2Activity extends BaseActivity implements View.OnClickListener {

    private TopBar topBar;
    private TextView menuSave,menuNew;
    private Spinner spinner;
    private ListView listView;
    private Button leftButton2;

    private List<DetePro> deteProList;
    private List<ProductDeteAspectD> deteAspectList;
    private String vcBatchCode;
    private Integer lDeteId;

    private float bgAlpha = 1f;
    private boolean bright = false;
    public static boolean weatherSave = false;

    private static final long DURATION = 500;
    private static final float START_ALPHA = 0.7f;
    private static final float END_ALPHA = 1f;

    private PopupWindow mPopupWindow;
    private AnimUtil animUtil;
    private Integer lEquipment;
    private String vcEquipment;

    //    private List<String> aspectItems = new ArrayList<String>();
    private AspectValue2Adapter aspectValueAdapter;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.QUERY_ASPECT_ITEM_RESULT:
                handleQueryAspect(msg);
                break;
            case IdiyMessage.QUERY_ASPECT_VALUE_RESULT:
                handleQueryAspectValue(msg);
                break;
            case IdiyMessage.SAVE_PRODUCT_ASPECT_DETE_VALUE_RESULT:
                handleSaveProductAspectDeteValue(msg);
                break;
            case IdiyMessage.LEFT_SHOW_VIEW_RESULT:
                handleShowLeftView(msg);
                break;
            default:
                break;
        }
    }

    private void handleShowLeftView(Message msg) {
        RResult rResult = (RResult) msg.obj;

        if (null != msg.obj) {
            if ("200".equals(rResult.getCode())) {
                weatherSave = false;
            }
            if (!rResult.isOk() || !"200".equals(rResult.getCode())) {
                tip(rResult.getMsg());
            }
        }
        Intent intent = new Intent(ProductRight2Activity.this, ProductLeftActivity.class);
        intent.putExtra("vcBatchCode", vcBatchCode);
        intent.putExtra("lEquipment", lEquipment);
        intent.putExtra("vcEquipment", vcEquipment);
        intent.putExtra("lDeteId", lDeteId);
        ProductRight2Activity.this.startActivity(intent);
        finish();
    }

    /**
     * 外观检测值保存结果
     *
     * @author xuz
     * @date 2019/3/5 5:54 PM
     * @param [msg]
     * @return void
     */
    private void handleSaveProductAspectDeteValue(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        if ("200".equals(rResult.getCode())) {
            tip("保存成功");
            weatherSave = false;

//            // 清空数据
//            deteAspectList.clear();
//            aspectValueAdapter.notifyDataSetChanged();
        }

        if (!rResult.isOk() || !"200".equals(rResult.getCode())) {
            tip(rResult.getMsg());
        }
    }

    /**
     * 获取外观检测值
     *
     * @author xuz
     * @date 2019/2/28 5:34 PM
     * @param [msg]
     * @return void
     */
    private void handleQueryAspectValue(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        if ("200".equals(rResult.getCode())) {
            List<ProductDeteAspectD> deteAspectList2 = JSON.parseArray(rResult.getData(), ProductDeteAspectD.class);
//            HashMap<String, List> hashMap = new HashMap<String, List>();
//
//            // 将数据保存到本地线程中
//            hashMap.put("deteAspectList", deteAspectList);
//            hashMap.put("deteProList", deteProList);
//            ListThread.set(hashMap);

            // 获取到历史数据，并替换自动生成的deteAspectList中相同的检测项目
            if (null != deteAspectList2) {
                out:
                for (ProductDeteAspectD productDeteAspectD2 : deteAspectList2) {
                    Integer lDeteProj2 = productDeteAspectD2.getlDeteProj();
                    inner:
                    for (Iterator<ProductDeteAspectD> iterator=deteAspectList.iterator(); iterator.hasNext();) {
                        ProductDeteAspectD productDeteAspectD = iterator.next();
                        Integer lDeteProj = productDeteAspectD.getlDeteProj();
                        if (lDeteProj2.equals(lDeteProj)) {
                            iterator.remove();
                            deteAspectList.add(productDeteAspectD2);
                            break inner;
                        }
                    }
                }
            }

            aspectValueAdapter = new AspectValue2Adapter(this,this, mController, R.layout.activity_product_right_list_view_item, deteAspectList, deteProList);
            listView.setAdapter(aspectValueAdapter);
            aspectValueAdapter.notifyDataSetChanged();
        }

        if (!rResult.isOk() || !"200".equals(rResult.getCode())) {

            aspectValueAdapter = new AspectValue2Adapter(this,this, mController, R.layout.activity_product_right_list_view_item, deteAspectList, deteProList);
            listView.setAdapter(aspectValueAdapter);
            aspectValueAdapter.notifyDataSetChanged();
            tip(rResult.getMsg());
        }
    }

    /**
     * 获取外观检测项目
     *
     * @author xuz
     * @date 2019/2/28 5:34 PM
     * @param [msg]
     * @return void
     */
    private void handleQueryAspect(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        if ("200".equals(rResult.getCode())) {
            deteProList = JSON.parseArray(rResult.getData(), DetePro.class);
            if (null!=deteProList) {
                for (DetePro detePro : deteProList) {
                    Integer lDeteProj = detePro.getlDeteProj();
                    ProductDeteAspectD productDeteAspectD = new ProductDeteAspectD();
                    productDeteAspectD.setlDeteProj(lDeteProj);
                    productDeteAspectD.setVcRemark("App检测生成");
                    productDeteAspectD.setlDataState(1);
                    productDeteAspectD.setlDeteId(lDeteId);
                    productDeteAspectD.setVcDeteName(detePro.getVcDeteName());
                    // 默认为未检测状态
                    productDeteAspectD.setlResult(3);
                    deteAspectList.add(productDeteAspectD);
                }
            }

//            aspectValueAdapter = new AspectValue2Adapter(this,this, mController,
//                    R.layout.activity_product_right2_list_view_item, deteAspectList, deteProList);
//            listView.setAdapter(aspectValueAdapter);
//            aspectValueAdapter.notifyDataSetChanged();

            // 发送网络请求，查询外观检测值
            mController.sendAsynMessage(IdiyMessage.QUERY_ASPECT_VALUE, lDeteId);
        }
        if (!rResult.isOk() || !"200".equals(rResult.getCode())) {
            tip(rResult.getMsg());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 实现透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        setContentView(R.layout.activity_product_right2);

        initController();
        initUI();

    }

    private void initController() {
        mController = new ProductRightController(this);
        mController.setIModeChangeListener(this);
    }

    private void initUI() {
        mPopupWindow = new PopupWindow(this);
        animUtil = new AnimUtil();
        DialogUIUtils.init(this);

        topBar = findViewById(R.id.product_right_activity_topbar);
        final LayoutInflater inflater = LayoutInflater.from(ProductRight2Activity.this);
        spinner = inflater.inflate(R.layout.activity_product_right2_list_view_item, null).findViewById(R.id.spinner_arrays);
        listView = findViewById(R.id.aspect_value_list_view2);
        leftButton2 = findViewById(R.id.left_button_2);

        // 初始化线程，防止空指针异常
        HashMap<String, List> hashMap = new HashMap<String, List>();
        deteAspectList = new ArrayList<ProductDeteAspectD>();
        deteProList = new ArrayList<DetePro>();
        hashMap.put("deteProList", deteProList);
        hashMap.put("deteAspectList", deteAspectList);
        ListThread.set(hashMap);

        Intent intent = getIntent();
        vcBatchCode = intent.getStringExtra("vcBatchCode");
        lEquipment = intent.getIntExtra("lEquipment", -1);
        vcEquipment = intent.getStringExtra("vcEquipment");
        lDeteId = intent.getIntExtra("lDeteId", -1);

        topBar.setOnLeftAndRightClickListener(new TopBar.OnLeftAndRightClickListener() {
            @Override
            public void OnLeftButtonClick() {
                if (weatherSave) {
                    alertDialog(lDeteId, IdiyMessage.SAVE_PRODUCT_ASPECT_DETE_VALUE, true);
                } else {
                    finish();
                }

            }

            @Override
            public void OnRightButtonClick() {
//                showPop();
//                toggleBright(); 至此，后面的很多代码都没用了

                // 这里没有再用菜单按钮了
                saveAspectValue(IdiyMessage.SAVE_PRODUCT_ASPECT_DETE_VALUE);

            }

            @Override
            public void OnThridButtonClick() {
                // 新增一行外观检测值数据
                newProductDeteAspect();
            }
        });

        // 点击后进入质量采集页面
        leftButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weatherSave) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ProductRight2Activity.this)
                            .setTitle("保存").setMessage("是否保存当前检测项目检测值").setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (lDeteId == -1) {
                                        Toast.makeText(ProductRight2Activity.this, "检测单不存在", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    saveAspectValue(IdiyMessage.LEFT_SHOW_VIEW);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    weatherSave = false;
                                    Message msg = new Message();
                                    msg.what = IdiyMessage.LEFT_SHOW_VIEW_RESULT;
                                    mHandler.sendMessage(msg);
                                }
                            }).create();
                    alertDialog.show();
                } else {
                    Message msg = new Message();
                    msg.what = IdiyMessage.LEFT_SHOW_VIEW_RESULT;
                    mHandler.sendMessage(msg);
                }
            }
        });

        if (null == lDeteId || lDeteId == -1) {
            tip("没有相关检测单");
            return;
        }
        // 发送网络请求，查询外观检测项目
        mController.sendAsynMessage(IdiyMessage.QUERY_ASPECT_ITEM, lDeteId+"");

    }

    /**
     * 保存外观检测值
     *
     * @author xuz
     * @date 2019/3/13 6:33 PM
     * @param []
     * @return void
     */
    private void saveAspectValue(int action) {
        HashMap<String, List> hashMap = ListThread.get();
//        List<ProductDeteAspectD> deteAspectList = hashMap.get("deteAspectList");
//        List<Integer> ids = new ArrayList<Integer>();
        for (Iterator<ProductDeteAspectD> iterator = deteAspectList.iterator(); iterator.hasNext();) {
            ProductDeteAspectD productDeteAspectD = iterator.next();
            Integer deteProjId = productDeteAspectD.getlDeteProj();
            Integer lDeteId = productDeteAspectD.getlDeteId();

            if (null == lDeteId || lDeteId == -1) {
                ActivityUtils.showDialog(ProductRight2Activity.this, "保存错误", "没有相关联的检测单");
                return;
            }

//            for (Iterator<Integer> iterator1=ids.iterator(); iterator1.hasNext();) {
//                Integer id = iterator1.next();
//                if (id == deteProjId) {
//                    ActivityUtils.showDialog(ProductRight2Activity.this, "检测项目错误", "保存有重复的检测项目");
//                    return;
//                }
//            }
//            ids.add(deteProjId);
        }
        if (null == deteAspectList || deteAspectList.size() == 0) {
            ProductDeteAspectD pd = new ProductDeteAspectD();
            pd.setlDeteId(lDeteId);
            deteAspectList.add(pd);
        }
        Gson g = new Gson();
        String jsonString = g.toJson(deteAspectList);
        mController.sendAsynMessage(action, jsonString);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_save:
                mPopupWindow.dismiss();
                // 保存外观检测值
                saveAspectValue(IdiyMessage.SAVE_PRODUCT_ASPECT_DETE_VALUE);
                break;
            case R.id.menu_new:
                mPopupWindow.dismiss();
                // 新增一行外观检测值数据
                newProductDeteAspect();
                break;
            default:
                break;
        }
    }

    /**
     * 新增一行外观检测值数据
     *
     * @author xuz
     * @date 2019/3/4 1:07 PM
     * @param []
     * @return void
     */
    private void newProductDeteAspect() {

        View edit = LayoutInflater.from(ProductRight2Activity.this).inflate(R.layout.alertdialog_edit2, null);
//        final EditText edit1 = edit.findViewById(R.id.edit_sum);
//        final EditText edit2 = edit.findViewById(R.id.edit_err);
        final Spinner spinner2 = edit.findViewById(R.id.spinner_arrays2);
        List<AspectIdAndValue> aspectIdAndValueList = new ArrayList<>();
        for (DetePro detePro : deteProList) {
            AspectIdAndValue aspectIdAndValue = new AspectIdAndValue(detePro.getlDeteProj(), detePro.getVcDeteName());
            aspectIdAndValueList.add(aspectIdAndValue);

        }
        ArrayAdapter<AspectIdAndValue> adapter =
                new ArrayAdapter<AspectIdAndValue>(this, R.layout.spinner_item, aspectIdAndValueList);
        spinner2.setAdapter(adapter);
        // 设置默认值
        spinner2.setVisibility(View.VISIBLE);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                AspectIdAndValue item = (AspectIdAndValue) spinner2.getSelectedItem();
                spinner2.setId(item.getId());
                spinner2.setTag(item.getValue());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(ProductRight2Activity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("外观检测项目选择");
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                weatherSave = true;

                // 获取系统当前时间
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());


                ProductDeteAspectD productDeteAspectD = new ProductDeteAspectD();
                productDeteAspectD.setlDeteId(lDeteId);
                productDeteAspectD.setlResult(3);
                productDeteAspectD.setlDeteProj(spinner2.getId());
                productDeteAspectD.setdCreateTime(simpleDateFormat.format(date));
                productDeteAspectD.setVcDeteName((String) spinner2.getTag());

                deteAspectList.add(productDeteAspectD);
                aspectValueAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }

    private void showPop() {
        // 设置布局文件
        mPopupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.pop_add, null));
        // 为了避免部分机型不显示，我们需要重新设置一下宽高
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        mPopupWindow.setAnimationStyle(R.style.pop_add);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        mPopupWindow.setOutsideTouchable(true);
        // 相对于 + 号正下面，同时可以设置偏移量
        mPopupWindow.showAsDropDown(topBar, -100, 0);
        // 设置pop关闭监听，用于改变背景透明度
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });

        menuSave = mPopupWindow.getContentView().findViewById(R.id.menu_save);
        menuNew = mPopupWindow.getContentView().findViewById(R.id.menu_new);

        menuSave.setOnClickListener(this);
        menuNew.setOnClickListener(this);

    }

    private void toggleBright() {
        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
        animUtil.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                // 此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        animUtil.addEndListner(new AnimUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                // 在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        animUtil.startAnimator();
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
        // everything behind this window will be dimmed.
        // 此方法用来设置浮动层，防止部分手机变暗无效
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 弹框，提示是否保存
     *
     * @author xuz
     * @date 2019/3/5 4:34 PM
     * @param [lProTarget, lDeteId, parent]
     * @return void
     */
    private void alertDialog(final int lDeteId, final int action, final boolean flag) {
        AlertDialog alertDialog = new AlertDialog.Builder(ProductRight2Activity.this)
                .setTitle("保存").setMessage("是否保存当前检测项目检测值").setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (lDeteId == -1) {
                            Toast.makeText(ProductRight2Activity.this, "检测单不存在", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        saveAspectValue(action);
                        if (flag) {
                            finish();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (flag) {
                            finish();
                        }
                    }
                }).create();
        alertDialog.show();
        weatherSave = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (weatherSave) {
            alertDialog(lDeteId, IdiyMessage.SAVE_PRODUCT_ASPECT_DETE_VALUE, true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
