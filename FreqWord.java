import java.util.*;
public class FreqWord implements Comparable<FreqWord>{
    private String word;
    private Integer rank;
    
    public FreqWord(String s, int r){
        this.word = s;
        this.rank = r;
    }

    public String getWord(){
        return word;
    }
    
    public Integer getRank(){
        return rank;
    }
    
    @Override
    public int compareTo(FreqWord other) {
        return this.getRank().compareTo(other.getRank());
    }
    
    @Override
    public String toString(){
        //return Integer.toString(rank);
        return word;
    }

}  

 
