function startSearch(n) {
	console.clear();

	var options = [];
	var weights = [];
	var visited = [];
	var last_node;

	resetMap();

	weights.push(0 + n.heuristic);
	options[weights[0]] = n;
	n.reachCost = 0;

	while (weights.length > 0) {
		var min_weight = min(weights);
		var min_node = options[min_weight];

		if (PROGRAM_MODE.length >= 10) {
			// Is optimized mode
			weights.splice(weights.indexOf(min_weight), 1);
		} else {
			// Is regular mode
			weights = [];
		}

		if (min_node.isTarget) {
			last_node = min_node;
			break;
		} else if (visited.includes(min_node.label)) {
			continue;
		}
		visited.push(min_node.label);

		min_node.neighbors.forEach((neighbor) => {
			var calculated_weight;
			switch (programModes.indexOf(PROGRAM_MODE)) {
				case 0: // Is A*
				case 1:
					calculated_weight =
						min_node.reachCost +
						neighbor[1] +
						neighbor[0].heuristic;
					break;
				case 2: // Is GBFS
				case 3:
					calculated_weight = neighbor[0].heuristic;
					break;
			}
			(PROGRAM_MODE == "A*" ? min_node.reachCost + neighbor[1] : 0) +
				neighbor[0].heuristic;

			if (!visited.includes(neighbor[0].label)) {
				if (
					neighbor[0].reachCost &&
					neighbor[0].reachCost > min_node.reachCost + neighbor[1]
				) {
					neighbor[0].reachCost = min_node.reachCost + neighbor[1];
					neighbor[0].parent = [min_node, neighbor[1]];
				}
				options[calculated_weight] = neighbor[0];
				weights.push(calculated_weight);
			}
		});
	}

	n.parent = undefined;

	if (!last_node) {
		console.log("No path");
		n.drawNode(false, true);
	}

	console.log(`Path cost: (starting from ${n.label})`);
	while (last_node) {
		console.log(last_node.label, "weight: " + last_node.reachCost);

		last_node.drawNode(true);
		if (last_node.parent) {
			drawEdge(
				last_node,
				last_node.parent[0],
				last_node.parent[1],
				selectedColor
			);
		} else {
			break;
		}

		last_node = last_node.parent[0];
	}
}
