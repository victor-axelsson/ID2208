package se.kth.factories;

import java.util.Random;

/**
 * Created by victoraxelsson on 2017-01-26.
 */
public abstract class Factory {

    protected Random rand;
    public Factory(){
        rand = new Random();
    }

    protected int getRandomInt(int min, int max){
        return rand.nextInt(max) + min;
    }

    protected String getRandom(String[] input){
        return input[getRandomInt(0, input.length)];
    }
}
