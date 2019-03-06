package edu.andrews.cptr252.arn.bugtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

public class BugDetailsActivity extends FragmentActivity {
    /** Viewpager component that allows you to browse bugs by swiping */
    private ViewPager mViewPager;

    /** Array of bugs */
    private ArrayList<Bug> mBugs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the view pager
        mViewPager = new ViewPager(this);
        // ViewPager needs a resource id
        mViewPager.setId(R.id.viewPager);
        // Set the ViewPager for this activity to be the ViewPager
        // (Previously, it used the activity_fragment layout)
        setContentView(mViewPager);

        // get the list of bugs
        mBugs = BugList.getInstance(this).getBugs();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            // Create a new BugDetailsFragment for bug at given position in list.
            @Override
            public Fragment getItem(int i) {
                Bug bug = mBugs.get(i);
                // Create a new instance of the BugDetailsFragment
                // with the Bug id as an argument.
                return BugDetailsFragment.newInstance(bug.getId());
            }

            @Override
            public int getCount() {
                return mBugs.size();
            }
        });

        // BugListFragment now launches BugDetailsActivity with a specific bug id.
        // Get the Intent sent to this activity from the BugListFragment.
        UUID bugId = (UUID)getIntent().getSerializableExtra(BugDetailsFragment.EXTRA_BUG_ID);

        // Search through the list of bugs until we find the bug
        // with the same id as the one extracted from the intent.
        for (int i = 0; i < mBugs.size(); i++) {
            if (mBugs.get(i).equals(bugId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        // If there is an App Bar (aka ActionBar),
        // display the title of the current bug there.
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Bug bug = mBugs.get(i);
                if (bug.getTitle() != null) {
                    setTitle(bug.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
