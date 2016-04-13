package com.yrd.store;

import android.app.Application;
import org.xutils.x;
/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2016/3/10 11:09
 ***************************************/
public class StoreApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
