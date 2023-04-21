package com.techelevator;

import com.techelevator.gamedata.GameBoard;
import com.techelevator.gamedata.TextHandler;


public class MainCLI {
    public static void main(String[] args) {
        GameBoard gb = new GameBoard();
        TextHandler.beginAventureMessage();
        gb.displayLayout();

        while(true) {
            String direction = TextHandler.chooseDirection();
            if (direction.equalsIgnoreCase("x")){
                break;
            }
            if (direction.equalsIgnoreCase("l")){
                gb.updateInventory();
            }
            gb.makeMove(direction);
            gb.checkInventory();
            gb.displayLayout();
            System.out.println(gb.eventEvaluator(gb.getCurrentPosition()));
        }

    }

}
