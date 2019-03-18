package edu.andrews.cptr252.arn.bugtracker;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Read/Write a list of bugs from/to a JSON file
 */
public class BugJSONSerializer {
    private Context mContext;
    private String mFilename;

    public BugJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    /**
     * Load list of bugs from JSON file on local device
     * @return the list of bugs read from file
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<Bug> loadBugs() throws IOException, JSONException {
        ArrayList<Bug> bugs = new ArrayList<>();
        BufferedReader reader = null;
        try {
            // open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // add next line from JSON file to StringBuilder
                jsonString.append(line);
            }
            // parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of bugs from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                bugs.add(new Bug(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // we will ignore this one, since it happens when we start fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return bugs;
    }

    /**
     * Write a list of bugs to local device
     * @param bugs is list of bugs to save
     * @throws JSONException
     * @throws IOException
     */
    public void saveBugs(ArrayList<Bug> bugs) throws JSONException, IOException {
        // build an array in JSON
        JSONArray array = new JSONArray();
        for (Bug bug : bugs)
            array.put(bug.toJSON());

        // write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
