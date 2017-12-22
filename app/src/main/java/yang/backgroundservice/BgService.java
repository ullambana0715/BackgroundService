package yang.backgroundservice;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/12/19.
 */

public class BgService extends Service {
    public static final int NOTICE_ID = 999;
    public static final String TAG  = BgService.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"Intent:"+intent);//打印当前进入service的来源
        Log.i(TAG,"flags:"+flags);
        Log.i(TAG,"startId:"+startId);
        startForeground(NOTICE_ID,new Notification());//伪装成前台进程,但是这种方式在7.x之前都是很好用的，7.X以后的设备会有google系统提示
        // 当 SDk 版本大于19时，需要通过内部 Service 类启动同样 id 的 Service
        if (Build.VERSION.SDK_INT >= 19) {
            Intent innerIntent = new Intent(this, InnerService.class);
            startService(innerIntent);
        }

        /**
         * 这里返回值是使用系统 Service 的机制自动重新启动，不过这种方式以下两种方式不适用：
         * 1.Service 第一次被异常杀死后会在5秒内重启，第二次被杀死会在10秒内重启，第三次会在20秒内重启，一旦在短时间内 Service 被杀死达到5次，则系统不再拉起。
         * 2.进程被取得 Root 权限的管理工具或系统工具通过 forestop 停止掉，无法重启。
         */
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 实现一个内部的 Service，让后台服务的优先级提高到前台服务，不保证所有系统可用
     */
    public static class InnerService extends Service{

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.i(TAG, "InnerService -> onStartCommand");
            startForeground(NOTICE_ID, new Notification());//启动同样id的service
            stopSelf();//可选,stop的话可绕过7.x系统提示，但是bgService还可以在后台运行。
            return START_STICKY;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
