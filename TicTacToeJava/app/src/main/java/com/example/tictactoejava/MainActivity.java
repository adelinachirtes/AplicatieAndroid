
package com.example.tictactoejava;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    boolean playerOneActive;
    private TextView player1, player2, player;
    private Button[] buttons = new Button[9];
    private Button reset, playagain;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7},
            {2,5,8}, {0,4,8}, {2,4,6}};
    int rounds;
    private int player1Cnt, player2Cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = findViewById(R.id.score_player1);
        player2 = findViewById(R.id.score_player2);
        player = findViewById(R.id.textStatus);
        reset = findViewById(R.id.btn_reset);
        playagain = findViewById(R.id.btn_play_again);

        buttons[0] = findViewById(R.id.btn1);
        buttons[1] = findViewById(R.id.btn2);
        buttons[2] = findViewById(R.id.btn3);
        buttons[3] = findViewById(R.id.btn4);
        buttons[4] = findViewById(R.id.btn5);
        buttons[5] = findViewById(R.id.btn6);
        buttons[6] = findViewById(R.id.btn7);
        buttons[7] = findViewById(R.id.btn8);
        buttons[8] = findViewById(R.id.btn9);

        for(int i=0; i < buttons.length; i++)
        {
            buttons[i].setOnClickListener(this);
        }
        player1Cnt = 0;
        player2Cnt = 0;
        playerOneActive = true;
        rounds = 0;
    }

    public void onClick(View view)
    {
        if(!((Button)view).getText().toString().equals(""))
            return;
        else if(checkWinner())
            return;
        String bID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(bID.substring(bID.length()-1, bID.length()));

        if(playerOneActive)
        {
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#ffc34a"));
            gameState[gameStatePointer] = 0;
        }
        else
        {
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#70fc3a"));
            gameState[gameStatePointer] = 1;
        }
        rounds++;

        if(checkWinner())
        {
            if(playerOneActive)
            {
                player1Cnt++;
                updatePlayerScore();
                player.setText("Player 1 has won");
            }
            else
            {
                player2Cnt++;
                updatePlayerScore();
                player.setText("Player 2 has won");
            }
        }
        else
        {
            if(rounds == 9)
                player.setText("No Winner");
            else
                playerOneActive = !playerOneActive;
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                player1Cnt = 0;
                player2Cnt = 0;
                updatePlayerScore();
            }
        });
        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                updatePlayerScore();

            }
        });
    }

    private boolean checkWinner() {
        boolean winnerRes = false;
        for(int[] winningPositions: winningPositions)
        {
            if(gameState[winningPositions[0]] == gameState[winningPositions[1]] &&
                    gameState[winningPositions[1]] == gameState[winningPositions[2]] &&
                    gameState[winningPositions[0]] != 2)
            {
                winnerRes = true;
            }
        }
        return winnerRes;
    }

    private void playAgain() {
        rounds = 0;
        playerOneActive = true;
        for(int i=0; i < buttons.length; i++)
        {
            gameState[i] = 2;
            buttons[i].setText("");
        }
        player.setText("Status");
    }

    private void updatePlayerScore() {
        player1.setText(Integer.toString(player1Cnt));
        player2.setText(Integer.toString(player2Cnt));
    }
}