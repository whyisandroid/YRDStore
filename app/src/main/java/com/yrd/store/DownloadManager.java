
package com.yrd.store;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.yrd.store.ui.MainActivity;
import com.yrd.store.ui.entities.Store;
import com.yrd.store.ui.entities.StoreResp;
import com.yrd.store.utils.Json_U;
import com.yrd.store.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * ****************************************
 * 类描述： 调用 系统下载
 * 类名称：DownloadManager
 *
 * @version: 1.0
 * @author: why
 * @time: 2014-11-5 上午10:32:51
 *****************************************/

public class DownloadManager {
    public static final String UPDATE_OPTIONAL = "0";//非强制更新
    public static final String UPDATE_FORCE = "1"; //强制更新

    private static int progress;
    private static final String directory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/YRDStore/download/";
    public static boolean isDownload = false;//是否正在下载
    private static DownloadManager dManager;

    public static DownloadManager getInstance() {
        if (dManager == null) {
            dManager = new DownloadManager();
        }
        return dManager;
    }

    @SuppressWarnings("deprecation")
    public void startDown(final Context context,final Store store,final Handler mHandler) {
        if (TextUtils.isEmpty(store.downloadUrl)) {
            LogUtil.Log("DownApk Err", "url地址为空！ ");
            return;
        }
        String filePath = directory + store.fileName.replace(".apk", "") + "_temp";
        final File mFile = new File(directory + store.fileName);
        if (mFile.exists()) {
            // TODO 如果文件存在，直接打开
            openFile(context, mFile);
            return;
        }

        RequestParams params = new RequestParams(store.downloadUrl);
        params.setSaveFilePath(filePath);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                isDownload = true;
            }

            @Override
            public void onLoading(long total, long current, boolean b) {
                if (current != total && current != 0) {
                    progress = (int) (current / (float) total * 100);
                } else {
                    progress = 100;
                }
                store.progress = progress;
                mHandler.obtainMessage(MainActivity.PROGRESS,store).sendToTarget();
            }


            @Override
            public void onSuccess(File file) {
                isDownload = false;
                file.renameTo(mFile);
                // openFile(context, new File(directory + new File(url).getName()));
                openFile(context, mFile);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                isDownload = false;
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

    private void openFile(Context context, File file) {

        //  比对文件大小 文件不对 就删除掉 避免 安装错误


        LogUtil.Log("DownApk ", "APK 大小： " + file.length());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        // Log.i(TAG, "下载完成...");
    }
}

