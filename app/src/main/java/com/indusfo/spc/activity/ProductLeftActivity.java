package com.indusfo.spc.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.indusfo.spc.R;
import com.indusfo.spc.adapter.GridViewAdapter;
import com.indusfo.spc.bean.InstrumentD;
import com.indusfo.spc.bean.ProcessTargetD;
import com.indusfo.spc.bean.ProductDeteD;
import com.indusfo.spc.bean.ProductDeteValueD;
import com.indusfo.spc.bean.RResult;
import com.indusfo.spc.cons.AppParams;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.controller.ProductLeftController;
import com.indusfo.spc.dialog.CustomDialog;
import com.indusfo.spc.utils.ActivityUtils;
import com.indusfo.spc.utils.DialogUtils;
import com.indusfo.spc.utils.StringUtils;
import com.indusfo.spc.utils.TcpConnection;
import com.indusfo.spc.utils.TopBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProductLeftActivity extends BaseActivity {

    private GridView gridView;
    private Button leftButton,addDeteValue;
    private Button rightButton;
    private Integer tableRow = 1;
    private TableLayout tableLayout;
    private ScrollView scrollView;
    private TopBar topBar;
    private EditText deteValueText;
    private TextView leftHeight,leftCenter,leftBottom,leftUnit;

    // 检测结果集
    private List<String> valueList = new ArrayList<String>();
    private List<String> timeList = new ArrayList<String>();
    private String deteValues;
    private String timeValues;

    private Integer lDeteId;
    private String vcBatchCode;
    private Integer lProTarget;
    private Integer lEquipment;
    private String vcEquipment;
    private HashMap<String, String> hashMapForBatchEquipment;
    public static boolean weatherSave = false;

    TcpConnection tcpConnection;
    private String TcpComm;
    SharedPreferences dataSet;
    Timer timer;
    private long startTime = 0;

    private CustomDialog customDialog;
    private int countProgress = 0;

    private static Integer mounts = 1;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.QUERY_TARGET_RESULT:
                handleShowFirstList(msg);
                break;
            case IdiyMessage.QUERY_TARGET_BY_ID_RESULT:
                handleShowDeteValueList(msg);
                break;
            case IdiyMessage.SAVE_DETE_VALUE_RESULT:
                handleSaveDeteValue(msg);
                break;
            case IdiyMessage.GET_TCP_PARAMS_RESULT:
                handleGetTcpParams(msg);
                break;
            case IdiyMessage.GET_TCP_MSG_RESULT:
                handleTcpMsgResult(msg);
                break;
            case IdiyMessage.RIGHT_VIEW_SHOW_RESULT:
                handleToRight(msg);
                break;
            case IdiyMessage.REFRESH_DETE_VALUE_RESULT:
                handleRefreshDeteValue(msg);
                break;
            case IdiyMessage.SAVE_DETE_VALUE_AND_REFRESH_RESULT:
                handleSaveAndRefresh(msg);
                break;
            default:
                break;
        }
    }

