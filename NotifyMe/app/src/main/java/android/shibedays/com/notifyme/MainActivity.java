package android.shibedays.com.notifyme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
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

    private static final String ACTION_UPDATE_NOTIF =
            "android.shibedays.com.notifyme.ACTION_UPDATE_NOTIF";

    private NotificationReceiver mNotifReceiver = new NotificationReceiver();

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

        mNotifyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sendNotif();
            }
        });

        mNotifyUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotif();
            }
        });

        mNotifyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotif();
            }
        });

        mNotifyButton.setEnabled(true);
        mNotifyUpdate.setEnabled(false);
        mNotifyCancel.setEnabled(false);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIF);
        registerReceiver(mNotifReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNotifReceiver);
    }

    public void sendNotif() {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notifPendingIntent = PendingIntent.getActivity(this, NOTIF_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri webpage = Uri.parse(getString(R.string.url_mat_design));
        Intent learnMoreIntent = new Intent(Intent.ACTION_VIEW, webpage);
        PendingIntent learnMorePendingIntent = PendingIntent.getActivity
                (this, NOTIF_ID, learnMoreIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent broadcastIntent = new Intent(ACTION_UPDATE_NOTIF);
        PendingIntent broadcastPendingIntent = PendingIntent.getBroadcast
                (this, NOTIF_ID, broadcastIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_notif)
                .setContentIntent(notifPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .addAction(R.drawable.ic_update, "Update", broadcastPendingIntent)
                .addAction(R.drawable.ic_info_outline_black_24dp, "Learn More", learnMorePendingIntent);
        Notification myNotif = notifBuilder.build();
        mNotifManager.notify(NOTIF_ID, myNotif);

        mNotifyButton.setEnabled(false);
        mNotifyUpdate.setEnabled(true);
        mNotifyCancel.setEnabled(true);
    }

    public void updateNotif() {
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(),R.drawable.mascot);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notifPendingIntent = PendingIntent.getActivity(this, NOTIF_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri webpage = Uri.parse(getString(R.string.url_mat_design));
        Intent learnMoreIntent = new Intent(Intent.ACTION_VIEW, webpage);
        PendingIntent learnMorePendingIntent = PendingIntent.getActivity
                (this, NOTIF_ID, learnMoreIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("You've been notified!")
                .setContentText("This notification is updated!.")
                .setSmallIcon(R.drawable.ic_notif)
                .setContentIntent(notifPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .addAction(R.drawable.ic_info_outline_black_24dp, "Learn More", learnMorePendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(androidImage)
                        .setBigContentTitle("Notification Updated!")
                );

        mNotifyButton.setEnabled(false);
        mNotifyUpdate.setEnabled(false);
        mNotifyCancel.setEnabled(true);

        mNotifManager.notify(NOTIF_ID, notifBuilder.build());

    }

    public void cancelNotif() {
        mNotifManager.cancel(NOTIF_ID);

        mNotifyButton.setEnabled(true);
        mNotifyUpdate.setEnabled(false);
        mNotifyCancel.setEnabled(false);
    }

    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver(){

        }

        @Override
        public void onReceive(Context context, Intent intent){
            updateNotif();
        }
    }
}
