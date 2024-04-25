package com.example.a61cquizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {

    // Screen elements
    TextView tv_welcomeMessage, tv_questionNumber, tv_question;
    ProgressBar pb_progressBar;
    Button btn_A, btn_B, btn_C, btn_D, btn_submit, btn_next;

    // Variables for screen elements
    int questionNumber, score;
    Quiz quiz;
    String answer;
    boolean answerAlreadySubmitted;

    // Colours
    int clr_btn_unclicked = R.color.DarkGray;
    int clr_btn_active = R.color.holo_blue_dark;
    int clr_btn_inactive = R.color.LightGrey;
    int clr_btn_answerIncorrect = R.color.holo_red_light;
    int clr_brn_answerCorrect = R.color.holo_green_light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);

        answerAlreadySubmitted = false;

        // Find our screen elements
        tv_welcomeMessage = findViewById(R.id.tv_welcomeMessage);
        tv_questionNumber = findViewById(R.id.tv_questionNumber);
        pb_progressBar = findViewById(R.id.pb_progressBar);
        tv_question = findViewById(R.id.tv_question);
        btn_A = findViewById(R.id.btn_A);
        btn_B = findViewById(R.id.btn_B);
        btn_C = findViewById(R.id.btn_C);
        btn_D = findViewById(R.id.btn_D);
        btn_submit = findViewById(R.id.btn_submit);
        btn_next = findViewById(R.id.btn_next);

        // Set default colours for buttons
        btn_A.setBackgroundColor(getResources().getColor(clr_btn_unclicked));
        btn_B.setBackgroundColor(getResources().getColor(clr_btn_unclicked));
        btn_C.setBackgroundColor(getResources().getColor(clr_btn_unclicked));
        btn_D.setBackgroundColor(getResources().getColor(clr_btn_unclicked));
        btn_submit.setBackgroundColor(getResources().getColor(clr_btn_active));
        btn_next.setBackgroundColor(getResources().getColor(clr_btn_inactive));

        // Pull our quiz, question number and score from the quiz
        Intent intent = getIntent();
        quiz = (Quiz) intent.getSerializableExtra("quiz");
        questionNumber = intent.getIntExtra("questionNumber", 0);
        score = intent.getIntExtra("score", 0);

        // Oh, and the username
        String userName = intent.getStringExtra("userName");

        // Welcome the user
        tv_welcomeMessage.setText("Welcome " + userName + "!");

        // Get quiz length and display it in the progress bar
        int quizLength = quiz.getQuiz().size();
        tv_questionNumber.setText((questionNumber+1) + "/" + quizLength);
        float progress = (float) (questionNumber+1) / quizLength;
        int progressPerc = (int) (progress * 100);
        pb_progressBar.setProgress(progressPerc);

        // Get the question itself
        Question question = quiz.getQuiz().get(questionNumber);

        // Display the question
        tv_question.setText(question.getQuestion());

        // Populate the buttons with the answers
        btn_A.setText(question.getOptions().get(0));
        btn_B.setText(question.getOptions().get(1));
        btn_C.setText(question.getOptions().get(2));
        btn_D.setText(question.getOptions().get(3));

        // Set on-click listeners for answer options
        btn_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If an answer's already been submitted, don't accept changes to the answer!
                if (answerAlreadySubmitted) return;

                // Register button press and register it graphically too
                answer = "A";
                btn_A.setBackgroundColor(Color.DKGRAY);

                // Reset colours of other buttons, if they've been previously pressed
                int clr_btn_unpressed = R.color.DarkGray;
                btn_B.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
                btn_C.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
                btn_D.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
            }
        });
        btn_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerAlreadySubmitted) return;

                answer = "B";
                btn_B.setBackgroundColor(Color.DKGRAY);

                int clr_btn_unpressed = R.color.DarkGray;
                btn_A.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
                btn_C.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
                btn_D.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
            }
        });
        btn_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerAlreadySubmitted) return;

                answer = "C";
                btn_C.setBackgroundColor(Color.DKGRAY);

                int clr_btn_unpressed = R.color.DarkGray;
                btn_A.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
                btn_B.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
                btn_D.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
            }
        });
        btn_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerAlreadySubmitted) return;

                answer = "D";
                btn_D.setBackgroundColor(Color.DKGRAY);

                int clr_btn_unpressed = R.color.DarkGray;
                btn_A.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
                btn_B.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
                btn_C.setBackgroundColor(getResources().getColor(clr_btn_unpressed));
            }
        });

        // Check answer when user clicks submit button
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If the answer's already been submitted, don't do anything on subsequent presses
                if (answerAlreadySubmitted) return;

                // Set the question as answered to prevent changes to the answer
                answerAlreadySubmitted = true;
                // Reflect this in the colour of the submit button too
                btn_submit.setBackgroundColor(getResources().getColor(clr_btn_inactive));

                // Check if the answer was correct
                boolean correct = answer.equals(question.getCorrect_answer());

                // Colour the buttons according to the answer selected and which was correct
                if (correct) {
                    switch (answer) {
                        case "A":
                            btn_A.setBackgroundColor(getResources().getColor(clr_brn_answerCorrect));
                            break;
                        case "B":
                            btn_B.setBackgroundColor(getResources().getColor(clr_brn_answerCorrect));
                            break;
                        case "C":
                            btn_C.setBackgroundColor(getResources().getColor(clr_brn_answerCorrect));
                            break;
                        default:
                            btn_D.setBackgroundColor(getResources().getColor(clr_brn_answerCorrect));
                            break;
                    }

                    score += 1;
                }
                else {
                    switch (question.getCorrect_answer()) {
                        case "A":
                            btn_A.setBackgroundColor(getResources().getColor(clr_brn_answerCorrect));
                            break;
                        case "B":
                            btn_B.setBackgroundColor(getResources().getColor(clr_brn_answerCorrect));
                            break;
                        case "C":
                            btn_C.setBackgroundColor(getResources().getColor(clr_brn_answerCorrect));
                            break;
                        default:
                            btn_D.setBackgroundColor(getResources().getColor(clr_brn_answerCorrect));
                            break;
                    }
                    switch (answer) {
                        case "A":
                            btn_A.setBackgroundColor(getResources().getColor(clr_btn_answerIncorrect));
                            break;
                        case "B":
                            btn_B.setBackgroundColor(getResources().getColor(clr_btn_answerIncorrect));
                            break;
                        case "C":
                            btn_C.setBackgroundColor(getResources().getColor(clr_btn_answerIncorrect));
                            break;
                        default:
                            btn_D.setBackgroundColor(getResources().getColor(clr_btn_answerIncorrect));
                            break;
                    }
                }

                // Finally, graphically register that the Next button can now be pressed
                btn_next.setBackgroundColor(getResources().getColor(clr_btn_active));
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This button shouldn't do anything until the user has submitted an answer
                if (!answerAlreadySubmitted) return;

                questionNumber += 1;

                // Go to the results screen if we're at the end of the quiz
                if (questionNumber == quizLength) {
                    Intent intent = new Intent(QuestionActivity.this, ResultsActivity.class);
                    intent.putExtra("quizLength", quizLength);
                    intent.putExtra("score", score);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                }
                // Or the next question if we aren't
                else {
                    Intent intent = new Intent(QuestionActivity.this, QuestionActivity.class);
                    intent.putExtra("quiz", quiz);
                    intent.putExtra("questionNumber", questionNumber);
                    intent.putExtra("score", score);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                }

            }
        });

    }
}