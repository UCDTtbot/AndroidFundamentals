package android.shibedays.com.whowroteit;

import android.content.Context;
import android.shibedays.com.whowroteit.NetworkUtils;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by ttbot on 1/31/18.
 */

public class BookLoader extends AsyncTaskLoader<String> {

    private String mQueryString;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        // forceLoad() is required to force the loadInBackground() method to actually start loading

        forceLoad();
    }

    public BookLoader(Context context, String query) {
        super(context);

        mQueryString = query;
    }

    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);
    }
}
