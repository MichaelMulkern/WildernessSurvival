package com.techelevator.gamedata;

import java.util.Scanner;

public class TextHandler {
    public TextHandler() {
    }

    public void beginAventureMessage(){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Wilderness Survival v1.0!");
        System.out.println("You are lost in the woods and need to find a way out!");
        System.out.println("The map represents an unexplored forest area, and the player position is marked by an \u001B[33mX\u001B[0m.");
        System.out.println("Moving through the forest will reveal the area and you'll face random encounters.\n" +
                "Every move you make will take one water to complete, and water can be refilled from any stream you find (\u001B[34mO\u001B[0m).\n" +
                "\u001B[36mM\u001B[0mountains will allow you see adjacent squares, but they use 1 extra water to climb.\n" +
                "Search old \u001B[35mC\u001B[0mabins for useful items! There are three possible items you can find, each will eliminate a particular threat or help you see further.\n" +
                "Watch out for bears! Most will leave you alone, but be careful not to startle one!\n" +
                "There is a search party looking for you represented by an \u001B[31mH\u001B[0m. Find them to be rescued!" +
                "The game board is randomly generated, so difficulty will vary from game to game.");
        System.out.println("Press enter to begin the adventure!");
        input.nextLine();
    }

    public String chooseDirection(){
        Scanner input = new Scanner(System.in);
        System.out.println("\nChoose a direction to travel\n(N)orth (S)outh\n(E)ast (W)est\n(I)nventory\nE(x)it to exit game: ");
        String direction = input.nextLine();
        return direction;
    }
    //----Encounter text--------------
    public static String encounterWater(){
        return "\nYou find a stream of cold mountain \u001B[34mwater\u001B[0m! You feel refreshed!";
    }

    public static String encounterCamp(){
        return "\nYou find the ruins of a \u001B[35mcabin\u001B[0m! You search the area";
    }

    public static String encounterMountain(){
        return "\nYou crest a \u001B[36mmountain\u001B[0m and have a spectacular view!\nYou can see into ajacent squares, but used an extra water summiting the peak.";
    }

    public static String victory(){
        return "\nYou found the search party and have been rescued!!\nGAME OVER";
    }

    //---Inventory Text------------
    public static String findBinocular(){
        return " and find a binocular!\nThis allows you to see into ajacent squares.\nRemember this doesn't allow you to see the location of bears, be careful!";
    }
    public static String findWhistle(){
        return " and find a whistle!\nThis eliminates the threat of bears!";
    }
    public static String findJug(){
        return " and find a large jug!\nYou can now carry all the water you'll need!";
    }
    public static String findNothing(){
        return " and find nothing of value.";
    }

    public static String showInventory(){
        return "Your inventory contains: ";
    }

    public static String emptyInventory(){
        return "Your inventory is empty.";
    }

    public String displayWater(){
        return "Water remaining: ";
    }

}
