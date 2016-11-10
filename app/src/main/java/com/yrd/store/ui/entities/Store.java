package com.yrd.store.ui.entities;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2016/3/10 15:31
 ***************************************/
public class Store {
    public String fileName; // 文件名字
    public String downloadUrl; // 下载URL
    public String fileMd5;  //  文件md5
    public int progress = 0;  // 下载进度
    public boolean  downFlag;  // 下载完成标志位

   public Store(String name,String url, int progress ,boolean downFlag){
       this.downFlag = downFlag;
       this.fileName = name;
       this.progress = progress;
       this.downloadUrl = url;
   }
}
