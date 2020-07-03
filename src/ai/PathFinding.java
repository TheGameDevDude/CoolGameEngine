package ai;

import java.util.ArrayList;
import java.util.List;

import math.Vector3f;

public class PathFinding {
	public Grid grid;
	public Node startNode;
	public Node targetNode;
	private MinHeap openSet = new MinHeap();
	private List<Node> closedSet = new ArrayList<Node>();
	public List<Node> reversePath = new ArrayList<Node>();

	public PathFinding(Grid grid) {
		this.grid = grid;
	}

	public void findPath(Vector3f startPos, Vector3f targetPos) {
		openSet.clear();
		closedSet.clear();
		reversePath.clear();

		float distance = new Vector3f(startPos.Xpos - targetPos.Xpos, startPos.Ypos - targetPos.Ypos, startPos.Zpos - targetPos.Zpos).getMagnitude();
		if (distance >= 5.0f) {
			return;
		}

		startNode = grid.nodeFromWorldPoint(startPos);
		targetNode = grid.nodeFromWorldPoint(targetPos);

		if (targetNode.walkable == false) {
			return;
		}

		openSet.insert(startNode);

		while (openSet.size() > 0) {
			Node currentNode = openSet.extractLowestFCost();
			closedSet.add(currentNode);

			if (currentNode == targetNode) {
				retracePath(startNode, targetNode);
				return;
			}

			// calculating neighbors for the current node
			for (Node neighbor : grid.getNeighbours(currentNode)) {
				if (neighbor.walkable == false || closedSet.contains(neighbor)) {
					continue;
				}

				// shortest path to the neighbors
				int newMovementCostToNeighbour = currentNode.gCost + getDistance(currentNode, neighbor);
				if (newMovementCostToNeighbour < neighbor.gCost || !openSet.contains(neighbor)) {
					neighbor.gCost = newMovementCostToNeighbour;
					neighbor.hCost = getDistance(neighbor, targetNode);
					neighbor.parent = currentNode;
					if (!openSet.contains(neighbor)) {
						openSet.insert(neighbor);
					}
				}
			}
		}
	}

	void retracePath(Node startNode, Node endNode) {
		List<Node> path = new ArrayList<Node>();
		reversePath.clear();
		Node currentNode = endNode;

		while (currentNode != startNode) {
			path.add(currentNode);
			currentNode = currentNode.parent;
		}

		for (int i = path.size() - 1; i >= 0; i--) {
			reversePath.add(path.get(i));
		}
	}

	// get distance between two nodes
	int getDistance(Node nodeA, Node nodeB) {
		int distX = Math.abs(nodeA.gridX - nodeB.gridX);
		int distZ = Math.abs(nodeA.gridZ - nodeB.gridZ);

		if (distX > distZ) {
			return 14 * distZ + 10 * (distX - distZ);
		}

		return 14 * distX + 10 * (distZ - distX);
	}

}
