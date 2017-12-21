package yang.backgroundservice;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Administrator on 2017/12/19.
 */

public class App extends Application {
    public static App app;
    public static final int TIME_REPEATE = 1 * 60 * 1000;//设定alarm唤醒间隔
    public static final int ALARM_REPEATE_CODE = 1024;//设定唤醒
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        //通过alarm唤醒
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(this, ALARM_REPEATE_CODE, new Intent(BgReciever.BGRECIEVER_ACTION), PendingIntent.FLAG_UPDATE_CURRENT);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), TIME_REPEATE, pi);

        //通过5.0以上jobservice唤醒
        JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(this,BgJobService.class));
        builder.setPeriodic(TIME_REPEATE);
        builder.setPersisted(true);
        JobScheduler scheduler = (JobScheduler)getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(builder.build());
    }
}
