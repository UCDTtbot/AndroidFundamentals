package android.shibedays.com.whowroteit;

import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private TextView mBookTitle;
    private TextView mBookAuthor;
    private EditText mInputBookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookTitle = findViewById(R.id.bookTitleText);
        mBookAuthor = findViewById(R.id.bookAuthorText);
        mInputBookTitle = findViewById(R.id.bookInput);
    }

    public void searchBooks(View view) {
        String query = mInputBookTitle.getText().toString();
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() && query.length() != 0){
            Bundle queryBundle = new Bundle();
            queryBundle.putString("query", query);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            mBookTitle.setText(R.string.loading_message);
            mBookAuthor.setText("");
        } else {
            if(query.length() < 1){
                mBookAuthor.setText("");
                mBookTitle.setText(R.string.message_no_input);
            } else {
                mBookAuthor.setText("");
                mBookTitle.setText(R.string.message_no_network);
            }
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, args.getString("query"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
