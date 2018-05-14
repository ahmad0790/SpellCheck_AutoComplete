import java.io.Serializable;
import java.util.*;
public class DictionaryArray implements Dictionary{
    private String[] bstArray= new String[109582];
    //for small dict
    //private String[] bstArray= new String[4440];

    /*public DictionaryArray(int words){
        String[] bstArray= new String[words];
    }*/

    private int counter = 0;
    
    public int size(){
        return counter;
    }
    
    public boolean isWord(String s){
        for (int i=0;i<bstArray.length;i++){
            if(bstArray[i].equals(s)){
                return true;
            }
        }
        return false;
    }
    
    public boolean addWord(String s){
        bstArray[counter] = s;
        counter++;
        return true;
    } 
    
    public String [] getDict(){
        return bstArray;
    }
}