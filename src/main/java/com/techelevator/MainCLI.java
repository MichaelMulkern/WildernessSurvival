package com.techelevator;

import com.techelevator.gamedata.GameBoard;
import com.techelevator.gamedata.TextHandler;


public class MainCLI {
    public static void main(String[] args) {
        GameBoard gb = new GameBoard();
        TextHandler display = new TextHandler();

        display.beginAventureMessage();
        gb.displayLayout();

        while(true) {
            String direction = display.chooseDirection();
            if (direction.equalsIgnoreCase("x")){
                break;
            }else if(direction.equalsIgnoreCase("i")){
                System.out.println(gb.getInventory());

            }
            gb.makeMove(direction);
            gb.checkInventory();
            gb.displayLayout();
            System.out.println(gb.eventEvaluator(gb.getCurrentPosition()));
            gb.checkWater();
            if(gb.isGameOver()){
                System.out.println(gb.gameEndCondition());
                break;
            }

        }

    }


}
