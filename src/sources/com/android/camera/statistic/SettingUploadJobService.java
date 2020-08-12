package com.android.camera.statistic;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Slog;
import com.android.camera.CameraApplicationDelegate;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;

public class SettingUploadJobService extends JobService {
    private static final boolean DEBUG = Build.IS_DEBUGGABLE;
    private static final int JOB_ID = 17495010;
    public static final long QUERY_PERIOD = 86400000;
    private static String TAG = "CameraSettingJob";

    public static void scheduleSettingUploadJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (jobScheduler == null) {
            Slog.w(TAG, "scheduleJob(): JobScheduler not supported");
            return;
        }
        jobScheduler.cancel(JOB_ID);
        if (jobScheduler.schedule(new JobInfo.Builder(JOB_ID, new ComponentName(context, SettingUploadJobService.class)).setRequiredNetworkType(2).setMinimumLatency(86400000).setPersisted(true).build()) <= 0) {
            Slog.w(TAG, String.format("scheduleJob failed: job id is %s", Integer.valueOf((int) JOB_ID)));
        }
    }

    public boolean onStartJob(final JobParameters jobParameters) {
        Slog.d(TAG, "Analysis job is scheduled");
        new Thread(new Runnable() {
            /* class com.android.camera.statistic.SettingUploadJobService.AnonymousClass1 */

            public void run() {
                if (Util.isUserUnlocked(CameraApplicationDelegate.getAndroidContext()) && !CameraSettings.isShowFirstUseHint()) {
                    DataRepository.dataItemLive().putLong(CameraSettings.KEY_LAST_SETTING_UPDATE_TIME, System.currentTimeMillis());
                    SettingRecord settingRecord = new SettingRecord(SettingUploadJobService.this.getApplicationContext());
                    settingRecord.startRecord();
                    settingRecord.endRecord();
                }
                SettingUploadJobService.this.jobFinished(jobParameters, false);
                SettingUploadJobService.scheduleSettingUploadJob(SettingUploadJobService.this.getApplicationContext());
            }
        }).start();
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
