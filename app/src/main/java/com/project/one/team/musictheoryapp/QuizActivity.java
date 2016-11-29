package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    public static final String ARGS_TOPIC = "topic";

    public static final int NUMBER_OF_ANSWERS = 4;
    private JSONArray jsonArray;
    private TextView questionTextView;
    private int index = 0;
    private int numberOfQuestions;
    private int correctAnswer;
    private List<TextView> answerTextViews;
    private List<String> answers;
    private Toast wrongAnswerMsg;

    public final View.OnClickListener CORRECT_ANSWER_CLICK = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nextQuestion();
        }
    };

    public final View.OnClickListener INCORRECT_ANSWER_CLICK = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            wrongAnswerMsg.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        wrongAnswerMsg = Toast.makeText(getApplicationContext(), "Wrong answer!", Toast.LENGTH_SHORT);

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
        questionTextView = (TextView) findViewById(R.id.questionText);
        answerTextViews = new ArrayList<TextView>();
        answerTextViews.add((TextView) findViewById(R.id.answer1Text));
        answerTextViews.add((TextView) findViewById(R.id.answer2Text));
        answerTextViews.add((TextView) findViewById(R.id.answer3Text));
        answerTextViews.add((TextView) findViewById(R.id.answer4Text));
        answerTextViews = Collections.unmodifiableList(answerTextViews);

        if (getIntent().hasExtra(ARGS_TOPIC)) {
            String topic = getIntent().getStringExtra(ARGS_TOPIC);
            // Read in the json file in and parse it
            try {
                InputStream is = getAssets().open(topic + "_quiz.json");
                int size = is.available();
                byte[] buff = new byte[size];
                is.read(buff);
                is.close();
                String json = new String(buff, "UTF-8");
                jsonArray = new JSONArray(json);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else {
            //throw new Exception("Topic info needed");
        }

        // Initialise the json key names
        String[] temp = {"correctAnswer", "wrongAnswer1", "wrongAnswer2", "wrongAnswer3"};
        answers = Arrays.asList(temp);

        // Show the first question
        numberOfQuestions = jsonArray.length();
        nextQuestion();
    }

    public void nextQuestion() {
        try {
            // Randomise the order of the json keys
            Collections.shuffle(answers);
            correctAnswer = answers.indexOf("correctAnswer");

            // Get the next question, show it on screen
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            questionTextView.setText((CharSequence) jsonObject.get("question"));
            for (int i = 0; i<NUMBER_OF_ANSWERS; i++)
                answerTextViews.get(i).setText((CharSequence) jsonObject.get(answers.get(i)));

            // Set the click actions for the answers
            for (int i = 0; i<NUMBER_OF_ANSWERS; i++)
                answerTextViews.get(i).setOnClickListener(INCORRECT_ANSWER_CLICK);
            answerTextViews.get(correctAnswer).setOnClickListener(CORRECT_ANSWER_CLICK);

            index += 1;
            // TODO: just loops for now
            if (index == numberOfQuestions) index = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
