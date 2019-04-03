package edu.andrews.cptr252.arn.bugtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import edu.andrews.cptr252.arn.bugtracker.database.BugCursorWrapper;
import edu.andrews.cptr252.arn.bugtracker.database.BugDbHelper;
import edu.andrews.cptr252.arn.bugtracker.database.BugDbSchema.BugTable;


/**
 * Manage list of bugs. This is a singleton class.
 * Only one instance of this class may be created.
 */
public class BugList {
    /** Instance variable for BugList */
    private static BugList sOurInstance;

    /** SQLite DB where bugs are stored */
    private SQLiteDatabase mDatabase;

    /**
     * Pack bug information into a ContentValues object
     * @param bug to pack
     * @return resulting ContentValues object
     */
    public static ContentValues getContentValues(Bug bug) {
        ContentValues values = new ContentValues();
        values.put(BugTable.Cols.UUID, bug.getId().toString());
        values.put(BugTable.Cols.TITLE, bug.getTitle());
        values.put(BugTable.Cols.DESCRIPTION, bug.getDescription());
        values.put(BugTable.Cols.DATE, bug.getDate().getTime());
        values.put(BugTable.Cols.SOLVED, bug.isSolved() ? 1 : 0);

        return values;
    }

    private BugCursorWrapper queryBugs(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                BugTable.NAME,
                null,   // Columns - null selects all columns
                whereClause,
                whereArgs,
                null,   // groupBy
                null,   //having
                null    //orderby
        );

        return new BugCursorWrapper(cursor);
    }

    /** Reference to information about app environment */
    private Context mAppContext;

    /** Tag for message log */
    private static final String TAG = "BugList";
    /** name of JSON file containing list of bugs */
    private static final String FILENAME = "bugs.json";
    /** Reference to JSON serializer for a list of bugs */
    private BugJSONSerializer mSerializer;

    /**
     * Add a bug to the list
     * @param bug is the bug to be added
     */
    public void addBug(Bug bug) {
        ContentValues values = getContentValues(bug);
        mDatabase.insert(BugTable.NAME, null, values);
    }

    /**
     * Update information for a given bug
     * @param bug contains the latest information for the bug
     */
    public void updateBug(Bug bug) {
        String uuidString = bug.getId().toString();
        ContentValues values = getContentValues(bug);

        mDatabase.update(BugTable.NAME, values, BugTable.Cols.UUID + " = ? ",
                new String[] { uuidString} );
    }

    /**
     * Delete a given bug from list of bugs
     * @param bug is the bug to delete
     */
    public void deleteBug(Bug bug) {
        String uuidString = bug.getId().toString();
        mDatabase.delete(BugTable.NAME, BugTable.Cols.UUID + " = ? ",
                new String[] { uuidString} );
    }

    private BugList(Context appContext) {
        mAppContext = appContext.getApplicationContext();

        // Open DB file or create it if it does not already exist
        // If the DB is older version, onUpgrade will be called
        mDatabase = new BugDbHelper(mAppContext).getWritableDatabase();
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
        ArrayList<Bug> bugs = new ArrayList<>();
        BugCursorWrapper cursor = queryBugs(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                bugs.add(cursor.getBug());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return bugs;
    }

    /**
     * Return the bug with the given id
     * @param id Unique id for the bug
     * @return Bug object or null if not found
     */
    public Bug getBug(UUID id) {
        BugCursorWrapper cursor = queryBugs(BugTable.Cols.UUID + " = ? ",
                new String[] { id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getBug();
        } finally {
            cursor.close();
        }
    }

    /**
     * Return file containing image for a given bug
     * @param bug is the bug to get image for
     * @return file containing image
     */
    public File getPhotoFile(Bug bug) {
        File externalFilesDir = mAppContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, bug.getPhotoFilename());
    }
}
