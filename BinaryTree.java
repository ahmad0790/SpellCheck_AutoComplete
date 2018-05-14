import java.io.Serializable;
import java.util.*;

/**
 * Class for a binary tree that stores type E objects.
 *
 * @param <E> The element type *
 */
public class BinaryTree<E> implements Serializable {

    /*<listing chapter="6" number="1">*/
    /**
     * Class to encapsulate a tree node.
     *
     * @param <E> The element type
     */
    protected static class Node<E> implements Serializable {
        // Data Fields

        /**
         * The information stored in this node.
         */
        public E data;
        /**
         * Reference to the left child.
         */
        public Node<E> left;
        /**
         * Reference to the right child.
         */
        public Node<E> right;

        // Constructors
        /**
         * Construct a node with given data and no children.
         *
         * @param data The data to store in this node
         */
        public Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }


        // Methods
        /**
         * Returns a string representation of the node.
         *
         * @return A string representation of the data fields
         */
        @Override
        public String toString() {
            return data.toString();
        }

    }
    /*</listing>*/
    // Data Field
    /**
     * The root of the binary tree
     */
    protected Node<E> root;
    
    public BinaryTree() {
        root = null;
    }

    protected BinaryTree(Node<E> root) {
        this.root = root;
    }

    public void addNode(E data) {
        Queue <Node<E>> nodeQueue = new LinkedList <Node<E>>();
        Node<E> currentNode = null;
        Node<E> addedNode = null;
        boolean added = false;
        
        //start with the root node and check if it is null
        if (root == null){
            this.root = new Node<E>(data);
            addedNode = root;
            added = true;
        }
        //if root already exists then let's traverse the tree using BFS
        //looking for an empty child node where we can place our new node
        else {
            nodeQueue.add(root);

            //the queue checks if there are any nodes still left to check for insertion
            while (!nodeQueue.isEmpty() && added == false){
                currentNode = nodeQueue.remove();
                if (currentNode != null) {
                    if (currentNode.left == null) {
                        currentNode.left = new Node<E>(data);
                        addedNode = currentNode.left;
                        added= true;

                    }
                    else if (currentNode.right == null && added == false){
                        currentNode.right = new Node<E>(data);
                        added = true;
                        addedNode = currentNode.right;
                    }
                    nodeQueue.add(currentNode.left);
                    nodeQueue.add(currentNode.right);
                }
            }
        }
    }
    //breadth first search algorithm
    public boolean searchBFS(E data) {
        Queue <Node<E>> nodeQueue = new LinkedList <Node<E>>();
        nodeQueue.add(root);
        boolean valueFound = false;
        while (!nodeQueue.isEmpty()){
            Node<E> currentNode = nodeQueue.remove();
            if (currentNode != null) {
                //System.out.println(currentNode.data);
                valueFound = false;
                if (currentNode.data == data){
                    valueFound = true;
                    return valueFound;
                }
                nodeQueue.add(currentNode.left);
                nodeQueue.add(currentNode.right);
            }
        }

        return valueFound;
    }

    //the depth first search algorithm uses preorder traversal
    public int searchDFS(E data){
        ArrayList<Integer> datafoundList = new ArrayList<Integer>();
        return this.searchDFS(root, data, datafoundList) ;
    }

    //the depth first search algorithm uses preorder traversal
    private int searchDFS(Node<E> currentNode, E data, ArrayList<Integer> datafoundList) {

        if (currentNode == null){
            //recursive base case - do nothing
        }

        else if (currentNode != null){
            //System.out.println(currentNode.data);
            if (currentNode.data == data) {
                datafoundList.add(1);
            }
            // the recursive call to go down the tree by calling the child directly below
            // first go down the left child
            // then onto the right child
            searchDFS(currentNode.left, data, datafoundList);
            searchDFS(currentNode.right, data, datafoundList);
        }
        //number of times we found the element
        return datafoundList.size();
    }

    public E getData() {
        if (root != null) {
            return root.data;
        } else {
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root, 1, sb);
        return sb.toString();
    }

    /** Converts a sub-tree to a string.
     Performs a preorder traversal.
     @param node The local root
     @param depth The depth
     @param sb The StringBuilder to save the output
     */
    private void toString(Node<E> node, int depth,
                          StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append("  ");
        }
        if (node == null) {
            sb.append("null\n");
        } else {
            sb.append(node.toString());
            sb.append("\n");
            toString(node.left, depth + 1, sb);
            toString(node.right, depth + 1, sb);
        }
    }
}

