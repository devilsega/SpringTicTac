package ua.devilsega.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.devilsega.JPA.Model.GameInstance;
import ua.devilsega.Services.GamePlayCalc;

/**
 * REST controller
 */

@RestController
public class RestApiController {
    @Autowired
    private GamePlayCalc gamePlayCalc;

    @RequestMapping(method= RequestMethod.POST, value="/api/tictactoe/game/newgame")
    public Object createNewGame(@RequestParam(value = "player")String player){
        GameInstance result = gamePlayCalc.createNewGame(player);
        if (result!=null){
            return result;
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null");
        }
    }
    @RequestMapping(method= RequestMethod.POST, value="/api/tictactoe/game/{id}/connect")
    public Object joinGame(@PathVariable(value = "id")int id, @RequestParam(value = "player")String player){
        GameInstance result = gamePlayCalc.joinGame(id,player);
        if(result!=null){
            return result;
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null");
        }
    }

    @RequestMapping(method= RequestMethod.GET, value="/api/tictactoe/game/{id}/currentstate")
    public Object getGamePlayArea(@PathVariable(value = "id") int id){
        if (gamePlayCalc.getCurrentState(id)!=null){
            return gamePlayCalc.getCurrentState(id);
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null");
    }

    @RequestMapping(method= RequestMethod.GET, value="/api/tictactoe/game/{id}/getwinner")
    public Object getWinner(@PathVariable(value = "id") int id){
        if (gamePlayCalc.getCurrentState(id)!=null){
            return gamePlayCalc.getWinner(id);
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null");
    }

    @RequestMapping(method= RequestMethod.POST, value="/api/tictactoe/game/{id}/move")
    public ResponseEntity<String> makeAMove(@PathVariable(value = "id") int id, @RequestParam(value = "move")String move, @RequestParam(value = "player")String player){
        if (gamePlayCalc.setMove(id, player, move)){
            return ResponseEntity.status(HttpStatus.OK).body("null");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null");
        }
    }

    @RequestMapping(method= RequestMethod.POST, value="/api/tictactoe/game/{id}/endgame")
    public ResponseEntity<String> endGame(@PathVariable(value = "id") int id){
        if (gamePlayCalc.endGame(id)){
            return ResponseEntity.status(HttpStatus.OK).body("null");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null");
        }
    }
}