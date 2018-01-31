package android.shibedays.com.whowroteit;

import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

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

        // if we need to re-get the loader, check if it exists with the previous ID
        if(getSupportLoaderManager().getLoader(0) != null){
            // if the loader exists, we initialize it
            getSupportLoaderManager().initLoader(0,null,this);

            // We are only reassociating the loader to the activity if a query has already been executed.
            // In the initial state of the app, no data is loaded so there is none to preserve
        }
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
        try{
            // get all json objects from our JSON string
            JSONObject jsonObject = new JSONObject(data);
            // get all the items from the JSON object
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for(int i = 0; i<itemsArray.length(); i++) {
                // get the current item object at num i
                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                String author = null;
                // Get the info of the current book
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    author = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // if both Title and Author exist, update the textviews and return
                if (title != null && author != null) {
                    mBookTitle.setText(title);
                    mBookAuthor.setText(author);
                    return;
                }

                mBookTitle.setText(R.string.no_results_found);
                mBookAuthor.setText("");
            }
        } catch (Exception e){
            mBookTitle.setText(R.string.no_results_found);
            mBookAuthor.setText("");
            e.printStackTrace();
            Log.d(MainActivity.class.getSimpleName(), e.toString());
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
