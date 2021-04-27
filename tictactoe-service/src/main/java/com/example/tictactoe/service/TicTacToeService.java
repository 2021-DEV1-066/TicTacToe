package com.example.tictactoe.service;

import com.example.tictactoe.BoardProperties;
import com.example.tictactoe.GameState;
import com.example.tictactoe.TicTacToeElement;
import com.example.tictactoe.exception.ErrorType;
import com.example.tictactoe.exception.TicTacToeServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class TicTacToeService {

    private static final Logger logger = LoggerFactory.getLogger(TicTacToeService.class);
    private BoardProperties boardProperties;
    private GameState gameState;

    public TicTacToeService(BoardProperties boardProperties) {
        this.boardProperties = boardProperties;
        initBoard(boardProperties.getBoardLength());
    }

    public void initBoard(int size) {
        gameState = new GameState(size);
    }

    public void addElement(TicTacToeElement element, int x, int y) throws TicTacToeServiceException {
        checkElement(element);
        checkLocationIsValid(x, y);
        gameState.getBoard()[x][y] = element.getValue();
        gameState.setLastElementPlayed(element);
        gameState.addMoveToCount();
        checkWinningMove(element,x,y);
    }

    public GameState getGameState() {
        return gameState;
    }

    private void checkWinningMove(TicTacToeElement element,int x,int y) {
        // Winner can only be the last played element
        int logicalSize = boardProperties.getBoardLength()-1;
        // Check column (only the one we just played)
        for(int i=0;i<=logicalSize;i++){
            if( gameState.getBoard()[x][i] != element.getValue()){
                break;
            }
            // Be sure to be at the last index
            if(i == logicalSize){
                gameState.setWinner(element);
            }

        }
        // Check row (only the one we just played)
        for(int i=0;i<=logicalSize;i++){
            if( gameState.getBoard()[i][y] != element.getValue()){
                break;
            }
            // Be sure to be at the last index
            if(i == logicalSize){
                gameState.setWinner(element);
            }
        }
        // Check diagonal
            // normal diagonal
            for(int i= 0;i<=logicalSize;i++){
                if( gameState.getBoard()[i][i] != element.getValue()){
                    break;
                }
                // Be sure to be at the last index
                if(i == logicalSize){
                    gameState.setWinner(element);
                }
            }
            // anti diagonal
            for(int i= 0;i<=logicalSize;i++){
                if( gameState.getBoard()[i][logicalSize-i] != element.getValue()){
                    break;
                }
                //Be sure to be at the last index
                if(i == logicalSize){
                    gameState.setWinner(element);
                }
            }
            // check draw
            if(gameState.getMoveCount() == Math.pow(logicalSize,2)-1){
                gameState.setDraw(true);
            }

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

        if (gameState.getBoard()[x][y] != 0) {
            logger.error("Location [x:{},y:{}] is out of boundary", x, y);
            throw new TicTacToeServiceException("Given location already exist", ErrorType.POSITION_ALREADY_EXIST_ERROR);
        }
    }

    private void checkElement(TicTacToeElement element) throws TicTacToeServiceException {
        if (gameState.getLastElementPlayed() == null && element != TicTacToeElement.X) {
            logger.error("X must be the first element to be played - received: [{}]", element);
            throw new TicTacToeServiceException("X must be the first to play", ErrorType.X_FIRST_ERROR);
        } else if (gameState.getLastElementPlayed() == element) {
            logger.error("Not correct element received - previous: [{}] -received: [{}]", gameState.getLastElementPlayed(), element);
            if (element == TicTacToeElement.X) {
                throw new TicTacToeServiceException("It's not your turn - it's O player to make the move!", ErrorType.X_TURN_ERROR);
            } else {
                throw new TicTacToeServiceException("It's not your turn - it's X player to make the move!", ErrorType.O_TURN_ERROR);
            }
        }
    }
}