//    保存并更新
    private void handleSaveAndRefresh(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        if ("200".equals(rResult.getCode())) {
            tip("保存成功");

            // 保存成功后，清空列表数据
            int childCount = tableLayout.getChildCount();
            for (int i=1; i<childCount; childCount--) {
                tableLayout.removeViewAt(i);
            }
            // 刷新检测值数据(不再刷新了)
//            mController.sendAsynMessage(IdiyMessage.REFRESH_DETE_VALUE, lProTarget, vcBatchCode);
        }
        if (!rResult.isOk() || !"200".equals(rResult.getCode())) {
            tip(rResult.getMsg());
        }
    }

    /**
     * 不同于其他保存操作跳转页面，这个会进行单独的检测值列表刷新
     *  这个不用重新连接天平仪器
     * @author xuz
     * @date 2019/3/23 3:24 PM
     * @param [msg]
     * @return void
     */
    private void handleRefreshDeteValue(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        ProcessTargetD processTargetD = null;
        deteValueText.setText("");
        if ("200".equals(rResult.getCode())) {
            String data = rResult.getData();
            ProductDeteD productDeteD = JSON.parseObject(data, ProductDeteD.class);
            if (productDeteD == null) {
                tip("检测单为空！");
                return;
            }
            // 用变量存放检测值id
            lDeteId = productDeteD.getlDeteId();
            // 存放每组样本数
            if (null != productDeteD.getlSwatch()) {
                mounts = productDeteD.getlSwatch();
            }

            List<ProcessTargetD> processTargetDList = productDeteD.getProcessTargetDList();
            List<ProductDeteValueD> productDeteValueDList = null;

            // 清空之前的检测值数据
            int count2 = tableLayout.getChildCount();
            for (int i=1; i<count2; count2--) {
                tableLayout.removeViewAt(i);

            }

            if (processTargetDList != null && processTargetDList.size() != 0) {
                // 第一个工艺目标
                processTargetD = processTargetDList.get(0);
                productDeteValueDList = processTargetD.getProductDeteValueList();
                // 用变量存储工艺目标id
                lProTarget = processTargetD.getlProTarget();

                // 设置点击下的颜色
                int count1 = gridView.getChildCount();
                for (int i=0; i<count1; i++) {
                    TextView textView = (TextView) gridView.getChildAt(i);
                    textView.setBackgroundColor(getResources().getColor(R.color.colorWeChat));
                    if (lProTarget.toString().equals(textView.getHint().toString())) {
                        textView.setBackgroundColor(Color.RED);
                    }
                }

                // 显示第一条数据，上下限值和中心值
                initUpperAndLower(processTargetD);


            } else {
                tip(rResult.getMsg());
            }

            if (productDeteValueDList != null && productDeteValueDList.size() != 0) {
                for (Iterator<ProductDeteValueD> iterator = productDeteValueDList.iterator(); iterator.hasNext(); ) {
                    ProductDeteValueD productDeteValueD = iterator.next();
                    createRow(productDeteValueD);
                }
            }
        }

        if(!rResult.isOk() || rResult.getData().isEmpty()) {
            tip(rResult.getMsg());
        }
    }


    private void handleToRight(Message msg) {
        // 展示右边的界面
        Intent intent = new Intent(ProductLeftActivity.this, ProductRight2Activity.class);
        intent.putExtra("vcBatchCode", vcBatchCode);
        intent.putExtra("lDeteId", lDeteId);
        intent.putExtra("lEquipment", lEquipment);
        intent.putExtra("vcEquipment", vcEquipment);
        intent.putExtra("lDeteId", lDeteId);
        ProductLeftActivity.this.startActivity(intent);

        finish();
    }

    /**
     * 获取TCP连接返回的数据
     *
     * @author xuz
     * @date 2019/3/8 12:03 PM
     * @param [msg]
     * @return void
     */
    private void handleTcpMsgResult(Message msg) {
        if (msg.obj != null) {
            // 获取数据
            String data = msg.obj.toString();
            // 给检测值文本框设置数据（纯数字）
            data = StringUtils.submitDoubleFromStr(data);
            deteValueText.setText(data);
        }

    }

    /**
     * 获取Tcp连接参数
     *
     * @author xuz
     * @date 2019/3/8 10:56 AM
     * @param [msg]
     * @return void
     */
    private void handleGetTcpParams(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        if ("200".equals(rResult.getCode())) {
            String data = rResult.getData();
            InstrumentD instrumentD = JSON.parseObject(data, InstrumentD.class);
            TcpComm = instrumentD.getVcComm();
            String[] tcpComms = TcpComm.split(":");
            String ip = tcpComms[0];

            Integer port = null;
            try {
                port = Integer.valueOf(tcpComms[1]);
            } catch (NumberFormatException e) {
                ActivityUtils.showDialog(ProductLeftActivity.this, "连接失败", "仪器连接异常，端口格式错误");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 弹框,并建立Socket连接
            if (null != instrumentD.getlInsTypeName()) {
                customDialog.setStr(instrumentD.getlInsTypeName()+"连接中");
            }

            if (null==ip) {
                tip("仪器的IP设置为空");
                return;
            }
            if (null==port) {
                tip("仪器的端口号设置为空");
                return;
            }
            showMyDialog();

            tcpConnection = new TcpConnection(mHandler, ip, port, IdiyMessage.GET_TCP_MSG_RESULT);
            new Thread(tcpConnection).start();
        }
        if (!rResult.isOk() || !"200".equals(rResult.getCode())) {
            tip(rResult.getMsg());
        }
    }

    /**
     * 实时发送指令到测试仪器
     *
     * @author xuz
     * @date 2019/3/8 4:31 PM
     * @param []
     * @return void
     */
    private void sendSocketRequest() {
        timer = new Timer();
        if (null == tcpConnection.revHandler) {
            ActivityUtils.showDialog(ProductLeftActivity.this, "连接失败", "仪器已被占用，或者ip端口号错误");
            return;
        }
        // 实时请求TCP连接，获取仪器数据
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 提交数据
                Message message = new Message();
                message.what = IdiyMessage.SET_TCP_MSG;
                message.obj = AppParams.JJ_CODE;
                tcpConnection.revHandler.sendMessage(message);
            }
        };
        timer.schedule(task, 0, 1500);
    }

    /**
     * 保存检测值结果
     *
     * @author xuz
     * @date 2019/2/28 10:24 AM
     * @param [msg]
     * @return void
     */
    private void handleSaveDeteValue(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        if ("200".equals(rResult.getCode())) {
            tip("保存成功");

        }
        if (!rResult.isOk() || !"200".equals(rResult.getCode())) {
            tip(rResult.getMsg());
        }
    }

    /**
     * 获取检测条件值，生成表格展示
     *
     * @author xuz
     * @date 2019/2/27 3:00 PM
     * @param [msg]
     * @return void
     */
    private void handleShowDeteValueList(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        ProcessTargetD processTargetD = null;
        deteValueText.setText("");
        if ("200".equals(rResult.getCode())) {
            String data = rResult.getData();
            ProductDeteD productDeteD = JSON.parseObject(data, ProductDeteD.class);
            if (productDeteD == null) {
                tip("检测单为空！");
                return;
            }
            // 用变量存放检测值id(现在的lDeteId直接由主界面选择条目后传过来)
//            lDeteId = productDeteD.getlDeteId();
            // 存放每组样本数
            if (null!=productDeteD.getlSwatch()) {
                mounts = productDeteD.getlSwatch();
            }

            List<ProcessTargetD> processTargetDList = productDeteD.getProcessTargetDList();
            List<ProductDeteValueD> productDeteValueDList = null;

            // 清空之前的检测值数据
            int count2 = tableLayout.getChildCount();
            for (int i=1; i<count2; count2--) {
                tableLayout.removeViewAt(i);

            }

            if (processTargetDList != null && processTargetDList.size() != 0) {
                // 第一个工艺目标
                processTargetD = processTargetDList.get(0);
                productDeteValueDList = processTargetD.getProductDeteValueList();
                // 用变量存储工艺目标id
                lProTarget = processTargetD.getlProTarget();

                // 设置点击下的颜色
                int count1 = gridView.getChildCount();
                for (int i=0; i<count1; i++) {
                    TextView textView = (TextView) gridView.getChildAt(i);
                    textView.setBackgroundColor(getResources().getColor(R.color.colorWeChat));
                    if (lProTarget.toString().equals(textView.getHint().toString())) {
                        textView.setBackgroundColor(Color.RED);
                    }
                }

                // 显示第一条数据，上下限值和中心值
                initUpperAndLower(processTargetD);


            } else {
                tip(rResult.getMsg());
            }

            // 展示历史数据，现在不需要了
            /*if (productDeteValueDList != null && productDeteValueDList.size() != 0) {
                for (Iterator<ProductDeteValueD> iterator = productDeteValueDList.iterator(); iterator.hasNext(); ) {
                    ProductDeteValueD productDeteValueD = iterator.next();
                    createRow(productDeteValueD);
                }
            }*/
        }

        // 关闭定时任务
        if (timer != null) {
            timer.cancel();
        }
        // 关闭Tcp连接
        if (tcpConnection != null) {
            tcpConnection.close();
        }


        // 如果是天平仪器，发送网络请求，得到这台机器对应天平的TCP连接参数
        if (null != processTargetD && processTargetD.getlInsType() == 3 && lEquipment != -1) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lEquipment", lEquipment);
            jsonObject.put("vcEquipment", vcEquipment);
            jsonObject.put("lInsType", processTargetD.getlInsType());
            jsonObject.put("lInsTypeName", processTargetD.getlInsTypeName());
            mController.sendAsynMessage(IdiyMessage.GET_TCP_PARAMS, jsonObject);
        }

        if(!rResult.isOk() || rResult.getData().isEmpty()) {
            tip(rResult.getMsg());
        }
    }

    /**
     * 获取工艺目标表中的检测项目，横向滑动展示
     *
     * @author xuz
     * @date 2019/2/27 9:22 AM
     * @param [msg]
     * @return void
     */
    private void handleShowFirstList(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        ProcessTargetD processTargetD = null;
        if ("200".equals(rResult.getCode())) {
            String data = rResult.getData();
            ProductDeteD productDeteD = JSON.parseObject(data, ProductDeteD.class);
            if (productDeteD == null) {
                tip("检测单为空！");
                return;
            }
            // 用变量存放检测值id
//            lDeteId = productDeteD.getlDeteId();
            // 存放每组样本数
            if (null!=productDeteD.getlSwatch()) {
                mounts = productDeteD.getlSwatch();
            }

            List<ProcessTargetD> processTargetDList = productDeteD.getProcessTargetDList();

            List<ProductDeteValueD> productDeteValueDList = null;

            if (processTargetDList != null && processTargetDList.size() != 0) {
                // 第一个工艺目标
                processTargetD = processTargetDList.get(0);
                productDeteValueDList = processTargetD.getProductDeteValueList();
                lProTarget = processTargetD.getlProTarget();

                // 设置GridView参数，绑定数据
                setGridView(processTargetDList);

                // 显示第一条数据，上下限值和中心值
                initUpperAndLower(processTargetD);


            } else {
                tip("该检测单还未添加检测项目！");
            }

            // 展示历史数据，现在不需要了
            /*if (productDeteValueDList != null && productDeteValueDList.size() != 0) {
                for (Iterator<ProductDeteValueD> iterator = productDeteValueDList.iterator(); iterator.hasNext(); ) {
                    ProductDeteValueD productDeteValueD = iterator.next();
                    createRow(productDeteValueD);
                }
            }*/

        }

        // 如果是天平仪器，发送网络请求，得到这台机器对应天平的TCP连接参数
        if (null != processTargetD && processTargetD.getlInsType() == 3 && lEquipment != -1) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lEquipment", lEquipment);
            jsonObject.put("vcEquipment", vcEquipment);
            jsonObject.put("lInsType", processTargetD.getlInsType());
            jsonObject.put("lInsTypeName", processTargetD.getlInsTypeName());
            mController.sendAsynMessage(IdiyMessage.GET_TCP_PARAMS, jsonObject);
        }
        if(!rResult.isOk() || rResult.getData().isEmpty()) {
            tip(rResult.getMsg());
        }
    }

    /**
     * 初始化上下限
     *
     * @author xuz
     * @date 2019/3/6 1:17 PM
     * @param [processTargetD]
     * @return void
     */
    private void initUpperAndLower(ProcessTargetD processTargetD) {
        if (processTargetD.getlUnitNo() != null) {
            leftUnit.setText("中心值("+processTargetD.getlUnitNo()+"):");
        } else {
            leftUnit.setText("");
        }
        if (processTargetD.getVcUpperLimit() != null) {
            leftHeight.setText(processTargetD.getVcUpperLimit());
        } else {
            leftHeight.setText("");
        }
        if (processTargetD.getVcCenter() != null) {
            leftCenter.setText(processTargetD.getVcCenter());
        } else {
            leftCenter.setText("");
        }
        if (processTargetD.getVcLowerLimit() != null) {
            leftBottom.setText(processTargetD.getVcLowerLimit());
        } else {
            leftBottom.setText("");
        }
    }

    /**
     * 设置GridView参数，绑定数据
     *
     * @author xuz
     * @date 2019/2/27 9:51 AM
     * @param [deteList]
     * @return void
     */
    private void setGridView(List<ProcessTargetD> processTargetDList) {

        int size = processTargetDList.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(5); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数

        GridViewAdapter adapter = new GridViewAdapter(this, R.layout.activity_product_left_grid_item, mController, processTargetDList, hashMapForBatchEquipment, mounts);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_left);
        initController();
        initUI();
    }

    private void initController() {
        mController = new ProductLeftController(this);
        mController.setIModeChangeListener(this);
    }

    private void initUI() {
        gridView = findViewById(R.id.grid);
        leftButton = findViewById(R.id.left_button_1);
        rightButton = findViewById(R.id.right_button_1);
        tableLayout = findViewById(R.id.scanningTable);
        scrollView = findViewById(R.id.scrollView);
        topBar = findViewById(R.id.product_left_activity_topbar);
        addDeteValue = findViewById(R.id.add_dete_value);
        deteValueText = findViewById(R.id.dete_value_text);
        leftHeight = findViewById(R.id.left_height);
        leftCenter = findViewById(R.id.left_center);
        leftBottom = findViewById(R.id.left_bottom);
        leftUnit = findViewById(R.id.left_unit);

        dataSet = getSharedPreferences("dataSet", 0);
        customDialog = new CustomDialog(this);

        Intent intent = getIntent();
        vcBatchCode = intent.getStringExtra("vcBatchCode");
        lEquipment = intent.getIntExtra("lEquipment", -1);
        vcEquipment = intent.getStringExtra("vcEquipment");
        lDeteId = intent.getIntExtra("lDeteId", -1);

        // 给hashMap设置值
        hashMapForBatchEquipment = new HashMap<String, String>();
        hashMapForBatchEquipment.put("vcBatchCode", vcBatchCode);
        hashMapForBatchEquipment.put("lEquipment", lEquipment+"");
        hashMapForBatchEquipment.put("vcEquipment", vcEquipment);
        hashMapForBatchEquipment.put("lDeteId", lDeteId+"");

        timer = new Timer();

        if (null!=lDeteId) {
            // 发送网络请求，查询检测项目列表
            mController.sendAsynMessage(IdiyMessage.QUERY_TARGET, lDeteId+"");
        } else {
            tip("不存在该检测单");
        }

        topBar.setThrirdButtonVisibility(false);
        topBar.setOnLeftAndRightClickListener(new TopBar.OnLeftAndRightClickListener() {
            @Override
            public void OnLeftButtonClick() {
                if (weatherSave) {
                    getTimeAndValueData();
                    int d = deteValues.split(",").length;
                    if (d % mounts != 0) {
                        // 如果检测数目不对，走这个弹框
                        DialogUtils dialogUtils = new DialogUtils() {
                            @Override
                            public void leftButtonMethod() {
                                finish();
                            }

                            @Override
                            public void rightButtonMethod() {
                                return;
                            }
                        };
                        dialogUtils.showDialog(ProductLeftActivity.this, "检测数目不符合", "当前每组样本数少于" + mounts + "个，是否放弃当前检测值！", "继续检测", "放弃");
                    } else {
                        // 检测数目对走这个弹出框，是否保存检测值
                        alertDialog(lProTarget, lDeteId, timeValues, deteValues, true);
                    }
                } else {
                    finish();
                }

            }

            /**
             * 保存检测值
             *
             * @author xuz
             * @date 2019/3/4 9:34 AM
             * @param []
             * @return void
             */
            @Override
            public void OnRightButtonClick() {

                // 如果检测的数目不是每组样本数，则不能保存
                getTimeAndValueData();

                int d = deteValues.split(",").length;
                if (d % mounts != 0) {
                    ActivityUtils.showDialog(ProductLeftActivity.this, "检测数目不符合", "当前每组样本数少于" + mounts + "个，是否放弃当前检测值！");
                    return;
                }

                if (lDeteId == null || lProTarget == null) {
                    tip("不存在检测单或检测项目");
                    return;
                }
                // 保存并更新
                mController.sendAsynMessage(IdiyMessage.SAVE_DETE_VALUE_AND_REFRESH, lProTarget, lDeteId, timeValues, deteValues);

                // 将ID通过SharedPreferences传递给Adapter
                weatherSave = false;
//                getTimeAndValueData();
                SharedPreferences.Editor editor = dataSet.edit();
                editor.putInt("lProTarget", lProTarget);
                editor.putInt("lDeteId", lDeteId);
//                editor.putBoolean("weatherSave", false);
                editor.putString("timeValues", timeValues);
                editor.putString("deteValues", deteValues);
                editor.commit();
            }

            @Override
            public void OnThridButtonClick() {

            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weatherSave) {
                    // 如果检测的数目不是每组样本数，则不能保存
                    getTimeAndValueData();
                    int d = deteValues.split(",").length;
                    if (d % mounts != 0) {
                        // 如果检测数目不对，走这个弹框
                        DialogUtils dialogUtils = new DialogUtils() {
                            @Override
                            public void leftButtonMethod() {
                                weatherSave = false;
                                Message msg = new Message();
                                msg.what = IdiyMessage.RIGHT_VIEW_SHOW_RESULT;
                                mHandler.sendMessage(msg);
                            }

                            @Override
                            public void rightButtonMethod() {
                                return;
                            }
                        };
                        dialogUtils.showDialog(ProductLeftActivity.this, "检测数目不符合", "当前每组样本数少于" + mounts + "个，是否放弃当前检测值！", "继续检测", "放弃");
                    } else {
                        // 检测数目正确弹出，是否保存检测值
                        AlertDialog alertDialog = new AlertDialog.Builder(ProductLeftActivity.this)
                                .setTitle("保存").setMessage("是否保存当前检测项目检测值").setIcon(R.mipmap.ic_launcher)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (lDeteId == 0) {
                                            Toast.makeText(ProductLeftActivity.this, "检测单不存在", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        // 如果检测的数目不是每组样本数，则不能保存
                                        getTimeAndValueData();
//                                        int d = deteValues.split(",").length;
//                                        if (d % mounts != 0) {
//                                            ActivityUtils.showDialog(ProductLeftActivity.this, "检测数目不符合", "每组样本数应为：*"+mounts+"，保存失败！");
//                                            return;
//                                        }
                                        weatherSave = false;
                                        mController.sendAsynMessage(IdiyMessage.RIGHT_VIEW_SHOW, lProTarget, lDeteId, timeValues, deteValues);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        weatherSave = false;
                                        Message msg = new Message();
                                        msg.what = IdiyMessage.RIGHT_VIEW_SHOW_RESULT;
                                        mHandler.sendMessage(msg);
                                    }
                                }).create();
                        alertDialog.show();
                    }
                } else {
                    Message msg = new Message();
                    msg.what = IdiyMessage.RIGHT_VIEW_SHOW_RESULT;
                    mHandler.sendMessage(msg);
                }

                // 测试界面
//                ActivityUtils.start(ProductLeftActivity.this, TestActivity.class, true);
            }
        });

        addDeteValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deteValueStr = deteValueText.getText().toString();
                if (deteValueStr.isEmpty()) {
                    tip("检测值不能为空");
                }
                if (lDeteId != null && lProTarget != null) {
                    if (!StringUtils.isInteger(deteValueStr) && !StringUtils.isDouble(deteValueStr)) {
                        tip("检测值输入格式错误：" + deteValueStr);
                        return;
                    }
                    // 去除掉回车，空格
                    addRow(StringUtils.replaceBlank(deteValueStr));
                    // 新增数据后，设为空
                    deteValueText.setText("");
                } else {
                    ActivityUtils.showDialog(ProductLeftActivity.this, "检测值添加错误", "不存在检测单或检测项目，添加失败");
                    deteValueText.setText("");
                }

            }
        });

        deteValueText.addTextChangedListener(textWatcher);

    }

    protected void onStop() {
        super.onStop();
        Log.e("onStop", ".........................................");

        // 关闭定时任务
        if (timer != null) {
            timer.cancel();
        }

        // 关闭Tcp连接
        if (tcpConnection != null) {
            tcpConnection.close();
        }
    }

    /**
     * 获取列表的时间和值集合，封装为请求参数
     *
     * @author xuz
     * @date 2019/3/5 5:12 PM
     * @param []
     * @return void
     */
    private void getTimeAndValueData() {
        // 清空集合
        timeList.clear();
        valueList.clear();
        int count = 0;
        int childCount = tableLayout.getChildCount();
        long admitTime = System.currentTimeMillis();
        for (int i=1; i<childCount; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            TextView textView1 = (TextView) row.getChildAt(1);
            TextView textView2 = (TextView) row.getChildAt(2);
            // 获取提交时间，作为新增数据的检测时间
            if(row.getId()== -1) {
                count++;
                int cm = (count-1)/mounts;
                // 在保存时超过了每组样本数，应该已另一个时间点保存
                timeList.add(StringUtils.timeStamp2Date((admitTime+cm*6000)+"", "yyyy-MM-dd HH:mm:ss"));
            } else {
                timeList.add((String) textView1.getText());
            }


            /*long time = Long.valueOf(StringUtils.date2TimeStamp(textView1.getText().toString(), "yyyy-MM-dd HH:mm:ss"));
            if (admitTime - time < 100000) {
                timeList.add(StringUtils.timeStamp2Date(admitTime+"", "yyyy-MM-dd HH:mm:ss"));
            } else {
                timeList.add((String) textView1.getText());
            }*/

            valueList.add((String) textView2.getText());
        }
        if (null == timeList || null == valueList || timeList.size() == 0
                || valueList.size() == 0) {
//            tip("没有检测值，无法保存数据");
            deteValues = "";
            timeValues = "";
            return;
        }
        deteValues = ActivityUtils.listToString(valueList, ',');
        timeValues = ActivityUtils.listToString(timeList, ',');
    }

    // 检测，新增行
    private void addRow(String deteValueStr) {

        LayoutInflater inflater = LayoutInflater.from(ProductLeftActivity.this);
        final TextView tv1 = inflater.inflate(R.layout.product_left_textview, null).findViewById(R.id.textview3);
        final TextView tv2 = inflater.inflate(R.layout.product_left_textview, null).findViewById(R.id.textview3);
        final TextView tv3 = inflater.inflate(R.layout.product_left_textview, null).findViewById(R.id.textview3);
        tv1.setTextSize(20);
        tv2.setTextSize(20);
        tv3.setTextSize(20);

        // 获取行
        final TableRow row = getTableRow();

        // 为行这只长按事件
        row.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    // 按下，变色
                    case MotionEvent.ACTION_DOWN:
                        tv1.setBackgroundColor(getResources().getColor(R.color.tableItemColor));
                        tv2.setBackgroundColor(getResources().getColor(R.color.tableItemColor));
                        tv3.setBackgroundColor(getResources().getColor(R.color.tableItemColor));
                        break;
                    // 抬起还原
                    case MotionEvent.ACTION_UP:
                        tv1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        tv2.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        tv3.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        break;
                    // 移出边界
                    case MotionEvent.ACTION_CANCEL:
                        tv1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        tv2.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        tv3.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        // 序号
        tv1.setText((tableLayout.getChildCount()) +"");
        row.addView(tv1);

        // 获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        tv2.setText(simpleDateFormat.format(date));
        row.addView(tv2);

        // 填入检测值
        if (deteValueStr != null) {
            tv3.setText(deteValueStr);
            Double deteDouble = StringUtils.convertToDouble(deteValueStr, 0.0);
            String heightStr = leftHeight.getText().toString();
            Double heightDouble = 0.0;
            String bottomStr = leftBottom.getText().toString();
            Double bottomDouble = 0.0;
            if (!heightStr.isEmpty()) {
                heightDouble = StringUtils.convertToDouble(heightStr, 0.0);
            }
            if (!bottomStr.isEmpty()) {
                bottomDouble = StringUtils.convertToDouble(bottomStr, 0.0);
            }
            // 测试值不在范围内，显示红色
            if (deteDouble > heightDouble) {
                tv3.setTextColor(getResources().getColor(R.color.leftHeightColor));
            } else if (deteDouble < bottomDouble) {
                tv3.setTextColor(getResources().getColor(R.color.leftBottomColor));
            }
        }
        row.addView(tv3);

        tableLayout.addView(row, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        // 始终在最底部
        scrollBottom(scrollView, tableLayout);

        // 将ID通过SharedPreferences传递给Adapter
        getTimeAndValueData();
        weatherSave = true;
        SharedPreferences.Editor editor = dataSet.edit();
        editor.putInt("lProTarget", lProTarget);
        editor.putInt("lDeteId", lDeteId);
//        editor.putBoolean("weatherSave", weatherSave);
        editor.putString("timeValues", timeValues);
        editor.putString("deteValues", deteValues);
        editor.commit();
//        weatherSave = false;

        // 长按之后可以删除
        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 弹框
                AlertDialog alertDialog = new AlertDialog.Builder(ProductLeftActivity.this)
                        .setTitle("删除检测值").setMessage("是否删除该行检测值").setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tableLayout.removeView(row);

                                // 重新排序
                                int count = tableLayout.getChildCount();
                                for (int j=1; j<count; j++) {
                                    TableRow child = (TableRow) tableLayout.getChildAt(j);
                                    TextView text = (TextView) child.getChildAt(0);
                                    text.setText(j+"");
                                }

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                return false;
            }
        });

    }

    /**
     * 查询，创建行
     *
     * @author xuz
     * @date 2019/1/19 10:19 AM
     * @param [scanning]
     * @return void
     */
    private void createRow(ProductDeteValueD deteVCond) {

        try {
            Integer lScanningId = deteVCond.getlDeteValueId();


            LayoutInflater inflater = LayoutInflater.from(ProductLeftActivity.this);
            TextView tv1 = inflater.inflate(R.layout.product_left_textview, null).findViewById(R.id.textview3);
            TextView tv2 = inflater.inflate(R.layout.product_left_textview, null).findViewById(R.id.textview3);
            TextView tv3 = inflater.inflate(R.layout.product_left_textview, null).findViewById(R.id.textview3);
            tv1.setTextSize(20);
            tv2.setTextSize(20);
            tv3.setTextSize(20);
            tv1.setBackgroundColor(getResources().getColor(R.color.tableItemColor));
            tv2.setBackgroundColor(getResources().getColor(R.color.tableItemColor));
            tv3.setBackgroundColor(getResources().getColor(R.color.tableItemColor));

            // 获取行
            final TableRow row = getTableRow();

            String dCreateTime = deteVCond.getdCreateTime();
            String vcValue = deteVCond.getVcValue();
            Integer lDeteValueId = deteVCond.getlDeteValueId();

            // 序号
            tv1.setText(tableLayout.getChildCount()+"");
            row.addView(tv1);
            if (null != lScanningId) {
                row.setId(lScanningId);
            }

            if (dCreateTime != null) {
                tv2.setText(dCreateTime);
            }
            row.addView(tv2);

            if (vcValue != null) {
                tv3.setText(vcValue);
                Double deteDouble = StringUtils.convertToDouble(vcValue, 0.0);
                String heightStr = leftHeight.getText().toString();
                Double heightDouble = 0.0;
                String bottomStr = leftBottom.getText().toString();
                Double bottomDouble = 0.0;
                if (!heightStr.isEmpty()) {
                    heightDouble = StringUtils.convertToDouble(heightStr, 0.0);
                }
                if (!bottomStr.isEmpty()) {
                    bottomDouble = StringUtils.convertToDouble(bottomStr, 0.0);
                }
                // 测试值不在范围内，显示红色
                if (deteDouble > heightDouble) {
                    tv3.setTextColor(getResources().getColor(R.color.leftHeightColor));
                } else if (deteDouble < bottomDouble) {
                    tv3.setTextColor(getResources().getColor(R.color.leftBottomColor));
                }
            }
            row.addView(tv3);

           /* if (lDeteValueId != null) {
                row.setId(lDeteValueId);
            }*/

            tableLayout.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

            // 始终在最底部
            scrollBottom(scrollView, tableLayout);

            // 长按之后可以删除
            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Integer rowId = row.getId();
                    if (null != rowId) {
                        return false;
                    }
                    // 弹框
                    AlertDialog alertDialog = new AlertDialog.Builder(ProductLeftActivity.this)
                            .setTitle("删除检测值").setMessage("是否删除该行检测值").setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tableLayout.removeView(row);
                                    weatherSave = true;
                                    // 将ID通过SharedPreferences传递给Adapter
                                    getTimeAndValueData();
                                    SharedPreferences.Editor editor = dataSet.edit();
                                    editor.putInt("lProTarget", lProTarget);
                                    editor.putInt("lDeteId", lDeteId);
//                                    editor.putBoolean("weatherSave", weatherSave);
                                    editor.putString("timeValues", timeValues);
                                    editor.putString("deteValues", deteValues);
                                    editor.commit();
//                                    weatherSave = false;
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).create();
                    alertDialog.show();
                    return false;
                }
            });


        } catch (NullPointerException e) {
            Log.w("NullPointException:", "..");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (weatherSave) {
            getTimeAndValueData();
            int d = deteValues.split(",").length;
            if (d % mounts != 0) {
                // 如果检测数目不对，走这个弹框
                DialogUtils dialogUtils = new DialogUtils() {
                    @Override
                    public void leftButtonMethod() {
                        finish();
                    }

                    @Override
                    public void rightButtonMethod() {
                        return;
                    }
                };
                dialogUtils.showDialog(ProductLeftActivity.this, "检测数目不符合", "当前每组样本数少于" + mounts + "个，是否放弃当前检测值！", "继续检测", "放弃");
            } else {
                // 弹出，是否保存检测值
                alertDialog(lProTarget, lDeteId, timeValues, deteValues, true);
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置行格式
     *
     * @author xuz
     * @date 2019/3/1 4:15 PM
     * @param []
     * @return android.widget.TableRow
     */
    @NonNull
    private TableRow getTableRow() {
        // 创建行
        final TableRow row = new TableRow(ProductLeftActivity.this);
        row.setGravity(Gravity.CENTER);
        row.setBackgroundColor(Color.WHITE);
        row.setPadding( 1, 1, 1, 1);
        return row;
    }

    /**
     * 定位到最底部
     *
     * @author xuz
     * @date 2019/1/21 3:48 PM
     * @param [scrollView, inner]
     * @return void
     */
    private void scrollBottom(final ScrollView scrollView, final View inner) {
        Handler handler = new Handler();
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (scrollView == null || inner == null) {
                    return;
                }
                // 内层高度超过外层
                int offset = inner.getMeasuredHeight()
                        - scrollView.getMeasuredHeight();
                if (offset < 0) {
                    offset = 0;
                }
                scrollView.scrollTo(0, offset);
            }
        });
    }

    // 文本内容变化监听
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        }

        // 监听文本变化，如果有换行符则新增一行数据
        @Override
        public void afterTextChanged(Editable editable) {
            String deteValueStr = deteValueText.getText().toString();
            boolean isIn = StringUtils.isInteger(deteValueStr);
            boolean isDou = StringUtils.isDouble(deteValueStr);
            if (deteValueStr.endsWith("\n")) {
//                try {
//                    // 实际过程中，数值可能分为两部分，睡一段时间再获取
//                    Thread.sleep(800);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                String str = deteValueText.getText().toString();
                String resultStr = StringUtils.replaceBlank(deteValueStr);
                // 将中文输入法的句号换为点
                resultStr = resultStr.replace('。', '.');
                if (lDeteId != null || lProTarget != null) {
                    if (!StringUtils.isInteger(resultStr) && !StringUtils.isDouble(resultStr)) {
                        tip("检测值输入格式错误：" + deteValueStr);
                        return;
                    }
                    // 新增数据后，设为空
                    deteValueText.setText("");
                    addRow(resultStr);
                } else {
                    ActivityUtils.showDialog(ProductLeftActivity.this, "检测值添加错误", "不存在检测单或检测项目，添加失败");
                    deteValueText.setText("");
                }
            }

        }
    };

    /**
     * 展示自定义的进度条弹出框
     * 进度条满了后，进行Socket连接
     *
     * @author xuz
     * @date 2019/3/9 1:28 PM
     * @param []
     * @return void
     */
    private void showMyDialog() {
        customDialog.show();
        final Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                countProgress += 25;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (customDialog != null && customDialog.isShowing()) {
                            customDialog.setProgress(countProgress);
                        }
                    }
                });
                if (countProgress >= 100) {
                    timer2.cancel();
                }
            }
        }, 0, 500);
        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (timer2 != null) timer2.cancel();
                countProgress = 0;
                // 实时发送指令到测试仪器
                sendSocketRequest();
            }
        });

    }

    /**
     * 弹框，提示是否保存
     *
     * @author xuz
     * @date 2019/3/5 4:34 PM
     * @param [lProTarget, lDeteId, parent]
     * @return void
     */
    private void alertDialog(final int lProTarget, final int lDeteId, final String timeValues, final String deteValues, final boolean flag) {

        AlertDialog alertDialog = new AlertDialog.Builder(ProductLeftActivity.this)
                .setTitle("保存").setMessage("是否保存当前检测项目检测值").setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (lDeteId == -1) {
                            Toast.makeText(ProductLeftActivity.this, "检测单不存在", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // 如果检测的数目不是每组样本数，则不能保存
                        getTimeAndValueData();
                        int d = deteValues.split(",").length;
                        if (d % mounts != 0 && !flag) {
                            ActivityUtils.showDialog(ProductLeftActivity.this, "检测数目不符合", "当前每组样本数少于" + mounts + "个，是否放弃当前检测值！");
                            return;
                        }

                        mController.sendAsynMessage(IdiyMessage.SAVE_DETE_VALUE, lProTarget, lDeteId, timeValues, deteValues);
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

}
