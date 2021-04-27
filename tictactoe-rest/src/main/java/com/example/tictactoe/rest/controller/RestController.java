package com.example.tictactoe.rest.controller;

import com.example.tictactoe.BoardProperties;
import com.example.tictactoe.TicTacToeElement;
import com.example.tictactoe.exception.TicTacToeServiceException;
import com.example.tictactoe.rest.api.ElementInformation;
import com.example.tictactoe.service.TicTacToeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RestController {

    private static Logger logger = LoggerFactory.getLogger(RestController.class);

    private TicTacToeService ticTacToeService;
    private BoardProperties boardProperties;

    public RestController(TicTacToeService ticTacToeService, BoardProperties boardProperties) {
        this.ticTacToeService = ticTacToeService;
        this.boardProperties = boardProperties;
    }

    @PostMapping(value = "/tic-tac-toe/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newGame(@RequestParam("size") int size) {
        ticTacToeService.initBoard(size);
        return ResponseEntity.ok(String.format("New Tic Tac Toe game of size [%d] has began - X player to make the move!", size));
    }

    @PostMapping(value = "/tic-tac-toe/play", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> playElementOnBoard(@Valid @RequestBody ElementInformation elementInformation) {
        // x and y already validated
        TicTacToeElement element = validationElement(elementInformation.getValue());
        if(ticTacToeService.getGameState().getLastElementPlayed() == null) {
            // Init game
            ticTacToeService.initBoard(boardProperties.getBoardLength());
        }
        // play
        try {
            ticTacToeService.addElement(element, elementInformation.getX(), elementInformation.getY());
            if (ticTacToeService.getGameState().getWinner() != null) {
                // Game finished re-init board
                ticTacToeService.initBoard(boardProperties.getBoardLength());
                return ResponseEntity.ok(String.format("Player [%s] has won the game by playing location [%d, %d] - Game has been re-launch: X turn to play !", element, elementInformation.getX(), elementInformation.getY()));
            }else if(ticTacToeService.getGameState().isDraw()){
                // Game finished re-init board
                ticTacToeService.initBoard(boardProperties.getBoardLength());
                return ResponseEntity.ok(String.format("The game is a draw - Game has been re-lunch: X turn to play !"));
            }
        } catch (TicTacToeServiceException e) {
            logger.error("Error while trying to play a move", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(String.format("Player placed [%s] in location [%d, %d]", element, elementInformation.getX(), elementInformation.getY()));

    }

    @GetMapping(value = "/tic-tac-toe")
    public ResponseEntity<String> viewGameState() {
        if(ticTacToeService.getGameState() == null){
            return ResponseEntity.ok("The game isn't started yet !");
        }
        return ResponseEntity.ok(ticTacToeService.getGameState().toString());
    }

    // Validation method
    private TicTacToeElement validationElement(String element) {
        TicTacToeElement elem = TicTacToeElement.valueOf(element);
        if (elem == null) {
            // Exception
        }
        return elem;
    }
}
