/*
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

import com.yrd.store.utils.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

*/
/**
 * ****************************************
	 * 类描述： 调用 系统下载
	 * 类名称：DownloadManager  
 	 * @version: 1.0
	 * @author: why
	 * @time: 2014-11-5 上午10:32:51 
*****************************************
 *//*

public class DownloadManager {
	public static final String UPDATE_OPTIONAL = "0";//非强制更新
	public static final String UPDATE_FORCE= "1"; //强制更新
	
	private NotificationManager manager;
	private Notification notification;
	private RemoteViews contentView;
	private static long progress;
	private static final String directory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/CreditWealth/download/";
	public static boolean isDownload = false;//是否正在下载
	private String url ; // 正在下载的url
	private boolean downFlag = true; //用来判断使用哪一个URL
	private DownloadManager() {
	}

	private static DownloadManager dManager;

	public static DownloadManager getInstance() {
		if (dManager == null) {
			dManager = new DownloadManager();
		}
		return dManager;
	}

	private Handler handler = new Handler() {// 更改进度条的进度

		public void handleMessage(Message msg) {
			contentView.setProgressBar(R.id.pb, 100, (int) progress, false);
			notification.contentView = contentView;
			manager.notify(0, notification);
			super.handleMessage(msg);
		};
	};


	@SuppressWarnings("deprecation")
	public void down(final Context context,final String downloadUrl,final String commonDownloadUrl) {

		if(downFlag){
			url = downloadUrl; // 渠道url
		}else{
			url = commonDownloadUrl; // 备用url  默认
		}
		if(TextUtils.isEmpty(url)){
			LogUtil.Log("DownApk Err", "url地址为空！ ");
			return;
		}
		manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification(R.mipmap.ic_launcher, "正在下载...",
				System.currentTimeMillis());
		String[] files = new File(url).getName().trim().split("[.]");
		String file_temp = files[0];
		String filePath = directory	+ file_temp + "_temp";
		File file = new File(directory+new File(url).getName());
		if (file.exists()) {
			// TODO 如果文件存在，直接打开
			openFile(context, file);
			return;
		}
		HttpUtils http = new HttpUtils();
		http.download(url, filePath, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
				true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
				new RequestCallBack<File>() {
					@Override
					public void onStart() {
						isDownload = true;
						sendNotification(context);
					}

					@Override
					public void onLoading(long total, long current, boolean isUploading) {
						if (current != total && current != 0) {
							progress = (int) (current / (float) total * 100);
						} else {
							progress = 100;
						}
						handler.sendEmptyMessage(0);
					}

					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {
						isDownload = false;
						LogUtil.Log("responseInfo", responseInfo.contentLength + "    " + responseInfo.result.length());
						LogUtil.Log("DownApk SUCCESS","url地址： "+url);
						boolean result = responseInfo.result.renameTo(new File(directory+new File(url).getName()));
						openFile(context, new File(directory + new File(url).getName()));
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// testTextView.setText(msg);
						LogUtil.Log("DownApk Err","url地址： "+url+"          "+msg);
						isDownload = false;
						if(downFlag){
							downFlag = false;
							down(context,downloadUrl,commonDownloadUrl);
						}
					}
				});

	}

	private void openFile(Context context, File file) {

		//  比对文件大小 文件不对 就删除掉 避免 安装错误


		LogUtil.Log("DownApk ","APK 大小： "+file.length());
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		// Log.i(TAG, "下载完成...");
		manager.cancel(0);
	}

	*/
/**
	 * 发送通知
	 *//*

	private void sendNotification(Context context) {
		contentView = new RemoteViews(context.getPackageName(), R.layout.notify_view);
		contentView.setProgressBar(R.id.pb, 100, 0, false);
		notification.contentView = contentView;
		manager.notify(0, notification);
	}
	
	*/
/**
	 * @return progress : return the property progress.
	 *//*

	public static long getProgress() {
		return progress;
	}
}
*/
