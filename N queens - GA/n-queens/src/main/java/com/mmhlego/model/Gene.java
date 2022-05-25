package com.mmhlego.model;

public class Gene {
	public int Number;

	public Gene(int number) {
		this.Number = number;
	}

	public static Gene[] allNodes = new Gene[Constants.NODES_SIZE];

	public static void loadGenes() {
		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			allNodes[i] = new Gene(i + 1);
		}
	}

	@Override
	public String toString() {
		return String.valueOf(Number);
	}
}
