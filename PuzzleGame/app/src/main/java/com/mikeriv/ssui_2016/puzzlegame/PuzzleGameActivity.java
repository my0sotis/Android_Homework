package com.mikeriv.ssui_2016.puzzlegame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mikeriv.ssui_2016.puzzlegame.model.PuzzleGameBoard;
import com.mikeriv.ssui_2016.puzzlegame.model.PuzzleGameState;
import com.mikeriv.ssui_2016.puzzlegame.model.PuzzleGameTile;
import com.mikeriv.ssui_2016.puzzlegame.util.PuzzleImageUtil;
import com.mikeriv.ssui_2016.puzzlegame.view.PuzzleGameTileView;

import java.util.Random;

public class PuzzleGameActivity extends AppCompatActivity {

    // The default grid size to use for the puzzle game 4 => 4x4 grid
    private static final int DEFAULT_PUZZLE_BOARD_SIZE = 2;

    // The id of the image to use for our puzzle game
    private static final int TILE_IMAGE_ID = R.drawable.kitty;

    /**
     * Button Listener that starts a new game - this must be attached to the new game button
     */
    private final View.OnClickListener mNewGameButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO start a new game if a new game button is clicked
            if(view.getId() == R.id.btn_newgame) {
                startNewGame();
            }
        }
    };

    private final View.OnClickListener mResetButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_reset) {
                mPuzzleGameBoard.reset();
                refreshGameBoardView();
                score = 0;
                updateScore();
                mGameState = PuzzleGameState.NONE;
            }
        }
    };

    private final View.OnClickListener mSettingButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_settings) {
                ShowSingleDialog();
            }
        }
    };

    /**
     * Click Listener that Handles Tile Swapping when we click on a tile that is
     * neighboring the empty tile - this must be attached to every tileView in the grid
     */
    private final View.OnClickListener mGameTileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           // TODO handle swapping tiles and updating the tileViews if there is a valid swap
            // with an empty tile
            // If any changes happen, be sure to update the state of the game to check for a win
            // condition
            int id = view.getId();
            int i = id / mPuzzleBoardSize;
            int j = id % mPuzzleBoardSize;
            boolean ismatch = false;
            for (int k = 0; k < mPuzzleBoardSize; k++) {
                for (int l = 0; l < mPuzzleBoardSize; l++) {
                    if(mPuzzleGameBoard.areTilesNeighbors(i,j,k,l)) {
                        if(mPuzzleGameBoard.getTile(k,l).isEmpty()) {
                            mPuzzleGameBoard.swapTiles(i,j,k,l);
                            refreshGameBoardView(i,j);
                            refreshGameBoardView(k,l);
                            ismatch = true;
                        }
                    }
                    if(ismatch) break;
                }
                if(ismatch) break;
            }
            if(ismatch) {
                score++;
                updateScore();
                updateGameState();
            }
        }
    };

    // Game State - what the game is currently do in
    private PuzzleGameState mGameState = PuzzleGameState.NONE;

    // The size of our puzzle board (mPuzzleBoardSize x mPuzzleBoardSize grid)
    private int mPuzzleBoardSize = DEFAULT_PUZZLE_BOARD_SIZE;

    // The puzzle board model
    private PuzzleGameBoard mPuzzleGameBoard;

    // Views
    private TextView mScoreTextView;

    private LinearLayout mGameBoard;

    // The views for the puzzle board tile models
    private PuzzleGameTileView[][] mPuzzleTileViews =
            new PuzzleGameTileView[mPuzzleBoardSize][mPuzzleBoardSize];

    private  int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mScoreTextView = findViewById(R.id.text_score);

        // TODO initialize references to any containers views or layout groups etc.
        mGameBoard = findViewById((R.id.gameboard));

        Button btn_newgame = findViewById(R.id.btn_newgame);
        btn_newgame.setOnClickListener(mNewGameButtonOnClickListener);

        Button btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(mResetButtonOnClickListener);

        Button btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(mSettingButtonOnClickListener);

        // Initializes the game and updates the game state
        initGame();
        updateScore();
    }

    /**
     * Creates the puzzle board and the PuzzleGameTiles that serve as the model for the game. It
     * does image slicing to get the appropriate bitmap subdivisions of the TILE_IMAGE_ID. It
     * then creates a set for PuzzleGameTileViews that are used to display the information in models
     */
    private void initGame() {
        mPuzzleGameBoard = new PuzzleGameBoard(mPuzzleBoardSize, mPuzzleBoardSize);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;

        ViewGroup.LayoutParams lp = mGameBoard.getLayoutParams();
        lp.height = width - 100;
        mGameBoard.setLayoutParams(lp);

        // Get the original image bitmap
        Bitmap fullImageBitmap = BitmapFactory.decodeResource(getResources(), TILE_IMAGE_ID);
        // Now scale the bitmap so it fits out screen dimensions and change aspect ratio (scale) to
        // fit a square
        int fullImageWidth = fullImageBitmap.getWidth();
        int fullImageHeight = fullImageBitmap.getHeight();
        int squareImageSize = (fullImageWidth > fullImageHeight) ? fullImageWidth : fullImageHeight;
        fullImageBitmap = Bitmap.createScaledBitmap(
                fullImageBitmap,
                squareImageSize,
                squareImageSize,
                false);

        // TODO calculate the appropriate size for each puzzle tile
        int SizePerTile = squareImageSize / mPuzzleBoardSize;

        // TODO create the PuzzleGameTiles for the PuzzleGameBoard using sections of the bitmap.
        // You may find PuzzleImageUtil helpful for getting sections of the bitmap
        // Also ensure the last tile (the bottom right tile) is set to be an "empty" tile
        // (i.e. not filled with an section of the original image
        for (int i = 0; i < mPuzzleBoardSize; i++) {
            for (int j = 0; j < mPuzzleBoardSize; j++) {
                BitmapDrawable bd = new BitmapDrawable(getResources(),
                        PuzzleImageUtil.getSubdivisionOfBitmap(fullImageBitmap,
                                SizePerTile,SizePerTile,i,j));
                PuzzleGameTile pgt = new PuzzleGameTile(i * mPuzzleBoardSize + j,bd);
                if(i == mPuzzleBoardSize - 1 && j == mPuzzleBoardSize - 1)
                    pgt.setIsEmpty(true);
                mPuzzleGameBoard.setTile(pgt,i,j);
            }
        }

        // TODO createPuzzleTileViews with the appropriate width, height
        createPuzzleTileViews(mGameBoard.getWidth()/mPuzzleBoardSize - 5,
                mGameBoard.getHeight()/mPuzzleBoardSize - 5);
    }

    /**
     * Creates a set of tile views based on the tileWidth and height
     * @param minTileViewWidth the minimum width of the tile
     * @param minTileViewHeight the minimum height of the tile
     */

    private void createPuzzleTileViews(int minTileViewWidth, int minTileViewHeight) {
        int rowsCount = mPuzzleGameBoard.getRowsCount();
        int colsCount = mPuzzleGameBoard.getColumnsCount();
        // TODO Set up TileViews (that will be what the user interacts with)
        // Make sure each tileView gets a click listener for interaction
        // Be sure to set the appropriate LayoutParams so that your tileViews
        // So that they fit your game board properly
        for (int i = 0; i < rowsCount; i++) {
            LinearLayout tmp = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    0,1);
            tmp.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.MATCH_PARENT,1);
            para.setMargins(5,3,0,0);
            for (int j = 0; j < colsCount; j++) {
                mPuzzleTileViews[i][j] = new PuzzleGameTileView(this,
                        i * mPuzzleBoardSize + j,
                        minTileViewWidth,minTileViewHeight);
                mPuzzleTileViews[i][j].updateWithTile(mPuzzleGameBoard.getTile(i,j));
                if(mPuzzleGameBoard.isEmptyTile(i,j))
                    mPuzzleTileViews[i][j].setVisibility(View.INVISIBLE);
                mPuzzleTileViews[i][j].setId(i * colsCount + j);
                tmp.addView(mPuzzleTileViews[i][j],para);
            }
            mGameBoard.addView(tmp,params);
        }
    }

    private void setTilesOnClickListener() {
        for (int i = 0; i < mPuzzleBoardSize; i++) {
            for (int j = 0; j < mPuzzleBoardSize; j++) {
                mPuzzleTileViews[i][j].setOnClickListener(mGameTileOnClickListener);
            }
        }
    }

    /**
     * Shuffles the puzzle tiles randomly such that tiles may only swap if they are swapping with
     * an empty tile to maintain solve ability
     */
    private void shufflePuzzleTiles() {
        // TODO randomly shuffle the tiles such that tiles may only move spots if it is randomly
        // swapped with a neighboring empty tile
        for (int i = 0; i < mPuzzleGameBoard.getRowsCount(); i++) {
            for (int j = 0; j < mPuzzleGameBoard.getColumnsCount(); j++) {
                int k = (int)Math.floor(Math.random() * mPuzzleGameBoard.getRowsCount());
                int l = (int)Math.floor(Math.random() * mPuzzleGameBoard.getColumnsCount());
                mPuzzleGameBoard.swapTiles(i,j,k,l);
            }
        }
        if(!isSolveable() && !hasWonGame())
            shufflePuzzleTiles();
    }

    /**
     * Updates the game state by checking if the user has won. Also triggers the tileViews to update
     * their visuals based on the game board
     */
    private void updateGameState() {
        // TODO refresh tiles and handle winning the game and updating score
        if(mGameState == PuzzleGameState.PLAYING && hasWonGame()) {
            mGameState = PuzzleGameState.WON;
            showNormalDialog();
        }
    }

    private void refreshGameBoardView() {
        // TODO update the PuzzleTileViews with the data stored in the PuzzleGameBoard
        for (int i = 0; i < mPuzzleBoardSize; i++) {
            for (int j = 0; j < mPuzzleBoardSize; j++) {
                refreshGameBoardView(i,j);
            }
        }
    }

    private void refreshGameBoardView(int Row, int Col) {
        mPuzzleTileViews[Row][Col].updateWithTile(mPuzzleGameBoard.getTile(Row,Col));
        mPuzzleTileViews[Row][Col].setVisibility(View.VISIBLE);
        if(mPuzzleGameBoard.getTile(Row,Col).isEmpty())
            mPuzzleTileViews[Row][Col].setVisibility(View.INVISIBLE);
    }

    private boolean isSolveable() {
        int emptyRow = 0;
        boolean ismatch = false;
        for (int i = 0; i < mPuzzleBoardSize; i++) {
            for (int j = 0; j < mPuzzleBoardSize; j++) {
                if(mPuzzleGameBoard.getTile(i,j).isEmpty()) {
                    emptyRow = i + 1;
                    ismatch = true;
                }
                if(ismatch) break;
            }
            if(ismatch) break;
        }
        if(mPuzzleBoardSize % 2 == 1) {
            return (mPuzzleGameBoard.sumInversions() % 2 == 0);
        } else {
            return ((mPuzzleGameBoard.sumInversions() + mPuzzleBoardSize - emptyRow) % 2 == 0);
        }
    }

    /**
     * Checks the game board to see if the tile indices are in proper increasing order
     * @return true if the tiles are in correct order and the game is won
     */
    private boolean hasWonGame() {
        // TODO check if the user has won the game
        boolean res = true;
        for (int i = 0; i < mPuzzleBoardSize; i++) {
            for (int j = 0; j < mPuzzleBoardSize; j++) {
                if(!(mPuzzleGameBoard.getTile(i,j).getOrderIndex() == i * mPuzzleBoardSize + j))
                    res = false;
            }
        }
        return res;
    }

    /**
     * Updates the score displayed in the text view
     */
    private void updateScore() {
        // TODO update a score to be displayed to the user
        String text = String.format(getResources().getString(R.string.title_score_board),score);
        mScoreTextView.setText(text);
    }

    /**
     * Begins a new game by shuffling the puzzle tiles, changing the game state to playing
     * and showing a start message
     */
    private void startNewGame() {
        // TODO - handle starting a new game by shuffling the tiles and showing a start message,
        // and updating the game state
        shufflePuzzleTiles();
        refreshGameBoardView();
        mGameState = PuzzleGameState.PLAYING;
        score = 0;
        updateScore();
        setTilesOnClickListener();
    }

    private void showNormalDialog() {
        final AlertDialog.Builder MessageDialog =
                new AlertDialog.Builder(PuzzleGameActivity.this);
        MessageDialog.setTitle("Congratulations");
        String text = String.format(getResources().getString(R.string.title_puzzle_solved),score);
        MessageDialog.setMessage(text);
        MessageDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPuzzleGameBoard.reset();
                        refreshGameBoardView();
                        score = 0;
                        updateScore();
                    }
                });
        MessageDialog.show();
    }

    private void ShowSingleDialog() {
        final AlertDialog.Builder ChooseDifficultDialog =
                new AlertDialog.Builder(PuzzleGameActivity.this);
        ChooseDifficultDialog.setTitle("Choose Level");
        final String[] Difficults = {"Easy","Medium","Hard"};
        ChooseDifficultDialog.setSingleChoiceItems(Difficults, mPuzzleBoardSize - 3,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPuzzleBoardSize = which + 3;
            }
        });
        ChooseDifficultDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RemoveExistTileAndViews();
                initGame();

            }
        });
        ChooseDifficultDialog.show();
    }

    private void RemoveExistTileAndViews() {
        mGameBoard.removeAllViews();
        mPuzzleTileViews = new PuzzleGameTileView[mPuzzleBoardSize][mPuzzleBoardSize];
    }
}
