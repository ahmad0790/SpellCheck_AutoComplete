import java.util.*;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author Ahmad Khan
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    private int wordsAdded = 0;

    public AutoCompleteDictionaryTrie()
    {
        root = new TrieNode();
    }
    
    
    /** Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should convert the 
     * string to all lower case before you insert it. 
     * 
     * This method adds a word by creating and linking the necessary trie nodes 
     * into the trie, as described outlined in the videos for this week. It 
     * should appropriately use existing nodes in the trie, only creating new 
     * nodes when necessary. E.g. If the word "no" is already in the trie, 
     * then adding the word "now" would add only one additional node 
     * (for the 'w').
     * 
     * @return true if the word was successfully added or false if it already exists
     * in the dictionary.
     */
    public boolean addWord(String word)
    {
        // TODO: Implement this method
        //initialize for starting at root node
        word = word.toLowerCase();
        TrieNode currentNode = new TrieNode();
        currentNode = root;
        char currentChar;
        boolean wordAdded = false;
        
        //traverse down the word each letter at a time until out of letters
        for (int i = 0; i < word.length(); i++){
            currentChar = word.charAt(i);
            if (currentNode.getChild(currentChar) == null){
                currentNode.insert(currentChar);
                currentNode = currentNode.getChild(currentChar);
                if (i== (word.length() - 1) && currentNode.endsWord() == false){
                    wordAdded = true;
                    currentNode.setEndsWord(true);
                    wordsAdded = wordsAdded + 1;
                }
            }
            else {
                currentNode = currentNode.getChild(currentChar);
                if (i == (word.length() - 1) && currentNode.endsWord() == false){
                    wordAdded = true;
                    currentNode.setEndsWord(true);
                    wordsAdded = wordsAdded + 1;
                }
            }
                
        }
        return wordAdded;
    }
    
    /** 
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size()
    {
        //TODO: Implement this method
        return wordsAdded;
    }
    
    
    /** Returns whether the string is a word in the trie, using the algorithm
     * described in the videos for this week. */
    @Override
    public boolean isWord(String s) 
    {
        // TODO: Implement this method
        s = s.toLowerCase();
        TrieNode currentNode = new TrieNode();
        currentNode = root;
        char currentChar;
        if (s.length() == 0){
            return false;
        }
        
        else {
            for (int i = 0; i < s.length(); i++){
                currentChar = s.charAt(i);
                if (currentNode.getChild(currentChar) == null){
                    return false;
                }
                else {
                    currentNode = currentNode.getChild(currentChar);
                }   
            }
            if (currentNode.endsWord() == true) {
                return true;
            }
            else{
                return false;
            }
        }
    }
    
    
    public TrieNode findStem(String s) 
    {
        // TODO: Implement this method
        s = s.toLowerCase();
        TrieNode currentNode = new TrieNode();
        currentNode = root;
        char currentChar;
        for (int i = 0; i < s.length(); i++){
            if (currentNode.getChild(s.charAt(i)) != null){
                currentNode = currentNode.getChild(s.charAt(i));
            }
            
            else {
                return new TrieNode();
            }
        }
        return currentNode;
    }
    

    /** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public ArrayList<String> predictCompletions(String prefix, int numCompletions) 
     {
         // TODO: Implement this method
         // This method should implement the following algorithm:
         // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
         //    empty list
         // 2. Once the stem is found, perform a breadth first search to generate completions
         //    using the following algorithm:
         //    Create a queue (LinkedList) and add the node that completes the stem to the back
         //       of the list.
         //    Create a list of completions to return (initially empty)
         //    While the queue is not empty and you don't have enough completions:
         //       remove the first Node from the queue
         //       If it is a word, add it to the completions list
         //       Add all of its child nodes to the back of the queue
         // Return the list of completions
        ArrayList<String> wordsAdded = new ArrayList<String>();
         
        TrieNode currentNode = new TrieNode();
        currentNode = findStem(prefix);   
         
        Queue<TrieNode> q = new LinkedList<TrieNode>();
        q.add(currentNode);
         
         while (!q.isEmpty() && wordsAdded.size() < numCompletions){
            currentNode = q.poll();
            if (isWord(currentNode.getText())){
                wordsAdded.add(currentNode.getText());
            }
            Set<Character> currentNodeChildCharacters = currentNode.getValidNextCharacters();
            for (char childChar : currentNodeChildCharacters){
                q.add(currentNode.getChild(childChar));
            }
        }
         return wordsAdded;
     }

    // For debugging
    public void printTree()
    {
        printNode(root);
    }
    
    /** Do a pre-order traversal from this node down */
    public void printNode(TrieNode curr)
    {
        if (curr == null) 
            return;
        
        System.out.println(curr.getText());
        
        TrieNode next = null;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }

    /*public static void main (String [] args){
        AutoCompleteDictionaryTrie testDict = new AutoCompleteDictionaryTrie();
        System.out.println("-----");
        System.out.println(testDict.addWord("eats"));
        System.out.println(testDict.addWord("eat"));
        testDict.addWord("hello");
        testDict.addWord("help");
        testDict.addWord("helper");
        testDict.addWord("helps");
        System.out.println(testDict.addWord("he"));
        System.out.println(testDict.addWord("help"));
        testDict.printTree();
        
        String dictFile = "/Users/ahkhan/Downloads/MOOCTextEditor/data/words.small.txt";
        AutoCompleteDictionaryTrie emptyDict = new AutoCompleteDictionaryTrie();
        AutoCompleteDictionaryTrie smallDict = new AutoCompleteDictionaryTrie();
        DictionaryLoader.loadDictionary(smallDict, dictFile);

        /*smallDict.addWord("Hello");
        smallDict.addWord("HElLo");
        smallDict.addWord("help");
        smallDict.addWord("he");
        smallDict.addWord("hem");
        smallDict.addWord("hot");
        smallDict.addWord("hey");
        smallDict.addWord("a");
        smallDict.addWord("subsequent");
        smallDict.printTree();
        System.out.println("More Testing") ;
        System.out.println(smallDict.findStem("hel").getText());
        System.out.println(smallDict.isWord("h"));
        System.out.println(smallDict.isWord("what"));
        System.out.println(smallDict.isWord("hello"));
        System.out.println(smallDict.isWord("come"));
        List<String> list = smallDict.predictCompletions("hel", 4);
        System.out.println(Arrays.toString(list.toArray()));
        list = smallDict.predictCompletions("h", 4);
                System.out.println(Arrays.toString(list.toArray()));
        list = smallDict.predictCompletions("he", 4);
                System.out.println(Arrays.toString(list.toArray()));
        list = smallDict.predictCompletions("su", 4);
                System.out.println(Arrays.toString(list.toArray()));
       String s = "ehllo";
       System.out.println(s.substring(0,0) + s.substring(2,4));
       s = s.substring(0,0) + s.substring(2,4);
       System.out.println(s.length());
    }*/
}