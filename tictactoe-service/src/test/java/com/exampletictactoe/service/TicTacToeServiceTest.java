package com.exampletictactoe.service;

import com.example.tictactoe.exception.ErrorType;
import com.example.tictactoe.exception.TicTacToeServiceException;
import com.example.tictactoe.TicTacToeElement;
import com.example.tictactoe.service.TicTacToeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicTacToeServiceTest {

    TicTacToeService ticTacToeService;

    @Test
    public void addElementTest_EmptyBoardAndAddX_ShouldBeSuccessful() {
        Assertions.assertTrue(ticTacToeService.addElement(TicTacToeElement.X, 0, 0));
    }

    @Test
    public void addElementTest_EmptyBoardAndAddO_ShouldThrowError() {
        TicTacToeServiceException exception = Assertions.assertThrows(TicTacToeServiceException.class, () -> ticTacToeService.addElement(TicTacToeElement.O, 0, 0));
        Assertions.assertEquals(ErrorType.X_FIRST_ERROR, exception.getErrorType());
    }

    @Test
    public void addElementTest_AddXAndIsOTurn_ShouldThrowError() {
        TicTacToeServiceException exception = Assertions.assertThrows(TicTacToeServiceException.class, () -> ticTacToeService.addElement(TicTacToeElement.X, 0, 0));
        Assertions.assertEquals(ErrorType.X_TURN_ERROR, exception.getErrorType());
    }

    @Test
    public void addElementTest_AddOAndIsOTurn_ShouldThrowError() {
        TicTacToeServiceException exception = Assertions.assertThrows(TicTacToeServiceException.class, () -> ticTacToeService.addElement(TicTacToeElement.O, 0, 0));
        Assertions.assertEquals(ErrorType.O_TURN_ERROR, exception.getErrorType());
    }

    @Test
    public void addElementTest_AddOnPositionAlreadyExist_ShouldThrowError() {
        TicTacToeServiceException exception = Assertions.assertThrows(TicTacToeServiceException.class, () -> ticTacToeService.addElement(TicTacToeElement.O, 0, 0));
        Assertions.assertEquals(ErrorType.POSITION_ALREADY_EXIST_ERROR, exception.getErrorType());
    }

    @Test
    public void addElementTest_AddOnPositionDoesNotExist_ShouldThrowError() {
        TicTacToeServiceException exception = Assertions.assertThrows(TicTacToeServiceException.class, () -> ticTacToeService.addElement(TicTacToeElement.O, 0, 0));
        Assertions.assertEquals(ErrorType.POSITION_DOES_NOT_EXIST_ERROR, exception.getErrorType());
    }

}
