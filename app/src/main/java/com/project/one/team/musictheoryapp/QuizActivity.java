package com.project.one.team.musictheoryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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

    public static final String EXTRA_TOPIC = "topic";

    //public static final int NUMBER_OF_ANSWERS = 4;
    private static final int MULTI_CHOICE_ANSWERS = 4;
    private static final int TRUE_FALSE_ANSWERS = 2;
    private JSONArray jsonArray;
    private String topic;
    private TextView questionTextView;
    private int index = 0;
    private int numberOfQuestions;
    private int correctAnswer;
    private int quizMarks;
    private List<TextView> answerTextViews;
    private List<String> answers;
    private ProgressBar qProgress;
    final Handler handler = new Handler();

    public final View.OnClickListener CORRECT_ANSWER_CLICK = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setBackgroundColor(Color.GREEN);
            quizMarks += 1;
            ((TextView)findViewById(R.id.marks)).setText("Marks: "+quizMarks);
            findViewById(R.id.answer1Text).setClickable(false);
            findViewById(R.id.answer2Text).setClickable(false);
            findViewById(R.id.answer3Text).setClickable(false);
            findViewById(R.id.answer4Text).setClickable(false);
            handler.postDelayed(new Runnable() {
                public void run() {
                    nextQuestion();
                }
            }, 1000);

        }
    };

    public final View.OnClickListener INCORRECT_ANSWER_CLICK = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          //  Toast.makeText(getApplicationContext(), "Wrong answer!", Toast.LENGTH_SHORT).show();
            view.setBackgroundColor(Color.RED);
            findViewById(R.id.answer1Text).setClickable(false);
            findViewById(R.id.answer2Text).setClickable(false);
            findViewById(R.id.answer3Text).setClickable(false);
            findViewById(R.id.answer4Text).setClickable(false);
            handler.postDelayed(new Runnable() {
                public void run() {
                    nextQuestion();
                }
            }, 2000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get all references to the text fields
        questionTextView = (TextView) findViewById(R.id.questionText);
        answerTextViews = Collections.unmodifiableList(Arrays.asList(
                (TextView) findViewById(R.id.answer1Text),
                (TextView) findViewById(R.id.answer2Text),
                (TextView) findViewById(R.id.answer3Text),
                (TextView) findViewById(R.id.answer4Text)
        ));
        qProgress =(ProgressBar)findViewById(R.id.progressBar);

        if (getIntent().hasExtra(EXTRA_TOPIC)) {
            topic = getIntent().getStringExtra(EXTRA_TOPIC);

            // Read in the json file in and parse it
            try {
                InputStream is = getAssets().open(topic + "_quiz.json");
                int size = is.available();
                byte[] buff = new byte[size];
                is.read(buff);
                is.close();
                String json = new String(buff, "UTF-8");
                jsonArray = new JSONArray(json);
                numberOfQuestions = jsonArray.length();
            } catch (IOException | JSONException e) {
                new AlertDialog.Builder(this)
                        .setTitle("Error:")
                        .setMessage(e.toString())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Error:")
                    .setMessage("Quiz activity started without a topic argument.")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }

        // Initialise the json key names
        String[] temp = {"correctAnswer", "wrongAnswer1", "wrongAnswer2", "wrongAnswer3"};
        answers = Arrays.asList(temp);

        //Initialise the marks
        quizMarks = 0;
        ((TextView)findViewById(R.id.marks)).setText("Marks: "+quizMarks);

        // Show the first question
        if (jsonArray != null) nextQuestion();
    }

    /**
     * Displays next question, or end result screen if no more questions.
     *
     * Modified to accept True/False type questions as well as 4-answer Multiple Choice type
     * questions. Checking the length of the JSON object to determine this. (If length is 3, then assume
     * True/False question as 1 question element + 2 answer elements.) Question types can be combined
     * in JSON file.
     */
    public void nextQuestion() {
        findViewById(R.id.answer1Text).setBackgroundColor(Color.WHITE);

        findViewById(R.id.answer2Text).setBackgroundColor(Color.WHITE);
        findViewById(R.id.answer3Text).setBackgroundColor(Color.WHITE);
        findViewById(R.id.answer4Text).setBackgroundColor(Color.WHITE);
        qProgress.setProgress(index*33);

        try {
            // Randomise the order of the json keys
            // TODO: just loops for now
            if (index == numberOfQuestions) {
                findViewById(R.id.answer1Text).setClickable(false);
                findViewById(R.id.answer2Text).setClickable(false);
                findViewById(R.id.answer3Text).setClickable(false);
                findViewById(R.id.answer4Text).setClickable(false);
                ((TextView)findViewById(R.id.processText)).setText("Quiz Finished");
                ((TextView)findViewById(R.id.questionText)).setText(quizMarks+"/"+numberOfQuestions);
                ((TextView)findViewById(R.id.questionText)).setTextSize(80);
                ((TextView)findViewById(R.id.marks)).setText(" ");
                if(quizMarks==numberOfQuestions){
                    ((TextView)findViewById(R.id.questionText)).setTextColor(Color.GREEN);
               }else{
                    ((TextView)findViewById(R.id.questionText)).setTextColor(Color.RED);
                }
                findViewById(R.id.answer1Text).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer2Text).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer3Text).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer4Text).setVisibility(View.INVISIBLE);
                index = 0;
                //set up return button
                TextView returnButton = (TextView) findViewById(R.id.returnText);
                returnButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(QuizActivity.this, BasicSelectActivityV2.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_left,
                                R.anim.slide_right_out);
                    }
                });
                //set up retry button
                TextView retryButton = (TextView) findViewById(R.id.retryText);
                retryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(QuizActivity.this, QuizActivity.class);
                        i.putExtra(QuizActivity.EXTRA_TOPIC, topic );
                        startActivity(i);
                    }
                });
                findViewById(R.id.returnText).setVisibility(View.VISIBLE);
                findViewById(R.id.retryText).setVisibility(View.VISIBLE);
                findViewById(R.id.star1_margin).setVisibility(View.VISIBLE);
                findViewById(R.id.star2_margin).setVisibility(View.VISIBLE);
                findViewById(R.id.star3_margin).setVisibility(View.VISIBLE);
                if(quizMarks==1){
                    findViewById(R.id.star1).setVisibility(View.VISIBLE);
                }else if(quizMarks==2){
                    findViewById(R.id.star1).setVisibility(View.VISIBLE);
                    findViewById(R.id.star2).setVisibility(View.VISIBLE);
                }else if(quizMarks==3){
                    findViewById(R.id.star1).setVisibility(View.VISIBLE);
                    findViewById(R.id.star2).setVisibility(View.VISIBLE);
                    findViewById(R.id.star3).setVisibility(View.VISIBLE);
                }
            }else if(index < numberOfQuestions){
                // Get the next question, show it on screen
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                int numberOfAnswers = jsonObject.length(); // 5 for 4 answers + question, 3 for for 2 answers + question.

                // Create temp array as a quiz may have both true/false and multi choice questions:
                List<String> tempAnswers = new ArrayList<>(answers);

                // If this is a true/false question, remove the last two answers.
                if (numberOfAnswers == TRUE_FALSE_ANSWERS + 1) {
                    tempAnswers.remove(3);
                    tempAnswers.remove(2);
                } else if (numberOfAnswers == MULTI_CHOICE_ANSWERS + 1) {
                    Collections.shuffle(tempAnswers); // Only shuffle if it's multiple choice.
                }

                ((TextView)findViewById(R.id.processText)).setText("Question "+(index+1)+" of "+numberOfQuestions);
                correctAnswer = tempAnswers.indexOf("correctAnswer");

                questionTextView.setText((CharSequence) jsonObject.get("question"));
                //for (int i = 0; i<NUMBER_OF_ANSWERS; i++)
                for (int i = 0; i<numberOfAnswers - 1; i++)
                    answerTextViews.get(i).setText((CharSequence) jsonObject.get(tempAnswers.get(i)));

                // Set the click actions for the answers
                //for (int i = 0; i<NUMBER_OF_ANSWERS; i++)
                for (int i = 0; i<numberOfAnswers - 1; i++)
                    answerTextViews.get(i).setOnClickListener(INCORRECT_ANSWER_CLICK);

                answerTextViews.get(correctAnswer).setOnClickListener(CORRECT_ANSWER_CLICK);

                index += 1;

                findViewById(R.id.answer1Text).setVisibility(View.VISIBLE);
                findViewById(R.id.answer2Text).setVisibility(View.VISIBLE);
                findViewById(R.id.answer1Text).setClickable(true);
                findViewById(R.id.answer2Text).setClickable(true);

                if (numberOfAnswers == MULTI_CHOICE_ANSWERS + 1) {
                    findViewById(R.id.answer3Text).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer4Text).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer3Text).setClickable(true);
                    findViewById(R.id.answer4Text).setClickable(true);
                } else if (numberOfAnswers == TRUE_FALSE_ANSWERS + 1) {
                    findViewById(R.id.answer3Text).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer4Text).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer3Text).setClickable(false);
                    findViewById(R.id.answer4Text).setClickable(false);
                }
            }
        } catch (JSONException e) {
            new AlertDialog.Builder(this)
                    .setTitle("Error:")
                    .setMessage(e.toString())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }
}
