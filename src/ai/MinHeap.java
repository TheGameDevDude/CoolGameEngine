package ai;

import java.util.ArrayList;
import java.util.List;

public class MinHeap {
	private List<Node> heap;

	public MinHeap() {
		heap = new ArrayList<Node>();
	}

	public void insert(Node node) {
		heap.add(node);
		int i = heap.size() - 1;
		// if the child are having lower Fcost then swap with parent
		while (i != 0 && (heap.get(getParent(i)).fCost() > heap.get(i).fCost() || (heap.get(getParent(i)).fCost() == heap.get(i).fCost() && heap.get(getParent(i)).hCost > heap.get(i).hCost))) {
			Node temp = heap.get(getParent(i));
			heap.set(getParent(i), heap.get(i));
			heap.set(i, temp);
			i = getParent(i);
		}
	}

	// maintain structure of the heap
	private void heapify(int i) {
		int l = getLeftChild(i);
		int r = getRightChild(i);
		int smallest = i;

		if (l < heap.size() && (heap.get(l).fCost() < heap.get(smallest).fCost() || (heap.get(l).fCost() == heap.get(smallest).fCost() && heap.get(i).hCost < heap.get(smallest).hCost))) {
			smallest = l;
		}
		if (r < heap.size() && (heap.get(r).fCost() < heap.get(smallest).fCost() || (heap.get(r).fCost() == heap.get(smallest).fCost() && heap.get(i).hCost < heap.get(smallest).hCost))) {
			smallest = r;
		}

		// if one of the child is having lower Fcost than parent then swap
		if (i != smallest) {
			Node temp = heap.get(i);
			heap.set(i, heap.get(smallest));
			heap.set(smallest, temp);
			heapify(smallest);
		}
	}

	// the top most will have the lowest Fcost
	public Node extractLowestFCost() {
		Node root = heap.get(0);
		heap.set(0, heap.get(heap.size() - 1));
		heap.remove(heap.size() - 1);
		heapify(0);
		return root;
	}

	public Node get(int i) {
		return heap.get(i);
	}

	public void clear() {
		heap.clear();
	}

	public boolean contains(Node node) {
		return heap.contains(node);
	}

	public int size() {
		return heap.size();
	}

	private int getParent(int i) {
		return (i - 1) / 2;
	}

	private int getLeftChild(int i) {
		return 2 * i + 1;
	}

	private int getRightChild(int i) {
		return 2 * i + 2;
	}
}
