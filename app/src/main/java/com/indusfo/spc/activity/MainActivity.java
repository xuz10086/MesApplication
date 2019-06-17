package com.indusfo.spc.activity;

import android.os.Message;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.indusfo.spc.R;
import com.indusfo.spc.adapter.BatchSlideAdapter;
import com.indusfo.spc.bean.Batch;
import com.indusfo.spc.bean.Pro;
import com.indusfo.spc.bean.RResult;
import com.indusfo.spc.bean.RolePro;
import com.indusfo.spc.bean.SpinnerIdAndValue;
import com.indusfo.spc.cons.AppParams;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.controller.MainController;
import com.indusfo.spc.utils.TopBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 批次界面
 *  
 * @author xuz
 * @date 2019/2/26 12:31 PM
 */
public class MainActivity extends BaseActivity {

    // 主界面机台号输入框
    private EditText equipmentText;
//    private BatchAdapter batchAdapter;
    private BatchSlideAdapter batchAdapter;
    private ListView listView;
    private List<Batch> batchList;
    private TopBar topBar;
    private Button mainSearchButton;
    private Spinner proSpinner;

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    //下一页初始化为0
    int nextpage = 0;
    //每一页记载多少数据
    private int number = AppParams.PAGESIZE;
    //最多有几页
    private int maxpage = AppParams.MAXPAGE;
    //用来判断是否加载完成
    private boolean loadfinish = true;
    private View progressBar;

