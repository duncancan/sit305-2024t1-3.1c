package com.example.a61cquizapp;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {
    private List<Question> quiz;

    public List<Question> getQuiz() {
        return quiz;
    }
}
