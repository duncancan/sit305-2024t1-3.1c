package com.example.a61cquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TOPIC = "cats";
    public static final int STARTING_QUESTION = 0;
    public static final int STARTING_SCORE = 0;
    EditText et_userName;
    Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set up our screen elements
        et_userName = findViewById(R.id.et_userName);
        btn_start = findViewById(R.id.btn_start);

        // Set up HTTP logging for debugging purposes
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        // Set up our web connection to our quiz endpoint
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        LlamaQuiz myQuiz = retrofit.create(LlamaQuiz.class);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user's name
                String userName = et_userName.getText().toString();

                // Make a call to our connection
                Call<Quiz> call = myQuiz.getQuiz();
                call.enqueue(new Callback<Quiz>() {
                    @Override
                    public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                        // Handle non-200 responses by displaying the error message
                        if (!response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        // If call is successful
                        else {
                            Quiz quiz = response.body();
                            Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                            intent.putExtra("quiz", quiz);
                            intent.putExtra("questionNumber", STARTING_QUESTION);
                            intent.putExtra("score", STARTING_SCORE);
                            intent.putExtra("userName", userName);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Quiz> call, Throwable throwable) {
                        // Handle call failure by displaying error message
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}


