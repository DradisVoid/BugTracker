package edu.andrews.cptr252.arn.bugtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * Show the details for a bug and allow editing
 */
public class BugDetailsFragment extends Fragment {
    /** Tag for logging fragment messages */
    public static final String TAG = "BugDetailsFragment";
    /** Bug that is begin viewed/edited */
    private Bug mBug;
    /** Reference to title field for bug*/
    private EditText mTitleField;

    public BugDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBug = new Bug(); // create new bug
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bug_details, container, false);

        // get reference to EditText box for bug title
        mTitleField = v.findViewById(R.id.bug_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // left intentionally blank
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count) {
                // user typed text, update the bug title
                mBug.setTitle(s.toString());
                // write the new title to the message log for debugging
                Log.d(TAG, "Title changed to " + mBug.getTitle());
            }
            @Override
            public void afterTextChanged(Editable s) {
                // left intentionally blank
            }
        });
        return v;
    }

}
