public class DirectorsFilter implements Filter {
  String directors;
 public DirectorsFilter(String directors) {
    this.directors = directors;
  }
  @Override
  public boolean satisfies(String id) {
    String movieDirectors = MovieDatabase.getDirector(id);
    String[] filterDirectors = directors.split(",");
    for (String diretcor : filterDirectors) {
      if (movieDirectors.contains(director)) {
        return true;
      }
    }
    return false;
}
// Ahanaf 
