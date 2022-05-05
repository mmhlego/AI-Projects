package com.mmhlego.model;

import java.util.Random;

public class Selection {
	// public static Society rouletteWheelSelection(Society society) {
	// 	Society newSociety = new Society();
	// 	return newSociety;
	// }

	public static Society tournamentSelection(Society society) {
		Society newSociety = new Society();

		Random random = new Random();
		for (int i = 0; i < Constants.SOCIETY_SIZE - 1; i++) {
			Chromosome best = new Chromosome();
			for (int j = 0; j < Constants.TOURNAMENT_SIZE; j++) {
				int index = random.nextInt(Constants.SOCIETY_SIZE);

				if (j == 0) {
					best = society.members[i].clone();
				} else if (society.members[index].fitness() < best.fitness()) {
					best = society.members[index].clone();
				}
			}

			newSociety.members[i] = best.clone();
		}
		newSociety.members[Constants.SOCIETY_SIZE - 1] = society.bestChromosome().clone();

		int crossoverCount = Constants.CROSSOVER_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < crossoverCount; i += 2) {
			Chromosome[] crossovers = Chromosome.crossover(newSociety.members[i], newSociety.members[i + 1]);
			newSociety.members[i] = crossovers[0];
			newSociety.members[i + 1] = crossovers[1];
		}

		int mutatePercent = Constants.MUTATE_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < mutatePercent; i++) {
			int index = random.nextInt(Constants.SOCIETY_SIZE - 1);

			newSociety.members[index].mutate();
		}

		int mirrorPercent = Constants.MIRROR_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < mirrorPercent; i++) {
			int index = random.nextInt(Constants.SOCIETY_SIZE - 1);

			newSociety.members[index].mirrorSection();
		}

		return newSociety;
	}
}
