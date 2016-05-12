package com.sample.ormlite.ui.adapter;

import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.ormlite.R;

/**
 * Created by praween on 26/4/16.
 */
public class TestSpinnerAdapter extends BaseAdapter {

    private String[] mArray;
    public TestSpinnerAdapter(String[] array) {
        mArray =  array;
    }

    @Override
    public int getCount() {
        return mArray.length;
    }

    @Override
    public Object getItem(int position) {
        return mArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        convertView = layoutInflater.inflate(R.layout.adapter_spinner_item,parent,false);
        ((TextView)convertView.findViewById(R.id.tv_name)).setText(mArray[position]);
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(parent.getContext(),"On Long Click",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        convertView.setTag(position);
        return convertView;
    }
}
