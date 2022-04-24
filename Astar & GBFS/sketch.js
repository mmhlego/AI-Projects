var cities = [];
var edgeList = [];
var selectedColor;
var noPathColor;

const programModes = ["A*", "Optimized A*", "GBFS", "Optimized GBFS"];
// A*				 => weight = reach_cost + heuristic , clear weights in each step
// Optimized A*		 => weight = reach_cost + heuristic , add new nodes and their weights
// GBFS				 => weight = heuristic , clear weights in each step
// Optimized GBFS	 => weight = heuristic , add new nodes and their weights
var PROGRAM_MODE = programModes[1];

function setup() {
	createCanvas(windowWidth - 200, windowHeight - 20);
	selectedColor = color(150, 250, 50);
	noPathColor = color(250, 20, 20);

	loadDefaultMap();
	resetMap();
}

function setAlgorithm(mode) {
	PROGRAM_MODE = programModes[mode];

	console.log(mode);
}

function resetMap() {
	background(51);

	cities.forEach((n) => {
		n.resetNode();
		n.drawNode();
	});

	edgeList.forEach((n) => {
		var c1 = findNode(n[0]);
		var c2 = findNode(n[1]);

		if (c1 && c2) {
			drawEdge(c1, c2, n[2]);
		}
	});
}

function findNode(name) {
	return cities.find((n) => {
		return n.label === name;
	});
}

function draw() {
	noLoop();
}
