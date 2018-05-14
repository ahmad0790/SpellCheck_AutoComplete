import java.util.*;

/**
 * @author Ahmad Khan
 *
 */
public class DictionaryBST implements Dictionary 
{
   private BinarySearchTree<String> dict;
   private int dictSize = 0;
    
    // TODO: Implement the dictionary interface using a TreeSet.  
    // You'll need a constructor here
    public DictionaryBST(){
        dict = new BinarySearchTree<String>();
        dictSize = 0;
    }
    
    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
    public boolean addWord(String word) {
        // TODO: Implement this method
        boolean wordAdded = dict.add(word.toLowerCase());
        if (wordAdded == true){
            dictSize ++;
            return true;
        }
        return false;
    }


    /** Return the number of words in the dictionary */
    public int size()
    {
        // TODO: Implement this method
        return dictSize;
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
        //TODO: Implement this method
        if (dict.find(s.toLowerCase()) != null){
            return true;
        }
        else {
        return false;
        }
    }

}
