package com.techelevator.gamedata;

public class WaterManagement {
    private String[] waterMeter;
    private final int MAX_WATER = 10;

    public WaterManagement() {
        this.waterMeter = new String[MAX_WATER];
    }

    public void refillWater(){
        String[] refiller = new String[MAX_WATER];
        for (int i = 0; i < MAX_WATER; i++) {
            refiller[i] = "\u001B[34m=\u001B[0m";
        }
        setWaterMeter(refiller);
    }

    public void useWater(){
        String[] water = getWaterMeter();
        for (int i = 0; i < MAX_WATER; i++) {
            if (water[i].equals("-") && i > 0){
                water[i-1] = "-";
                setWaterMeter(water);
                break;
            }else if (i == 9){
                water[i] = "-";
                setWaterMeter(water);
                break;
            }
        }
    }

    public String[] getWaterMeter() {
        return waterMeter;
    }

    public void setWaterMeter(String[] waterMeter) {
        this.waterMeter = waterMeter;
    }

    public String displayWater(){
        String[] water = getWaterMeter();
        String formattedDisplay = "Water remaining: [";
        for(String x : water){
            formattedDisplay += x;
        }
        formattedDisplay += "]";

        return formattedDisplay;
    }
}
