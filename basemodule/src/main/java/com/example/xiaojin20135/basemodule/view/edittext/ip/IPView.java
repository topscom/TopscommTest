package com.example.xiaojin20135.basemodule.view.edittext.ip;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.view.edittext.AbsEditText;
import com.example.xiaojin20135.basemodule.view.edittext.AbsEditTextGroup;

/**
 * Created by lixiaojin on 2018-09-07 16:15.
 * 功能描述：
 */

public class IPView extends AbsEditTextGroup {
    public IPView(Context context) {
        this(context,null,0);
    }

    public IPView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IPView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getChildCount() {
        return 7;
    }

    @Override
    public AbsEditText getAbsEditText() {
        return new IPEditText(getContext());
    }

    @Override
    public String getSemicolomText() {
        return ".";
    }

    @Override
    public int getDelMaxLength() {
        return 3;
    }

    @Override
    public void applySemicolonTextViewTheme(TextView semicolonTextView) {
        semicolonTextView.setPadding(0,0,0,5);
        semicolonTextView.getPaint().setFakeBoldText(true);
        semicolonTextView.setBackgroundColor(0xFFFFFFFF);
        semicolonTextView.setGravity(Gravity.BOTTOM);
    }

    @Override
    public void applyEditTextTheme(AbsEditText absEditText) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length() == getDelMaxLength()) {
            for (int i=0; i< editTexts.size()-1; i++){
                if(editTexts.get(i).hasFocus()){ //hasFocus √ & isFocus ×
                    editTexts.get(i).clearFocus();
                    editTexts.get(i+1).requestFocus();

                    String lastEditTextValue = editTexts.get(i).getText().toString();
                    if (Integer.parseInt(lastEditTextValue) > 255) {
                        editTexts.get(i).setText(lastEditTextValue.substring(0, 2));
                        editTexts.get(i+1).setText(lastEditTextValue.substring(2, 3));
                        editTexts.get(i+1).setSelection(1);
                    }
                    break;
                }
            }
        }
    }

    /**
     * 设置初始值，例如默认网关："255\\.255\\.255\\.0" 正则表达式转义字符相关知识
     */
    public void setGatewayText(String[] ips){
        if(ips==null){
            ips = new String[]{"255","255","255","0"};
        }
        for (int i=0; i<editTexts.size(); i++){
            editTexts.get(i).setText(ips[i]);
        }
    }

}

