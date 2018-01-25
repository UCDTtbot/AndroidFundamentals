package shibedays.com.scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private int scoreTeamOne = 0;
    private int scoreTeamTwo = 0;

    private TextView viewTeamOne;
    private TextView viewTeamTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewTeamOne = findViewById(R.id.score_one);
        viewTeamTwo = findViewById(R.id.score_two);
    }

    public void increaseScore(View view){
        switch (view.getId()){
            case (R.id.plus_one):
                scoreTeamOne++;
                viewTeamOne.setText(Integer.toString(scoreTeamOne));
                break;
            case (R.id.plus_two):
                scoreTeamTwo++;
                viewTeamTwo.setText(Integer.toString(scoreTeamTwo));
                break;
            default:
                Log.e(TAG, "Something wrong in increaseScore");
        }
    }

    public void decreaseScore(View view){
        switch (view.getId()){
            case (R.id.minus_one):
                scoreTeamOne--;
                viewTeamOne.setText(Integer.toString(scoreTeamOne));
                break;
            case (R.id.minus_two):
                scoreTeamTwo--;
                viewTeamTwo.setText(Integer.toString(scoreTeamTwo));
                break;
            default:
                Log.e(TAG, "Something wrong in increaseScore");
        }
    }
}