    // 工序ID
    private Integer lProId;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.QUERY_BATCH_RESULT:
                handleQueryBatch(msg);
                break;
            case IdiyMessage.QUERY_BATCH_NEXT_PAGE_RESULT:
                handleQueryBatchNextPage(msg);
                break;
            case IdiyMessage.SET_DETE_STATUE_RESULT:
                handleSetDeteStatue(msg);
                break;
            case IdiyMessage.QUERY_PRO_BY_LOGIN_USER_RESULT:
                handleQueryPro(msg);
                break;
            default:
                break;
        }
    }

    /**
     * 查询登陆用户对应工序
     *
     * @author xuz
     * @date 2019/6/5 2:59 PM
     * @param [msg]
     * @return void
     */
    private void handleQueryPro(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        if ("200".equals(rResult.getCode())) {
            String data = rResult.getData();
            if (null != data && !data.isEmpty()) {
                RolePro rolePro = JSON.parseObject(data, RolePro.class);
                if (rolePro!=null) {
                    List<Pro> proList = rolePro.getProList();
                    if (null!=proList) {
                        SpinnerView(proList);
                    }

                }

            }
        }

        if(!rResult.isOk() || rResult.getData().isEmpty()) {
            tip(rResult.getMsg());
        }
    }

    /**
     * 工序Spinner，Spinner点击事件
     *
     * @author xuz
     * @date 2019/6/5 3:09 PM
     * @param [proList]
     * @return void
     */
    private void SpinnerView(List<Pro> proList) {
        // 封装Spinner列表集合
        final List<SpinnerIdAndValue> spinnerIdAndValueList = new ArrayList<>();
        for (Pro pro : proList) {
            Integer lProId = pro.getlProId();
            String vcProName = pro.getVcProName();

            SpinnerIdAndValue spinnerIdAndValue = new SpinnerIdAndValue(lProId, vcProName);
            spinnerIdAndValueList.add(spinnerIdAndValue);
        }

        ArrayAdapter<SpinnerIdAndValue> adapter =
                new ArrayAdapter<SpinnerIdAndValue>(this, android.R.layout.simple_spinner_item, spinnerIdAndValueList);
        // 设置下拉列表风格
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // 将adapter添加到spinner中
        proSpinner.setAdapter(adapter);
        // 设置默认值
        proSpinner.setVisibility(View.VISIBLE);

        proSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 获取选中的工序ID
                lProId = spinnerIdAndValueList.get(position).getId();
                equipmentText.setText("");
                // 重新查询该工序下的检测单
                queryBatch(IdiyMessage.QUERY_BATCH,"", lProId, number, 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void handleSetDeteStatue(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        if ("200".equals(rResult.getCode())) {
//            tip("产品检测单判定成功");
//            // 刷新
//            queryBatch(IdiyMessage.QUERY_BATCH,"", 1, number, 1);
//            equipmentText.setText("");
        }

        if(!rResult.isOk() || rResult.getData().isEmpty()) {
            tip(rResult.getMsg());
        }

    }

    private void handleQueryBatchNextPage(Message msg) {
        final RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        if ("200".equals(rResult.getCode())) {
            String data = rResult.getData();
            List<Batch> list = JSON.parseArray(data, Batch.class);
            if (list.size() > 0) {
                for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                    Batch batch = (Batch) iterator.next();
                    batchList.add(batch);
                }
            }
            batchAdapter.notifyDataSetChanged();
            loadfinish = true;
            //当下一页的数据加载完成之后移除改视图
            if (listView.getFooterViewsCount() != 0) {
                listView.removeFooterView(progressBar);
            }
        }

        if(!rResult.isOk() || rResult.getData().isEmpty()) {
            if (!rResult.getMsg().contains("没有查询到机台")) {
                tip(rResult.getMsg());
            }
            // 移除改视图
            listView.removeFooterView(progressBar);
        }
    }

    private void handleQueryBatch(Message msg) {
        final RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }

        if ("200".equals(rResult.getCode())) {
            String data = rResult.getData();
            batchList = JSON.parseArray(data, Batch.class);
//            batchAdapter = new BatchAdapter(this, this, R.layout.activity_main_list_view_item, batchList);
            batchAdapter = new BatchSlideAdapter(this, this, R.layout.activity_main_list_view_item, batchList);
            //添加listview的脚跟视图，这个方法必须在listview.setAdapter()方法之前，否则无法显示视图
            listView.addFooterView(progressBar);
            listView.setAdapter(batchAdapter);
            loadfinish = true;
            //当下一页的数据加载完成之后移除改视图
            if (listView.getFooterViewsCount() != 0) {
                listView.removeFooterView(progressBar);
            }
            batchAdapter.notifyDataSetChanged();
        }

        if(!rResult.isOk() || rResult.getData().isEmpty()) {
            if (!rResult.getMsg().contains("没有查询到机台")) {
                tip(rResult.getMsg());
            }
            // 移除改视图
            listView.removeFooterView(progressBar);

            batchList.clear();
            if (null != batchAdapter) {
                batchAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initController();
        initUI();
    }

    private void initController() {
        mController = new MainController(this);
        mController.setIModeChangeListener(this);
    }

    private void initUI() {
        equipmentText = findViewById(R.id.equipment_text);
        listView = findViewById(R.id.batch_list_view);
        topBar = findViewById(R.id.main_activity_topbar);
        mainSearchButton = findViewById(R.id.main_search_button);
        proSpinner = findViewById(R.id.pro);

        // listview中脚跟的视图
        progressBar = this.getLayoutInflater().inflate(R.layout.progress, null);

        batchList = new ArrayList<Batch>();

        // 查询登陆用户的工序
        mController.sendAsynMessage(IdiyMessage.QUERY_PRO_BY_LOGIN_USER);

//        queryBatch(IdiyMessage.QUERY_BATCH,"", 1, number, 1);
//        mController.sendAsynMessage(IdiyMessage.QUERY_BATCH, number, 1, "");

        topBar.setThrirdButtonVisibility(false);
        topBar.setOnLeftAndRightClickListener(new TopBar.OnLeftAndRightClickListener() {
            @Override
            public void OnLeftButtonClick() {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    tip("再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }

            @Override
            public void OnRightButtonClick() {
                // 刷新
                queryBatch(IdiyMessage.QUERY_BATCH,"", lProId, number, 1);
                equipmentText.setText("");
            }

            @Override
            public void OnThridButtonClick() {

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 得到listview最后一项的id
                int lastItemId = listView.getLastVisiblePosition();

                // 判断用户是否滑动到最后一项，因为索引值从零开始所以要加上1
                if((lastItemId+1) == totalItemCount) {
                    /**
                     * 计算当前页，因为每一页只加载十条数据，所以总共加载的数据除以每一页的数据的个数
                     * 如果余数为零则当前页为currentPage=totalItemCount/number；
                     * 如果不能整除则当前页为(int)(totalItemCount/number)+1;
                     * 下一页则是当前页加1
                     */
                    int currentPage = totalItemCount % number;
                    if(currentPage == 0) {
                        currentPage = totalItemCount / number;
                    }
                    else {
                        currentPage = (int) (totalItemCount / number) + 1;
                    }
                    System.out.println("当前页为："+currentPage);
                    nextpage = currentPage+1;
                    // 当总共的数据大于0是才加载数据
                    if (totalItemCount > 0) {
                        // 判断当前页是否超过最大页，以及上一页的数据是否加载完成
                        if(loadfinish) {
                            //添加页脚视图
                            listView.addFooterView(progressBar);
                            loadfinish = false;

                            // 获取文本框模糊字段查询
                            String vcEquipment = equipmentText.getText().toString();
                            // 获取当前页数据
                            queryBatch(IdiyMessage.QUERY_BATCH_NEXT_PAGE,vcEquipment, lProId, number, nextpage);
//                            mController.sendAsynMessage(IdiyMessage.QUERY_BATCH_NEXT_PAGE, number, nextpage, vcEquipment);
                            //还可以通过这样的方式实现
                            //AsyncTaskLoadData asynctask=new AsyncTaskLoadData(totalItemCount);
                            ///asynctask.execute();
                        }
                    }

                }
            }
        });

        equipmentText.addTextChangedListener(textWatcher);
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
            long currentTime = System.currentTimeMillis();
            if (currentTime - exitTime > 1.5) {
                final String equipmentStr = equipmentText.getText().toString();

                queryBatch(IdiyMessage.QUERY_BATCH, equipmentStr, lProId, number, 1);
            }
            exitTime = currentTime;
//            mController.sendAsynMessage(IdiyMessage.QUERY_BATCH, number, 1, equipmentStr);

        }
    };

    /**
     * 发送网络请求，查询批次信息
     *
     * @author xuz
     * @date 2019/3/9 3:36 PM
     * @param
     * @return
     */
    private void queryBatch(int action, String vcEquipment, Integer lProId, Integer pagesize, Integer pageindex) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dim", vcEquipment);
        jsonObject.put("lProId", lProId);
        jsonObject.put("pagesize", pagesize);
        jsonObject.put("pageindex", pageindex);
        mController.sendAsynMessage(action, jsonObject);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                tip("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



}
