package com.mmhlego;

import com.mmhlego.model.Chromosome;
import com.mmhlego.model.Gene;

public class Tester {
	public static void main(String[] args) {
		Gene.loadGenes();

		Chromosome c1 = Chromosome.randomChromosome();
		Chromosome c2 = Chromosome.randomChromosome();

		Chromosome.PMX(c1, c2);
	}
}
