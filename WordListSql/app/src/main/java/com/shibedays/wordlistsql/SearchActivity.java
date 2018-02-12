package com.shibedays.wordlistsql;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText mEditText;
    private WordListOpenHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mTextView = findViewById(R.id.search_result);
        mEditText = findViewById(R.id.search_word);
        mDB = new WordListOpenHelper(this);
    }

    public void showResult(View view) {
        String word = mEditText.getText().toString();
        mTextView.setText("Search term: " + word + ":\n\n");
        Cursor cur = mDB.search(word);

        if(cur != null && cur.getCount() > 0){
            cur.moveToFirst();
            int index;
            String result;
            do{
                index = cur.getColumnIndex(WordListOpenHelper.KEY_WORD);
                result = cur.getString(index);
                mTextView.append(result + "\n");
            } while(cur.moveToNext());
            cur.close();
        }
    }
}
