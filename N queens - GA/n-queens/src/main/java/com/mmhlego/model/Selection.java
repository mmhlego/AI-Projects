package com.mmhlego.model;

import java.util.ArrayList;
import java.util.Random;

public class Selection {
	public static Population proportionalSelection(Population population) {
		Population newPopulation = new Population();
		Random random = new Random();

		population.sortMembers();

		ArrayList<Chromosome> leftoverParents = new ArrayList<>();
		for (int i = 0; i < Constants.SOCIETY_SIZE / 2; i++) {
			leftoverParents.add(population.members[i].clone());
			newPopulation.members[i] = population.members[i].clone();
		}

		leftoverParents = Chromosome.shuffleChromosomes(leftoverParents);

		for (int i = Constants.SOCIETY_SIZE / 2; i < Constants.SOCIETY_SIZE; i += 2) {
			if (leftoverParents.size() == 1) {
				newPopulation.members[i] = leftoverParents.get(0).clone();
				break;
			}

			Chromosome parent1 = leftoverParents.remove(0);
			Chromosome parent2 = leftoverParents.remove(0);

			Chromosome[] offsprings = (Constants.USE_PMX) ? Chromosome.PMX(parent1, parent2)
					: Chromosome.crossover(parent1, parent2);

			newPopulation.members[i] = offsprings[0].clone();
			newPopulation.members[i + 1] = offsprings[1].clone();
		}

		newPopulation.members[Constants.SOCIETY_SIZE - 1] = population.bestChromosome().clone();

		int mutatePercent = Constants.MUTATE_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < mutatePercent; i++) {
			int index = random.nextInt(Constants.SOCIETY_SIZE - 1);

			newPopulation.members[index].mutate();
		}

		int mirrorPercent = Constants.INVERSION_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < mirrorPercent; i++) {
			int index = random.nextInt(Constants.SOCIETY_SIZE - 1);

			newPopulation.members[index].mirrorSection();
		}

		return newPopulation;
	}

	public static Population tournamentSelection(Population population) {
		Population newPopulation = new Population();

		Random random = new Random();
		for (int i = 0; i < Constants.SOCIETY_SIZE - 1; i++) {
			Chromosome best = population.members[random.nextInt(Constants.SOCIETY_SIZE)].clone();
			for (int j = 1; j < Constants.TOURNAMENT_SIZE; j++) {
				int index = random.nextInt(Constants.SOCIETY_SIZE);

				if (population.members[index].fitness() < best.fitness()) {
					best = population.members[index].clone();
				}
			}

			newPopulation.members[i] = best.clone();
		}
		newPopulation.members[Constants.SOCIETY_SIZE - 1] = population.bestChromosome().clone(); // elitism

		int crossoverCount = Constants.CROSSOVER_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < crossoverCount; i += 2) {
			if (Constants.LOG_CROSSOVER)
				System.out.println("Crossover #" + (i / 2 + 1));

			Chromosome[] crossovers = (Constants.USE_PMX)
					? Chromosome.PMX(newPopulation.members[i], newPopulation.members[i + 1])
					: Chromosome.crossover(newPopulation.members[i], newPopulation.members[i + 1]);

			newPopulation.members[i] = crossovers[0].clone();
			newPopulation.members[i + 1] = crossovers[1].clone();
		}

		int mutatePercent = Constants.MUTATE_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < mutatePercent; i++) {
			int index = random.nextInt(Constants.SOCIETY_SIZE - 1);

			newPopulation.members[index].mutate();
		}

		int mirrorPercent = Constants.INVERSION_PERCENT * Constants.SOCIETY_SIZE / 100;
		for (int i = 0; i < mirrorPercent; i++) {
			int index = random.nextInt(Constants.SOCIETY_SIZE - 1);

			newPopulation.members[index].mirrorSection();
		}

		return newPopulation;
	}
}
