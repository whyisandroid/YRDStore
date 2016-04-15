package com.yrd.store.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yrd.store.DownloadManager;
import com.yrd.store.R;
import com.yrd.store.ui.entities.Store;

import java.util.List;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2016/3/10 15:25
 ***************************************/
public class StoreAdapter extends BaseAdapter {
    private List<Store> mList;
    private Context mContext;
    private Handler mHandler;

    public StoreAdapter(Context mContext,List<Store> list,Handler mHandler){
        this.mContext = mContext;
        this.mList = list;
        this.mHandler = mHandler;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Store store = mList.get(position);
         if(convertView == null){
             convertView = LayoutInflater.from(mContext).inflate(R.layout.store_item, null);
         }
        TextView tvStore = (TextView)convertView.findViewById(R.id.tv_store);
        final TextView tvDown = (TextView)convertView.findViewById(R.id.tv_down);
        tvStore.setText(store.fileName);
        tvDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Toast.makeText(mContext,"正在下载",Toast.LENGTH_LONG).show();
                DownloadManager.getInstance().startDown(mContext,store,mHandler);
                tvDown.setText("下载中");
            }
        });
        return convertView;
    }
}
