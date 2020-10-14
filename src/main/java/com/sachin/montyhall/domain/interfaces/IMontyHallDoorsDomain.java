package com.sachin.montyhall.domain.interfaces;

import java.util.List;

public interface IMontyHallDoorsDomain {
    public int openDoorWithGoat(int chosenDoor) ;
    public int chooseAnotherDoor(int chosenDoor, int openDoor);
    public List<String> getRandomlyAssignedDoorList();
    public boolean isThereCARBehindTheDoor(int chosenDoor);
    public int chooseDoor();
    public double getWinCount();
    public void incrementWinCount();
}
