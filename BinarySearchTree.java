import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T>{
	public BinaryNode root;
	private int size;
	private int modCount;
	
	public BinarySearchTree(){
		root = null;
		size = 0;
		modCount = 0;
	}
	
	public boolean isEmpty() {
		if (this.root == null) {
			return true;
		}
		return false;
	}

	public Iterator<T> iterator() {
		return new InOrderIterator(this.root);
	}
	
	public Iterator<T> preOrderIterator() {
		return new PreOrderIterator();
	}
	
	public int height() {
		if (root == null) return -1;
		return root.height();
	}

	public int size() {
		return size;
	}

	public String toString() {
		return toArrayList().toString();
	}
	
	public ArrayList<T> toArrayList() {
		ArrayList<T> a = new ArrayList<T>();
		Iterator<T> i = new InOrderIterator(root);
		while (i.hasNext()) {
			a.add(i.next());
		}
		return a;
	}
	
	public Object[] toArray() {
		return toArrayList().toArray();
	}
	
	public boolean insert(T e){
		if (e == null) {
			throw new IllegalArgumentException();
		}
		if (root == null) {
			root = new BinaryNode(e);
			size++;
			modCount++;
			return true;
		}
		return root.insert(e);
	}
	
	public boolean remove(T e){
		if (e == null) {
			throw new IllegalArgumentException();
		}
		MyBoolean b = new MyBoolean();
		if (root == null) {
			return false;
		}
		root = root.remove(e, b);
		if (b.getValue()) {
			modCount++;
			return true;
		}
		return false;
	}
	
	public class BinaryNode {
		private T element;
		private BinaryNode leftChild;
		private BinaryNode rightChild;
		
		public BinaryNode(T element){
			this.element = element;
			this.leftChild = null;
			this.rightChild = null;		
		}

		public int height() {
			int lHeight = -1;
			if (leftChild != null) {
				lHeight = leftChild.height();
			}
			int rHeight = -1;
			if (rightChild != null) {
				rHeight = rightChild.height();
			}
			return (lHeight > rHeight)?lHeight+1:rHeight+1;
		}
		
		public boolean insert(T e){
			int c = element.compareTo(e);
			if (c == 0) {
				return false;
			}
			if (c > 0) {
				if (leftChild == null) {
					leftChild = new BinaryNode(e);
					size++;
					modCount++;
					return true;
				}
				return leftChild.insert(e);
			}
			if (rightChild == null) {
				rightChild = new BinaryNode(e);
				size++;
				modCount++;
				return true;
			}
			return rightChild.insert(e);
		}
		
		public BinaryNode remove(T e, MyBoolean b) {
			int c = e.compareTo(element);
			if (c == -1) {
				if (this.leftChild == null) {
					b.setFalse();
					return null;
				}
				this.leftChild = this.leftChild.remove(e, b);
				return this;
			}
			if (c == 1) {
				if (this.rightChild == null) {
					b.setFalse();
					return null;
				}
				this.rightChild = this.rightChild.remove(e, b);
				return this;
			}
			if (this.leftChild == null && this.rightChild == null) {
				size--;
				return null;
			}
			if (this.leftChild == null) {
				size--;
				return this.rightChild;
			}
			if (this.rightChild == null) {
				size--;
				return this.leftChild;
			}
			
			BinaryNode current = this.leftChild;
			while (current.rightChild != null) {
				current = current.rightChild;
			}
			this.element = current.element;
			this.leftChild = this.leftChild.remove(current.element, b);
			return this;
		}

		public BinaryNode getLeftChild() {
			return this.leftChild;
		}
		
		public BinaryNode getRightChild() {
			return this.rightChild;
		}

		public T getElement() {
			return this.element;
		}
	}
		
	private class InOrderIterator implements Iterator<T> {
		Stack<BinaryNode> s;
		private int iteratorModCount;
		private T lastElement;
		
		public InOrderIterator(BinaryNode root){
			iteratorModCount = modCount;
			this.s = new Stack<BinaryNode>();
			if (root != null) {
				fillStack(root);
			}
		}

		private void fillStack(BinaryNode n) {
			s.push(n);
			if (n.leftChild != null) {
				fillStack(n.leftChild);
			}
		}
		
		public boolean hasNext() {
			return !s.empty();
		}

		public T next() {
			if (s.empty()) {
				throw new NoSuchElementException();
			}
			BinaryNode n = s.pop();
			if (n.rightChild != null) {
				fillStack(n.rightChild);
			}
			lastElement = n.element;
			return n.element;
		}
		
		public void remove() {
			if (lastElement == null) {
				throw new IllegalStateException();
			}
			if (modCount != iteratorModCount) {
				throw new ConcurrentModificationException();
			}
			BinarySearchTree.this.remove(lastElement);
			iteratorModCount++;
			lastElement = null;
	
		}

	}

	private class PreOrderIterator implements Iterator<T> {
		private Stack<BinaryNode> s;
		private int iteratorModCount;
		private BinaryNode lastNode;
		
		public PreOrderIterator() {
			iteratorModCount = modCount;
			s = new Stack<BinaryNode>();
			if (root != null) {
				s.push(root);
			}
		}

		public boolean hasNext() {
			return !s.empty();
		}

		public T next() {
			if (s.empty()) {
				throw new NoSuchElementException();
			}
			BinaryNode n = s.pop();
			if (n.rightChild != null) {
				s.push(n.rightChild);
			}
			if (n.leftChild != null) {
				s.push(n.leftChild);
			}
			lastNode = n;
			return n.element;
		}
		
		public void remove() {
			if (lastNode == null) {
				throw new IllegalStateException();
			}
			if (modCount != iteratorModCount) {
				throw new ConcurrentModificationException();
			}
			BinarySearchTree.this.remove(lastNode.element);
			iteratorModCount++;
			
			if (lastNode.leftChild != null && lastNode.rightChild != null) {
				s.pop();
				s.pop();
				s.push(lastNode);
			}
			
			lastNode = null;
		}

	}
	
	private class MyBoolean {
		private boolean value = true;
		
		boolean getValue(){
			return this.value;
		}
		
		void setFalse(){
			this.value = false;
		}
	}
	
}
