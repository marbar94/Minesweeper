package com.stabalce.minesweeper;

import android.support.v7.widget.AppCompatButton;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;


public class Cell extends AppCompatButton implements View.OnClickListener, View.OnLongClickListener {
    boolean isMine = false;
    boolean isRevealed = false;
    boolean isFlag = false;

    public static boolean gameOver = false;

    int numOfMines = 0;

    public ArrayList<Cell> neighbours = new ArrayList<>();

    public Cell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.setBackgroundResource(R.drawable.full);
    }

    public void SetMine() {
        isMine = true;
    }

    @Override
    public boolean onLongClick(View v) {
        if(isRevealed)
            return false;

        if(gameOver)
            return false;

        if(isFlag) {
            isFlag = false;
            this.setBackgroundResource(R.drawable.full);
        }
        else {
            isFlag = true;
            this.setBackgroundResource(R.drawable.flag);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(isFlag)
            return;

        if(gameOver)
            return;

        isRevealed = true;

        Reveal();
    }

    private void Reveal() {
        isRevealed = true;

        if(isMine) {
            gameOver = true;
            this.setBackgroundResource(R.drawable.mine);
        }
        else {
            this.setBackgroundResource(R.drawable.empty);

            if(numOfMines != 0)
            this.setText(Integer.toString(numOfMines));

            if(numOfMines == 0)
                for(int i = 0; i < neighbours.size(); i++)
                    if(!neighbours.get(i).IsRevealed())
                        neighbours.get(i).Reveal();
        }
    }

    public boolean IsMine() {
        return isMine;
    }

    public boolean IsRevealed() { return isRevealed; }

    public void SetNumberOfMines(int numberOfMines) {
        numOfMines = numberOfMines;
    }
}
