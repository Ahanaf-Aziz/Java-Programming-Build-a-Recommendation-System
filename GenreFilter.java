public class GenreFilter implements Filter {
    private final String genre;
    public GenreFilter(String genre) {
        if (genre == null || genre.isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be null or empty");
        }
        this.genre = genre.toLowerCase();
    }
    @Override
    public boolean satisfies(String id) {
        String movieGenres = MovieDatabase.getGenres(id);
        return movieGenres != null && movieGenres.toLowerCase().contains(genre);
    }
}
// Ahanaf 
