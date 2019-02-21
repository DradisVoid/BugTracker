package edu.andrews.cptr252.arn.bugtracker;

import android.support.v4.app.Fragment;

/**
 * Activity for displaying list of bugs
 */
public class BugListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new BugListFragment();
    }
}
