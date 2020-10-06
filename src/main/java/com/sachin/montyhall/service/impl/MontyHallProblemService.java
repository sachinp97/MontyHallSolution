package com.sachin.montyhall.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.sachin.montyhall.service.interfaces.IMontyHallProblemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MontyHallProblemService implements IMontyHallProblemService {

	private static final int NUMBER_OF_DOORS = 3;
	private static final String GOAT = "Goat";
	private static final String CAR = "Car";
	private static final Logger logger = LoggerFactory.getLogger(MontyHallProblemService.class);

	private List<String> randomlyAssignedDoorList;
	
	@Override
	public double getWinPercentage(Integer iterations, boolean switched) {
		double countOfWins = 0;
		try {
			boolean win;
			Integer chosenDoor, openDoor;
			Random doorNumberGenerator = SecureRandom.getInstanceStrong();
			initAssignedDoorList();
			logger.info("Iteration Door 1 Door 2 Door 3 Selected Switched Win");
			logger.info("--------------------------------------------------------");
			for (int i = 0; i < iterations; i++) {
				Collections.shuffle(getRandomlyAssignedDoorList(), new Random());
				chosenDoor = doorNumberGenerator.nextInt(2) ;
				openDoor = openDoorWithGoat(chosenDoor);
				chosenDoor = switched ? getOtherDoor(chosenDoor, openDoor, doorNumberGenerator) : chosenDoor;
				win = CAR.equals(randomlyAssignedDoorList.get(chosenDoor));
				String formattedString = String.format("%9s %6s %6s %6s %8s %8s %4s", i,
						randomlyAssignedDoorList.get(0), randomlyAssignedDoorList.get(1),
						randomlyAssignedDoorList.get(2), chosenDoor + 1, switched, win);
				logger.info(formattedString);
				countOfWins = countOfWins + (win ? 1 : 0);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		double winPercentage = (countOfWins / iterations) * 100;
		logger.info("Win Percentage: {}%", winPercentage);
		return winPercentage;
	}

	private int openDoorWithGoat(int chosenDoor) {
		int door = 0;
		while (GOAT.equals(getRandomlyAssignedDoorList().get(door)) && door != chosenDoor - 1) {
			door++;
		}
		return door;
	}

	private Integer getOtherDoor(int chosenDoor, int openDoor, Random doorNumberGenerator) {
		return doorNumberGenerator.ints(0, NUMBER_OF_DOORS).filter(nextDoor -> nextDoor != chosenDoor
				&& nextDoor != openDoor && GOAT.equals(getRandomlyAssignedDoorList().get(nextDoor))).findFirst()
				.getAsInt();
	}

	public void initAssignedDoorList(){
		randomlyAssignedDoorList = new ArrayList<>();
		randomlyAssignedDoorList.add(CAR);
		for (int i = 1; i < NUMBER_OF_DOORS; i++) {
			randomlyAssignedDoorList.add(GOAT);
		}
	}
	public List<String> getRandomlyAssignedDoorList() {
		return randomlyAssignedDoorList;
	}

	public void setRandomlyAssignedDoorList(List<String> randomlyAssignedDoorList) {
		this.randomlyAssignedDoorList = randomlyAssignedDoorList;
	}
}
