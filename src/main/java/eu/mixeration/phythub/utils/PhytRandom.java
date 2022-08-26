package eu.mixeration.phythub.utils;

import java.util.Random;

public class PhytRandom {

    public int randomInt(int a, int b) {
        Random random = new Random();
        return random.nextInt(a, b);
    }

    public int randomInt(int a) {
        Random random = new Random();
        return random.nextInt(a);
    }

}
