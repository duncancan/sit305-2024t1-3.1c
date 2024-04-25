package com.example.a61cquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultsActivity extends AppCompatActivity {

    // Screen elements
    TextView tv_congratsMessage, tv_score;
    Button btn_finish;

    // Variables for screen elements
    int quizLength, score;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);

        // Find our screen elements
        tv_congratsMessage = findViewById(R.id.tv_congratsMessage);
        tv_score = findViewById(R.id.tv_score);
        btn_finish = findViewById(R.id.btn_finish);

        // Pull our username & score from the instantiated intent
        Intent intent = getIntent();
        quizLength = intent.getIntExtra("quizLength", -1);
        score = intent.getIntExtra("score", -1);
        userName = intent.getStringExtra("userName");

        // Populate our screen elements
        tv_congratsMessage.setText("Congratulations, " + userName + "!");
        tv_score.setText(score + "/" + quizLength);

        // Go home when the user clicks finish
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}