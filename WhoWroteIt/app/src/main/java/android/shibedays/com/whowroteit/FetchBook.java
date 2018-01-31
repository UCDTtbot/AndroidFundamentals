package android.shibedays.com.whowroteit;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ttbot on 1/31/18.
 */

public class FetchBook extends AsyncTask<String, Void, String> {

    private TextView mBookTitle;
    private TextView mBookAuthor;

    public FetchBook(TextView titleView, TextView authorView){
        mBookAuthor = authorView;
        mBookTitle = titleView;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {

    }
}
