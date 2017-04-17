package com.example.aot.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.TimerTask;

import static com.example.aot.eggtimer.R.id.controllerButton;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekbar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;


    public void updateTimer(int secondsLeft){
        int minutes = (int)secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondSeconds = Integer.toString(seconds);

        if (Integer.toString(seconds) == "0"){
            secondSeconds = "00";
        }

        else if (seconds <= 9){
            secondSeconds = "0" + secondSeconds;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondSeconds);
    }
    public void resetTimer(){
        timerSeekbar.setEnabled(true);
        counterIsActive = false;
        countDownTimer.cancel();
        controllerButton.setText("Go!");
    }
    public void controlTimer(View view){

        if (counterIsActive == false) {
            counterIsActive = true;
            timerSeekbar.setEnabled(false);
            controllerButton.setText("STOP");

            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    counterIsActive = false;
                    resetTimer();
                    timerTextView.setText("0:00");
                    final MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.roar);
                    mplayer.start();
                    new CountDownTimer(5000,100){

                        @Override
                        public void onTick(long millisUntilFinished) {
                            Log.i("time passed: ", String.valueOf(millisUntilFinished));
                        }

                        @Override
                        public void onFinish() {
                            Log.i("finished", "done");
                            mplayer.pause();
                            mplayer.stop();
                            resetTimer();
                        }
                    }.start();

                }
            }.start();
        }
        else{
            resetTimer();
            timerTextView.setText("0:30");
            timerSeekbar.setProgress(30);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar = (SeekBar) findViewById(R.id.timerSeekbar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);
        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
