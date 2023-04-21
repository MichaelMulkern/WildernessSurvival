package com.techelevator.gamedata;

import java.security.SecureRandom;
import java.util.*;

public class GameBoard {
    private String[] activeBoard;
    private int currentPosition;
    private int rescueLocation;

    //----Encounter variables-------
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

    //-----Inventory related variables------
    private Set<InventoryItem> inventory = new HashSet<>();
    private List<String> inventoryPicker = new ArrayList<>();



    public GameBoard() {
        this.activeBoard = createLayout();

    }

    public String[] createLayout() {
        String[] layout = new String[MAX_BOARD_SIZE];
        for (int i = 0; i < layout.length; i++) {
            layout[i] = "*";
        }
        currentPosition = randomStart();
        rescueLocation = createRescueLocation();
        waterLocations = createWaterLocations();
        campLocations = createCampLocations();
        bearLocations = createBearLocations();
        setInventoryPicker();
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
//TODO impliment water consumption
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
                System.out.print("\u001B[35mC\u001B[0m");
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

    public Set<Integer> createBearLocations(){
        Set<Integer> bear = new HashSet<>();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            while (true) {
                int randomizer = random.nextInt(MAX_BOARD_SIZE-1);
                if (!usedLocations.contains(randomizer)) {
                    bear.add(randomizer);
                    usedLocations.add(randomizer);
                    break;
                }
            }
        }
        return bear;
    }
//TODO impliment mountain views
    public String eventEvaluator(int position){
        String response = "";
        if(waterLocations.contains(getCurrentPosition())){
            response = TextHandler.encounterWater();
        }else if(campLocations.contains(getCurrentPosition())){
            response = TextHandler.encounterCamp() + updateInventory();
            campLocations.remove(position);
        }else if(rescueLocation == position){
            response = TextHandler.victory();
        }else if(bearLocations.contains(position)){
            response = TextHandler.encounterBear();
        }
        return response;
    }
//----------Check inventory--------------
    public void checkInventory(){
        if (inventory.size() != 0){
            for (InventoryItem item : inventory){
                if(item.getName() == "binocular") {
                    updateVisitedSquares(item.produceEffect(getCurrentPosition()));
                }else if(item.getName() == "jug"){

                }
            }
        }
    }

    public String updateInventory(){
        Random random = new Random();
        int roll = random.nextInt(2);
        int winner = 1;
        if (roll == winner && inventoryPicker.size() > 0){
            int newItem = random.nextInt(inventoryPicker.size());
            if (inventoryPicker.get(newItem).equals("binocular")){
                InventoryItem binocular = new Binocular(getCurrentPosition());
                inventory.add(binocular);
                inventoryPicker.remove(newItem);
                return TextHandler.findBinocular();
            }else if (inventoryPicker.get(newItem).equals("whistle")){
                InventoryItem whistle = new Whistle(getCurrentPosition());
                inventory.add(whistle);
                inventoryPicker.remove(newItem);
                bearLocations.clear();
                return TextHandler.findWhistle();
            }else if (inventoryPicker.get(newItem).equals("jug")){
                InventoryItem jug = new Jug(getCurrentPosition());
                inventory.add(jug);
                inventoryPicker.remove(newItem);
                return TextHandler.findJug();
            }
        }
        return TextHandler.findNothing();
    }


    public int randomStart(){
        Random random = new Random();
        int startingPosition = random.nextInt(MAX_BOARD_SIZE-1);
        visitedSquares.add(startingPosition);
        usedLocations.add(startingPosition);
        return startingPosition;
    }

    public int createRescueLocation() {
        Random random = new Random();
        int rescuePosition;
        while (true) {
            int randomizer = random.nextInt(MAX_BOARD_SIZE - 1);
            if (!usedLocations.contains(randomizer)) {
                rescuePosition = randomizer;
                usedLocations.add(randomizer);
                break;
            }

        }
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

    public void setInventoryPicker() {
        inventoryPicker.add("binocular");
        inventoryPicker.add("whistle");
        inventoryPicker.add("jug");
    }

    public void updateVisitedSquares(int[] updateArray){
        for (int i = 0; i < updateArray.length; i++) {
            visitedSquares.add(updateArray[i]);
        }
    }

    public boolean checkVictoryCondition(int position){
        if (position == rescueLocation){
            return true;
        }
        return false;
    }

    public String getInventory(){
        String inventoryStacker = "";
        if(inventory.size() == 0){
            return TextHandler.emptyInventory();
        }
        for (InventoryItem item : inventory){
            inventoryStacker = inventoryStacker + (item.getName() + " ");
        }
        return TextHandler.showInventory() + inventoryStacker;
    }

}
