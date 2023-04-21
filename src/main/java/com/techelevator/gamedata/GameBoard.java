package com.techelevator.gamedata;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.IntStream;

public class GameBoard {
    private String[] activeBoard;
    private int currentPosition;
    private int rescueLocation;

    private Set<Integer> visitedSquares = new HashSet<>();
    private Set<Integer> waterLocations = new HashSet<>();
    private Set<Integer> campLocations = new HashSet<>();
    private Set<Integer> bearLocations = new HashSet<>();
    private Set<Integer> usedLocations = new HashSet<>();

    private final int MAX_BOARD_SIZE = 310;
    private final int MIN_BOARD_SIZE = 0;
    private final int MOVE_NORTH = -31;
    private final int MOVE_SOUTH = 31;
    private final int MOVE_EAST = 1;
    private final int MOVE_WEST = -1;

    private Set<InventoryItem> inventory = new HashSet<>();


    public GameBoard() {
        this.activeBoard = createLayout();
        this.currentPosition = randomStart();
    }

    public String[] createLayout() {
        String[] layout = new String[MAX_BOARD_SIZE];
        for (int i = 0; i < layout.length; i++) {
            layout[i] = "*";
        }
        rescueLocation = createRescueLocation();
        waterLocations = createWaterLocations();
        campLocations = createCampLocations();
    return layout;
    }

    public void displayLayout() {
        String[] lo = getActiveBoard();
        int counter = 0;
        System.out.print("\033[H\033[2J"); //ASCII escape codes to clear the console
        //System.out.flush();
        for (int i = 0; i < lo.length; i++) {
            if (counter == 30){
                sortBoardSquare(i);
                System.out.print("\n");
                counter = 0;
            }else {
                sortBoardSquare(i);
                counter++;
            }

        }
    }

    public void updateGameboard(String[] oldBoard, int position, String update){
        oldBoard[position] = update;
        setActiveBoard(oldBoard);
    }

    public void makeMove(String direction){
        int moveNumber = getCurrentPosition();
        if (direction.equalsIgnoreCase("n")){
            if ((moveNumber+MOVE_NORTH) < MIN_BOARD_SIZE){
                System.out.println("\n\nInvalid move, please select again");
            }else{
                moveNumber += MOVE_NORTH;
            }

        }else if (direction.equalsIgnoreCase("s")){
            if ((moveNumber+MOVE_SOUTH) > MAX_BOARD_SIZE){
                System.out.println("\n\nInvalid move, please select again");
            }else{
                moveNumber += MOVE_SOUTH;
            }
        }else if (direction.equalsIgnoreCase("e")){
            moveNumber += MOVE_EAST;
        }else if (direction.equalsIgnoreCase("w")){
            moveNumber += MOVE_WEST;
        }
        visitedSquares.add(moveNumber);
        setCurrentPosition(moveNumber);
        updateGameboard(getActiveBoard(), getCurrentPosition(), "X");
    }

    public void sortBoardSquare(int position){
        if(position == getCurrentPosition()){
            System.out.print("\u001B[33mX\u001B[0m");
        }else if(visitedSquares.contains(position)){
            if (waterLocations.contains(position)){
                System.out.print("\u001B[34mO\u001B[0m");
            }else if(campLocations.contains(position)) {
                System.out.print("C");
            }else if(rescueLocation == position){
                System.out.print("\u001B[31mH\u001B[0m");
            }else {
                System.out.print(".");
            }
        }
        else{
            System.out.print("\u001B[32m*\u001B[0m");
        }
    }
    //----------Create random encounters here---------------
    public Set<Integer> createWaterLocations(){
        Set<Integer> water = new HashSet<>();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 25; i++) {
            while(true) {
                int randomizer = random.nextInt(MAX_BOARD_SIZE-1);
                if (!usedLocations.contains(randomizer)) {
                    water.add(randomizer);
                    usedLocations.add(randomizer);
                    break;
                }
            }
        }
        return water;
    }

    public Set<Integer> createCampLocations(){
        Set<Integer> camp = new HashSet<>();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 25; i++) {
            while (true) {
                int randomizer = random.nextInt(MAX_BOARD_SIZE-1);
                if (!usedLocations.contains(randomizer)) {
                    camp.add(randomizer);
                    usedLocations.add(randomizer);
                    break;
                }
            }
        }
        return camp;
    }

    public String eventEvaluator(int position){
        String response = "";
        if(waterLocations.contains(getCurrentPosition())){
            response = TextHandler.encounterWater();
        }else if(campLocations.contains(getCurrentPosition())){
            response = TextHandler.encounterCamp();
        }
        return response;
    }
//----------Check inventory--------------
    public void checkInventory(){
        if (inventory.size() != 0){
            for (InventoryItem item : inventory){
                updateVisitedSquares(item.produceEffect(getCurrentPosition()));
            }
        }
    }

    public void updateInventory(){
        InventoryItem binocular = new Binocular(getCurrentPosition());
        inventory.add(binocular);
    }

    public int randomStart(){
        Random random = new Random();
        int startingPosition = random.nextInt(MAX_BOARD_SIZE-1);
        visitedSquares.add(startingPosition);
        return startingPosition;
    }

    public int createRescueLocation(){
        Random random = new Random();
        int rescuePosition = random.nextInt(MAX_BOARD_SIZE-1);
        usedLocations.add(rescuePosition);
        return rescuePosition;
    }

    //--------G&S------------
    public String[] getActiveBoard() {
        return activeBoard;
    }

    public void setActiveBoard(String[] activeBoard) {
        this.activeBoard = activeBoard;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Set<Integer> getWaterLocations() {
        return waterLocations;
    }

    public void setInventory(Set<InventoryItem> inventory) {
        this.inventory = inventory;
    }

    public Set<Integer> updateVisitedSquares(int[] updateArray){
        for (int i = 0; i < updateArray.length; i++) {
            visitedSquares.add(updateArray[i]);
        }
        return visitedSquares;
    }
}
