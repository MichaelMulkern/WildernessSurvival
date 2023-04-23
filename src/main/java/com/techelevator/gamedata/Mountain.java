package com.techelevator.gamedata;

public class Mountain {

    int[] viewDistance;
    int currentPosition;

    public Mountain(int currentPosition){
        this.currentPosition = currentPosition;
    }

    public int[] produceEffect() {
        viewDistance = new int[] {currentPosition-31,currentPosition+31,currentPosition+1,currentPosition-1};
        if(viewDistance[0]<0){
            viewDistance[0] = currentPosition;
        }
        if(viewDistance[1]>309){
            viewDistance[1] = currentPosition;
        }
        if(viewDistance[2]<0){
            viewDistance[2] = currentPosition;
        }
        if(viewDistance[3]>309){
            viewDistance[3] = currentPosition;
        }
        return viewDistance;
    }
}
