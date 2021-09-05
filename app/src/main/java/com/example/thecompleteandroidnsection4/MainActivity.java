package com.example.thecompleteandroidnsection4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    void changeTimesTable(int timesValue, ArrayAdapter<String> arrayAdapter, ArrayList<String> timesTableList) {
        for (int i = 0; i < 10; i++) {
            timesTableList.set(i, Integer.toString((i + 1) * timesValue));
        }

        arrayAdapter.notifyDataSetChanged();
    }

    void convertSecondsToTimerString(int totalSeconds, TextView timerTextView) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds - (minutes * 60);
        String secondString = Integer.toString(seconds);
        if (seconds < 10) {
            secondString = "0" + secondString;
        }

        String minuteString = Integer.toString(minutes);
        if (minutes < 10) {
            minuteString = "0" + minuteString;
        }
        String timerString = minuteString + ":" + secondString;

        timerTextView.setText(timerString);
    }

    SeekBar eggTimerSeekBar;
    TextView eggTimerTextView;
    boolean countDownIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer(View view) {
        Button timerStartButton = (Button) view;

        convertSecondsToTimerString(30, eggTimerTextView);
        eggTimerSeekBar.setProgress(30);
        countDownTimer.cancel();
        timerStartButton.setText("START");
        eggTimerSeekBar.setEnabled(true);
        countDownIsActive = false;
    }

    public void controlEggTimer(View view) {
        Log.i("Button Pressed", "Pressed");
        Button timerStartButton = (Button) view;

        if (!countDownIsActive) {
            countDownIsActive = true;
            eggTimerSeekBar.setEnabled(false);

            countDownTimer = new CountDownTimer(eggTimerSeekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    convertSecondsToTimerString((int) (millisUntilFinished / 1000), eggTimerTextView);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer(view);
                }
            };
            countDownTimer.start();


            timerStartButton.setText("STOP");
        } else {
            resetTimer(view);
        }
    }

    public void showTextView(View view) {
        TextView textView = findViewById(R.id.helloWorldTextView);
        textView.setVisibility(View.VISIBLE);
    }

    public void hideTextView(View view) {
        TextView textView = findViewById(R.id.helloWorldTextView);
        textView.setVisibility(View.INVISIBLE);
    }


    CountDownTimer brainTrainerCountDown;
    int variableA;
    int variableB;
    int answer;
    int totalQuestions;
    int correctQuestions;


    public void setNewSumQuestion() {
        Random randomGenerator = new Random();
        variableA = randomGenerator.nextInt(50) + 1;
        variableB = randomGenerator.nextInt(49) + 1;

        answer = variableA + variableB;
        int correctOptionNumber = randomGenerator.nextInt(4);
        ArrayList<Integer> randomOptions = new ArrayList<Integer>();

        for (int i = 0; i < 4; i++) {
            int incorrectOption = randomGenerator.nextInt(99) + 1;
            while (incorrectOption == answer) {
                incorrectOption = randomGenerator.nextInt(99) + 1;
            }
            randomOptions.add(incorrectOption);
        }
        randomOptions.set(correctOptionNumber, answer);

        Button optionOne = findViewById(R.id.optionOne);
        Button optionTwo = findViewById(R.id.optionTwo);
        Button optionFour = findViewById(R.id.optionFour);
        Button optionThree = findViewById(R.id.optionThree);


        optionOne.setText(Integer.toString(randomOptions.get(0)));
        optionTwo.setText(Integer.toString(randomOptions.get(1)));
        optionThree.setText(Integer.toString(randomOptions.get(2)));
        optionFour.setText(Integer.toString(randomOptions.get(3)));

        TextView sumTextView = findViewById(R.id.sumTextView);
        sumTextView.setText(variableA + " + " + variableB);
    }

    public void sumOptionClicked(View view) {
        Button buttonClicked = (Button) view;
        int selectedAnswer = Integer.parseInt((String) buttonClicked.getText());

        TextView questionCounterText = findViewById(R.id.questionCounterText);
        TextView answerJudgementText = findViewById(R.id.answerJudgmentText);

        if (selectedAnswer == answer) {
            totalQuestions += 1;
            correctQuestions += 1;
            answerJudgementText.setText("Correct!");
        } else {
            totalQuestions += 1;
            answerJudgementText.setText("Wrong");
        }

        if (answerJudgementText.getVisibility() == View.INVISIBLE) {
            answerJudgementText.setVisibility(View.VISIBLE);
        }
        questionCounterText.setText(correctQuestions + "/" + totalQuestions);
        setNewSumQuestion();
    }

    public void restartBrainTrainer(View view) {
        Button optionOne = findViewById(R.id.optionOne);
        Button optionTwo = findViewById(R.id.optionTwo);
        Button optionFour = findViewById(R.id.optionFour);
        Button optionThree = findViewById(R.id.optionThree);

        optionOne.setEnabled(true);
        optionTwo.setEnabled(true);
        optionThree.setEnabled(true);
        optionFour.setEnabled(true);

        startBrainTrainer(view);
    }


    public void startBrainTrainer(View view) {
        Button goButtonView = findViewById(R.id.goButton);
        goButtonView.setVisibility(View.GONE);

        ConstraintLayout constraintLayoutView = findViewById(R.id.sumScreen);
        constraintLayoutView.setVisibility(View.VISIBLE);

        totalQuestions = 0;
        correctQuestions = 0;

        TextView questionCounterText = findViewById(R.id.questionCounterText);
        questionCounterText.setText(correctQuestions + "/" + totalQuestions);

        TextView answerJudgementText = findViewById(R.id.answerJudgmentText);
        answerJudgementText.setVisibility(View.INVISIBLE);

        Button playBrainTrainerAgain = findViewById(R.id.playBrainTrainerAgain);
        playBrainTrainerAgain.setVisibility(View.INVISIBLE);

        setNewSumQuestion();

        TextView timerCountDown = findViewById(R.id.timerCountDown);
        brainTrainerCountDown = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRem = (int) millisUntilFinished / 1000;
                timerCountDown.setText(secondsRem + "s");
            }

            @Override
            public void onFinish() {
                timerCountDown.setText("30s");
                Button optionOne = findViewById(R.id.optionOne);
                Button optionTwo = findViewById(R.id.optionTwo);
                Button optionFour = findViewById(R.id.optionFour);
                Button optionThree = findViewById(R.id.optionThree);

                optionOne.setEnabled(false);
                optionTwo.setEnabled(false);
                optionThree.setEnabled(false);
                optionFour.setEnabled(false);

                playBrainTrainerAgain.setVisibility(View.VISIBLE);

                TextView answerJudgementText = findViewById(R.id.answerJudgmentText);
                answerJudgementText.setText("Your Score: " + correctQuestions + "/" + totalQuestions);
            }
        };

        brainTrainerCountDown.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        // List View example using array adaptor
