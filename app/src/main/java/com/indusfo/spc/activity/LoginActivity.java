package com.indusfo.spc.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.indusfo.spc.R;
import com.indusfo.spc.bean.RResult;
import com.indusfo.spc.bean.User;
import com.indusfo.spc.bean.VersionInfo;
import com.indusfo.spc.broad.NetWorkStateRecevier;
import com.indusfo.spc.cons.AppParams;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.cons.NetworkConst;
import com.indusfo.spc.cons.UpdateStatus;
import com.indusfo.spc.controller.LoginController;
import com.indusfo.spc.utils.ActivityUtils;
import com.indusfo.spc.utils.AppUtils;
import com.indusfo.spc.utils.Md5Util;
import com.indusfo.spc.utils.SDCardUtils;
import com.indusfo.spc.utils.ToastUtils;
import com.indusfo.spc.utils.UpdateVersionUtil;
import com.indusfo.spc.utils.UrlUtils;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /*
     * UI 界面
     */
    private EditText mUsernameView;
    private EditText mPasswordView;
    // 进度条
    private View mProgressView;
    // 登陆表单
    private View mLoginFormView;
    // 勾选框
    private CheckBox checkBox;
    // 设置设备号
    private ImageView settinUrl;

    // App更新
    private TextView updateApp, version;

    EditText tv;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.LOGIN_ACTION_RESULT:
                handleLoginResult(msg);
                break;
            case IdiyMessage.SAVE_USER_TODB_RESULT:
                handleSaveUserToDb((boolean) msg.obj);
                break;
            case IdiyMessage.GET_USER_FROM_DB_RESULT:
                handleGetUser(msg.obj);
                break;
            case IdiyMessage.GET_URL_FROM_LOCAL_RESULT:
                handleSetEditText(msg.obj);
            default:
                break;
        }
    }

    private void handleSetEditText(Object obj) {
        String url = (String) obj;
        tv.setText(url);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("请输入URL");
        builder.setMessage("例如：10.0.0.28:8080");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setView(tv);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                        tip(tv.getText().toString());
                mController.sendAsynMessage(IdiyMessage.SETTING_URL, tv.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }

    private void handleGetUser(Object o) {
        if (o != null) {
            User user = (User) o;
            // 判断ifsave是否为1:勾选了记住密码
            if (user.getIfsave() == 1) {
                checkBox.setChecked(true);
                mUsernameView.setText(user.getUsername());
                mPasswordView.setText(user.getPassword());
            }
        }
    }

    private void handleSaveUserToDb(boolean ifSuccess) {
        if (ifSuccess) {
            ActivityUtils.start(this, MainActivity.class, true);
        } else {
            tip("登录异常");
        }
    }

    private void handleLoginResult(Message msg) {
        RResult rResult = (RResult) msg.obj;
        if (null==rResult) {
            tip("网络连接错误");
            return;
        }
        // 状态为200，跳转到主页
        if ("200".equals(rResult.getCode())) {

//            ActivityUtils.start(this, FunctionActivity.class, true);
            // 保存用户信息
            String username = mUsernameView.getText().toString();
            String password = mPasswordView.getText().toString();
            String cookie = rResult.getCookie();
//            String fileDir = getCacheDir().getPath();
            int ifsave;
            if (checkBox.isChecked()) {
                ifsave = 1;
            } else {
                ifsave = 0;
            }
            String userId = "111";

            mController.sendAsynMessage(IdiyMessage.SAVE_USER_TODB, username, password, ifsave, userId);

//            mController.sendAsynMessage(IdiyMessage.SAVE_COOKIE, cookie, fileDir);
            mController.sendAsynMessage(IdiyMessage.SAVE_COOKIE, cookie);

        } else{
            mPasswordView.setError("用户名或密码错误！");
            tip(rResult.getMsg());
        }

        if ("erro url".equals(rResult.getCode())) {
            tip("后台连接的ip地址错误");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initController();
        initUI();

        // 如果存在安装包，则删除
        String filePath = getCacheDir() + "/downloadApk/app-release.apk";
        SDCardUtils.removeFile(filePath);

    }

    private void initController() {

        mController = new LoginController(this);
        mController.setIModeChangeListener(this);
    }

    // 权限确认
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }
    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == AppParams.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                //访问服务器 试检测是否有新版本发布
                UpdateVersionUtil.checkVersion(LoginActivity.this, new UpdateVersionUtil.UpdateListener() {

                    @Override
                    public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
                        //判断回调过来的版本检测状态
                        switch (updateStatus) {
                            case UpdateStatus.YES:
                                //弹出更新提示
                                UpdateVersionUtil.showDialog(LoginActivity.this, versionInfo);
                                break;
                            case UpdateStatus.NO:
                                //没有新版本
                                ToastUtils.showToast(getApplicationContext(), "已经是最新版本了!");
                                break;
                            case UpdateStatus.NOWIFI:
                                //当前是非wifi网络
                                ToastUtils.showToast(getApplicationContext(), "只有在wifi下更新！");
                                break;
                            case UpdateStatus.ERROR:
                                //检测失败
                                ToastUtils.showToast(getApplicationContext(), "检测失败，请稍后重试！");
                                break;
                            case UpdateStatus.TIMEOUT:
                                //链接超时
                                ToastUtils.showToast(getApplicationContext(), "链接超时，请检查网络设置!");
                                break;
                        }
                    }

                });
            } else {
                // Permission Denied
            }
        }
    }

    private void initUI() {
        // 用户名文本输入框
        mUsernameView = (EditText) findViewById(R.id.email);
        // Password文本输入框
        mPasswordView = (EditText) findViewById(R.id.password);
        // 表单
        mLoginFormView = findViewById(R.id.login_form);
        // 进度条
        mProgressView = findViewById(R.id.login_progress);
        checkBox = findViewById(R.id.checkbox_pwd);
        settinUrl = findViewById(R.id.setting_url);
        // app更新
        updateApp = findViewById(R.id.update_app);
        // app版本显示
        version = findViewById(R.id.version);

        tv = new EditText(LoginActivity.this);

        // 获取版本号并设置
        String versionName = AppUtils.getVersionName(LoginActivity.this);
        version.setText(versionName);
//        populateAutoComplete();

        // 给Password文本框添加监听事件
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // 点击后，编辑URL地址
        settinUrl.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                mController.sendAsynMessage(IdiyMessage.GET_URL_FROM_LOCAL);
                // 上一个编辑框已经存在了parent，重新创建一个编辑框
                tv = new EditText(LoginActivity.this);
            }
        });

        mController.sendAsynMessage(IdiyMessage.GET_USER_FROM_DB, 0);

