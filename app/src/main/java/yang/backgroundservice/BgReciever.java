package yang.backgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2017/12/19.
 */

public class BgReciever extends BroadcastReceiver {
    public static final String BGRECIEVER_ACTION = "bgreciever_action";
    public static final String TAG = BgReciever.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {//接受manifest定义的action
        Log.i(TAG,TAG+" action:"+intent.getAction());
        Intent newIntent = new Intent(context,BgService.class);
        newIntent.setAction(intent.getAction());
        context.startService(newIntent);

        //监听Screen广播，在锁频中提高service存活
        
    }
}