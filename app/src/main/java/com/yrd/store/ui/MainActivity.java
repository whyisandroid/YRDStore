package com.yrd.store.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.yrd.store.R;
import com.yrd.store.ui.adapter.StoreAdapter;
import com.yrd.store.ui.entities.Store;
import com.yrd.store.ui.entities.StoreResp;
import com.yrd.store.utils.Json_U;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView mLvStore;
    private StoreAdapter adapter;
    private List<Store> mList;

    private static final int SUCCESS = 1;



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
        getStore();

    }
    private void findView() {
        mLvStore = (ListView)findViewById(R.id.lv_store);
    }

    private void initView(){
        mList = new ArrayList<Store>();
        adapter = new StoreAdapter(this,mList);
        mLvStore.setAdapter(adapter);
    }


    private void getStore() {
        RequestParams params = new RequestParams("http://10.141.4.56:7777/mainController/listAndroidFile.action");
        params.addQueryStringParameter("clientType", "2");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String str) {
                StoreResp storeResp = Json_U.parseJsonToObj(str,StoreResp.class);
                mList.clear();
                mList.addAll(storeResp.data);
                mHandler.obtainMessage(SUCCESS).sendToTarget();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
