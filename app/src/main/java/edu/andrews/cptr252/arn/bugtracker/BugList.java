package edu.andrews.cptr252.arn.bugtracker;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Manage list of bugs. This is a singleton class.
 * Only one instance of this class may be created.
 */
public class BugList {
    /** Instance variable for BugList */
    private static BugList sOurInstance;

    /** List of Bugs */
    private ArrayList<Bug> mBugs;
    /** Reference to information about app environment */
    private Context mAppContext;

    /** Tag for message log */
    private static final String TAG = "BugList";
    /** name of JSON file containing list of bugs */
    private static final String FILENAME = "bugs.json";
    /** Reference to JSON serializer for a list of bugs */
    private BugJSONSerializer mSerializer;

    /**
     * Write bug list to JSON File
     * @return true if successful, false otherwise
     */
    public boolean saveBugs() {
        try {
            mSerializer.saveBugs(mBugs);
            Log.d(TAG, "Bugs saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving bugs: " + e);
            return false;
        }
    }

    /**
     * Add a bug to the list
     * @param bug is the bug to be added
     */
    public void addBug(Bug bug) {
        mBugs.add(bug);
        saveBugs();
    }

    private BugList(Context appContext) {
        mAppContext = appContext;

        // create our serializer to load and save bugs
        mSerializer = new BugJSONSerializer(mAppContext, FILENAME);

        try {
            // load bugs from JSON file
            mBugs = mSerializer.loadBugs();
        } catch (Exception e) {
            // Unable to load from file, so create empty bug list
            // Either file does not exist (okay)
            // Or file contains error (not great)
            mBugs = new ArrayList<>();
            Log.e(TAG, "Error loading bugs: " + e);
        }
    }

    /**
     * Return one and only instance of the bug list.
     * (If it does not exist, create it).
     *
     * @param c is the Application context
     * @return Reference to the bug list
     */
    public static BugList getInstance(Context c) {
        if (sOurInstance == null) {
            sOurInstance = new BugList((c.getApplicationContext()));
        }
        return sOurInstance;
    }

    /**
     * Return list of bugs
     * @return Array of Bug objects
     */
    public ArrayList<Bug> getBugs() {
        return mBugs;
    }

    /**
     * Return the bug with the given id
     * @param id Unique id for the bug
     * @return Bug object or null if not found
     */
    public Bug getBug(UUID id) {
        for(Bug bug : mBugs) {
            if(bug.getId().equals(id))
                return bug;
        }
        return null;
    }
}
