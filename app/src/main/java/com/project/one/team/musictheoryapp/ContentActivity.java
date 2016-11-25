package com.project.one.team.musictheoryapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends FragmentActivity implements ContentFragment.OnFragmentInteractionListener {

    private JSONArray jsonArray;
    private TextView titleTextView;
    private List<TextView> contentTextViews;
    private View selectedView;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        /*
        // Enable the back button on the quiz activity
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Enable the settings button on the quiz activity
        ((Button) findViewById(R.id.quizSettingsButton)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
        */

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        contentTextViews = new ArrayList<>();

        String contentFile;
        if (getIntent().hasExtra("topic")) {
            contentFile = getIntent().getStringExtra("topic") + "_content.json";

            // Read in the json file in and parse it
            try {
                InputStream is = getAssets().open(contentFile);
                int size = is.available();
                byte[] buff = new byte[size];
                is.read(buff);
                is.close();
                String json = new String(buff, "UTF-8");
                jsonArray = new JSONArray(json);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            try {
                List<Fragment> fragments = getFragments();
                pagerAdapter = new ContentPagerAdapter(getSupportFragmentManager(), fragments);

                final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
                viewPager.setAdapter(pagerAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            //throw new Exception("No extra provided...");
            titleTextView.setText("No extra provided...");
        }
    }

    private List<Fragment> getFragments() throws JSONException {
        List<Fragment> fList = new ArrayList<>();

        for (int i=0; i<jsonArray.length(); i++) {
            String title = jsonArray.getJSONObject(i).getString("title");
            JSONArray contentArray = jsonArray.getJSONObject(i).getJSONArray("content");
            ContentFragment fragment = ContentFragment.newInstance(title, contentArray.getString(0));
            fList.add(fragment);

            for (int j=1; j<contentArray.length(); j++) {
                ContentFragment untitledFragment = ContentFragment.newInstance("", contentArray.getString(j));
                fList.add(untitledFragment);
            }
        }

        return fList;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO: ?
    }
}
