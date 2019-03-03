package edu.andrews.cptr252.arn.bugtracker;

import android.support.v4.app.Fragment;

import java.util.UUID;

public class BugDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        // BugListFragment now launches BugDetailsActivity with a specific Bug id
        // Get the Intent sent to this activity from the BugListFragment
        UUID bugId = (UUID)getIntent().getSerializableExtra(BugDetailsFragment.EXTRA_BUG_ID);

        // Create a new instance of the BugDetailsFragment with the Bug id as an argument
        return BugDetailsFragment.newInstance(bugId);
    }
}