//        mController.sendAsynMessage(IdiyMessage.GET_URL_FROM_LOCAL);

        // 进入登陆界面，校验是否有新版本
        UpdateVersionUtil.checkVersion(LoginActivity.this, new UpdateVersionUtil.UpdateListener() {

                    @Override
                    public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
                        //判断回调过来的版本检测状态
                        switch (updateStatus) {
                            case UpdateStatus.YES:
                                //弹出更新提示
                                UpdateVersionUtil.showDialog(LoginActivity.this, versionInfo);
                                break;
                            default:
                                break;
                        }
                    }
                });

        // 更新App
        updateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AppUtils.verifyStoragePermissions(LoginActivity.this);
                //访问服务器 试检测是否有新版本发布
                UpdateVersionUtil.checkVersion(LoginActivity.this, new UpdateVersionUtil.UpdateListener() {

                    @Override
                    public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
                        //判断回调过来的版本检测状态
                        switch (updateStatus) {
                            case UpdateStatus.YES:
                                //弹出更新提示
                                UpdateVersionUtil.showDialog(LoginActivity.this, versionInfo);
                                break;
                            case UpdateStatus.NO:
                                //没有新版本
                                ToastUtils.showToast(getApplicationContext(), "已经是最新版本了!");
                                break;
                            case UpdateStatus.NOWIFI:
                                //当前是非wifi网络
                                ToastUtils.showToast(getApplicationContext(), "只有在wifi下更新！");
                                break;
                            case UpdateStatus.ERROR:
                                //检测失败
                                ToastUtils.showToast(getApplicationContext(), "检测失败，请稍后重试！");
                                break;
                            case UpdateStatus.TIMEOUT:
                                //链接超时
                                ToastUtils.showToast(getApplicationContext(), "链接超时，请检查网络设置!");
                                break;
                        }
                    }

                });
            }
        });

    }

    /**
     * 点击登录按钮
     * 输入框值判断，发送网络请求来请求服务器
     *
     * @author xuz
     * @date 2019/1/4 9:20 AM
     * @param [view]
     * @return void
     */
    public void loginClick(View view) {

        // 在尝试登录时存储值。
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        // 加密
        String md5pwd = Md5Util.md5(password);
        if (ifValueWasEmpty(username, password)) {
            tip("请输入账号密码");
        }

        // 发送网络请求
        mController.sendAsynMessage(IdiyMessage.LOGIN_ACTION, username, md5pwd);
    }



    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mUsernameView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * 展示进度条
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

}

