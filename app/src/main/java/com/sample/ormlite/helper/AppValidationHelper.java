package com.sample.ormlite.helper;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by praween on 18/4/16.
 */
public class AppValidationHelper{

    /**
     *
     * @param email
     * @return true if email is valid else false
     */
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
