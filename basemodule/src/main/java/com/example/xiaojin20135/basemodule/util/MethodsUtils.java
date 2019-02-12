package com.example.xiaojin20135.basemodule.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by lixiaojin on 2018-09-04 14:01.
 * 功能描述：
 */

public enum MethodsUtils {
    METHODS_UTILS;

    /**
     * @author lixiaojin
     * @createon 2018-09-04 14:34
     * @Describe ArrayList中不重复插入数据
     */
    public ArrayList<String> addNewItem(ArrayList<String> arrayList,String item){
        if(arrayList == null){
            arrayList = new ArrayList<> ();
        }
        arrayList.add (item);
        HashSet<String> set = new HashSet<String> (arrayList);
        arrayList = new ArrayList<> (set);
        return arrayList;
    }

    /**
     * 16进制数组转字符串
     * @param bytes
     * @return
     */
    public String byteToHexString(byte[] bytes){
        Date in = new Date();
        StringBuilder frameStr = new StringBuilder();
        if(bytes == null){
            return "";
        }
        for(int i=0;i<bytes.length;i++){
            frameStr.append(((Integer.toHexString(bytes[i] & 0xFF).length() < 2) ? ("0" + Integer.toHexString(bytes[i] & 0xFF)) : Integer.toHexString(bytes[i] & 0xFF)) + " ");
        }
        Date out = new Date();
        return frameStr.toString();
    }
    /**
     * int数组转字符串
     * @param ints
     * @return
     */
    public String intToString(int[] ints){
        StringBuilder result = new StringBuilder();
        if(ints == null){
            return "";
        }
        for(int i=0;i<ints.length;i++){
            result.append(" " + Integer.toString(ints[i]));
        }
        return result.toString();
    }
    /**
     * 字符串数组转字符串
     * @param strs
     * @return
     */
    public String StringToString(String[] strs){
        StringBuilder result = new StringBuilder();
        if(strs == null){
            return "";
        }
        for(int i=0;i<strs.length;i++){
            result.append(" " + strs[i]);
        }
        return result.toString();
    }

}
