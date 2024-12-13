import java.util.ArrayList;
import java.util.HashMap;
public class EfficientRater implements Rater {
    private final String myID;
    private final HashMap<String, Rating> myRatings;
    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<>();
    }
    public void addRating(String movieID, double rating) {
        if (rating >= 0 && rating <= 5) {
            myRatings.put(movieID, new Rating(movieID, rating));
        } else {
            System.out.println("Invalid rating value. Please enter a value between 0 and 5.");
        }
    }
    public boolean hasRating(String movieID) {
        return myRatings.containsKey(movieID);
    }
    public String getID() {
        return myID;
    }
    public double getRating(String movieID) {
        Rating rating = myRatings.get(movieID);
        if (rating != null) {
            return rating.getValue();
        } else {
            return -1;
        }
    }
    public int numRatings() {
        return myRatings.size();
    }
    public ArrayList<String> getItemsRated() {
        return new ArrayList<>(myRatings.keySet());
    }
    public HashMap<String, Rating> getMyRatings() {
        return new HashMap<>(myRatings);
    }
}
//Ahanaf 
