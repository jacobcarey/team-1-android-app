package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class QuizActivity extends AppCompatActivity {

    private JSONArray jsonArray;
    private TextView question;
    private TextView answer1;
    private TextView answer2;
    private TextView answer3;
    private TextView answer4;
    private int index = 0;
    private int numberOfQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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

        // Get all references to the text fields
        question = (TextView) findViewById(R.id.questionText);
        answer1 = (TextView) findViewById(R.id.answer1Text);
        answer2 = (TextView) findViewById(R.id.answer2Text);
        answer3 = (TextView) findViewById(R.id.answer3Text);
        answer4 = (TextView) findViewById(R.id.answer4Text);

        // Read in the json file in and parse it
        try {
            InputStream is = getAssets().open("quiz_data.json");
            int size = is.available();
            byte[] buff = new byte[size];
            is.read(buff);
            is.close();
            String json = new String(buff, "UTF-8");
            jsonArray = new JSONArray(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        numberOfQuestions = jsonArray.length();
        nextQuestion();
    }

    public void nextQuestion() {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            question.setText((CharSequence) jsonObject.get("question"));
            answer1.setText((CharSequence) jsonObject.get("correctAnswer"));
            answer2.setText((CharSequence) jsonObject.get("wrongAnswer1"));
            answer3.setText((CharSequence) jsonObject.get("wrongAnswer2"));
            answer4.setText((CharSequence) jsonObject.get("wrongAnswer3"));
            // TODO: randomise the order of the answers

            // make the correct answer give some feedback
            answer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: check if the answer was the right one
                    nextQuestion();
                }
            });

            index += 1;
            // TODO: just loops for now
            if (index == numberOfQuestions) index = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
