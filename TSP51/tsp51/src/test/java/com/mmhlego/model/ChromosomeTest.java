package com.mmhlego.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ChromosomeTest {
	@Before
	public void Before() {
		Gene.loadGenes();
	}

	@Test
	public void testPMX() {
		Chromosome c1 = Chromosome.randomChromosome();
		Chromosome c2 = Chromosome.randomChromosome();

		Chromosome offsprings[] = Chromosome.PMX(c1, c2);

		for (int i = 0; i < 2; i++) {
			assertEquals(Constants.NODES_SIZE, offsprings[i].genes.length);

			boolean repeated[] = new boolean[Constants.NODES_SIZE];

			for (Gene g : offsprings[i].genes) {
				assertFalse(repeated[g.Number - 1]);
				repeated[g.Number - 1] = true;
			}
		}
	}

	@Test
	public void testClone() {
		Chromosome c1 = Chromosome.randomChromosome();
		Chromosome c2 = c1.clone();
		assertEquals(c1.toString(), c2.toString());
	}

	@Test
	public void testCrossover() {
		Chromosome c1 = Chromosome.randomChromosome();
		Chromosome c2 = Chromosome.randomChromosome();

		Chromosome offsprings[] = Chromosome.crossover(c1, c2);

		for (int i = 0; i < 2; i++) {
			assertEquals(Constants.NODES_SIZE, offsprings[i].genes.length);

			boolean repeated[] = new boolean[Constants.NODES_SIZE];

			for (Gene g : offsprings[i].genes) {
				assertFalse(repeated[g.Number - 1]);
				repeated[g.Number - 1] = true;
			}
		}
	}

	@Test
	public void testFitness() {
		Chromosome c1 = Chromosome.randomChromosome();
		Chromosome c2 = Chromosome.randomChromosome();

		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			c2.genes[i] = c1.genes[Constants.NODES_SIZE - 1 - i];
		}

		assertEquals(c1.fitness(), c2.fitness(), 0.1);
	}

	@Test
	public void testMirrorSection() {
		Chromosome c1 = Chromosome.randomChromosome();
		Chromosome c2 = c1.clone();
		c2.mirrorSection();

		int start = 0, end = 0;
		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			if (c1.genes[i].Number != c2.genes[i].Number) {
				start = i;
				break;
			}
		}

		for (int i = Constants.NODES_SIZE - 1; i >= 0; i--) {
			if (c1.genes[i].Number != c2.genes[i].Number) {
				end = i;
				break;
			}
		}

		int len = end - start;
		for (int i = 0; i < len; i++) {
			assertEquals(c1.genes[start + i].Number, c2.genes[end - i].Number);
		}
	}

	@Test
	public void testMutate() {
		Chromosome c1 = Chromosome.randomChromosome();
		Chromosome c2 = c1.clone();
		c2.mutate();

		int diff = 0;
		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			if (c1.genes[i].Number != c2.genes[i].Number) {
				diff++;
			}
		}

		assertTrue(0 <= diff && diff <= 4);
	}

	@Test
	public void testToString() {
		Chromosome c1 = Chromosome.randomChromosome();

		String ans = "";
		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			ans = ans.concat(Integer.toString(c1.genes[i].Number));
			if (i != Constants.NODES_SIZE - 1)
				ans = ans.concat(",");
		}

		assertEquals(ans, c1.toString());
	}
}
