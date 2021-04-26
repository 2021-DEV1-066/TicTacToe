package com.example.tictactoe.service;

import com.example.tictactoe.BoardProperties;
import com.example.tictactoe.TicTacToeElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TicTacToeService {

    private static Logger logger = LoggerFactory.getLogger(TicTacToeService.class);

    private int[][] board;

    @Autowired
    BoardProperties boardProperties;

    @PostConstruct
    public void setUp() {
        logger.info("board: {}", boardProperties.getBoardLength());
        board = new int[boardProperties.getBoardLength()][boardProperties.getBoardLength()];
    }

    public boolean addElement(TicTacToeElement element, int x, int y) {
        checkLocationInBoard(x, y);
        return checkWinningMove();
    }

    private boolean checkWinningMove() {
        return false;
    }

    // Throw exception
    private void checkLocationInBoard(int x, int y) {

    }
}
