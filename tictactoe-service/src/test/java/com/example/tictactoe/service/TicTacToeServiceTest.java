package com.example.tictactoe.service;

import com.example.tictactoe.BoardProperties;
import com.example.tictactoe.exception.ErrorType;
import com.example.tictactoe.exception.TicTacToeServiceException;
import com.example.tictactoe.TicTacToeElement;
import com.example.tictactoe.service.TicTacToeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicTacToeServiceTest {

    @Autowired
    TicTacToeService ticTacToeService;

    @Autowired
    BoardProperties boardProperties;

    @BeforeEach
    public void setUp() {
        ticTacToeService.initBoard(boardProperties.getBoardLength());
    }

    @Test
    public void addElementTest_EmptyBoardAndAddX_ShouldBeSuccessful() throws TicTacToeServiceException {
        Assertions.assertDoesNotThrow(() -> ticTacToeService.addElement(TicTacToeElement.X, 0, 0));
    }

    @Test
    public void addElementTest_XWinnerByColumn_ShouldBeSuccessful() throws TicTacToeServiceException{
        ticTacToeService.addElement(TicTacToeElement.X, 0, 0);
        ticTacToeService.addElement(TicTacToeElement.O, 1, 1);
        ticTacToeService.addElement(TicTacToeElement.X, 2, 0);
        ticTacToeService.addElement(TicTacToeElement.O, 1, 2);
        ticTacToeService.addElement(TicTacToeElement.X, 1, 0);
        Assertions.assertEquals(ticTacToeService.getGameState().getWinner(), TicTacToeElement.X);
    }

    @Test
    public void addElementTest_XWinnerByRow_ShouldBeSuccessful() throws TicTacToeServiceException{
        ticTacToeService.addElement(TicTacToeElement.X, 0, 0);
        ticTacToeService.addElement(TicTacToeElement.O, 1, 1);
        ticTacToeService.addElement(TicTacToeElement.X, 0, 2);
        ticTacToeService.addElement(TicTacToeElement.O, 1, 2);
        ticTacToeService.addElement(TicTacToeElement.X, 0, 1);
        Assertions.assertEquals(ticTacToeService.getGameState().getWinner(), TicTacToeElement.X);
    }

    @Test
    public void addElementTest_XWinnerByDiagonal_ShouldBeSuccessful() throws TicTacToeServiceException{
        ticTacToeService.addElement(TicTacToeElement.X, 1, 1);
        ticTacToeService.addElement(TicTacToeElement.O, 2, 2);
        ticTacToeService.addElement(TicTacToeElement.X, 2, 0);
        ticTacToeService.addElement(TicTacToeElement.O, 1, 2);
        ticTacToeService.addElement(TicTacToeElement.X, 0, 2);
        Assertions.assertEquals(ticTacToeService.getGameState().getWinner(), TicTacToeElement.X);
    }

    @Test
    public void addElementTest_XWinnerByAntiDiagonal_ShouldBeSuccessful() throws TicTacToeServiceException{
        ticTacToeService.addElement(TicTacToeElement.X, 0, 0);
        ticTacToeService.addElement(TicTacToeElement.O, 1, 0);
        ticTacToeService.addElement(TicTacToeElement.X, 1, 1);
        ticTacToeService.addElement(TicTacToeElement.O, 1, 2);
        ticTacToeService.addElement(TicTacToeElement.X, 2, 2);
        Assertions.assertEquals(ticTacToeService.getGameState().getWinner(), TicTacToeElement.X);
    }

    @Test
    public void addElementTest_ShouldBeDraw() throws TicTacToeServiceException{
        ticTacToeService.addElement(TicTacToeElement.X, 0, 0);
        ticTacToeService.addElement(TicTacToeElement.O, 1, 0);
        ticTacToeService.addElement(TicTacToeElement.X, 1, 1);
        ticTacToeService.addElement(TicTacToeElement.O, 2, 2);
        ticTacToeService.addElement(TicTacToeElement.X, 2, 0);
        ticTacToeService.addElement(TicTacToeElement.O, 2, 1);
        ticTacToeService.addElement(TicTacToeElement.X, 0, 1);
        ticTacToeService.addElement(TicTacToeElement.O, 0, 2);
        ticTacToeService.addElement(TicTacToeElement.X, 1, 2);
        Assertions.assertTrue(ticTacToeService.getGameState().isDraw());
    }

    @Test
    public void addElementTest_EmptyBoardAndAddO_ShouldThrowError() {
        TicTacToeServiceException exception = Assertions.assertThrows(TicTacToeServiceException.class, () -> ticTacToeService.addElement(TicTacToeElement.O, 0, 0));
        Assertions.assertEquals(ErrorType.X_FIRST_ERROR, exception.getErrorType());
    }

    @Test
    public void addElementTest_AddXAndIsOTurn_ShouldThrowError() throws TicTacToeServiceException{
        // First play X
        ticTacToeService.addElement(TicTacToeElement.X, 0, 0);

        TicTacToeServiceException exception = Assertions.assertThrows(TicTacToeServiceException.class, () -> ticTacToeService.addElement(TicTacToeElement.X, 0, 1));
        Assertions.assertEquals(ErrorType.X_TURN_ERROR, exception.getErrorType());
    }

    @Test
    public void addElementTest_AddOAndIsXTurn_ShouldThrowError() throws TicTacToeServiceException {
        // First play X then O
        ticTacToeService.addElement(TicTacToeElement.X, 0, 0);
        ticTacToeService.addElement(TicTacToeElement.O, 0, 1);

        TicTacToeServiceException exception = Assertions.assertThrows(TicTacToeServiceException.class, () -> ticTacToeService.addElement(TicTacToeElement.O, 2, 2));
        Assertions.assertEquals(ErrorType.O_TURN_ERROR, exception.getErrorType());
    }

    @Test
    public void addElementTest_AddOnPositionAlreadyExist_ShouldThrowError() throws TicTacToeServiceException {
        // First play X then O
        ticTacToeService.addElement(TicTacToeElement.X, 0, 0);

        TicTacToeServiceException exception = Assertions.assertThrows(TicTacToeServiceException.class, () -> ticTacToeService.addElement(TicTacToeElement.O, 0, 0));
        Assertions.assertEquals(ErrorType.POSITION_ALREADY_EXIST_ERROR, exception.getErrorType());
    }

    @Test
    public void addElementTest_AddOnPositionDoesNotExist_ShouldThrowError() throws TicTacToeServiceException {
        // First play X then O
        ticTacToeService.addElement(TicTacToeElement.X, 0, 0);

        TicTacToeServiceException exception = Assertions.assertThrows(TicTacToeServiceException.class, () -> ticTacToeService.addElement(TicTacToeElement.O, 10, 2));
        Assertions.assertEquals(ErrorType.POSITION_DOES_NOT_EXIST_ERROR, exception.getErrorType());
    }

}
