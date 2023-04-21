package com.techelevator.gamedata;

import java.util.Scanner;

public class TextHandler {

    public static void beginAventureMessage(){
        Scanner input = new Scanner(System.in);
        System.out.println("You are lost in the woods and need to find a way out!\nPress enter to begin the adventure!");
        input.nextLine();
    }

    public static String chooseDirection(){
        Scanner input = new Scanner(System.in);
        System.out.println("\nChoose a direction to travel\n(N)orth (S)outh\n(E)ast (W)est\n(L)ook around the area\nE(x)it to exit game: ");
        String direction = input.nextLine();
        return direction;
    }

    public static String encounterWater(){
        return "\nYou find a stream of fresh \u001B[34mwater\u001B[0m and refill your canteen!";
    }

    public static String encounterCamp(){
        return "\nYou find an abandoned campsite. This would be a good place to look for useful items.";
    }
}
