/**
 * Write a description of App here.
 * 
 * @author Ahmad Khan
 * @version 1.1
 */
import java.util.*;
import java.util.Scanner;
public class App {
    
    public static void main(String [] args) {
        String dictFile = "words_sorted.txt";
        String freqWordsFile = "freqwords.csv";
        String smallDict = "words_sorted.txt"; 
        //use this smaller dict below if you wnat to speed performance
        //String smallDict = "words_small_unsorted.txt"; 
        AutoCompleteDictionaryTrie trieDict = new AutoCompleteDictionaryTrie();
        DictionaryLoader.loadDictionary(trieDict, dictFile);
        
        //freq words loaded
        DictionaryLoader dl = new DictionaryLoader();
        HashMap<String, FreqWord> freqWordsDict = dl.loadFreqWords(freqWordsFile);
        boolean programRunning = true;
        ArrayStack<String> undoStack = new ArrayStack<String>();
        int numWords = trieDict.size();

        
        System.out.println("Welcome to Ahmad's SpellChecker and AutoComplete");
        System.out.println("There are a total of " + numWords +" English words in the dictionary!" );
        System.out.println("------------------------------------------------");

        while(programRunning == true) {
            System.out.println("What would you like do?");
            System.out.println("Press 1 to spell check your text");
            System.out.println("Press 2 to auto complete a word or question");
            System.out.println("Press 3 to check if a word is in the dictionary");
            System.out.println("Presss any other number to exit");
            Scanner keyboard = new Scanner(System.in);
            Scanner textInput = new Scanner(System.in);
            int inputChoice = keyboard.nextInt();
            
            if (inputChoice == 1) {
                System.out.println("Please enter your text below");
                System.out.println("Or Press U to copy paste your old text");
                
                String inputText = textInput.nextLine();
                //remove spelling
                inputText = inputText.replaceAll("[^a-zA-Z ]", "");
                
                if (inputText.toLowerCase().equals("u")){
                    inputText = undoStack.pop();
                    System.out.println("This was your last text paragraph");
                    System.out.println(inputText);
                }
                else {
                    undoStack.push(inputText);
                }
                
                String[] inputTextArray = inputText.split(" ");
                ArrayList<String> misspelledWords = new ArrayList<String>();
                
                for (int i=0; i< inputTextArray.length; i++){
                    if (trieDict.isWord(inputTextArray[i])==false && !inputTextArray[i].toLowerCase().equals("i")){
                        misspelledWords.add(inputTextArray[i]);
                    }
                }
                
                if (misspelledWords.size() == 0){
                    System.out.println("Good job! Seems like you have no spelling errors!");
                }
                
                else if (misspelledWords.size()>0) {                 
                    System.out.println("You have misspelled the following words."); 
                    System.out.println("Please double check your spellings");
                    System.out.println("");

                    for (int i=0; i< misspelledWords.size(); i++){
                        System.out.println(misspelledWords.get(i));
                    }
                    
                    System.out.println("");
                    System.out.println("Would you like some help with improving your spellings?");
                    System.out.println("Press Y for yes. Press N for no");
                    inputText = textInput.nextLine();
                    
                    if (inputText.toLowerCase().equals("y")){
                        System.out.println("Great. Let me give you some correctly spelled suggestions.");
                        System.out.println("How many suggestions at most would you like for each word?");
                        int numSpellingSuggestions = keyboard.nextInt();
                        
                        System.out.println("What kinds of recommendations would you like");
                        System.out.println("Press 1 for Lexicographical Similarity Recommendations");
                        System.out.println("Press 2 for Most Likely to Occur Similarity");
                        
                        int recommendationType = keyboard.nextInt();
                        NearbyWords closeWords = new NearbyWords(trieDict);
                        
                        System.out.println("Great here are some words for you?");
                        for (int i=0; i< misspelledWords.size(); i++){
                            String misspelled = misspelledWords.get(i);
                            System.out.println("Spelling Suggestions for \""+misspelled+"\" are:");
                            List<String> suggestedWords = closeWords.suggestions(misspelled, numSpellingSuggestions);
                            ArrayList<FreqWord> suggestedWordsLikely = new ArrayList<FreqWord>();
                            
                            //pruning for likely occuring words
                            PriorityQueue<FreqWord> pq = new PriorityQueue<>();
                            for (String word: suggestedWords){
                                if (freqWordsDict.get(word) != null){
                                    pq.add(freqWordsDict.get(word));
                                }
                            }
                            
                            for (int j=0; j< numSpellingSuggestions; j++){
                                suggestedWordsLikely.add(pq.poll());
                            }
                            
                            if (recommendationType == 1){
                                System.out.println(suggestedWords);
                            }
                            else if (recommendationType == 2){
                                System.out.println(suggestedWordsLikely);
                            }
                        }
                        
                        System.out.println("Would you like the spellchecker to automatically fix the spelling errors?");
                        System.out.println("Press Y for yes. Press N for no");
                        inputText = textInput.nextLine();
                        
                        if (inputText.toLowerCase().equals("y")){
                            String correctedString = "";
                            for (int i=0; i< inputTextArray.length; i++){
                                correctedString = correctedString + " ";
                                if (misspelledWords.contains(inputTextArray[i])){
                                    List<String> suggestedWord = closeWords.suggestions(inputTextArray[i], 1);
                                    if ( recommendationType == 1){
                                        correctedString = correctedString + suggestedWord.get(0);
                                    }
                                    else if (recommendationType ==2){
                                        System.out.println("Not implemented for Word Frequency Ranks due to lack of words");
                                    }
                                }
                                else{
                                    correctedString = correctedString + inputTextArray[i];
                                }
                            }
                            System.out.println("The correced sentence is as follows:");
                            System.out.println("------------------------------------");
                            System.out.println(correctedString);
                        }
                    }
                    
                    else if (inputText.toLowerCase().equals("n")){
                        System.out.println("Okay we are skipping spell checking");
                    }
                    else{
                        System.out.println("Please make sure you enter a valid letter");
                    }
                }
            }
            
            else if (inputChoice == 2){
                System.out.println("What do you need help autocompleting?");
                
                String inputText = textInput.nextLine();
                String[] inputTextArray = inputText.split(" ");
                String prefixWord = inputTextArray[inputTextArray.length-1];
                prefixWord = prefixWord.toLowerCase();
                
                System.out.println("How many results at most would you like?");
                int resultsChoice = keyboard.nextInt();
                ArrayList<String> recommendedWords = new ArrayList<String>();
                recommendedWords = trieDict.predictCompletions(prefixWord, resultsChoice);
                
                System.out.println("Here are some recommended completed words for your sentence");
                for (int i=0; i< recommendedWords.size(); i++){
                    System.out.println(recommendedWords.get(i));
                }
            }
            
            else if (inputChoice == 3){
                System.out.println("What word would you like to check for in the dictionary?");
                String inputText = textInput.nextLine();
                inputText = inputText.toLowerCase();
                
                //other dictionaries for performance comparis
                DictionaryLL linkedListDict = new DictionaryLL();
                DictionaryHashSet hashDict = new DictionaryHashSet();
                DictionaryBST bstDict = new DictionaryBST();
                DictionaryArray arrayDict = new DictionaryArray();
                DictionaryLoader.loadDictionary(bstDict, smallDict);
                DictionaryLoader.loadDictionary(linkedListDict, smallDict);
                DictionaryLoader.loadDictionary(hashDict, smallDict);
                DictionaryLoader.loadDictionary(arrayDict, smallDict);
        
                 if (linkedListDict.isWord(inputText) && hashDict.isWord(inputText) 
                        && bstDict.isWord(inputText) && trieDict.isWord(inputText)
                        && arrayDict.isWord(inputText)){
                        App timeTakenApp = new App();
                        System.out.println("The word: " + inputText + " does exist in the dictionary");
                        System.out.println("These are the dictionaries we searched through and how long it took");
                        System.out.println("");
                        
                        System.out.println("ARRAY (TRAVERSAL) :");
                        timeTakenApp.timeTaken(arrayDict, inputText,1000);
                        System.out.println("ARRAY (BINARY SEARCH) :");
                        String [] sortedArray = (arrayDict.getDict());
                        Arrays.sort(sortedArray);
                        timeTakenApp.timeTaken(sortedArray, inputText,1000);
                        System.out.println("LINKED LIST :");
                        timeTakenApp.timeTaken(linkedListDict, inputText,1000);
                        System.out.println("HASHSET:");
                        timeTakenApp.timeTaken(hashDict, inputText,1000);
                        System.out.println("BINARY SEARCH TREE");
                        timeTakenApp.timeTaken(bstDict, inputText,1000);
                        System.out.println("TRIE");
                        timeTakenApp.timeTaken(trieDict, inputText,1000);   
                        System.out.println("BALANCED BINARY SEARCH TREE");
                        BalancedBST balBST = new BalancedBST();
                        balBST.createBST(sortedArray);
                        timeTakenApp.timeTaken(balBST, inputText,1000); 
                        System.out.println("");
                    }
                 else{
                     System.out.println("Sorry but the word does not exist in the dictionary");
                 } 
            }
            
            
            else {
                System.out.println("Now exiting the spellchecker and autocomplete");
                System.out.println("---------------------------------------------");
                programRunning = false;
            }
            
        }   
    
    }

