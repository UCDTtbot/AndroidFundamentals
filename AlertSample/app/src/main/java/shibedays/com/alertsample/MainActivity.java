package shibedays.com.alertsample;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowAlert(View view) {
        AlertDialog.Builder myAlertBuilder = new
                AlertDialog.Builder(MainActivity.this);
        //Set dialog title
        myAlertBuilder.setTitle(R.string.alert_title);
        //Set the message
        myAlertBuilder.setMessage(R.string.alert_message);
        //Add buttons
        myAlertBuilder.setPositiveButton(R.string.alert_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.alert_pressed, Toast.LENGTH_SHORT).show();
            }
        });

        myAlertBuilder.setNegativeButton(R.string.alert_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.alert_canceled, Toast.LENGTH_SHORT).show();
            }
        });

        myAlertBuilder.show();
    }
}
