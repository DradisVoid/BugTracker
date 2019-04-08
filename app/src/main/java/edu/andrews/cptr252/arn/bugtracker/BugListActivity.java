package edu.andrews.cptr252.arn.bugtracker;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Activity for displaying list of bugs
 */
public class BugListActivity extends SingleFragmentActivity
        implements BugListFragment.Callbacks, BugDetailsFragment.Callbacks {

    @Override
    public void onBugSelected(Bug bug) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            // start an instance of bugDetailsActivity
            Intent i = new Intent(this, BugDetailsActivity.class);
            i.putExtra(BugDetailsFragment.EXTRA_BUG_ID, bug.getId());
            startActivityForResult(i, 0);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detail_fragment_container);
            Fragment newDetail = BugDetailsFragment.newInstance(bug.getId());

            if (oldDetail != null) {
                ft.remove(oldDetail);
            }

            ft.add(R.id.detail_fragment_container, newDetail);
            ft.commit();
        }
    }

    public void onBugUpdated(Bug bug) {
        FragmentManager fm = getSupportFragmentManager();
        BugListFragment listFragment = (BugListFragment)fm.findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    @Override
    protected Fragment createFragment() {
        return new BugListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }
}
