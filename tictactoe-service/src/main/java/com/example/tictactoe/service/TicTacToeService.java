package com.example.tictactoe.service;

import com.example.tictactoe.BoardProperties;
import com.example.tictactoe.GameState;
import com.example.tictactoe.TicTacToeElement;
import com.example.tictactoe.exception.ErrorType;
import com.example.tictactoe.exception.TicTacToeServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TicTacToeService {

    private static Logger logger = LoggerFactory.getLogger(TicTacToeService.class);

    private static int[][] board;

    private TicTacToeElement lastElementPlayed;
    private int moveCount=0;

    private BoardProperties boardProperties;

    public TicTacToeService(BoardProperties boardProperties) {
        this.boardProperties = boardProperties;
        initBoard(boardProperties.getBoardLength());
    }

    public void initBoard(int size) {
        board = new int[size][size];
        lastElementPlayed = null;
        moveCount = 0;
    }

    public void addElement(TicTacToeElement element, int x, int y) throws TicTacToeServiceException {
        checkElement(element);
        checkLocationIsValid(x, y);
        // add element in board
        board[x][y] = element.getValue();
        // assign lastPlayedElement
        lastElementPlayed = element;
        moveCount++;
    }

    public GameState getGameState() throws TicTacToeServiceException {
        return new GameState();
    }

    private boolean checkWinningMove(TicTacToeElement element,int x,int y) {
        // Winner can only be the last played element
        int logicalSize = boardProperties.getBoardLength()-1;
        // Check column (only the one we just played)
        for(int i=0;i<=logicalSize;i++){
            if(board[x][i] != element.getValue()){
                break;
            }
            // Be sure to be at the last index
            if(i == logicalSize){
                return true;
            }

        }
        // Check row (only the one we just played)
        for(int i=0;i<=logicalSize;i++){
            if(board[i][y] != element.getValue()){
                break;
            }
            // Be sure to be at the last index
            if(i == logicalSize){
                return true;
            }
        }
        // Check diagonal
        if (x == y){
            // normal diagonal
            for(int i= 0;i<=logicalSize;i++){
                if(board[i][i] != element.getValue()){
                    break;
                }
                //Be sure to be at the last index
                if(i == logicalSize){
                    return true;
                }
            }
            // anti diagonal
            for(int i= 0;i<=logicalSize;i++){
                // x=1 y =3
                // x=3 y=1
                if(board[i][logicalSize-i] != element.getValue()){
                    break;
                }
                //Be sure to be at the last index
                if(i == logicalSize){
                    return true;
                }
            }
            // check draw
            if(moveCount == Math.pow(logicalSize,2)-1){
                return true;
            }
        }
        return false;
    }

    // Throw exception
    private void checkLocationIsValid(int x, int y) throws TicTacToeServiceException {
        if (x < 0 || x > boardProperties.getBoardLength()-1) {
            throw new TicTacToeServiceException("Given X location is out of boundary", ErrorType.POSITION_DOES_NOT_EXIST_ERROR);
        }

        if (y < 0 || y > boardProperties.getBoardLength()-1) {
            logger.error("Location [y:{}] is out of boundary", x);
            throw new TicTacToeServiceException("Given y Location is out of boundary", ErrorType.POSITION_DOES_NOT_EXIST_ERROR);
        }

        if (board[x][y] != 0) {
            logger.error("Location [x:{},y:{}] is out of boundary", x, y);
            throw new TicTacToeServiceException("Given location already exist", ErrorType.POSITION_ALREADY_EXIST_ERROR);
        }
    }

    private void checkElement(TicTacToeElement element) throws TicTacToeServiceException {
        if (lastElementPlayed == null && element != TicTacToeElement.X) {
            logger.error("X must be the first element to be played - received: [{}]", element);
            throw new TicTacToeServiceException("X must be the first to play", ErrorType.X_FIRST_ERROR);
        } else if (lastElementPlayed == element) {
            logger.error("Not correct element received - previous: [{}] -received: [{}]", lastElementPlayed, element);
            if (element == TicTacToeElement.X) {
                throw new TicTacToeServiceException("It's not your turn - it's O player to make the move!", ErrorType.X_TURN_ERROR);
            } else {
                throw new TicTacToeServiceException("It's not your turn - it's X player to make the move!", ErrorType.O_TURN_ERROR);
            }
        }
    }
}
