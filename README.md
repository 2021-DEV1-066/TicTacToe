# TicTacToe  the game
## How to get started

- `git pull`
- `mvn clean install`
- `cd tictactoe-app`  
- `mvn spring-boot:run`

## How to play

#### Start a game
POST on http://localhost:8080/tic-tac-toe/play with json-encoded param
``{
"size": 3
}``

You can skip this step and directly play (size will be set to 3)

#### Make a move
POST on http://localhost:8080/tic-tac-toe/play with json-encoded param

``{
  "location_x":0,
  "location_y":1,
  "element":"X"
  }``


### Remarks
- Since the UI/ way of playing was not the main point, I didn't make any test against the api.