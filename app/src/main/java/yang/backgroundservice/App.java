package yang.backgroundservice;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;

/**
 * Created by Administrator on 2017/12/19.
 */

public class App extends Application {
    public static App app;
    public static final int TIME_REPEATE = 1 * 60 * 1000;//设定alarm唤醒间隔
    public static final int ALARM_REPEATE_CODE = 1024;//设定唤醒
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(this, ALARM_REPEATE_CODE, new Intent(BgReciever.BGRECIEVER_ACTION), PendingIntent.FLAG_UPDATE_CURRENT);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), TIME_REPEATE, pi);
    }
}
