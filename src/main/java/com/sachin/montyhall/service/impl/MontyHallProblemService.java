package com.sachin.montyhall.service.impl;

import java.util.Collections;
import java.util.Random;

import com.sachin.montyhall.domain.interfaces.IMontyHallDoorsDomain;
import com.sachin.montyhall.service.interfaces.IMontyHallProblemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MontyHallProblemService implements IMontyHallProblemService {

	private static final Logger logger = LoggerFactory.getLogger(MontyHallProblemService.class);
	
	@Autowired
	IMontyHallDoorsDomain montyHallDomain;
	
	@Override
	public double getWinPercentage(Integer iterations, boolean switched) {
		double countOfWins = 0;
		boolean win;
		int chosenDoor;
		int openDoor;
		logger.info("Iteration Door 1 Door 2 Door 3 Selected Switched Win");
		logger.info("--------------------------------------------------------");
		for (int i = 0; i < iterations; i++) {
			Collections.shuffle(montyHallDomain.getRandomlyAssignedDoorList(), new Random());
			chosenDoor = montyHallDomain.chooseDoor();
			openDoor = montyHallDomain.openDoorWithGoat(chosenDoor);
			chosenDoor = switched ? montyHallDomain.chooseAnotherDoor(chosenDoor, openDoor) : chosenDoor;
			win = montyHallDomain.isThereCARBehindTheDoor(chosenDoor);
			String formattedString = String.format("%9s %6s %6s %6s %8s %8s %4s", i,
					montyHallDomain.getRandomlyAssignedDoorList().get(0),
					montyHallDomain.getRandomlyAssignedDoorList().get(1),
					montyHallDomain.getRandomlyAssignedDoorList().get(2), chosenDoor + 1, switched, win);
			logger.info(formattedString);
			countOfWins = countOfWins + (win ? 1 : 0);
		}
		double winPercentage = (countOfWins / iterations) * 100;
		logger.info("Win Percentage: {}%", winPercentage);
		return winPercentage;
	}
}
