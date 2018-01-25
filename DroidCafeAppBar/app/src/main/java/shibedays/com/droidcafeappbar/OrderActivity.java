package shibedays.com.droidcafeappbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = OrderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    public void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.same_day:
                if(checked)
                    //Same day
                    displayToast(getString(R.string.chosen) + getString(R.string.radio_same_day));
                break;
            case R.id.next_day:
                if(checked)
                    //next day
                    displayToast(getString(R.string.chosen) + getString(R.string.radio_next_day));
                break;
            case R.id.pickup:
                //pickup
                if(checked)
                    displayToast(getString(R.string.chosen) + getString(R.string.radio_pick_up));
                break;
            default:
                Log.e(TAG, getString(R.string.nothing_clicked));
                break;
        }
    }
}