    public void timeTaken(Dictionary dict, String s, double numIterations)
    {
        double difference = 0;
        double seconds = 0;
        
        for (int i = 0; i < numIterations; i++){
            double startTime = System.nanoTime();
            dict.isWord(s);
            double endTime = System.nanoTime();
            difference = difference + (endTime - startTime);
            seconds = seconds + (difference / 1000000000);
        }
        System.out.println("Search executed in " + difference/numIterations + " nanoseconds");
        //System.out.println(" (" + seconds/numIterations*1.000 + " seconds)");
    }
    
    public void timeTaken(BalancedBST dict, String s, double numIterations)
    {
        double difference = 0;
        double seconds = 0;
        
        for (int i = 0; i < numIterations; i++){
            double startTime = System.nanoTime();
            dict.find(s);
            double endTime = System.nanoTime();
            difference = difference + (endTime - startTime);
            seconds = seconds + difference / 1000000000;
        }
        System.out.println("Search executed in " + difference/numIterations + " nanoseconds");
        //System.out.println(" (" + seconds/numIterations *1.000 + " seconds)");
        
    }
    
    public void timeTaken(String[] sortedArray, String s, double numIterations)
    {
        double difference = 0;
        double seconds = 0;
        
        for (int i = 0; i < numIterations; i++){
            double startTime = System.nanoTime();
            binarySearch(sortedArray, 0, sortedArray.length, s);
            double endTime = System.nanoTime();
            difference = difference + (endTime - startTime);
            seconds = seconds + difference / 1000000000;
        }
        System.out.println("Search executed in " + difference/numIterations + " nanoseconds");
        //System.out.println(" (" + seconds/numIterations *1.000 + " seconds)");
        
    }
    
    public boolean binarySearch(String [] sortedArray, int start, int end, String s){
        int mid = (start + end) /2;
        if (sortedArray[mid].equals(s)){
            return true;
        }
        
        else if (s.compareTo(sortedArray[mid])<0) {
            return binarySearch(sortedArray,start, mid-1, s);
        }
        
        else {
            return binarySearch(sortedArray,mid+1,end, s);
        }  
    }
}
