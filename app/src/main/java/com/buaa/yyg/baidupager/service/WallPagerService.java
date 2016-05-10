package com.buaa.yyg.baidupager.service;

/**
 * Created by yyg on 2016/5/10.
 */
//public class WallPaperService extends Service {
//    private int current = 0;  //当前壁纸下标
//    private int[] papers = new int[]{ nnn};
//    private WallpaperManager wManager = null;   //定义WallpaperManager服务
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        wManager = WallpaperManager.getInstance(this);
//    }
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if(current >= 4)current = 0;
//        try{
//            wManager.setResource(papers[current++]);
//        }catch(Exception e){e.printStackTrace();}
//        return START_STICKY;
//    }
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}
