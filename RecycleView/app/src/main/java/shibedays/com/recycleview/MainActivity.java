package shibedays.com.recycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private LinkedList mWordList;
    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int x = 0; x < 20; x++){
            mWordList.addLast("Word" + Integer.toString(mCount++));
            Log.d(TAG, mWordList.getLast().toString());
        }
    }
}
