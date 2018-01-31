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
        super.onPostExecute(s);
        try{
            // get all json objects from our JSON string
            JSONObject jsonObject = new JSONObject(s);
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

                mBookTitle.setText("No Results Found");
                mBookAuthor.setText("");
            }
        } catch (Exception e){
            mBookTitle.setText("No Results Found");
            mBookAuthor.setText("");
            e.printStackTrace();
            Log.d(FetchBook.class.getSimpleName(), e.toString());
        }
    }
}
