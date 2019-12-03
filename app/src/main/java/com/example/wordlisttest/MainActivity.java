package com.example.wordlisttest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button allWords;
    private Button addWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allWords = findViewById(R.id.AllWords);
        allWords.setOnClickListener(new AllWords());
        addWord = findViewById(R.id.AddWord);
        addWord.setOnClickListener(new AddWord());
    }

    class AllWords implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AllWordsActivity.class);
            startActivity(intent);
        }
    }
    class AddWord implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
            startActivity(intent);
        }
    }
}
