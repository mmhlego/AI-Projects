import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Algorithm {

	// ====================================== Number of nodes
	final static int nodeCount = 10;
	static int startNode = 1;
	static int targetNode = 0;

	public static void main(String[] args) {
		File data = new File("./test.txt");
		Scanner reader;

		// System.out.print("Start Node(1-" + nodeCount + ") : ");
		// System.out.print("Target Node(1-" + nodeCount + ") (enter 0 for full traversal) : ");

		startNode = Integer.parseInt(args[0]);
		targetNode = Integer.parseInt(args[1]);

		try {
			reader = new Scanner(data);
		} catch (FileNotFoundException e) {
			System.out.println("File not found (test.txt)");
			return;
		}

		// ====================================== Read data
		boolean[][] adj = new boolean[nodeCount][nodeCount];
		while (reader.hasNextLine()) {
			int node1 = reader.nextInt();
			int node2 = reader.nextInt();

			if (node1 > nodeCount || node2 > nodeCount) {
				System.out.println("Invalid node value");
				return;
			}

			adj[node1 - 1][node2 - 1] = true;
			adj[node2 - 1][node1 - 1] = true;
		}

		// ====================================== run the algorithms
		System.out.println("Java Results:");
		BFS(adj);
		DFS(adj);
		System.out.println("==================================================");

		reader.close();
	}

	public static void BFS(boolean[][] adj) {
		myQueue<Integer> queue = new myQueue<>();
		ArrayList<Integer> ans = new ArrayList<>();
		boolean[] visited = new boolean[nodeCount];
		queue.push(startNode);

		while (!queue.isEmpty()) {
			int node = queue.pop();
			ans.add(node);
			visited[node - 1] = true;

			if (node == targetNode)
				break;

			for (int i = 0; i < nodeCount; i++) {
				if (adj[node - 1][i] && !visited[i]) {
					queue.push(i + 1);
					visited[i] = true;
				}
			}
		}

		System.out.print("BFS result: ");
		for (Integer val : ans) {
			System.out.print(val + ", ");
		}
		System.out.println();
	}

	public static void DFS(boolean[][] adj) {
		myStack<Integer> stack = new myStack<>();
		ArrayList<Integer> ans = new ArrayList<>();
		boolean[] visited = new boolean[nodeCount];
		stack.push(startNode);

		while (!stack.isEmpty()) {
			int node = stack.pop();
			ans.add(node);
			visited[node - 1] = true;

			if (node == targetNode)
				break;

			for (int i = 0; i < nodeCount; i++) {
				if (adj[node - 1][i] && !visited[i]) {
					stack.push(i + 1);
					visited[i] = true;
				}
			}
		}

		System.out.print("DFS result: ");
		for (Integer val : ans) {
			System.out.print(val + ", ");
		}
		System.out.println();
	}
}

// ====================================== Queue class
class myQueue<T> {
	ArrayList<T> data = new ArrayList<>();

	public void push(T val) {
		data.add(val);
	}

	public T pop() {
		return data.remove(0);
	}

	public boolean isEmpty() {
		return data.isEmpty();
	}
}

// ====================================== Stack class
class myStack<T> {
	ArrayList<T> data = new ArrayList<>();

	public void push(T val) {
		data.add(val);
	}

	public T pop() {
		return data.remove(data.size() - 1);
	}

	public boolean isEmpty() {
		return data.isEmpty();
	}
}
