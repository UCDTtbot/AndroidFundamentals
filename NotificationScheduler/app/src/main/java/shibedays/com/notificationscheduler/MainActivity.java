package shibedays.com.notificationscheduler;

import android.app.TaskStackBuilder;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private JobScheduler mScheduler;
    private static final int JOB_ID = 0;

    private Switch mDeviceIdle;
    private Switch mDeviceCharging;
    private SeekBar mSeekBar;
    private TextView mTimeLabel;
    private TextView mSeekBarProgress;
    private Switch mPeriodicSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeviceCharging = findViewById(R.id.chargingSwitch);
        mDeviceIdle = findViewById(R.id.idleSwitch);
        mSeekBar = findViewById(R.id.seekbar);
        mSeekBarProgress = findViewById(R.id.seekbarProgress);
        mPeriodicSwitch = findViewById(R.id.periodicSwitch);
        mTimeLabel = findViewById(R.id.seekbarLabel);

        mPeriodicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mTimeLabel.setText(R.string.periodic_interval);
                } else {
                    mTimeLabel.setText(R.string.override_deadline);
                }
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress > 0){
                    mSeekBarProgress.setText(String.valueOf(progress) + " s");
                } else {
                    mSeekBarProgress.setText("Not Set");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void scheduleJob(View view) {
        RadioGroup networkOptions = findViewById(R.id.networkOptions);
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int selectedNetworkID = networkOptions.getCheckedRadioButtonId();
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
        switch (selectedNetworkID){
            case R.id.noNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
            default:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
        }


        ComponentName serviceName = new ComponentName(getPackageName(), NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(selectedNetworkOption)
                .setRequiresDeviceIdle(mDeviceIdle.isChecked())
                .setRequiresCharging(mDeviceCharging.isChecked());

        int seekBarInt = mSeekBar.getProgress();
        boolean seekBarSet = seekBarInt > 0;

        if(mPeriodicSwitch.isChecked()){
            if(seekBarSet){
                builder.setPeriodic(seekBarInt * 1000);
            } else {
                Toast.makeText(this, "Please set a periodic interval", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (seekBarSet) {
                builder.setOverrideDeadline(seekBarInt * 1000);
            }
        }

        boolean constraintSet = (selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE)
                || mDeviceCharging.isChecked() || mDeviceIdle.isChecked()
                || seekBarSet;
        if(constraintSet){
            mScheduler.schedule(builder.build());
            Toast.makeText(this, R.string.job_scheduled, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please choose at least one constraint", Toast.LENGTH_SHORT).show();
        }


    }

    public void cancelJobs(View view) {
        if(mScheduler != null){
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(this, R.string.job_cancelled, Toast.LENGTH_SHORT).show();
        }
    }
}