//        ListView myListView = findViewById(R.id.myListView);
//
//        List<String> myFamily = new ArrayList<String>();
//
//        myFamily.add("Toshal");
//        myFamily.add("Archana");
//        myFamily.add("Umesh");
//        myFamily.add("Vishwali");
//        myFamily.add("Manorama");
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myFamily);
//        myListView.setAdapter(arrayAdapter);
//
//        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("INFO",  myFamily.get(position));
//                Toast.makeText(getApplicationContext(), "Hello " + myFamily.get(position), Toast.LENGTH_LONG).show();
//            }
//        });



        // Code for Times Table App
//        setContentView(R.layout.times_table);
//
//        SeekBar timesTableSeekbar = findViewById(R.id.timesTableSeekBar);
//        ListView timesTableListView = findViewById(R.id.timesTableListView);
//        int timeValue = 10;
//
//        ArrayList <String> timesTableList = new ArrayList<String>();
//
//        for (int i = 1; i <= 10; i++) {
//            timesTableList.add(Integer.toString(i * timeValue));
//        }
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timesTableList);
//
//        timesTableListView.setAdapter(arrayAdapter);
//
//
//        timesTableSeekbar.setMax(20);
//        timesTableSeekbar.setProgress(10);
//
//        timesTableSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                int min = 1;
//                int timesTable;
//
//                if (progress < min) {
//                    timesTable = min;
//                    seekBar.setProgress(timesTable);
//                } else {
//                    timesTable = progress;
//                }
//
//                changeTimesTable(timesTable, arrayAdapter, timesTableList);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });


        // Code for timers using Handler.
//        Handler handler = new Handler();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                Log.i("Runnable has run!", "A second Must have passed...");
//                handler.postDelayed(this, 1000);
//            }
//        };
//        handler.post(run);

//        new CountDownTimer(10000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                Log.i("Time Remaining in sec: ", Long.toString(millisUntilFinished / 1000));
//            }
//
//            public void onFinish() {
//                Log.i("Time's Up", "0 seconds remaining");
//            }
//        }.start();

        // Code for eggtimer app.
//        setContentView(R.layout.egg_timer);
//
//        eggTimerSeekBar = findViewById(R.id.eggTimerSeekBar);
//        eggTimerTextView = findViewById(R.id.eggTimerTextView);
//        eggTimerSeekBar.setMax(600);
//        eggTimerSeekBar.setProgress(30);
//        convertSecondsToTimerString(30, eggTimerTextView);
//
//
//        eggTimerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                convertSecondsToTimerString(progress, eggTimerTextView);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        // Code for showing and hiding elements
//        setContentView(R.layout.show_hide);

        // Code for brainTrianer app.
        setContentView(R.layout.brain_trainer);
        Button goButtonView = findViewById(R.id.goButton);
        goButtonView.setVisibility(View.VISIBLE);

        ConstraintLayout constraintLayoutView = findViewById(R.id.sumScreen);
        constraintLayoutView.setVisibility(View.GONE);
    }
}