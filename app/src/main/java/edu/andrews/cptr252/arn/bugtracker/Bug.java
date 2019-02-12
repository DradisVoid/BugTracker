package edu.andrews.cptr252.arn.bugtracker;

import java.util.UUID;

/**
 * Manage information for a specified bug
 */
public class Bug {
    /** Unique Id for the Bug */
    private UUID mId;
    /** Title of bug */
    private String mTitle;
    /**
     * Create and initialize a new Bug.
     */
    public Bug() {
        // Generate unique identifier for the new bug
        mId = UUID.randomUUID();
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
}
