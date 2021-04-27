package com.example.tictactoe;

public class GameState {
    private TicTacToeElement winner;
    private int[][] board;
    private boolean hasNextMove;
    private boolean isDraw;

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
