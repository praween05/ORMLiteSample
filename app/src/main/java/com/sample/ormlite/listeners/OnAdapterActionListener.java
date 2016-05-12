package com.sample.ormlite.listeners;

/**
 * Created by praween on 18/4/16.
 */
public interface OnAdapterActionListener {
    public void onDeleteAction(int position,String[] where, String[] values);
    public void onUpdateAction(int position);
}
