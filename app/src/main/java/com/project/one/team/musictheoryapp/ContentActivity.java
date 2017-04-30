package com.project.one.team.musictheoryapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.PageIndicator;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Activity used for displaying topic tutorial content.</p>
 *
 * <p>Upon creation of this Activity, an intent extra is passed along from the difficulty topic select
 * activities specifying the topic of this tutorial. Using this extra, the content for the tutorial is
 * retrieved from JSON files and passed along to {@link ContentFragment} objects which represent
 * pages of content.</p>
 *
 * <p>The created ContentFragment objects are added to a {@link ContentPagerAdapter} which
 * is then passed to a {@link ViewPager}, allowing the user to swipe between the ContentFragment
 * pages.</p>
 *
 * <p>At the end of each ContentPagerAdapter, a {@link QuizLinkFragment} is added that
 * directs the user to the corresponding quiz for that topic.</p>
 *
 * @author Team One
 */

public class ContentActivity extends FragmentActivity {

    public static final String EXTRA_TOPIC = "topic";

    private JSONArray jsonArray;
    private TextView titleTextView;
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

        String contentFile;
        if (getIntent().hasExtra(EXTRA_TOPIC)) {
            contentFile = getIntent().getStringExtra(EXTRA_TOPIC) + "_content.json";

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

                PageIndicator pageIndicator = (LinePageIndicator) findViewById(R.id.contentPageIndicator);
                pageIndicator.setViewPager(viewPager);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // If the current topic does not have a quiz, simply increment the user's progress just
            // for reading the topic.
            if (!TopicParser.topicHasQuiz(ContentActivity.this, getIntent().getStringExtra(EXTRA_TOPIC))) {
                String topic = TopicParser.topicIDToTopic(getIntent().getStringExtra(EXTRA_TOPIC));
                String difficulty = TopicParser.topicIDToDifficulty(getIntent().getStringExtra(EXTRA_TOPIC));
                SharedPreferences progression = getSharedPreferences("progression", MODE_PRIVATE);

                int topic_index = Topics.getInstance(this).getTopics(difficulty).indexOf(topic);

                if (topic_index < Topics.getInstance(this).getTopics(difficulty).size()-1 && topic_index+1 > progression.getInt(difficulty, 0))
                    Progression.getInstance(this).increment(difficulty);
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
            String image;
            try
            {
                image = jsonArray.getJSONObject(i).getString("image");
            }
            catch(Exception e)
            {
                image = null;
            }
            JSONArray contentArray = jsonArray.getJSONObject(i).getJSONArray("content");
            ContentFragment fragment = ContentFragment.newInstance(title, image, contentArray.getString(0));
            fList.add(fragment);

            for (int j=1; j<contentArray.length(); j++) {
                ContentFragment untitledFragment = ContentFragment.newInstance(title, image, contentArray.getString(j));
                fList.add(untitledFragment);
            }
        }

            if (TopicParser.topicHasQuiz(ContentActivity.this, getIntent().getStringExtra(EXTRA_TOPIC)))
                // Add the fragment that contains the link to the quiz for this topic section
                // if we have a quiz file for that topic.
                fList.add(QuizLinkFragment.newInstance(getIntent().getStringExtra(EXTRA_TOPIC)));


        return fList;
    }

}
