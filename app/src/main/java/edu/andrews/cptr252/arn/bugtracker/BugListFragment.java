package edu.andrews.cptr252.arn.bugtracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

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

    /**
     * Adapter responsible for getting the view for a bug
     */
    private class BugAdapter extends ArrayAdapter<Bug> {

        public BugAdapter(ArrayList<Bug> bugs) {
            super(getActivity(), 0, bugs);
        }

        /**
         * Return the view for a given bug in the list
         * @param position Position of view in the list
         * @param convertView Existing view that can still be used
         * @param parent The layout that contains the views generated
         * @return View containing bug information
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, infalte one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_bug, null);
            }

            // Configure the view for this bug
            Bug bug = getItem(position);

            // set the title for the bug in the view
            TextView titleTextView =
                    convertView.findViewById(R.id.bug_list_item_titleTextView);
            titleTextView.setText(bug.getTitle());
            // set the date bug was logged in the view
            TextView dateTextView =
                    convertView.findViewById(R.id.bug_list_item_dateTextView);
            dateTextView.setText(bug.getDate().toString());

            // show whether bug is solved or not
            CheckBox solvedCheckBox =
                    convertView.findViewById(R.id.bug_ilist_item_solvedCheckBox);
            solvedCheckBox.setChecked(bug.isSolved());
            return convertView;
        }
    } // end BugAdapter

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.bug_list_label);
        mBugs = BugList.getInstance(getActivity()).getBugs();

        // use our custom bug adapter for generating views for each bug
        BugAdapter adapter = new BugAdapter(mBugs);
        setListAdapter(adapter);
    }

    /**
     * Respond to user "click" on individual bugs in list.
     * @param l ListView containing the item that was clicked
     * @param v View that was clicked within the ListView
     * @param position Position of view in list
     * @param id row id of item that was clicked
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Bug bug = (Bug)(getListAdapter()).getItem(position);

        // start an instance of Bug Details Fragment
        Intent i = new Intent(getActivity(), BugDetailsActivity.class);
        // pass the id of the bug as an intent
        i.putExtra(BugDetailsFragment.EXTRA_BUG_ID, bug.getId());
        startActivity(i);
    }

    /**
     * Bug list fragment was paused (user was most likely editing a bug).
     * Notify the adapter that the data set (Bug list) may have changed.
     * THe adapter should update the views.
     */
    @Override
    public void onResume() {
        super.onResume(); // first execute the parent's onResume method
        ((BugAdapter)getListAdapter()).notifyDataSetChanged();
    }
}
