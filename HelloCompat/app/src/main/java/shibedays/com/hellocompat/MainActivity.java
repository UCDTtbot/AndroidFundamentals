package shibedays.com.hellocompat;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mHelloText;

    // array of color names, these match the color resources in color.xml
    private String[] mColorArray = {"red",
            "pink",
            "purple",
            "deep_purple",
            "indigo",
            "blue",
            "light_blue",
            "cyan",
            "teal",
            "green",
            "light_green",
            "lime",
            "yellow",
            "amber",
            "orange",
            "deep_orange",
            "brown",
            "grey",
            "blue_grey",
            "black" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelloText = findViewById(R.id.hello_textview);

        if(savedInstanceState != null){
            mHelloText.setTextColor(savedInstanceState.getInt("color"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        //save the current text color
        outState.putInt("color", mHelloText.getCurrentTextColor());
    }


    public void changeColor(View view) {
        Random random = new Random();
        String colorName = mColorArray[random.nextInt(20)];
        int colorResourceName = getResources().getIdentifier(colorName, "color", getApplicationContext().getPackageName());

        int colorRes = ContextCompat.getColor(this, colorResourceName);
        mHelloText.setTextColor(colorRes);
        //API 15 call
        //int colorRes = getResources().getColor(colorResourceName);
        //API 23+ call
        //int colorRes = getResources().getColor(colorResourceName, this.getTheme());
    }
}
