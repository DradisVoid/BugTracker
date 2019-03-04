package edu.andrews.cptr252.arn.bugtracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Fragment for selecting date bug appeared
 */
public class DatePickerFragment extends DialogFragment {
    /** Key for sending bug data between fragments */
    public static final String EXTRA_DATE = "BugTracker.DATE";

    /** Date bug discovered */
    Date mDate;

    /**
     * Create a new isntance of fragment
     * @param date Given date when bug appears
     * @return an instance of the date picker fragment
     */
    public static DatePickerFragment newInstance(Date date) {
        // create a new bundle that will hold the date
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        // Create the new fragment
        DatePickerFragment fragment = new DatePickerFragment();
        // pass the new fragment the bundle with the date
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Let the calling fragment know whether the new date was accepted or canceled.
     * @param resultCode is RESULT_OK if user clicks OK button.
     */
    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            // no target fragment, nowhere to send date
            return;
        }
        // Create an intent to send back
        // to calling fragment (e.g. BugDetailsFragment)
        Intent i = new Intent();
        // add date to intent
        i.putExtra(EXTRA_DATE, mDate);
        // send intent back to calling fragment along with resultCode
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }
    /**
     * Executed when the dialog is created.
     * Construct the view for the dialog.
     * @param savedInstanceState is the argument bundle containing initial date
     * @return the created dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Extract the date from the argument bundle
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
        // Access the day, month and year from the date object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // Inflate the view for the date picker using our layout
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);
        // Get a reference to the date picker object
        DatePicker datePicker = v.findViewById(R.id.dialog_date_datePicker);
        // Initialize the date picker with our given date
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            // Every time the date is changed in the date picker,
            // write the new date to the argument bundle so the date
            // is preserved on rotation.
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                mDate = new GregorianCalendar(year, month, day).getTime();
                // update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        // build the dialog and return it.
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                    // when the user clicks OK, create an intent with the new date
                    // and send it back to the caller (BugDetailsFragment in our case)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
}
