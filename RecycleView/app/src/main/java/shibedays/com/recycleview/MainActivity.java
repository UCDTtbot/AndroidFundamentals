package shibedays.com.recycleview;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private final LinkedList<String> mWordList = new LinkedList<>();
    private int mCount = 0;

    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int x = 0; x < 20; x++){
            mWordList.addLast("Word" + Integer.toString(mCount++));
            Log.d(TAG, mWordList.getLast());
        }

        //Get a handle to the RecyclerView (in the XML)
        mRecyclerView = findViewById(R.id.recycler_view);
        //Create an adapater and supply the data to be displayed.
        mAdapater = new WordListAdapter(this, mWordList);
        //Connect the adapter with the RecyclerView
        mRecyclerView.setAdapter(mAdapater);
        //Give the recyclerView a default layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add a floating action click handler for creating new entries
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wordListSize = mWordList.size();
                // Add a new word to the end of the wordList
                mWordList.add("+ Word " + wordListSize);
                // Notify the adapter that the data has changed so it can
                // update the RecyclerView to display the data
                mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
                // Scroll to the bottom
                mRecyclerView.smoothScrollToPosition(wordListSize);
            }
        });
    }
}
