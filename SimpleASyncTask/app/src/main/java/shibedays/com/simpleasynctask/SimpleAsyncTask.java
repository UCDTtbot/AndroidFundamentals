package shibedays.com.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by ttbot on 1/30/2018.
 */

// The three parameters are:
    // Params for the doInBackground function
    // The progress Data type
    // The return data type
public class SimpleAsyncTask extends AsyncTask <Void, Void, String> {

    private TextView mTextView;

    public SimpleAsyncTask(TextView  tv){
        mTextView = tv;
    }

    @Override
    protected String doInBackground(Void... voids) {
        // Generate a random number between 0 and 10
        Random ran = new Random();
        int ranNum = ran.nextInt(11);

        // Make the task take long enough that we have
        // time to rotate the phone while it is running
        int sleepNum = ranNum * 500;

        // Sleep for the random amount of time
        try{
            Thread.sleep(sleepNum);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        // Return a String Result
        // This value is automatically passed to the onPostExecute()
        return "Awake at last after sleeping for " + sleepNum + " milliseconds!";
    }

    @Override
    protected void onPostExecute(String result){
        mTextView.setText(result);
    }

    public TextView getmTextView() {
        return mTextView;
    }

    public void setmTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }
}
