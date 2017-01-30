package com.alex.rollthedices;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private TextView rollResult;

    private Random rand;
    private ArrayList<ImageView> diceImageView;
    private TextView scoreText;
    //Todo field to hold the score
    private int score;
    private int dice1;
    private int dice2;
    private int dice3;
    private int soundId;
    private SoundPool sp;
    private MediaPlayer mPlayer;

    //ArrayList to hold all the three dice values

    ArrayList<Integer> dice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rollDice(view);
                sp.play(soundId, 1, 1, 0, 0, 1);
                mPlayer.start();
            }
        });

        //Todo set initial score
        score =0;

        rollResult = (TextView) findViewById(R.id.textTop);
        scoreText = (TextView) findViewById(R.id.scoreText) ;

        rand = new Random();
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = sp.load(getBaseContext(), R.raw.Shake_Roll_Dice, 1);


        mPlayer= MediaPlayer.create(getBaseContext(), R.raw.Shake_Roll_Dice);

        //Create an array list to container for the dice
        dice = new ArrayList<>();

        ImageView di1Image = (ImageView) findViewById(R.id.die1Image);
        ImageView di2Image = (ImageView) findViewById(R.id.die2Image);
        ImageView di3Image = (ImageView) findViewById(R.id.die3Image);

        diceImageView = new ArrayList<>();

        diceImageView.add(di1Image);
        diceImageView.add(di2Image);
        diceImageView.add(di3Image);




        //Toast.makeText(getApplicationContext(),"Welcome to Dice Game",Toast.LENGTH_SHORT).show();
    }


    public void rollDice(View v){


        dice1 = rand.nextInt(6)+1;
        dice2 = rand.nextInt(6)+1;
        dice3 = rand.nextInt(6)+1;

        dice.clear();

        dice.add(dice1);
        dice.add(dice2);
        dice.add(dice3);




        for(int i = 0; i < diceImageView.size();i++){

            String imageName= "die_"+dice.get(i)+".png";

            try{
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream,null);

                diceImageView.get(i).setImageDrawable(d);
            }catch(IOException e){

                e.printStackTrace();
            }
        }


       String msg="";

        if(dice1 == dice2 && dice1 == dice3){

            //Triple
            int scoreDelta = dice1 * 100;
            msg = "You rolled a triple "+dice1+"! You scored "+scoreDelta+" points!";

            score +=scoreDelta;

        }else if(dice1 == dice2 || dice1 == dice3 || dice2 == dice3){

            // doubles

            msg = "You rolled double for 50 points!";
            score+=50;

        }else{
            msg = "You didn't score .Try again!";
        }

        //apdate the app to display the result
        rollResult.setText(msg);
        scoreText.setText("Score:  "+score);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
