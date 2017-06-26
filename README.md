**##TIC-TAC-TOE Game README ver.1.0##**
-
This is tic-tac-toe game realisation on Spring Boot framework with use of Spring jpa, REST controller, in-memory database (h2) for backend and thymeleaf template engine, JQuery for frontend user interface.
Made for training and practising in javaEE.

**##HOW TO USE##**
-
- Compile to JAR archive (comes with Tomcat server by default), run in java environment.
- Browser entry point: http:/localhost:8080

**##RESTFULL HOWTO##**
-
Project has a RESTfull realisation of game pipeline. Pathways are as follows:

- /api/tictactoe/game/newgame (POST)
requires params: "player" - name of player.
Success: creates a new game instance in the DB, returns HttpStatus 200 and json serialisation of this game object.
Failure: returns HttpStatus 400.

- /api/tictactoe/game/{id}/connect (POST)
requires params: "player" - name of player, pathvariable {id} - ID of pending game.
Success: join a specified game instance, returns HttpStatus 200 and json serialisation of this game object.
Failure: returns HttpStatus 400.

- /api/tictactoe/game/{id}/currentstate (GET)
requires params: pathvariable {id} - ID of pending game.
Success: returns HttpStatus 200 and json serialisation of game object specified by ID.
Failure: returns HttpStatus 400.

- /api/tictactoe/game/{id}/getwinner (GET)
requires params: pathvariable {id} - ID of pending game.
Success: returns HttpStatus 200 and json serialisation of player object who won the game (if any), which is specified by id.
Failure: returns HttpStatus 400.

- /api/tictactoe/game/{id}/move (POST)
requires params: pathvariable {id} - ID of pending game, "move" - id of field, "player" name of player.
"move" is a parameter from standart tic-tac field (a1,a2,a3,b1,b2,b3,c1,c2,c3), to which the specified player can move.
Success: returns HttpStatus 200.
Failure: returns HttpStatus 400.

- /api/tictactoe/game/{id}/endgame (POST)
requires params: pathvariable {id} - ID of pending game.
This command ends the specified game without winner calculation (draw).
Success: returns HttpStatus 200.
Failure: returns HttpStatus 400.

**##AUTHOR##**
-
Written by Sergey Sorokin, sorokin1984@gmail.com
The MIT License.