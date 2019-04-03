package com.example.sjaddya.braintrainer;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timeTextView, problemTextView, scoreTextView, scoreDisplayTextView, result;
    LinearLayout solutionLinearLayout;
    Button playAgain, option1, option2, option3, option4, btn;

    Button[] options;

    CountDownTimer gameTimer;

    int score = 0, total = 0;

    char op;

    boolean timeFinished = false;

    Spinner timeSpinner, operationSpinner;

    public static int stringCompare(String str1, String str2)
    {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }


        if (l1 != l2) {
            return l1 - l2;
        }


        else {
            return 0;
        }
    }

    public void show(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public void hide(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public void showResult(String s, char op) {
        result.setText(s);
        show(result);
        setText(score,total,op);
    }

    public void startGame(View view) {

        hide(view);

        int t;
        String time = timeSpinner.getSelectedItem().toString();
        String arrOfTime[] = time.split(" ", 2);
        if(stringCompare(arrOfTime[1], "sec") == 0) {
            t = Integer.parseInt(arrOfTime[0]);
        }
        else
            t = Integer.parseInt(arrOfTime[0])*60;

        String operation = operationSpinner.getSelectedItem().toString();
        String arrOfOperation[] = operation.split(" ", 2);
        op = arrOfOperation[0].charAt(0);

        hide(timeSpinner);
        hide(operationSpinner);
        show(timeTextView);
        show(problemTextView);
        show(scoreTextView);
        show(solutionLinearLayout);
        randOpp(op);
        gameStart(t);

    }

    public void setText(int score, int total, char op) {
        scoreTextView.setText(score + "/" + total);
        randOpp(op);
    }

    public void playAgain(View view) {

        int t;
        hide(scoreDisplayTextView);
        hide(playAgain);
        hide(timeSpinner);
        hide(operationSpinner);
        score = 0; total = 0;

        String operation = operationSpinner.getSelectedItem().toString();
        String arrOfOperation[] = operation.split(" ", 2);
        op = arrOfOperation[0].charAt(0);

        setText(score,total, op);
        timeFinished = false;
        String time = timeSpinner.getSelectedItem().toString();
        String arrOfTime[] = time.split(" ", 2);
        if(stringCompare(arrOfTime[1], "sec") == 0) {
            t = Integer.parseInt(arrOfTime[0]);
        }
        else
            t = Integer.parseInt(arrOfTime[0])*60;
        gameStart(t);

    }

    public void gameStart(int time) {

        gameTimer = new CountDownTimer(time*1000 + 100,1000) {
            @Override
            public void onTick(long l) {
                timeTextView.setText(l/1000 + "s");
            }

            @Override
            public void onFinish() {
                timeTextView.setText("0s");
                show(scoreDisplayTextView);
                scoreDisplayTextView.setText("Your score is : " + score + "/" + total);
                show(playAgain);
                hide(result);
                show(timeSpinner);
                show(operationSpinner);
                timeFinished = true;
            }
        }.start();

    }

    public void randomButton(int i,int t) {
        btn = options[i];
        String num = Integer.toString(t);
        if(t<=9&&t>=0)
            num = "0"+num;
        btn.setText(num);
    }

    public void randOpp(char op){
        Random rand = new Random();

        int a = rand.nextInt(50);
        int b = rand.nextInt(50);

        problemTextView.setText(a + " " + op + " " + b);


        for(int i = 0; i < 4; i++) {
            int t = rand.nextInt(100);

            switch (op) {
                case '+' :
                    while(t == a + b)
                        t = rand.nextInt(100);
                    randomButton(i,t);
                    break;

                case'-' :
                    while(t == a - b)
                        t = rand.nextInt(100);
                    randomButton(i,t);
                    break;

                case '*' :
                    while(t == a * b)
                        t = rand.nextInt(100);
                    randomButton(i,t);
                    break;
                case '/' :
                    while(t == a / b)
                        t = rand.nextInt(100);
                    randomButton(i,t);
                    break;
                case '%' :
                    while(t == a % b)
                        t = rand.nextInt(100);
                    randomButton(i,t);
                    break;
            }

        }

        switch (op) {
            case '+' :
                randomButton(rand.nextInt(options.length),a+b);
                break;

            case'-' :
                randomButton(rand.nextInt(options.length),a-b);
                break;

            case '*' :
                randomButton(rand.nextInt(options.length),a*b);
                break;
            case '/' :
                randomButton(rand.nextInt(options.length),a/b);
                break;
            case '%' :
                randomButton(rand.nextInt(options.length),a%b);
                break;
        }


    }

    public void guess(View view) {
        if(!timeFinished) {
            if (view.getId() == btn.getId()) {
                score++;
                total++;
                showResult("Correct", op);
            } else {
                total++;
                showResult("Incorrect", op);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSpinner = findViewById(R.id.spinnerTime);
        operationSpinner = findViewById(R.id.spinnerOperation);
        timeTextView = findViewById(R.id.timeTextView);
        result = findViewById(R.id.result);
        problemTextView = findViewById(R.id.problemTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        scoreDisplayTextView = findViewById(R.id.scoreDisplayTextView);
        solutionLinearLayout = findViewById(R.id.solutionLinearLayout);
        playAgain = findViewById(R.id.playAgain);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        options= new Button[] {option1, option2, option3, option4};


        @SuppressLint("ResourceType") ArrayAdapter<String> timeArrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.time));

        timeSpinner.setAdapter(timeArrayAdapter);

        ArrayAdapter<String> operationArrayAdapter= new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.operation));

        operationSpinner.setAdapter(operationArrayAdapter);

    }
}