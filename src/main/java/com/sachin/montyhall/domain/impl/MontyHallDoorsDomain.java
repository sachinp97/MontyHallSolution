package com.sachin.montyhall.domain.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sachin.montyhall.domain.interfaces.IMontyHallDoorsDomain;

import org.springframework.stereotype.Component;

@Component
public class MontyHallDoorsDomain implements IMontyHallDoorsDomain {
    private static final int NUMBER_OF_DOORS = 3;
    private static final String GOAT = "Goat";
    private static final String CAR = "Car";
    private List<String> randomlyAssignedDoorList;
    private Random doorNumberGenerator;

    public MontyHallDoorsDomain() {
        try {
            this.doorNumberGenerator = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        this.randomlyAssignedDoorList = new ArrayList<>();
        randomlyAssignedDoorList.add(CAR);
        for (int i = 1; i < NUMBER_OF_DOORS; i++) {
            randomlyAssignedDoorList.add(GOAT);
        }
    }

    public int openDoorWithGoat(int chosenDoor) {
        int door = 0;
        while (CAR.equals(getRandomlyAssignedDoorList().get(door)) || door == chosenDoor) {
            door++;
        }
        return door;
    }

    public int chooseAnotherDoor(int chosenDoor, int openDoor) {
        int door = 0;
        while (door == openDoor || door == chosenDoor) {
            door++;
        }
        return door;
    }

    public List<String> getRandomlyAssignedDoorList() {
        return randomlyAssignedDoorList;
    }

    public void setRandomlyAssignedDoorList(List<String> randomlyAssignedDoorList) {
        this.randomlyAssignedDoorList = randomlyAssignedDoorList;
    }

    public boolean isThereCARBehindTheDoor(int chosenDoor) {
        return CAR.equals(getRandomlyAssignedDoorList().get(chosenDoor));
    }

    public int chooseDoor() {
        return doorNumberGenerator.nextInt(2);
    }
}
