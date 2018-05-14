import java.io.Serializable;
import java.util.*;
/**
 * Write a description of BalancedBST here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BalancedBST<E extends Comparable<E>>  {
    Node<String> root;
    
    protected  class Node<String> implements Serializable {
        // Data Fields
        public String data;
        public Node<String> left;
        public Node<String> right;

        public Node(String data) {
            this.data = data;
            left = null;
            right = null;
        }

    }
    
    public BalancedBST(){
        this.root = null;
    }
    
    public void createBST(String [] array){
        this.root = createBST(0, array.length - 1, array);
    }
    
    public Node<String> createBST(int start, int end, String [] bstArray){
        
        if(start>end){
            return null;
        }
        else{
            int mid = (start + end) /2;  
            Node<String> root = new Node<String>(bstArray[mid]);
            
            root.left = createBST(start,mid-1, bstArray);
            root.right = createBST(mid+1,end, bstArray);
            return root;
        }
    }
    
    Node sortedArrayToBST(int arr[], int start, int end) {
 
        /* Base Case */
        if (start > end) {
            return null;
        }
 
        /* Get the middle element and make it root */
        int mid = (start + end) / 2;
        Node node = new Node(arr[mid]);
 
        /* Recursively construct the left subtree and make it
         left child of root */
        node.left = sortedArrayToBST(arr, start, mid - 1);
 
        /* Recursively construct the right subtree and make it
         right child of root */
        node.right = sortedArrayToBST(arr, mid + 1, end);
         
        return node;
    }
    
    public String find(String target) { 
        return find(root, target); 
    } 
    
    private String find(Node<String> localRoot, String target) { 
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
}
