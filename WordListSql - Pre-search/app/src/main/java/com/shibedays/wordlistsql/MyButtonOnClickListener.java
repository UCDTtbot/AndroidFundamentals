package com.shibedays.wordlistsql;

import android.view.View;

/**
 * Created by ttbot on 2/11/2018.
 */

public class MyButtonOnClickListener implements View.OnClickListener {
    private static final String TAG = View.OnClickListener.class.getSimpleName();

    int id;
    String word;

    public MyButtonOnClickListener(int id, String word) {
        this.id = id;
        this.word = word;
    }

    public void onClick(View v) {
        // Implemented in WordListAdapter
    }
}
