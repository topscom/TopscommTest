package com.example.xiaojin20135.basemodule.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.retrofit.bean.ActionResult;
import com.example.xiaojin20135.basemodule.retrofit.bean.ResponseBean;
import com.example.xiaojin20135.basemodule.retrofit.helper.RetrofitManager;
import com.example.xiaojin20135.basemodule.retrofit.presenter.PresenterImpl;
import com.example.xiaojin20135.basemodule.retrofit.view.IBaseView;
import com.example.xiaojin20135.basemodule.util.ConstantUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.MultipartBody;

/**
 * @author lixiaojin
 * @create 2018-07-14
 * @Describe
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView{
    public static List<Activity> activities = new ArrayList<>();
    public static String TAG = "";
    private static AlertDialog.Builder alertDialog;
    private static Toast toast;
    private SharedPreferences sharedPreferences;
    public static ProgressDialog progressDialog;
    private PresenterImpl presenterImpl;
    public  boolean isShowProgressDialog=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addActivity(this);
        if(getLayoutId() != 0){
            setContentView(getLayoutId());
        }else{
            //throw new IllegalArgumentException("返回一个正确的ContentView!");
        }
        ButterKnife.bind (this);
        loadData();
        initView();
        initEvents();

        TAG = this.getLocalClassName();
        Log.d("BaseActivity",TAG);
        presenterImpl = new PresenterImpl (this,this);
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initEvents();
    protected abstract void loadData();

    @Override
    protected void onPause() {
        if(progressDialog!=null){
            progressDialog.dismiss ();
        }
        super.onPause();
    }

    private void addActivity(Activity activity){
        if(activity != null && !activities.contains(activity)){
            activities.add(activity);
            BaseApplication.setActivity(activity);
        }
        Log.d(TAG,"activities.size = " + activities.size());
    }

    private void removeActivity(Activity activity){
        if(activity != null && activities.contains(activity)){
            activities.remove(activity);
        }
    }

    public static List<Activity> getActivities(){
        return activities;
    }

    //退出
    public static void exit() {
        if (activities != null && activities.size() > 0) {
            for (Activity activity : activities) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    public SharedPreferences getMySharedPreferences() {
        if(sharedPreferences == null){
            sharedPreferences = getSharedPreferences(ConstantUtil.SHAREDNAME,MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public void showAlertDialog(Context context, String text, String title){
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ;
            }
        });
        alertDialog.show();
    }

    public void showAlertDialog(Context context, String text){
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("");
        alertDialog.setMessage(text);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ;
            }
        });
        alertDialog.show();
    }

    public void showAlertDialog(Context context,int id){
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("");
        alertDialog.setMessage(id);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ;
            }
        });
        alertDialog.show();
    }
    public static void showToast(Context mContext,String text){
        /*if(toast == null){
            toast = Toast.makeText(mContext,text,Toast.LENGTH_LONG);
        }else{
            toast.setText(text);
        }
        toast.show();*/
        Toast.makeText(mContext,text,Toast.LENGTH_LONG).show ();
    }
    public static void showToast(Context mContext,int id){
        /*if(toast == null){
            toast = Toast.makeText(mContext,id,Toast.LENGTH_LONG);
        }else{
            toast.setText(id);
        }
        toast.show();*/
        Toast.makeText(mContext,id,Toast.LENGTH_LONG).show ();
    }

    /**
     * 返回键监听
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    //获取版本码
    public String getAppVersionName(){
        //获取版本码
        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(),0);
            return packageInfo == null ? "" : packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    @Override
    public void showProgress () {
        //等待框
        if(isShowProgressDialog){
            if(progressDialog == null || !progressDialog.isShowing ()){
                progressDialog = new ProgressDialog(this);
            }
            progressDialog.show();
        }

    }

    @Override
    public void setProgressText (String text) {
        if(progressDialog != null && progressDialog.isShowing ()){
            Log.d (TAG,"text = " + text);
            progressDialog.setMessage (text);
        }
    }

    @Override
    public void setProgressValue (int value) {
        if(progressDialog != null && progressDialog.isShowing ()){
            progressDialog.setProgress (value);
        }
    }


    @Override
    public void showProgress (boolean hideTitle,String message,boolean cancled) {
        //等待框
        if(progressDialog == null || !progressDialog.isShowing ()){
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(message);
        if(!hideTitle){
            progressDialog.setTitle(R.string.app_name);
        }
        progressDialog.setCancelable(cancled);
        progressDialog.show();
    }

    @Override
    public void dismissProgress () {
        if(progressDialog != null){
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }



    /**
     * @author lixiaojin
     * @createon 2018-07-17 10:23
     * @Describe 请求数据 ，带完整路径，自定义回调方法
     */
    public void tryToGetData(String url,String methodName,Map paraMap) {
        presenterImpl.loadData (url + ".json",methodName,paraMap);
    }
    /**
     * @author lixiaojin
     * @createon 2018-07-17 10:23
     * @Describe 请求数据 ，带完整路径，自定义回调方法
     */
    public void tryToGetData(String url,String methodName,String errorMethodName,Map paraMap) {
        presenterImpl.loadData (url + ".json",methodName,errorMethodName,paraMap);
    }
    /**
     * @author lixiaojin
     * @createon 2018-07-17 10:23
     * @Describe 请求数据 ，带完整路径，固定回调方法
     */
    public void tryToGetData(String url,Map paraMap) {
        presenterImpl.loadData (url + ".json",paraMap);
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-17 10:39
     * @Describe 请求数据，带请求方法，并自定义回调方法
     */
    public void getDataWithMethod(String url,Map paraMap) {
        presenterImpl.loadData (RetrofitManager.RETROFIT_MANAGER.BASE_URL + url + ".json",url,paraMap);
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-01 9:35
     * @Describe 上传文件
     */
    public void uploadFileWithMethod(String url,Map paraMap, MultipartBody.Part[] filePart){
        presenterImpl.uploadFile (RetrofitManager.RETROFIT_MANAGER.BASE_URL + url + ".json",url,paraMap,filePart);
    }


    /**
     * 文件上传，带完整地址
     * @param url
     * @param paraMap
     * @param filePart
     */
    public void uploadFileWithTotalUrl(String url,Map paraMap, MultipartBody.Part[] filePart){
        presenterImpl.uploadFile (url + ".json",url,paraMap,filePart);
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-17 10:39
     * @Describe 请求数据，带请求方法，固定回调方法
     */
    public void getDataWithCommonMethod(String url,Map paraMap) {
        presenterImpl.loadData (RetrofitManager.RETROFIT_MANAGER.BASE_URL + url + ".json",paraMap);
    }


    /**
     * @author lixiaojin
     * @createon 2018-07-19 8:39
     * @Describe 请求数据，带请求方法和和后缀，自定义回调方法
     */
    public void getDataWithMethod(String url,String suffix,Map paraMap){
        presenterImpl.loadData (RetrofitManager.RETROFIT_MANAGER.BASE_URL + url + suffix,url,paraMap);
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-19 8:39
     * @Describe 请求数据，带请求方法和和后缀，固定回调方法
     */
    public void getDataWithCommonMethod(String url,String suffix,Map paraMap){
        presenterImpl.loadData (RetrofitManager.RETROFIT_MANAGER.BASE_URL + url + suffix,paraMap);
    }



    @Override
    public void loadDataSuccess (Object tData) {
        Log.d (TAG,"loadDataSuccess");
    }

    @Override
    public void loadError (Throwable throwable) {
        Log.d (TAG,"loadDataError");
        requestError (throwable.getLocalizedMessage ());
    }

    @Override
    public void loadComplete () {
        Log.d (TAG,"loadDataComplete");
    }

    @Override
    public void loadSuccess (Object callBack) {
        Log.d (TAG,"loadSuccess");
        ResponseBean responseBean = (ResponseBean)callBack;
        ActionResult actionResult = responseBean.getActionResult ();
        if(actionResult.getSuccess ()){
            loadDataSuccess (callBack);
        }else{
            requestError (responseBean);
        }
    }

    @Override
    public void loadSuccess (Object tData, String methodName) {
        int index = methodName.lastIndexOf ("/");
        if(index < 0){
            index = 0;
        }else{
            index++;
        }
        methodName = methodName.substring (index);
        Log.d (TAG,"methodName = " + methodName);
        ResponseBean responseBean = (ResponseBean)tData;
        ActionResult actionResult = responseBean.getActionResult ();
        if(actionResult.getSuccess ()){
            if(methodName != null && !methodName.equals ("")){
                try {
                    Class c = this.getClass();
                    Method m1 = c.getDeclaredMethod(methodName,new Class[]{ResponseBean.class});
                    m1.invoke(this,new Object[]{responseBean});
                    Log.d (TAG,"调用自定义方法");
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertDialog (this,e.getLocalizedMessage ());
                }
            }else{
                showAlertDialog (this,"not found "+methodName+" method");
            }
        }else{
            requestError (responseBean);
        }
    }

    @Override
    public void loadSuccess (Object tData, String methodName,String errorMethodName) {

        ResponseBean responseBean = (ResponseBean)tData;
        ActionResult actionResult = responseBean.getActionResult ();
        if(actionResult.getSuccess ()){
            int index = methodName.lastIndexOf ("/");
            if(index < 0){
                index = 0;
            }else{
                index++;
            }
            methodName = methodName.substring (index);
            Log.d (TAG,"methodName = " + methodName);
            if(methodName != null && !methodName.equals ("")){
                try {
                    Class c = this.getClass();
                    Method m1 = c.getDeclaredMethod(methodName,new Class[]{ResponseBean.class});
                    m1.invoke(this,new Object[]{responseBean});
                    Log.d (TAG,"调用自定义方法");
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertDialog (this,e.getLocalizedMessage ());
                }
            }else{
                showAlertDialog (this,"not found "+methodName+" method");
            }
        }else{
            int index = errorMethodName.lastIndexOf ("/");
            if(index < 0){
                index = 0;
            }else{
                index++;
            }
            errorMethodName = errorMethodName.substring (index);
            Log.d (TAG,"methodName = " + errorMethodName);
            if(errorMethodName != null && !errorMethodName.equals ("")){
                try {
                    Class c = this.getClass();
                    Method m1 = c.getDeclaredMethod(errorMethodName,new Class[]{ResponseBean.class});
                    m1.invoke(this,new Object[]{responseBean});
                    Log.d (TAG,"调用自定义方法");
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertDialog (this,e.getLocalizedMessage ());
                }
            }else{
                showAlertDialog (this,"not found "+errorMethodName+" method");
            }
        }
    }

    @Override
    public void requestError (ResponseBean responseBean) {
        requestError (responseBean.getActionResult ().getMessage ());
        if(responseBean.isTimeout ()){
            reStartApp();
        }
    }
    public void reStartApp(){
            Intent intent = getBaseContext().getPackageManager() .getLaunchIntentForPackage(getBaseContext().getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
    }
    @Override
    public void requestError (String  message) {
        Log.d (TAG,"requestError : " + message);
        showToast (this,message);
    }

    /**
     * 界面跳转，不传参
     * @param tClass
     */
    protected void canGo(Class<?> tClass){
        canGo (tClass,null);
    }

    /**
     * 界面跳转，带参数
     * @param tClass
     * @param bundle
     */
    protected void canGo(Class<?> tClass,Bundle bundle){
        Intent intent = new Intent (this,tClass);
        if(bundle != null){
            intent.putExtras (bundle);
        }
        startActivity (intent);
    }

    /**
     * @author lixiaojin
     * @create 2018-07-14
    * @Describe 跳转到目标页面并杀死当前页面
     */
    protected void canGoThenKill(Class<?> tClass){
        canGoThenKill (tClass,null);
    }

    /**
     * @author lixiaojin
     * @create 2018-07-14
     * @Describe 跳转到目标页面，并杀死当前页面，带参数
     */
    protected void canGoThenKill(Class<?> tClass,Bundle bundle){
        canGo (tClass,bundle);
        finish ();
    }

    /**
     * @author lixiaojin
     * @create 2018-07-14
     * @Describe 跳转到目标位置，并返回结果
     */
    protected void canGoForResult(Class<?> tClass,int requestCode){
        Intent intent = new Intent (this,tClass);
        startActivityForResult (intent,requestCode);
    }

    /**
     * @author lixiaojin
     * @create 2018-07-14
     * @Describe 带参数跳转到目标位置并返回结果，
     */
    protected void canGoForResult(Class<?> tClass,int requestCode,Bundle bundle){
        Intent intent = new Intent (this,tClass);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult (intent,requestCode);
    }


}

