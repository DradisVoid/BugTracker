package edu.andrews.cptr252.arn.bugtracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Manage information for a specified bug
 */
public class Bug {
    /** Unique Id for the Bug */
    private UUID mId;
    /** Title of bug */
    private String mTitle;
    /** Description of bug */
    private String mDescription;
    /** Date when bug logged */
    private Date mDate;
    /** Has the bug been solved? */
    private boolean mSolved;

    /** JSON attribute for bug id */
    private static final String JSON_ID = "id";
    /** JSON attribute for bug title */
    private static final String JSON_TITLE = "title";
    /** JSON attribute for bug description */
    private static final String JSON_DESCRIPTION = "description";
    /** JSON attribute for bug date */
    private static final String JSON_DATE = "date";
    /** JSON attribute for bug solved status */
    private static final String JSON_SOLVED = "solved";

    /**
     * Initialize a new bug from a JSON object
     * @param json is the JSON object for a bug
     * @throws JSONException
     */
    public Bug(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.optString(JSON_TITLE);
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getLong(JSON_DATE));
        mDescription = json.optString(JSON_DESCRIPTION);
    }

    /**
     * Write the bug to a JSON object
     * @return JSON object containing the bug information
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_ID, mId.toString());
        jsonObject.put(JSON_TITLE, mTitle);
        jsonObject.put(JSON_DATE, mDate.getTime());
        jsonObject.put(JSON_SOLVED, mSolved);
        jsonObject.put(JSON_DESCRIPTION, mDescription);

        return jsonObject;
    }

    /**
     * Create and initialize a new Bug
     * @param id UUID for new bug
     */
    public Bug(UUID id) {
        mId = id;
        mDate = new Date();
    }

    /**
     * Create and initialize a new Bug with random UUID.
     */
    public Bug() {
        this(UUID.randomUUID());
    }
    /**
     * Return unique id for Bug.
     * @return Bug Id
     */
    public UUID getId() {
        return mId;
    }
    /**
     * Return the title for the bug.
     * @return Title of Bug
     */
    public String getTitle() {
        return mTitle;
    }
    /**
     * Provide a new title for the bug.
     * @param title New title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    // getters and setters for private fields
    public String getDescription() {
        return mDescription;
    }

    // set the description for a bug
    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
