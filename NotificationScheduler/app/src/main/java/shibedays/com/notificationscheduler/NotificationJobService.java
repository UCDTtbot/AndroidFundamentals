package shibedays.com.notificationscheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class NotificationJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Job Service")
                .setContentText("Your job is running!")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_working)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());
        // Return false if we don't need to run on a new thread
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        // Returning true reschedules the job if it fails
        return true;
    }
}
