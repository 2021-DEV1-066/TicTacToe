package com.example.tictactoe;

public class GameState {
    private TicTacToeElement winner;
    private int[][] board;
    private boolean hasNextMove;
    private boolean isDraw;
    private TicTacToeElement lastElementPlayed;
    private int moveCount;

    public GameState(int size) {
        this.winner = null;
        this.board = new int[size][size];
        this.hasNextMove = true;
        this.isDraw = false;
        this.lastElementPlayed = null;
        moveCount = 0;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void addMoveToCount() {
        this.moveCount++;
    }

    public TicTacToeElement getLastElementPlayed() {
        return lastElementPlayed;
    }

    public void setLastElementPlayed(TicTacToeElement lastElementPlayed) {
        this.lastElementPlayed = lastElementPlayed;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public TicTacToeElement getWinner() {
        return winner;
    }

    public void setWinner(TicTacToeElement winner) {
        this.winner = winner;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public boolean isHasNextMove() {
        return hasNextMove;
    }

    public void setHasNextMove(boolean hasNextMove) {
        this.hasNextMove = hasNextMove;
    }
}


