package android.shibedays.com.notifyme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mNotifyButton;
    private Button mNotifyUpdate;
    private Button mNotifyCancel;
    private NotificationManager mNotifManager;

    //give our notification a unique ID
    private static final int NOTIF_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotifyButton = findViewById(R.id.notify);
        mNotifyUpdate = findViewById(R.id.update);
        mNotifyCancel = findViewById(R.id.cancel);
        mNotifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }

    public void sendNotif(View view) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notifPendingIntent = PendingIntent.getActivity(this, NOTIF_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_notif)
                .setContentIntent(notifPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification myNotif = notifBuilder.build();
        mNotifManager.notify(NOTIF_ID, myNotif);
    }

    public void updateNotif(View view) {
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(),R.drawable.mascot);
    }

    public void cancelNotif(View view) {
        mNotifManager.cancel(NOTIF_ID);
    }
}
