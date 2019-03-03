package edu.andrews.cptr252.arn.bugtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;


/**
 * Show the details for a bug and allow editing
 */
public class BugDetailsFragment extends Fragment {
    /** Key used to pass the id of a bug */
    public static final String EXTRA_BUG_ID = "edu.andrews.cptr252.arn.bugtracker.bug_id";

    /** Tag for logging fragment messages */
    public static final String TAG = "BugDetailsFragment";

    /** Reference to bug description field */
    private EditText mDescriptionField;
    /** Reference to bug date button */
    private Button mDateButton;
    /** Reference to bug solved check box */
    private CheckBox mSolvedCheckBox;
    /** Bug that is begin viewed/edited */
    private Bug mBug;
    /** Reference to title field for bug*/
    private EditText mTitleField;

    public BugDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new BugDetailsFragment with a given Bug id as an argument
     * @param bugId
     * @return A reference to the new BugDetailsFragment
     */
    public static BugDetailsFragment newInstance(UUID bugId) {
        // Create a new argument Bundle object.
        // Add the Bug id as a n argument
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BUG_ID, bugId);
        // Create a new instance of BugDetailsFragment
        BugDetailsFragment fragment = new BugDetailsFragment();
        // Pass the bundle (containing the Bug id) to the fragment
        // THe bundle will be unpacked in the fragment's onCreate(Bundle) method
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Extract bug id from Bundle
        UUID bugId = (UUID)getArguments().getSerializable(EXTRA_BUG_ID);

        // Get the bug with the id from the Bundle.
        // This will be the bug that the fragment displays
        mBug = BugList.getInstance(getActivity()).getBug(bugId);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bug_details, container, false);

        // get reference to EditText box for bug title
        mTitleField = v.findViewById(R.id.bug_title);
        mTitleField.setText(mBug.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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

        // get reference to EditText box for bug description
        mDescriptionField = v.findViewById(R.id.bug_description);
        mDescriptionField.setText(mBug.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // left intentionally blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBug.setDescription(s.toString());
                Log.d(TAG, "Set description to "+s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                // left intentionally blank
            }
        });

        // get reference to bug date button.
        mDateButton = v.findViewById(R.id.bug_date);
        mDateButton.setText(mBug.getDate().toString());
        // disable button for now. we will enable it at a later time
        mDateButton.setEnabled(false);

        // get reference to solved check box
        mSolvedCheckBox = v.findViewById(R.id.bug_solved);
        mSolvedCheckBox.setChecked(mBug.isSolved());
        // toggle bug solved status when check box is tapped
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set the bugs's solved property
                mBug.setSolved(isChecked);
                Log.d(TAG, "Set solved status to "+isChecked);
            }
        });

        return v;
    }

}
