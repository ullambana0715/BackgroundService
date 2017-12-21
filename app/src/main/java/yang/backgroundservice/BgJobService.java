package yang.backgroundservice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Administrator on 2017/12/21.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BgJobService extends JobService {
    public static final String JOB_SERVICE_ACTION = "job_service_action";
    @Override
    public boolean onStartJob(JobParameters params) {
        Intent intent = new Intent(this,BgService.class);
        intent.setAction(JOB_SERVICE_ACTION);
        startService(intent);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
