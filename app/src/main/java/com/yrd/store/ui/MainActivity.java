package com.yrd.store.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.yrd.store.DownloadManager;
import com.yrd.store.R;
import com.yrd.store.ui.adapter.StoreAdapter;
import com.yrd.store.ui.entities.BaseResp;
import com.yrd.store.ui.entities.Store;
import com.yrd.store.ui.entities.StoreResp;
import com.yrd.store.utils.Json_U;
import com.yrd.store.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import  android.support.v4.widget.SwipeRefreshLayout;


public class MainActivity extends AppCompatActivity {
    private ListView mLvStore;
    private StoreAdapter adapter;
    private List<Store> mList;
    private SwipeRefreshLayout mRefreshStore; // 下拉刷新

    private static final int SUCCESS = 1;  // 获取数据成功 刷新数据
    public static final int PROGRESS = 2;  // 进度条处理



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    // check  File is 存在
                    checkFile();
                    adapter.notifyDataSetChanged();
                    mRefreshStore.setRefreshing(false);
                    break;
                case PROGRESS:
                    Store downStore = (Store)msg.obj;
                    for (int i = 0; i < mList.size(); i++) {
                        if(downStore.fileName.equals(mList.get(i).fileName)){
                            mList.get(i).progress = downStore.progress;
                            mList.get(i).downFlag = downStore.downFlag;
                        }
                    }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void findView() {
        mLvStore = (ListView)findViewById(R.id.lv_store);
        mRefreshStore = (SwipeRefreshLayout)findViewById(R.id.refresh_store);
    }

    private void initView(){
        mList = new ArrayList<Store>();
        Store store1 = new Store("YinrendaiAndroid.apk","http://appsource.yixin.com/upload/appdownload/MobileMoneyAndroid_106.apk",0,false);
        Store store2 = new Store("YinrendaiAndroid2.apk","http://appsource.yixin.com/upload/appdownload/MobileMoneyAndroid_106.apk",0,false);
        Store store3 = new Store("YinrendaiAndroid3.apk","http://appsource.yixin.com/upload/appdownload/MobileMoneyAndroid_106.apk",0,false);
        Store store4 = new Store("YinrendaiAndroid4.apk","http://appsource.yixin.com/upload/appdownload/MobileMoneyAndroid_106.apk",0,false);
        Store store5 = new Store("YinrendaiAndroid5.apk","http://appsource.yixin.com/upload/appdownload/MobileMoneyAndroid_106.apk",0,false);
        mList.add(store1);
        mList.add(store2);
        mList.add(store3);
        mList.add(store4);
        mList.add(store5);

        adapter = new StoreAdapter(this,mList,mHandler);


        mLvStore.setAdapter(adapter);
        mRefreshStore.setOnRefreshListener(mRefreshListener);
    }


    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener(){

        @Override
        public void onRefresh() {
            getStore();
        }
    };


    private void getStore() {
        RequestParams params = new RequestParams("http://10.141.4.56:7777/mainController/listAndroidFile.action");
        params.addQueryStringParameter("clientType", "2");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String str) {
                LogUtil.Log("MainActivity", str);
                StoreResp storeResp = Json_U.parseJsonToObj(str, StoreResp.class);
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

    /**
     *  判断文件是否已经存在
     */
    private void checkFile(){
        File downFile = new File(DownloadManager.directory);
        if (downFile.exists()) {
            File[] files = downFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                for (int j = 0; j < mList.size(); j++) {
                    if(files[i].getName().equals(mList.get(j).fileName)){
                        mList.get(j).downFlag = true;
                    }
                }
            }
        }
    }
}
