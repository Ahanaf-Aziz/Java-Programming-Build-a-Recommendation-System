public class MinutesFilter implements Filter {
    private final int minMinutes;
    private final int maxMinutes;
    public MinutesFilter(int minMinutes, int maxMinutes) {
        if (minMinutes < 0 || maxMinutes < 0) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        if (minMinutes > maxMinutes) {
            throw new IllegalArgumentException("Minimum minutes cannot exceed maximum minutes");
        }
        this.minMinutes = minMinutes;
        this.maxMinutes = maxMinutes;
    }
    public MinutesFilter(int minMinutes) {
        this(minMinutes, Integer.MAX_VALUE); // Set max to Integer.MAX_VALUE
    }
    @Override
    public boolean satisfies(String id) {
        int movieDuration = MovieDatabase.getMinutes(id);
        return movieDuration >= minMinutes && movieDuration <= maxMinutes;
    }
}
// Ahanaf 
