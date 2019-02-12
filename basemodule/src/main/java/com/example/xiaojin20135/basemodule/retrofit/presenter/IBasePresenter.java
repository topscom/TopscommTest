package com.example.xiaojin20135.basemodule.retrofit.presenter;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by lixiaojin on 2018-07-13.
 * 功能描述：
 */

public interface IBasePresenter {

    /**
     * 加载数据
     * @param url
     * @param paraMap
     */
    void loadData(String url,String methodName,Map paraMap);
    /**
     * 加载数据
     * @param url
     * @param paraMap
     */
    void loadData(String url,String methodName,String errorMethodName,Map paraMap);
    /**
     * @author lixiaojin
     * @createon 2018-09-01 9:33
     * @Describe 上传文件
     */
    void uploadFile(String url,String methodMethod,Map paraMap, MultipartBody.Part[] filePart);

    /**
     * 加载数据
     * @param url
     * @param paraMap
     */
    void loadData(String url,Map paraMap);

    /**
     * 注销
     */
    void unSubscribe ();
}
