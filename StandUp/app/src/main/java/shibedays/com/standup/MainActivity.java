package shibedays.com.standup;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton mToggleButton;
    private TextView mAlarmTitle;

    private NotificationManager mNotifManager;

    static private final int NOTIF_ID = 0;
    private static final String ACTION_NOTIF = "shibedays.com.standup.ACTION_NOTIFY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mToggleButton = findViewById(R.id.alarmToggle);
        mAlarmTitle = findViewById(R.id.textAlarmTitle);
        mNotifManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Intent notifyIntent = new Intent(ACTION_NOTIF);
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIF_ID, notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        mToggleButton.setChecked(alarmUp);
        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIF_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toastMessage;
                if(isChecked){
                    toastMessage = getString(R.string.alarm_set);
                    long triggerTime = SystemClock.elapsedRealtime() + 30000;
                    long repeatTime = 30000;
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatTime, notifyPendingIntent);
                } else {
                    toastMessage = getString(R.string.alarm_off);
                    mNotifManager.cancelAll();
                    alarmManager.cancel(notifyPendingIntent);
                }
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deliverNotif(Context context){

    }
}
