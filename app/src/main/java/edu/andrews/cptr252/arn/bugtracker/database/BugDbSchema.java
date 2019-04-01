package edu.andrews.cptr252.arn.bugtracker.database;

/**
 * Define schema for bug database
 */
public class BugDbSchema {
    public static final class BugTable {
        public static final String NAME = "bugs";

        /**
         * DB column names
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
