package shibedays.com.datetimepickers;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(),  this, hour, minute, true);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        MainActivity act = (MainActivity) getActivity();
        act.processTimePickerResult(hourOfDay, minute);
    }
}
