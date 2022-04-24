function loadDefaultMap() {
	addDefaultNodes();
	addDefaultEdges();
	connectEdges();
}

function addDefaultNodes() {
	cities = [];

	cities.push(new node("Arad", 65, 168, 366));
	cities.push(new node("Bucharest", 642, 463, 0));
	cities.push(new node("Craiova", 365, 540, 160));
	cities.push(new node("Dobreta", 200, 515, 242));
	cities.push(new node("Eforie", 946, 525, 161));
	cities.push(new node("Fagaras", 467, 244, 178));
	cities.push(new node("Giurgiu", 594, 568, 77));
	cities.push(new node("Hirsova", 892, 421, 151));
	cities.push(new node("Iasi", 780, 140, 226));
	cities.push(new node("Lugoj", 202, 370, 244));
	cities.push(new node("Mehadia", 205, 440, 241));
	cities.push(new node("Neamt", 653, 84, 234));
	cities.push(new node("Oradea", 140, 25, 380));
	cities.push(new node("Pitesti", 495, 391, 98));
	cities.push(new node("Rimnicu Vilcea", 330, 315, 193));
	cities.push(new node("Sibiu", 283, 225, 253));
	cities.push(new node("Timisoara", 70, 312, 329));
	cities.push(new node("Urziceni", 750, 423, 80));
	cities.push(new node("Vaslui", 847, 251, 199));
	cities.push(new node("Zerind", 95, 93, 374));

	cities[1].isTarget = true;
}

function addDefaultEdges() {
	clearEdges();
	edgeList = [
		["Oradea", "Zerind", 71],
		["Oradea", "Sibiu", 151],
		["Zerind", "Arad", 75],
		["Arad", "Sibiu", 140],
		["Arad", "Timisoara", 118],
		["Timisoara", "Lugoj", 111],
		["Lugoj", "Mehadia", 70],
		["Mehadia", "Dobreta", 75],
		["Dobreta", "Craiova", 120],
		["Craiova", "Rimnicu Vilcea", 146],
		["Craiova", "Pitesti", 138],
		["Rimnicu Vilcea", "Sibiu", 80],
		["Rimnicu Vilcea", "Pitesti", 97],
		["Sibiu", "Fagaras", 99],
		["Fagaras", "Bucharest", 211],
		["Pitesti", "Bucharest", 101],
		["Bucharest", "Giurgiu", 90],
		["Bucharest", "Urziceni", 85],
		["Urziceni", "Hirsova", 98],
		["Hirsova", "Eforie", 86],
		["Urziceni", "Vaslui", 142],
		["Vaslui", "Iasi", 92],
		["Iasi", "Neamt", 87],
	];
}

function connectEdges() {
	edgeList.forEach((n) => {
		var c1 = findNode(n[0]);
		var c2 = findNode(n[1]);

		if (c1 && c2) {
			c1.neighbors.push([c2, n[2]]);
			c2.neighbors.push([c1, n[2]]);
		}
	});
}

function clearEdges() {
	edgeList = [];
	var buttons = document.getElementsByTagName("button");

	for (var btn of buttons) {
		if (btn.id !== "RandomMap") {
			btn.remove();
		}
	}
}

function generateRandomMap(nodeCount = 8) {
	cities = [];
	clearEdges();

	let target = new node(
		"Target City",
		Math.floor(random(windowWidth - 230) / 1.2),
		Math.floor(random(windowHeight - 50) / 1.2),
		0
	);
	target.isTarget = true;
	cities.push(target);

	for (let i = 0; i < nodeCount - 1; i++) {
		let x = Math.floor(random(20, windowWidth - 230) / 1.2);
		let y = Math.floor(random(20, windowHeight - 50) / 1.2);

		let n = new node(
			`City #${i + 1}`,
			x,
			y,
			Math.floor(dist(x, y, target.pos.x, target.pos.y) / 1.44)
		);

		cities.push(n);
	}

	for (let i = 0; i < nodeCount; i++) {
		let newEdgeCount = Math.floor(2);

		generateRandomEdgesFrom(cities[i], newEdgeCount);
	}

	connectEdges();
	resetMap();
}

function generateRandomEdgesFrom(n, count) {
	/*while (count > 0) {
			let targetIndex = Math.floor(random(cities.length));
			let targetNode = cities[targetIndex];
			let d = Math.floor(
				dist(n.pos.x, n.pos.y, targetNode.pos.x, targetNode.pos.y)
			);
			let edge = [n.label, targetNode.label, d];
	
			if (
				edgeList.includes(edge) ||
				edgeList.includes([targetNode.label, n.label, d]) ||
				n.label === targetNode.label
			) {
				continue;
			}
	
			count--;
			edgeList.push(edge);
		}*/

	let nodes = [];
	let distances = [];

	for (let c of cities) {
		if (c.label !== n.label) {
			let d = Math.floor(dist(n.pos.x, n.pos.y, c.pos.x, c.pos.y) / 1.44);
			nodes.push(c.label);
			distances.push(d);
		}
	}

	while (count > 0 && distances.length > 0) {
		let index = distances.indexOf(min(distances));
		let edge = [n.label, nodes[index], distances[index]];

		// if (!intersects(edge)) {
		edgeList.push(edge);
		count--;
		// }

		nodes.splice(index, 1);
		distances.splice(index, 1);
	}
}

function intersects(edge) {
	let n1 = findNode(edge[0]);
	let n2 = findNode(edge[1]);

	for (let e of edgeList) {
		let c1 = findNode(e[0]);
		let c2 = findNode(e[1]);

		if (
			ccw(n1, n2, c1) != ccw(n2, c1, c2) &&
			ccw(n1, n2, c1) != ccw(n1, n2, c2)
		) {
			console.log(edge, e);

			return true;
		}
	}
	return false;
}

function ccw(p1, p2, p3) {
	return (
		(p3.pos.y - p1.pos.y) * (p2.pos.x - p1.pos.x) >
		(p2.pos.y - p1.pos.y) * (p3.pos.x - p1.pos.x)
	);
}
