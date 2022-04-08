import java.util.*;
/**
 * Write a description of class LargestQuakes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LargestQuakes {
    
    /*
       Calls getLargest to print the given number of
       earthquakes of largest magnitude
       */
    public void findLargestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        
        
        System.out.println("There are " + list.size() + " earthquakes.");
        
        int howMany = 5;
        ArrayList<QuakeEntry> largests = getLargest(list, howMany);
        
        for (QuakeEntry qe : largests) {
            System.out.println(qe);
        }
        /*System.out.println("The earthquake with the largest magnitude is " + 
        "at index " + largestIndex + " and it is: \n" + list.get(largestIndex));*/
    }
    
    /*
        Returns an integer representing the index location in data of the 
        earthquake with the largest magnitude. 
       */
    public int indexOfLargest(ArrayList<QuakeEntry> data) {
        int largestMagnitudeIndex = 0;
        double largestMagnitude = data.get(0).getMagnitude();
        
        for (int i = 0; i < data.size(); i++) {
            double currentMagnitude = data.get(i).getMagnitude();
            if (currentMagnitude > largestMagnitude) {
                largestMagnitude = currentMagnitude;
                largestMagnitudeIndex = i;
            }
        }
        
        return largestMagnitudeIndex;
    }
    
    /*
       Returns an ArrayList of type QuakeEntry of the top howMany largest 
       magnitude earthquakes from quakeData.
       */
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData,
    int howMany) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> quakeDataClone = new ArrayList<QuakeEntry>(quakeData);
        
        for (int i = 0; i < howMany; i++) {
            
            if (quakeDataClone.size() == 0) break;
            
            int currentLargestIndex = indexOfLargest(quakeDataClone);
            answer.add(quakeDataClone.get(currentLargestIndex));
            quakeDataClone.remove(quakeDataClone.get(currentLargestIndex));
        }
        
        return answer;
    }
}
