import java.util.ArrayList;
import java.util.HashMap;
import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;
public class FourthRatings {
    private Double getAverageByID(String movieID, Integer minimalRaters) {
        RaterDatabase.initialize("ratings.csv");
        ArrayList<Rater> myRaters = RaterDatabase.getRaters();
        long numOfRatings = myRaters.stream().filter(rater -> rater.hasRating(movieID)).count();
        if (numOfRatings >= minimalRaters) {
            return myRaters.stream()
                .filter(rater -> rater.hasRating(movieID))
                .mapToDouble(rater -> rater.getRating(movieID))
                .average()
                .orElse(0.0);
        }
        return 0.0;
    }
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        RaterDatabase.initialize("ratings.csv");
        ArrayList<Rating> list = new ArrayList<>();
        ArrayList<String> allMoviesIDs = MovieDatabase.filterBy(new TrueFilter());
        for (String movieID : allMoviesIDs) {
            double averageRating = getAverageByID(movieID, minimalRaters);
            if (averageRating != 0.0) {
                list.add(new Rating(movieID, averageRating));
            }
        }
        return list;
    }
    public ArrayList<Rating> getAverageRatingsByFilter(Integer minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> ratingsList = new ArrayList<>();
        ArrayList<String> allMoviesIDs = MovieDatabase.filterBy(filterCriteria);
        for (String movie_id : allMoviesIDs) {
            double averageRating = getAverageByID(movie_id, minimalRaters);
            if (averageRating != 0.0) {
                ratingsList.add(new Rating(movie_id, averageRating));
            }
        }
        sort(ratingsList);
        return ratingsList;
    }
    public double dotProduct(Rater meRater, Rater otherRater) {
        HashMap<String, Rating> myRatings = meRater.getMyRatings();
        double result = 0.0;
        for (String id : myRatings.keySet()) {
            if (otherRater.hasRating(id)) {
                double scale = meRater.getRating(id) - 5;
                result += scale * (otherRater.getRating(id) - 5);
            }
        }
        return result;
    }
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> list = new ArrayList<>();
        Rater me = RaterDatabase.getRater(id);
        for (Rater rater : RaterDatabase.getRaters()) {
            if (!me.getID().equals(rater.getID())) {
                double dotProduct = dotProduct(me, rater);
                if (dotProduct > 0) {
                    list.add(new Rating(rater.getID(), dotProduct));
                }
            }
        }
        list.sort(reverseOrder());
        return list;
    }
    public ArrayList<Rating> getSimilarRatings(String id, Integer numSimilarRaters, Integer minimalRaters) {
        ArrayList<Rating> ratingMovies = new ArrayList<>();
        ArrayList<Rating> ratingRaters = getSimilarities(id);
        for (String movie_id : MovieDatabase.filterBy(new TrueFilter())) {
            if (hasMinRaters(movie_id, minimalRaters, numSimilarRaters, ratingRaters)) {
                double sum = 0.0;
                double num = 0.0;
                for (int i = 0; i < numSimilarRaters; i++) {
                    Rater rater = RaterDatabase.getRater(ratingRaters.get(i).getItem());
                    if (rater.hasRating(movie_id)) {
                        sum += ratingRaters.get(i).getValue() * rater.getRating(movie_id);
                        num += 1.0;
                    }
                }
                if (num > 0) {
                    double average = sum / num;
                    ratingMovies.add(new Rating(movie_id, average));
                }
            }
        }
        ratingMovies.sort(reverseOrder());
        return ratingMovies;
    }
    private boolean hasMinRaters(String movie_id, Integer minimalRaters, Integer numSimilarRaters, ArrayList<Rating> ratingRaters) {
        int numOfRaters = 0;
        for (int i = 0; i < numSimilarRaters; i++) {
            Rater rater = RaterDatabase.getRater(ratingRaters.get(i).getItem());
            if (rater.hasRating(movie_id)) {
                numOfRaters++;
            }
        }
        return numOfRaters >= minimalRaters;
    }
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, Integer numSimilarRaters, Integer minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> ratingMovies = new ArrayList<>();
        ArrayList<Rating> ratingRaters = getSimilarities(id);
        for (String movie_id : MovieDatabase.filterBy(filterCriteria)) {
            if (hasMinRaters(movie_id, minimalRaters, numSimilarRaters, ratingRaters)) {
                double sum = 0.0;
                double num = 0.0;
                for (int i = 0; i < numSimilarRaters; i++) {
                    Rater rater = RaterDatabase.getRater(ratingRaters.get(i).getItem());
                    if (rater.hasRating(movie_id)) {
                        sum += ratingRaters.get(i).getValue() * rater.getRating(movie_id);
                        num += 1.0;
                    }
                }
                if (num > 0) {
                    double average = sum / num;
                    ratingMovies.add(new Rating(movie_id, average));
                }
            }
        }
        ratingMovies.sort(reverseOrder());
        return ratingMovies;
    }
}
// Ahanaf 
