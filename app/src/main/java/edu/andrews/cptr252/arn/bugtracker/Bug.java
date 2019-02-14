package edu.andrews.cptr252.arn.bugtracker;

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

    /**
     * Create and initialize a new Bug.
     */
    public Bug() {
        // Generate unique identifier for the new bug
        mId = UUID.randomUUID();
        mDate = new Date();
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
