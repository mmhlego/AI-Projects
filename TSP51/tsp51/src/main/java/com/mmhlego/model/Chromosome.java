package com.mmhlego.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Chromosome {
	public Gene[] genes = new Gene[Constants.NODES_SIZE];

	public int fitness() { // the less the better
		int cost = 0;

		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			cost += Gene.dist(genes[i], genes[(i + 1) % Constants.NODES_SIZE]);
		}

		return cost;
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < Constants.NODES_SIZE; i++)
			str += (genes[i].Number + ((i == Constants.NODES_SIZE - 1) ? "" : ","));

		return str;
	}

	public static Chromosome randomChromosome() {
		ArrayList<Integer> nums = new ArrayList<>();
		Chromosome ans = new Chromosome();

		for (int i = 0; i < Constants.NODES_SIZE; i++)
			nums.add(i);

		Random r = new Random();
		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			ans.genes[i] = Gene.allNodes[nums.remove(r.nextInt(nums.size()))];
		}

		return ans;
	}

	public void printData() {
		System.out.printf("%s\t%d\n", this.toString(), fitness());
	}

	public void mutate() {
		Random random = new Random();

		for (int i = 0; i < 2; i++) {
			int index1 = random.nextInt(Constants.NODES_SIZE);
			int index2 = random.nextInt(Constants.NODES_SIZE);
			Gene g = genes[index1];
			genes[index1] = genes[index2];
			genes[index2] = g;
		}
	}

	public static Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
		Chromosome c1 = new Chromosome();
		Chromosome c2 = new Chromosome();

		int crossoverPoint = new Random().nextInt(Constants.NODES_SIZE - 1) + 1;
		Set<Integer> s1 = new HashSet<>();
		Set<Integer> s2 = new HashSet<>();

		for (int i = 0; i < crossoverPoint; i++) {
			c1.genes[i] = parent1.genes[i];
			c2.genes[i] = parent2.genes[i];

			s1.add(parent1.genes[i].Number);
			s2.add(parent2.genes[i].Number);
		}

		int index = crossoverPoint;
		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			if (!s1.contains(parent2.genes[i].Number)) {
				c1.genes[index++] = parent2.genes[i];
			}
		}

		index = crossoverPoint;
		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			if (!s2.contains(parent1.genes[i].Number)) {
				c2.genes[index++] = parent1.genes[i];
			}
		}

		if (Constants.LOG_CROSSOVER) {
			System.out.println("Crossover point: " + crossoverPoint);
			parent1.printData();
			parent2.printData();
			c1.printData();
			c2.printData();
		}

		Chromosome[] ans = new Chromosome[2];
		ans[0] = c1;
		ans[1] = c2;
		return ans;
	}

	public static Chromosome[] PMX(Chromosome parent1, Chromosome parent2) {
		Chromosome c1 = new Chromosome();
		Chromosome c2 = new Chromosome();

		Random random = new Random();
		int first_point = random.nextInt(Constants.NODES_SIZE); // 0 - 50
		int second_point = random.nextInt(Constants.NODES_SIZE - first_point) + first_point; // first - 50

		Set<Integer> s1 = new HashSet<>();
		Set<Integer> s2 = new HashSet<>();

		for (int i = first_point; i <= second_point; i++) {
			c1.genes[i] = parent1.genes[i];
			c2.genes[i] = parent2.genes[i];

			s1.add(parent1.genes[i].Number);
			s2.add(parent2.genes[i].Number);
		}

		int index1 = 0;
		int index2 = 0;
		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			if (index1 == first_point)
				index1 = second_point + 1;
			if (index2 == first_point)
				index2 = second_point + 1;

			if (!s1.contains(parent2.genes[i].Number))
				c1.genes[index1++] = parent2.genes[i];
			if (!s2.contains(parent1.genes[i].Number))
				c2.genes[index2++] = parent1.genes[i];
		}

		if (Constants.LOG_CROSSOVER) {
			System.out.println("PMX points: [" + first_point + ", " + second_point + "]");
			parent1.printData();
			parent2.printData();
			c1.printData();
			c2.printData();
		}

		Chromosome[] ans = new Chromosome[2];
		ans[0] = c1;
		ans[1] = c2;
		return ans;
	}

	public Chromosome clone() {
		Chromosome ans = new Chromosome();

		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			ans.genes[i] = this.genes[i];
		}

		return ans;
	}

	public HBox getView() {
		return getView("");
	}

	public HBox getView(String prefix) {
		HBox view = new HBox();
		view.setAlignment(Pos.CENTER_LEFT);
		view.setPrefHeight(25);
		view.setMinWidth(245);
		String text = prefix + "  ";

		for (Gene gene : this.genes) {
			text += gene.Number + ", ";
		}
		text += "   =>   " + fitness() + "  ";

		view.getChildren().add(new Label(text));

		return view;
	}

	public void mirrorSection() {
		Random random = new Random();
		int index1 = random.nextInt(Constants.NODES_SIZE);
		int index2 = random.nextInt(Constants.NODES_SIZE - index1) + index1;
		int diff = index2 - index1;

		Chromosome backup = this.clone();

		for (int i = 0; i <= diff; i++) {
			this.genes[index1 + i] = backup.genes[index2 - i];
		}
	}

	public static ArrayList<Chromosome> shuffleChromosomes(ArrayList<Chromosome> chromosomes) {
		Random random = new Random();

		ArrayList<Chromosome> ans = new ArrayList<>();

		while (chromosomes.size() > 0) {
			ans.add(chromosomes.remove(random.nextInt(chromosomes.size())).clone());
		}

		return ans;
	}
}
