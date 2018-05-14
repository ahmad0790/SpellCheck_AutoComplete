import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author Ahmad implemented insertions, deletions and suggestions method
 *
 */
public class NearbyWords implements SpellingSuggest {
    // THRESHOLD to determine how many words to look through when looking
    // for spelling suggestions (stops prohibitively long searching)
    // For use in the Optional Optimization in Part 2.
    private static final int THRESHOLD = 1000; 

    Dictionary dict;

    public NearbyWords (Dictionary dict) 
    {
        this.dict = dict;
    }

    /** Return the list of Strings that are one modification away
     * from the input string.  
     * @param s The original String
     * @param wordsOnly controls whether to return only words or any String
     * @return list of Strings which are nearby the original string
     */
    public List<String> distanceOne(String s, boolean wordsOnly )  {
           List<String> retList = new ArrayList<String>();
           insertions(s, retList, wordsOnly);
           substitution(s, retList, wordsOnly);
           deletions(s, retList, wordsOnly);
           return retList;
    }

    
    /** Add to the currentList Strings that are one character mutation away
     * from the input string.  
     * @param s The original String
     * @param currentList is the list of words to append modified words 
     * @param wordsOnly controls whether to return only words or any String
     * @return
     */
    public void substitution(String s, List<String> currentList, boolean wordsOnly) {
        // for each letter in the s and for all possible replacement characters
        for(int index = 0; index < s.length(); index++){
            for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
                // use StringBuffer for an easy interface to permuting the 
                // letters in the String
                StringBuffer sb = new StringBuffer(s);
                sb.setCharAt(index, (char)charCode);

                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                if(!currentList.contains(sb.toString()) && 
                        (!wordsOnly||dict.isWord(sb.toString())) &&
                        !s.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }
    
    /** Add to the currentList Strings that are one character insertion away
     * from the input string.  
     * @param s The original String
     * @param currentList is the list of words to append modified words 
     * @param wordsOnly controls whether to return only words or any String
     * @return
     */
    public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
        // TODO: Implement this method 
        for (int i = 0; i <= s.length() ; i++){
            for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {           
                String a = s.substring(0,i);
                String b = s.substring(i,s.length());
                String newString = a + (char)charCode + b;
                
                if (wordsOnly == true){
                    if(!currentList.contains(newString) && dict.isWord(newString) == true && !s.equals(newString)) {
                        currentList.add(newString);
                    }
                }
                
                else {
                    if(!currentList.contains(s) && !s.equals(newString)) {
                        currentList.add(newString);
                    }
                }
            }
        }
    }
    

    /** Add to the currentList Strings that are one character deletion away
     * from the input string.  
     * @param s The original String
     * @param currentList is the list of words to append modified words 
     * @param wordsOnly controls whether to return only words or any String
     * @return
     */
    public void deletions(String s, List<String> currentList, boolean wordsOnly ) {
        // TODO: Implement this method
        for (int i = 0; i < s.length() -1; i++){       
            String a = s.substring(0,i);
            String b = s.substring(i+1,s.length());
            String newString = a + b;
            currentList.add(newString);
            
            if (wordsOnly == true){
                if(!currentList.contains(newString) && dict.isWord(newString) == true && !s.equals(newString)) {
                    currentList.add(newString);
                }
            }
            
            else {
                if(!currentList.contains(s) && !s.equals(newString)) {
                    currentList.add(newString);
                }
            }
        }
    }

    /** Add to the currentList Strings that are one character deletion away
     * from the input string.  
     * @param word The misspelled word
     * @param numSuggestions is the maximum number of suggestions to return 
     * @return the list of spelling suggestions
     */
    @Override
    public List<String> suggestions(String word, int numSuggestions) {

        // initial variables
        int wordsAdded = 0;
        String currentWord;
        Queue<String> q = new LinkedList<String>();     // String to explore
        HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same string multiple times
        List<String> retList = new LinkedList<String>();   // words to return
        List<String> suggestedWords = new LinkedList<String>();   // words to return
        // insert first node
        q.add(word);
        visited.add(word);
        
        while(!q.isEmpty() && wordsAdded <= numSuggestions) {
            currentWord = q.remove();
            suggestedWords = distanceOne(currentWord, true);
            for (int i = 0; i < suggestedWords.size(); i++){
               if (!visited.contains(suggestedWords.get(i)) && (wordsAdded + 1) <= numSuggestions){
                   visited.add(word);
                   q.add(suggestedWords.get(i));
                   if (dict.isWord(suggestedWords.get(i)) && retList.contains(suggestedWords.get(i)) == false){
                       retList.add(suggestedWords.get(i));
                       wordsAdded++;
                    }
                }
            }
        }
        return retList;
    }   

   /*public static void main(String[] args) {
       String word = "speel";
       // Pass NearbyWords any Dictionary implementation you prefer
       Dictionary d = new DictionaryHashSet();
       String dictFile = "/Users/ahkhan/Downloads/MOOCTextEditor/data/dict.txt";
       DictionaryLoader.loadDictionary(d, dictFile);
       NearbyWords w = new NearbyWords(d);
       List<String> l = w.distanceOne(word, true);
       System.out.println("One away word Strings for for \""+word+"\" are:");
       System.out.println(l+"\n");

       word = "tis";
       List<String> suggest = w.suggestions(word, 5);
       System.out.println("Spelling Suggestions for \""+word+"\" are:");
       System.out.println(suggest);
   }*/

}
