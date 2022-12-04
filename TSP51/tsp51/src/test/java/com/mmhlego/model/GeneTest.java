package com.mmhlego.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class GeneTest {
	@Test
	public void testLoadGenes() {
		Gene.allNodes = new Gene[Constants.NODES_SIZE];

		for (int i = 0; i < Constants.NODES_SIZE; i++)
			assertEquals(Gene.allNodes[i], null);

		Gene.loadGenes();

		for (int i = 0; i < Constants.NODES_SIZE; i++)
			assertEquals(Gene.allNodes[i].Number, i + 1);
	}

	@Test
	public void testDist() {
		Gene g1 = new Gene(1, 0, 3);
		Gene g2 = new Gene(2, 4, 0);

		assertEquals(Gene.dist(g1, g2), 5, 0.001);
	}
}
