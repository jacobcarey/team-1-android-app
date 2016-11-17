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
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    public static final double NUMBER_OF_ANSWERS = 4;
    private JSONArray jsonArray;
    private TextView question;
    private int index = 0;
    private int numberOfQuestions;
    private int correctAnswer;
    private List<TextView> answerTextViews;

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
        TextView answer1 = (TextView) findViewById(R.id.answer1Text);
        TextView answer2 = (TextView) findViewById(R.id.answer2Text);
        TextView answer3 = (TextView) findViewById(R.id.answer3Text);
        TextView answer4 = (TextView) findViewById(R.id.answer4Text);

        answerTextViews = new ArrayList<TextView>();
        answerTextViews.add(answer1);
        answerTextViews.add(answer2);
        answerTextViews.add(answer3);
        answerTextViews.add(answer4);

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
            List<String> answers = new ArrayList<String>();
            answers.add("correctAnswer");
            answers.add("wrongAnswer1");
            answers.add("wrongAnswer2");
            answers.add("wrongAnswer3");
            Collections.shuffle(answers);
            correctAnswer = answers.indexOf("correctAnswer");

            JSONObject jsonObject = jsonArray.getJSONObject(index);
            question.setText((CharSequence) jsonObject.get("question"));
            for (int i = 0; i<NUMBER_OF_ANSWERS; i++)
                answerTextViews.get(i).setText((CharSequence) jsonObject.get(answers.get(i)));

            View.OnClickListener correctAnswerClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextQuestion();
                }
            };

            View.OnClickListener incorrectAnswerClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: give feedback of incorrect answer
                }
            };

            for (int i = 0; i<NUMBER_OF_ANSWERS; i++)
                answerTextViews.get(i).setOnClickListener(incorrectAnswerClick);
            answerTextViews.get(correctAnswer).setOnClickListener(correctAnswerClick);

            index += 1;
            // TODO: just loops for now
            if (index == numberOfQuestions) index = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
