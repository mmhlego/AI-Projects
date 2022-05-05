package com.mmhlego.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Gene {
	public int Number;
	public int x, y;

	public Gene(int number, int x, int y) {
		this.Number = number;
		this.x = x;
		this.y = y;
	}

	public static int dist(Gene n1, Gene n2) {
		return (int) Math.sqrt(Math.pow((n1.x - n2.x), 2.0) + Math.pow((n1.y - n2.y), 2.0));
	}

	// static 

	public static Gene[] allNodes = new Gene[Constants.NODES_SIZE];

	public static void loadGenes() {
		File file = new File("TSP51.txt");

		try {
			Scanner reader = new Scanner(file);

			for (int i = 0; i < Constants.NODES_SIZE; i++) {
				int num = reader.nextInt();
				int x = reader.nextInt();
				int y = reader.nextInt();
				allNodes[i] = new Gene(num, x, y);
				// allNodes[i].printData();
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void printData() {
		System.out.printf("Gene %d at [%d %d]\n", Number, x, y);
	}
}
