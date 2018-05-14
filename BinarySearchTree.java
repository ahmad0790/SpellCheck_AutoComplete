import java.io.Serializable;
import java.util.*;
/**
 * Implementation of a Binary Search Tree
 * 
 * @Ahmad Khan
 */
public class BinarySearchTree<E extends Comparable<E>>  
             extends BinaryTree<E> {
    
    protected Node<E> root;
    
    public BinarySearchTree() {
        root = null;
    }
    
    protected BinarySearchTree(Node<E> root) {
        this.root = root;
    }
    
    public E find(E target) { 
        return find(root, target); 
    } 
    
    
    private E find(Node<E> localRoot, E target) { 
        if (localRoot == null){
            return null;
        }
        
        int nodeVal = target.compareTo(localRoot.data);
        if (nodeVal == 0){
            return localRoot.data;
        }
        else if (nodeVal > 0){
            return find(localRoot.right, target);
        }
        else {
            return find(localRoot.left, target);
        }
        
    }
    
    boolean added;
    public boolean add(E data){
        root = add(root, data); 
        return added;
    } 
    
    private Node<E> add(Node<E> node, E data) { 
        if (node == null){
            node = new Node<>(data); 
            added = true;
            return node;
        }
        
        if (data.compareTo(node.data) == 0){
            added = false;
            return node;

        }
        else if (data.compareTo(node.data)< 0){
            //node.left = add(node.left, data);
            node.left = add(node.left, data);
        }
        else {
            //node.right = add(node.right, data);
            node.right = add(node.right, data);
        }
        return node;
    } 
    
    //from book
    
    /*public E find(E target) { 
        return find(root, target); 
    } 
    
    private E find(Node<E> localRoot, E target) { 
        if (localRoot == null) 
            return null; 
    
        // Compare the target with the data field at the root. 
        int compResult = target.compareTo(localRoot.data); 
        if (compResult == 0) 
            return localRoot.data; 
        else if (compResult < 0) 
            return find(localRoot.left, target); 
        else 
            return find(localRoot.right, target); 
    }
    
    boolean addReturn;
    public boolean add(E item) { 
        root = add(root, item); 
        return addReturn; 
    } 

    private Node<E> add(Node<E> localRoot, E item) { 
        if (localRoot == null) { 
            // item is not in the tree — insert it. 
            addReturn = true; 
            return new Node<>(item); 
        } else if (item.compareTo(localRoot.data) == 0) { 
            // item is equal to localRoot.data 
            addReturn = false; 
            return localRoot; 
        } else if (item.compareTo(localRoot.data) < 0) { 
            // item is less than localRoot.data 
            localRoot.left = add(localRoot.left, item); 
            return localRoot; 
        } else { 
            // item is greater than localRoot.data 
            localRoot.right = add(localRoot.right, item); 
            return localRoot; 
        } 
    }*/
    
}
