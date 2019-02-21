package edu.andrews.cptr252.arn.bugtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import java.util.ArrayList;


/**
 * Fragment to display list of Bugs
 */
public class BugListFragment extends ListFragment {
    /** Tag for message log */
    private static final String TAG = "BugListFragment";

    /** Reference to list of bugs in display */
    private ArrayList<Bug> mBugs;

    public BugListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.bug_list_label);
        mBugs = BugList.getInstance(getActivity()).getBugs();
    }
}
