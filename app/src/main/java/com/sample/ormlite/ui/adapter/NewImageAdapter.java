package com.sample.ormlite.ui.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sample.ormlite.R;
import com.sample.ormlite.listeners.OnAdapterActionListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


public class NewImageAdapter extends RecyclerView.Adapter<NewImageAdapter.ViewHolder> {
    private final List<String> mUserDataModelList;
    private OnAdapterActionListener mOnAdapterActionListener;
    private Activity mActivity;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewImageAdapter(Activity activity, OnAdapterActionListener onDeleteListener, List<String> myDataset) {
        mUserDataModelList = myDataset;
        mOnAdapterActionListener = onDeleteListener;

        mActivity = activity;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public IOnRecycleViewItemClick mOnRecycleViewItemClick;
        private ImageView mDataIV;

        public ViewHolder(View v, IOnRecycleViewItemClick onRecycleViewItemClick) {
            super(v);
            mOnRecycleViewItemClick = onRecycleViewItemClick;
            mDataIV = (ImageView) v.findViewById(R.id.iv_delete);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnRecycleViewItemClick.onRecyclerViewItemClick(v, getAdapterPosition());
        }

        public interface IOnRecycleViewItemClick {
            void onRecyclerViewItemClick(View view, int position);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public NewImageAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, new ViewHolder.IOnRecycleViewItemClick() {
            @Override
            public void onRecyclerViewItemClick(View view, int position) {
//                switch (view.getId()) {
//                    case R.id.iv_delete:
//                        mOnAdapterActionListener.onDeleteAction(position,new String[]{DatabaseColumnName.COLUMN_ID}, new String[]{String.valueOf(mUserDataModelList.get(position).getId())});
//                        break;
//                    default:
//                        mOnAdapterActionListener.onUpdateAction(position);
//
//
//                }
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File file = new File(mUserDataModelList.get(position));

        Picasso.Builder builder = new Picasso.Builder(mActivity);
        builder.listener(new Picasso.Listener() {

            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.e("", "" + uri.getPath());
                exception.printStackTrace();
            }
        });

        Picasso pic = builder.build();
        pic.load(file).into(holder.mDataIV);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        final UserDataModel userDataModel = mUserDataModelList.get(position);
//        holder.mNameTV.setText(userDataModel.getUserName());
//        holder.mEmailTV.setText(userDataModel.getEmail());
    }

    // Return the size of your dataset (invoked by the layout manager)

    @Override
    public int getItemCount() {
        return mUserDataModelList.size();
    }

}