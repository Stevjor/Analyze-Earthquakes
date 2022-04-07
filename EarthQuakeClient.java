import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (qe.getMagnitude() > magMin) {
                answer.add(qe);
            }
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry qe : quakeData) {
            
            double currentDistance = qe.getLocation().distanceTo(from);
            
            if (currentDistance / 1000 < distMax) answer.add(qe);
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,
    double minDepth, 
    double maxDepth) {
        ArrayList<QuakeEntry> result = new ArrayList<QuakeEntry>();
        
        for (QuakeEntry qe : quakeData) {
            if (qe.getDepth() >  minDepth && qe.getDepth() < maxDepth) result.add(qe);
        }
        
        return result;
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData,
    String where, String phrase) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        
        for (QuakeEntry qe : quakeData) {
            String title = qe.getInfo();
            
            if (where == "start") {
                if (title.startsWith(phrase)) answer.add(qe);
            }
            
            else if (where == "any") {
                if (title.indexOf(phrase) != -1) answer.add(qe);
            }
            
            else if (where == "end") {
                if (title.endsWith(phrase)) answer.add(qe);
            }
        }
        
        return answer;
    }
    
    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        
        ArrayList<QuakeEntry> result = filterByMagnitude(list, 5.0);
        for (QuakeEntry qe : result) {
            System.out.println(qe);
        }
        System.out.println("Found " + result.size() + " quakes that match that criteria.");
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);
        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);
        ArrayList<QuakeEntry> distancesFiltered = filterByDistanceFrom(list, 1000.00, city);
        for (QuakeEntry qe : distancesFiltered) {
            System.out.println(qe.getLocation().distanceTo(city) / 1000 +
            " " + qe.getInfo());
        }
        System.out.println("Found " + distancesFiltered.size() + 
        " quakes that match that criteria.");

        // TODO
    }

    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("Read data for " + list.size() + " quakes.");
        
        double minimumDepth = -10000.0;
        double maximumDepth = -5000.0;
        System.out.println("Find quakes between " + minimumDepth + 
        " and " + maximumDepth + ".");
        
        ArrayList<QuakeEntry> depthsFiltered = filterByDepth(list, 
        minimumDepth, maximumDepth);
        
        for (QuakeEntry qe : depthsFiltered) {
            System.out.println(qe);
        }
        
        System.out.println("Found " + depthsFiltered.size() + 
        " quakes that match that criteria.");
    }
    
    public void quakesByPhrase() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("Read data for " + list.size() + " quakes.");
        
        String whereToSearch = "start";
        String phraseToSearch = "Explosion";
        
        ArrayList<QuakeEntry> phraseFiltered = filterByPhrase(list, 
        whereToSearch, phraseToSearch);
        
        for (QuakeEntry qe : phraseFiltered) {
            System.out.println(qe);
        }
        
        System.out.println("Found " + phraseFiltered.size() + 
        " quakes that match \"" + phraseToSearch + "\" at (the) " + 
        whereToSearch + ".");
    }
    
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
