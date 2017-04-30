package com.project.one.team.musictheoryapp;

import android.app.AlertDialog;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Helper class for getting a list of topics within a given difficulty.</p>
 *
 * @author Team One
 */

public class Topics {
    static JSONObject topics;
    private static Topics ourInstance;
    private Context context;

    public static Topics getInstance(Context ct) {
        if (ourInstance == null)
            ourInstance = new Topics(ct);

        return ourInstance;
    }

    private Topics(Context ct) {
        context = ct;
        initialise();
    }

    private void initialise() {
        try {
            InputStream is = context.getAssets().open("topics.json");
            int size = is.available();
            byte[] buff = new byte[size];
            is.read(buff);
            is.close();
            String json = new String(buff, "UTF-8");
            topics = new JSONObject(json);
        } catch (IOException | JSONException e) {
            new AlertDialog.Builder(context)
                    .setTitle("Error:")
                    .setMessage(e.toString())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public List<String> getTopics(String difficulty) {
        List list = new ArrayList<String>();
        try {
            JSONArray arr = topics.getJSONArray(difficulty);
            for (int i=0; i<arr.length(); i++)
                list.add(arr.getString(i));

        } catch (JSONException e) {
            new AlertDialog.Builder(context)
                    .setTitle("Error:")
                    .setMessage(e.toString())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return list;
    }
}
