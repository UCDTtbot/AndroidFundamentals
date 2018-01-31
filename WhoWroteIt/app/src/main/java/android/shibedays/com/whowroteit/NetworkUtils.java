package android.shibedays.com.whowroteit;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ttbot on 1/31/18.
 */

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?"; // Base URI for the books API
    private static final String QUERY_PARAM = "q"; // URI parameter for the search string
    private static final String MAX_RESULTS = "maxResults"; // URI parameter for limiting search result
    private static final String PRINT_TYPE = "printType"; // URI parameter to filter by print type


    static String getBookInfo(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try{
            // Build our URI string
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
            // Turn our URI into a URL
            URL requestURL = new URL(builtURI.toString());

            // Using our HttpURLConnection and our requestURL, connect to the internet
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Open InputStream to get the output JSON from our request
            InputStream inputStream = urlConnection.getInputStream();
            // Buffer to hold the input
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null){
                // input stream is null, nothing to return
                return null;
            }
            // Opens our buffered reader using our InputStream
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // String to hold the current line of our input stream
            String line;
            // while there still exists lines in our input stream
            while((line = reader.readLine()) != null){
                /* Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                but it does make debugging a *lot* easier if you print out the
                completed buffer for debugging
                 */
                // here, we are taking all of the lines from our input stream
                // adding a newline, and putting it into our buffer
                buffer.append(line + "\n");
            }
            if(buffer.length() < 1){
                // Stream was empty, return nothing
                return null;
            }
            /*
                After opening the URL request, getting the InputStream,
                opening an input reader using this input stream, and reading all of the
                input stream into a string buffer, we finally return the entirety
                of the string buffer as our full JSON string
             */
            bookJSONString = buffer.toString();
        } catch(IOException e ) {
            e.printStackTrace();
            return null;
        } finally {
            // We need to make sure we close the urlConnection and the reader
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, bookJSONString);
        return bookJSONString;
    }

}
