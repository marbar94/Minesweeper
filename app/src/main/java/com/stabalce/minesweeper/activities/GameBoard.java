package com.stabalce.minesweeper.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.stabalce.minesweeper.Cell;
import com.stabalce.minesweeper.R;

import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends AppCompatActivity {

    TableLayout tableLayout;
    TableRow tr;

    public static ArrayList<Cell> cellList;

    int WIDTH = 10;
    int HEIGHT = 10;
    int MINE_COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGame();
    }

    private void newGame() {
        Random rnd = new Random();

        cellList = new ArrayList<Cell>();

        int numOfMines;

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        // Create cells
        int i, j;
        for (i = 0; i < WIDTH; i++) {
            tr = new TableRow(this);
            tableLayout.addView(tr);
            for (j = 0; j < HEIGHT; j++) {
                Cell cell = new Cell(this, null, 0);
                tr.addView(cell);
                cell.setOnClickListener(cell);
                cell.setOnLongClickListener(cell);
                cellList.add(cell);
            }
        }

        // Set neighbours
        for(i = 0; i < WIDTH * HEIGHT; i++) {
            Cell cell = cellList.get(i);
            if(i % WIDTH != WIDTH && i - WIDTH - 1 >= 0)      //TOP LEFT
                cell.neighbours.add(cellList.get(i - WIDTH - 1));
            if(i - WIDTH >= 0)          //TOP
                cell.neighbours.add(cellList.get(i - WIDTH));
            if(i % WIDTH != WIDTH - 1 && i - WIDTH + 1 >= 0)      //TOP RIGHT
                cell.neighbours.add(cellList.get(i - WIDTH + 1));
            if(i % WIDTH != WIDTH && i - 1 >= 0)              //LEFT
                cell.neighbours.add(cellList.get(i - 1));
            if(i % WIDTH != WIDTH - 1 && i + 1 < WIDTH * HEIGHT)  //RIGHT
                cell.neighbours.add(cellList.get(i + 1));
            if(i % WIDTH != WIDTH && i + WIDTH - 1 < WIDTH * HEIGHT)      //DOWN LEFT
                cell.neighbours.add(cellList.get(i + WIDTH - 1));
            if(i + WIDTH < WIDTH * HEIGHT)          //DOWN
                cell.neighbours.add(cellList.get(i + WIDTH));
            if(i % WIDTH != WIDTH - 1 && i + WIDTH + 1 < WIDTH * HEIGHT)      //DOWN RIGHT
                cell.neighbours.add(cellList.get(i + WIDTH + 1));
        }

        // Generate mines
        while (MINE_COUNT > 0) {
            int n = rnd.nextInt(WIDTH * HEIGHT);
            if (!cellList.get(n).IsMine()) {
                cellList.get(n).SetMine();
                MINE_COUNT--;
            }
        }

        // Set numbers on board
        for (i = 0; i < cellList.size(); i++) {
            if(!cellList.get(i).IsMine()) {
                numOfMines = 0;
                if (i % WIDTH != WIDTH && i - WIDTH - 1 >= 0 && cellList.get(i - WIDTH - 1).IsMine()) { //TOP LEFT
                    numOfMines++;
                }
                if(i - WIDTH >= 0 && cellList.get(i - WIDTH).IsMine()) {                                //TOP
                    numOfMines++;
                }
                if(i % WIDTH != WIDTH - 1 && i - WIDTH + 1 >= 0 && cellList.get(i - WIDTH + 1).IsMine()) {  //TOP RIGHT
                    numOfMines++;
                }
                if(i % WIDTH != WIDTH && i - 1 >= 0 && cellList.get(i - 1).IsMine()) {                                        //LEFT
                    numOfMines++;
                }
                if(i % WIDTH != WIDTH - 1 && i + 1 < WIDTH * HEIGHT && cellList.get(i + 1).IsMine()) {  //RIGHT
                    numOfMines++;
                }
                if(i % WIDTH != WIDTH && i + WIDTH - 1 < WIDTH * HEIGHT && cellList.get(i + WIDTH - 1).IsMine()) {  //DOWN LEFT
                    numOfMines++;
                }
                if(i + WIDTH < WIDTH * HEIGHT && cellList.get(i + WIDTH).IsMine()) {                                //DOWN
                    numOfMines++;
                }
                if(i % WIDTH != WIDTH - 1 && i + WIDTH + 1 < WIDTH * HEIGHT && cellList.get(i + WIDTH + 1).IsMine()) { //DOWN RIGHT
                    numOfMines++;
                }

                cellList.get(i).SetNumberOfMines(numOfMines);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:
                cellList.get(0).gameOver = false;
                finish();
                startActivity(getIntent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
