package com.example.xiaojin20135.basemodule.fragment.base;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.activity.BaseActivity;
import com.example.xiaojin20135.basemodule.retrofit.bean.ActionResult;
import com.example.xiaojin20135.basemodule.retrofit.bean.ResponseBean;
import com.example.xiaojin20135.basemodule.retrofit.helper.RetrofitManager;
import com.example.xiaojin20135.basemodule.retrofit.presenter.PresenterImpl;
import com.example.xiaojin20135.basemodule.retrofit.view.IBaseView;

import java.lang.reflect.Method;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.MultipartBody;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements IBaseView{
    public static String TAG = "";
    private PresenterImpl presenterImpl;
    public  boolean isShowProgressDialog=true;
    public BaseFragment () {
        TAG = this.getClass ().getSimpleName ();
        Log.d("BaseActivity",TAG);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenterImpl = new PresenterImpl (this,getContext ());
        TextView textView = new TextView (getActivity ());
        textView.setText (R.string.hello_blank_fragment);
        return textView;
    }

    protected void bindView(View view){
        ButterKnife.bind (this,view);
    }

    protected abstract void initView(View view);

    protected abstract void initEvents(View view);



    @Override
    public void showProgress () {
        if(getActivity ()!=null&&isShowProgressDialog){
            ((BaseActivity)getActivity ()).showProgress ();
        }

    }


    @Override
    public void setProgressText (String text) {
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).setProgressText (text);
        }
    }

    @Override
    public void setProgressValue (int value) {
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).setProgressValue (value);
        }
    }

    @Override
    public void showProgress (boolean hideTitle, String message, boolean cancled) {
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).showProgress (hideTitle, message, cancled);
        }
    }

    @Override
    public void dismissProgress () {
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).dismissProgress ();
        }
    }

    public void showAlertDialog(Context context, String text, String title){
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).showAlertDialog (context, text, title);
        }
    }

    public void showAlertDialog(Context context, String text){
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).showAlertDialog (context, text);
        }
    }

    public void showAlertDialog(Context context,int id){
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).showAlertDialog (context, id);
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
        //((BaseActivity)getActivity ()).showToast (getActivity (),throwable.getLocalizedMessage ());
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
        Log.d (TAG,"loadDataSuccess with methodName :" + methodName);
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
                    ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),e.getLocalizedMessage ());
                }
            }else{
                ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),"not found "+methodName+" method");
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
            Log.d (TAG,"loadDataSuccess with methodName :" + methodName);
            if(methodName != null && !methodName.equals ("")){
                try {
                    Class c = this.getClass();
                    Method m1 = c.getDeclaredMethod(methodName,new Class[]{ResponseBean.class});
                    m1.invoke(this,new Object[]{responseBean});
                    Log.d (TAG,"调用自定义方法");
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),e.getLocalizedMessage ());
                }
            }else{
                ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),"not found "+methodName+" method");
            }
        }else{
            int index = errorMethodName.lastIndexOf ("/");
            if(index < 0){
                index = 0;
            }else{
                index++;
            }
            errorMethodName = errorMethodName.substring (index);
            Log.d (TAG,"loadDatafail with methodName :" + errorMethodName);
            if(errorMethodName != null && !errorMethodName.equals ("")){
                try {
                    Class c = this.getClass();
                    Method m1 = c.getDeclaredMethod(errorMethodName,new Class[]{ResponseBean.class});
                    m1.invoke(this,new Object[]{responseBean});
                    Log.d (TAG,"调用自定义方法");
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),e.getLocalizedMessage ());
                }
            }else{
                ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),"not found "+errorMethodName+" method");
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
        Intent intent =getActivity ().getPackageManager()
            .getLaunchIntentForPackage(getActivity().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public void requestError (String message) {
        Log.d (TAG,"requestError : " + message);
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).showToast (getActivity (), message);
        }
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
        Intent intent = new Intent (getActivity (),tClass);
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
        getActivity ().finish ();
    }

    /**
     * @author lixiaojin
     * @create 2018-07-14
     * @Describe 跳转到目标位置，并返回结果
     */
    protected void canGoForResult(Class<?> tClass,int requestCode){
        Intent intent = new Intent (getActivity (),tClass);
        startActivityForResult (intent,requestCode);
    }

    /**
     * @author lixiaojin
     * @create 2018-07-14
     * @Describe 带参数跳转到目标位置并返回结果，
     */
    protected void canGoForResult(Class<?> tClass,int requestCode,Bundle bundle){
        Intent intent = new Intent (getActivity (),tClass);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult (intent,requestCode);
    }

    @Override
    public void onPause () {
        dismissProgress ();
        super.onPause ();
    }
}
