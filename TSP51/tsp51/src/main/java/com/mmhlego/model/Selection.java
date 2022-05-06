package com.mmhlego.model;

import java.util.ArrayList;
import java.util.Random;

public class Selection {
	public static Society proportionalSelection(Society society) {
		Society newSociety = new Society();
		Random random = new Random();

		society.sortMembers();

		ArrayList<Chromosome> leftoverParents = new ArrayList<>();
		for (int i = 0; i < Constants.SOCIETY_SIZE / 2; i++) {
			leftoverParents.add(society.members[i].clone());
			newSociety.members[i] = society.members[i].clone();
		}

		leftoverParents = Chromosome.shuffleChromosomes(leftoverParents);

		for (int i = Constants.SOCIETY_SIZE / 2; i < Constants.SOCIETY_SIZE; i += 2) {
			if (leftoverParents.size() == 1) {
				newSociety.members[i] = leftoverParents.get(0).clone();
				break;
			}

			Chromosome parent1 = leftoverParents.remove(0);
			Chromosome parent2 = leftoverParents.remove(0);

			Chromosome[] offsprings = (Constants.USE_PMX) ? Chromosome.PMX(parent1, parent2)
					: Chromosome.crossover(parent1, parent2);

			newSociety.members[i] = offsprings[0].clone();
			newSociety.members[i + 1] = offsprings[1].clone();
		}

		newSociety.members[Constants.SOCIETY_SIZE - 1] = society.bestChromosome().clone();

		int mutatePercent = Constants.MUTATE_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < mutatePercent; i++) {
			int index = random.nextInt(Constants.SOCIETY_SIZE - 1);

			newSociety.members[index].mutate();
		}

		int mirrorPercent = Constants.INVERSION_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < mirrorPercent; i++) {
			int index = random.nextInt(Constants.SOCIETY_SIZE - 1);

			newSociety.members[index].mirrorSection();
		}

		return newSociety;
	}

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
			if (Constants.LOG_CROSSOVER)
				System.out.println("Crossover #" + (i / 2 + 1));

			Chromosome[] crossovers = (Constants.USE_PMX)
					? Chromosome.PMX(newSociety.members[i], newSociety.members[i + 1])
					: Chromosome.crossover(newSociety.members[i], newSociety.members[i + 1]);

			newSociety.members[i] = crossovers[0].clone();
			newSociety.members[i + 1] = crossovers[1].clone();
		}

		int mutatePercent = Constants.MUTATE_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < mutatePercent; i++) {
			int index = random.nextInt(Constants.SOCIETY_SIZE - 1);

			newSociety.members[index].mutate();
		}

		int mirrorPercent = Constants.INVERSION_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < mirrorPercent; i++) {
			int index = random.nextInt(Constants.SOCIETY_SIZE - 1);

			newSociety.members[index].mirrorSection();
		}

		return newSociety;
	}
}
