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
        System.out.println("\nChoose a direction to travel\n(N)orth (S)outh\n(E)ast (W)est\n(I)nventory\nE(x)it to exit game: ");
        String direction = input.nextLine();
        return direction;
    }
    //----Encounter text--------------
    public static String encounterWater(){
        return "\nYou find a stream of fresh \u001B[34mwater\u001B[0m and refill your canteen!";
    }

    public static String encounterCamp(){
        return "\nYou find the ruins of a \u001B[35mcabin\u001B[0m! You search the area";
    }

    public static String encounterBear(){
        return "\nYou've startled a bear and it \u001B[31mattacks\u001B[0m!\nGAME OVER";
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
        return " and find a jug!\nYou can now carry all the water you'll need!";
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

}
