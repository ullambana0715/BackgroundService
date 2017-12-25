package yang.backgroundservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/12/19.
 */

public class GhostActivity extends Activity {

    //进程优先级的声明参考AMS中 IMPORTANCE 的10个等级（26sdk版本中加入了一个）pre_26
    //而对于进程回收，Linux有自身设定的规则，通过进程oom阀值和当前内存阀值进行比较
    //得出oom阀值大于min内存阀值，并且自身占用内存大的进程。
    //通过Linux定义的oom_adj等级SYSTEM_ADJ和上层IMPORTANCE等级的对应关系，
    //同样在AMS的ProcessList里面通过oomAdjToImportance方法有声明

    //总结如下
    /*
    * 进程结束场景	                    结束方式	                影响范围
    Android 自身内存回收机制	        Low Memory Killer	        从IMPORTANCE数值从大到小
    第三方管理程序清理进程 无 Root 权限	killBackgroundProcess	    从IMPORTANCE数值大于6进程
    第三方管理程序清理进程 有 Root 权限	force-stop or Kill	        除当前前台进程外所有非系统进程
    Rom 清除进程（比如从任务管理器删除）force-stop or Kill	        所有非系统进程
    用户手动强制结束	                force-stop	                第三方应用以及非 System 进程
    */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this,BgService.class));
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenReceiver,filter);
    }

    BroadcastReceiver screenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){

            }else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){

            }
        }
    };
}
