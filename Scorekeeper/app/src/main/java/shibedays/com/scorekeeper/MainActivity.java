package shibedays.com.scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private final String STATE_SCORE_1 = "stateScore1";
    private final String STATE_SCORE_2 = "stateScore2";

    private int mScore1 = 0;
    private int mScore2 = 0;

    private TextView mScoreText1;
    private TextView mScoreText2;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SCORE_1, mScore1);
        outState.putInt(STATE_SCORE_2, mScore2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScoreText1 = findViewById(R.id.score_one);
        mScoreText2 = findViewById(R.id.score_two);

        if(savedInstanceState != null){
            mScore1 = savedInstanceState.getInt(STATE_SCORE_1);
            mScore2 = savedInstanceState.getInt(STATE_SCORE_2);

            mScoreText1.setText(Integer.toString(mScore1));
            mScoreText2.setText(Integer.toString(mScore2));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.night_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
        recreate();
        return true;
    }

    public void increaseScore(View view){
        switch (view.getId()){
            case (R.id.plus_one):
                mScore1++;
                mScoreText1.setText(Integer.toString(mScore1));
                break;
            case (R.id.plus_two):
                mScore2++;
                mScoreText2.setText(Integer.toString(mScore2));
                break;
            default:
                Log.e(TAG, "Something wrong in increaseScore");
        }
    }

    public void decreaseScore(View view){
        switch (view.getId()){
            case (R.id.minus_one):
                mScore1--;
                mScoreText1.setText(Integer.toString(mScore1));
                break;
            case (R.id.minus_two):
                mScore2--;
                mScoreText2.setText(Integer.toString(mScore2));
                break;
            default:
                Log.e(TAG, "Something wrong in increaseScore");
        }
    }
}
