//Sri Naga Hansi Mamidi

import java.util.*;

public class Treap<E extends Comparable<E> > {
	private static class Node<E>{
		
		//Data fields
		public E data; // Key for the search
		public int priority; // random heap priority
		public Node <E > left;
		public Node <E > right;
		
		//Constructor
		public Node(E data, int priority) {
			if(data==null) {
				throw new IllegalArgumentException();
			}
			else {
				this.left=null;
				this.right=null;
				this.data=data;
				this.priority=priority; 
			}
		}
			
		//Methods
		//performs a right rotation
		public Node<E> rotateRight(){
			
			    Node<E> temp = this.left;
				Node<E> left = temp.right;
				temp.right = this;
				this.left = left;
				return temp;
		}	
		
		//performs a left rotation
		public Node<E> rotateLeft() {
			Node<E> temp = this.right;
			Node<E> right = temp.left;
			temp.left = this;
			this.right = right;
			return temp;
		}
		
		public String toString() {
			return this.data.toString();		
		}	
	}
		
    // Data fields
	private Random priorityGenerator;
	private Node<E> root;
	
	//Constructors 
	//Method that creates an empty treap
	public Treap() {		
		priorityGenerator = new Random();
		root = null;
	}
	
	//creates an empty treap and initializes priorityGenerator using new Random(seed)
	public Treap(long seed) {
		
		priorityGenerator = new Random(seed);
		root = null;
	}
	
	// Method to reheap the tree
	public void reheap(Node<E> child, Stack<Node<E>> stack) {
		while (!stack.isEmpty()) {
		Node<E> parent = stack.pop();
		if (parent.priority < child.priority) {
		 if (parent.data.compareTo(child.data) > 0) {
			child = parent.rotateRight();
			}
		 else {
		child = parent.rotateLeft();
			}
	 if (!stack.isEmpty()) {
		if (stack.peek().left == parent) {
			stack.peek().left = child;
		}
		else {
		stack.peek().right = child;
						}
	 }
	 else { 
	    this.root = child;
		}}
		else {
		   break;
		 }}
		}
	
	//method and call to the function for inserting the element into the tree
	boolean add(E key) {
		return add(key, priorityGenerator.nextInt());
	}
	
	//Method to add the element into the tree with priority
	boolean add(E key, int priority) {
	 if (root == null) {
	  root = new Node<E>(key, priority);
		return true;
		}
	 else {
	  Node<E> n = new Node<E>(key, priority);
	  Stack<Node<E>> stack = new Stack<Node<E>>();
	  Node<E> localroot = root;
	 while (localroot != null) {
	  localroot.data.compareTo(key);
	  if (localroot.data.compareTo(key) == 0) {
		 return false;
				}
	  else {
		  if (localroot.data.compareTo(key) < 0) {
			stack.push(localroot);
			if (localroot.right == null) {
			  localroot.right = n;
			  reheap(n, stack);
            	return true;
				}
		else {
			localroot = localroot.right;
						}
			}
	else { 
		stack.push(localroot);
		if (localroot.left == null) {
		 localroot.left = n;
		 reheap(n, stack);
		 return true;
		}
	else {
		localroot = localroot.left;
		}}
		  }
			}
		return false;
		}
	}
	
	/*Method that deletes the node with the given key from the treap and returns true
	* if the key not found return false*/
	public boolean delete(E key) {
		if (find(key) == false || root == null) {
			return false;
		} else {
			root = delete(key, root);
			return true;
		}
	}
	
	//Method to delete the node with a key
	private Node<E> delete(E key, Node<E> localroot){
	  if (localroot == null) {
		return localroot;
	   }
	 else {
	   if (localroot.data.compareTo(key) < 0) {
		 localroot.right = delete(key, localroot.right);
		}
	   else {
		  if (localroot.data.compareTo(key) > 0) {
			localroot.left = delete(key, localroot.left);
			}
	  else {
		if (localroot.right == null) {
		  localroot = localroot.left;
			}
		else if (localroot.left == null) {
		  localroot = localroot.right;
			}
		else {
		  if (localroot.right.priority < localroot.left.priority) {
			localroot = localroot.rotateRight();
			 localroot.right = delete(key, localroot.right);
			  }
		 else {
			localroot = localroot.rotateLeft();
		    localroot.left = delete(key, localroot.left);
			}}}
			}
		 }
		 return localroot;
		 }
	
	/*Finds a node with the given key in the treap rooted at root and returns true if it finds it
	 * false otherwise 	 */
	private boolean find(Node<E> root,E key) {
		if(root==null) {
			return false;
		}
		if(key.compareTo(root.data) == 0) {
			return true;
		} 
	else if(key.compareTo(root.data) < 0) {
			return find(root.left,key);
		} 
	else {
			return find(root.right,key);
		}
	}
	
	/* Finds a node with the given key in the treap and return true if finds
	 *  otherwise return false	 */
	public boolean find(E key) {
		if(key==null) {
			throw new NullPointerException("Key cannot null");
		}
		return find(root, key);
	}
	
	//Method for representing the tree traversal as a string
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		preOrderTraverse(root, 1, stringBuilder);
		return stringBuilder.toString();
	}
	
	//logic to traverse across the tree
	private void preOrderTraverse(Node<E> node, int depth, StringBuilder stringBuilder) {
		for (int i = 1; i < depth; i++) {
			stringBuilder.append("  ");
		}
		if (node == null) {
			stringBuilder.append("null\n");
		} 
		else {
			stringBuilder.append("(key = " + node.toString() + ", ");
			stringBuilder.append("priority = ");
			stringBuilder.append(node.priority);
			stringBuilder.append(")");
			stringBuilder.append("\n");
			preOrderTraverse(node.left, depth + 1, stringBuilder);
			preOrderTraverse(node.right, depth + 1, stringBuilder);
		}
	}
		
	public static void main(String[] args) {

		Treap<Integer> testTree = new Treap<Integer>();
		//adding elements to the tree
		testTree.add(4,19); 
		testTree.add(2,31);
		testTree.add(6,70); 
		testTree.add(1,84);
		testTree.add(3,12); 
		testTree.add(5,83);
		testTree.add(7,26);
		System.out.println("Deleting a Node: "+ testTree.delete(2)); //deleting a node
		testTree.add(2,31);
		System.out.println("Node Found? : "+ testTree.find(3)); // finding the node with a key
		System.out.println(testTree.toString());	//Representing the tree traversal as a string

	}

}
