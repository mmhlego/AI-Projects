package com.mmhlego.model;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Society {
	public Chromosome[] members = new Chromosome[Constants.SOCIETY_SIZE];

	public Society() {}

	public static Society randomSociety() {
		Society s = new Society();
		for (int i = 0; i < Constants.SOCIETY_SIZE; i++) {
			s.members[i] = Chromosome.randomChromosome();
		}
		return s;
	}

	public void printData() {
		printData(1);
	}

	public void printData(int iteration) {
		System.out.printf(
				"=============================== Generation %d ===============================\n",
				iteration);
		// System.out.println("chromosome\tfitness");
		// int sum = 0;
		// for (Chromosome c : members)
		// 	sum += c.fitness();
		// int average = sum / Constants.SOCIETY_SIZE;

		double sum = 0;
		for (Chromosome c : members) {
			sum += c.fitness();

			if (Constants.LOG_CHROMOSOMES) {
				if (c.fitness() == bestChromosome().fitness())
					System.out.print("* ");
				c.printData();
			}
		}

		double average = sum / (double) Constants.SOCIETY_SIZE;
		System.out.println("Average fitness: " + average);
		System.out.println("Best fitness: " + bestChromosome().fitness());

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("values.txt", true));
			bw.write(average + " " + bestChromosome().fitness());
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Chromosome bestChromosome() {
		int best = Integer.MAX_VALUE;
		Chromosome ans = new Chromosome();
		for (Chromosome c : members) {
			if (best > c.fitness()) {
				ans = c;
				best = c.fitness();
			}
		}
		return ans;
	}
}
