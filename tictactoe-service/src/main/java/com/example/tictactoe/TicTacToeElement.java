package com.example.tictactoe;

public enum TicTacToeElement {
    X(1),
    O(2);

    private int value;

    TicTacToeElement(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TicTacToeElement fromValue(int value) {
        for (TicTacToeElement e : TicTacToeElement.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}
