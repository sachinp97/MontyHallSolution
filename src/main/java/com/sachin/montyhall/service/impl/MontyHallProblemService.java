package com.sachin.montyhall.service.impl;

import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

import com.sachin.montyhall.domain.interfaces.IMontyHallDoorsDomain;
import com.sachin.montyhall.service.interfaces.IMontyHallProblemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MontyHallProblemService implements IMontyHallProblemService {

	@Autowired
	IMontyHallDoorsDomain montyHallDomain;

	@Value("${montyhall.maxnumberofiterations.forcompleteresult}")
	private int maxNumberOfIterationsForCompleteResult;
	
	@Override
	public String getSolutionResults(Integer iterations, boolean switched) {
		StringBuilder responseStringBuilder = new StringBuilder();
		responseStringBuilder.append("Iteration Door 1 Door 2 Door 3 Selected Switched Win");
		responseStringBuilder.append("\n");
		responseStringBuilder.append("--------------------------------------------------------");
		responseStringBuilder.append("\n");
		IntStream.range(1, iterations + 1).forEach(index -> runMontyHallSolution(index, switched, responseStringBuilder));
		double winPercentage = (montyHallDomain.getWinCount() / iterations) * 100;
		String winPercentageString = String.format("Win Percentage: %s%%", winPercentage);
		responseStringBuilder.append(winPercentage);
		return iterations > 10000 ? winPercentageString : responseStringBuilder.toString();
	}

	private void runMontyHallSolution(int index, boolean switched, StringBuilder responseStringBuilder) {
		Collections.shuffle(montyHallDomain.getRandomlyAssignedDoorList(), new Random());
		int chosenDoor = montyHallDomain.chooseDoor();
		int openDoor = montyHallDomain.openDoorWithGoat(chosenDoor);
		chosenDoor = switched ? montyHallDomain.chooseAnotherDoor(chosenDoor, openDoor) : chosenDoor;
		boolean win = montyHallDomain.isThereCARBehindTheDoor(chosenDoor);
		responseStringBuilder.append(String.format("%9s %6s %6s %6s %8s %8s %4s %n", index,
				montyHallDomain.getRandomlyAssignedDoorList().get(0),
				montyHallDomain.getRandomlyAssignedDoorList().get(1),
				montyHallDomain.getRandomlyAssignedDoorList().get(2), chosenDoor + 1, switched, win));
		if (win) montyHallDomain.incrementWinCount();
	}
}
