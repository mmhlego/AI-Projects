class node {
	label = "";
	pos = { x: undefined, y: undefined };
	heuristic = 0;
	reachCost = Infinity;
	neighbors = [];
	parent = undefined;
	isTarget = false;

	constructor(label, x, y, h) {
		this.label = label;
		this.pos.x = x;
		this.pos.y = y;
		this.heuristic = h;
		this.reachCost = 0;
	}

	drawNode(isSelected = false, noPath = false) {
		fill(isSelected ? selectedColor : noPath ? noPathColor : 255);
		stroke(isSelected ? selectedColor : noPath ? noPathColor : 255);
		strokeWeight(0);
		ellipse(this.pos.x * 1.2, this.pos.y * 1.2, 15);

		var btn = createButton("");
		btn.position(this.pos.x * 1.2 - 7.5, this.pos.y * 1.2 - 7.5);
		btn.mousePressed((e) => startSearch(this));

		strokeWeight(1);
		stroke(51);
		textAlign(CENTER);
		text(this.label, this.pos.x * 1.2, this.pos.y * 1.2 + 20);

		if (isSelected) {
			stroke(51);
			fill(selectedColor);
			text(
				`${this.reachCost} + ${this.heuristic} = ${
					this.reachCost + this.heuristic
				}`,
				this.pos.x * 1.2,
				this.pos.y * 1.2 + 35
			);
		} else if (noPath) {
			stroke(51);
			fill(noPathColor);
			text(`No path`, this.pos.x * 1.2, this.pos.y * 1.2 + 35);
		}
	}

	getCost() {
		return this.reachCost + this.heuristic;
	}

	drawNeighbors() {
		this.neighbors.forEach((edge) => {
			drawEdge(this, edge[0], edge[1]);
		});
	}

	resetNode() {
		this.reachCost = Infinity;
		this.parent = undefined;
	}
}

function drawEdge(n, m, weight, color = 255) {
	stroke(color);
	line(m.pos.x * 1.2, m.pos.y * 1.2, n.pos.x * 1.2, n.pos.y * 1.2);

	stroke(51);
	fill(color);
	textAlign(CENTER);
	text(
		weight,
		(m.pos.x * 1.2 + n.pos.x * 1.2) / 2,
		(m.pos.y * 1.2 + n.pos.y * 1.2 + 25) / 2
	);
}
