package com.sample.ormlite.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.sample.ormlite.R;
import com.sample.ormlite.constants.AppConstants;
import com.sample.ormlite.listeners.OnFilterActionListener;
// ...

public class FilterDialog extends Dialog implements View.OnClickListener{
    private OnFilterActionListener mOnFilterListener;

    public FilterDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fragment_filter);
        findViewById(R.id.btn_limit_data).setOnClickListener(this);
        findViewById(R.id.btn_order_by_name).setOnClickListener(this);
        findViewById(R.id.btn_order_by_time).setOnClickListener(this);
        setTitle("Filter");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_limit_data:
                mOnFilterListener.onFilterAction(AppConstants.FILTER_LIMIT);
                dismiss();
                break;
            case R.id.btn_order_by_name:
                mOnFilterListener.onFilterAction(AppConstants.FILTER_ORDER_BY_NAME);
                dismiss();
                break;
            case R.id.btn_order_by_time:
                mOnFilterListener.onFilterAction(AppConstants.FILTER_ORDER_BY_TIME);
                dismiss();
                break;
        }
    }

    public void setOnFilterListener(OnFilterActionListener onFilterListener) {
        mOnFilterListener = onFilterListener;
    }
}