package edu.andrews.cptr252.arn.bugtracker;

import android.support.v4.app.Fragment;

public class BugDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new BugDetailsFragment();
    }
}
