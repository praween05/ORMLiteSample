package com.sample.ormlite.helper;

import android.app.Activity;
import android.app.Dialog;

import com.sample.ormlite.listeners.OnFilterActionListener;
import com.sample.ormlite.ui.dialog.FilterDialog;

/**
 * Created by praween on 18/4/16.
 */
public class DialogHelper {

    public static Dialog getFilterDialog(Activity context, OnFilterActionListener onFilterListener) {
        FilterDialog dialog = new FilterDialog(context);
        dialog.setOnFilterListener(onFilterListener);
        return dialog;
    }
}
