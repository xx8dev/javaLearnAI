package learning.task010;

public class FilmCard {
    private String title;
    private int releaseYear;
    public FilmCard(String title, int releaseYear){
        this.title = title;
        this.releaseYear = releaseYear;

    }
    @Override
    public boolean equals(Object other){
        if (this == other){
            return true;
        }
        if(!(other instanceof  FilmCard)){
            return false;
        }
        FilmCard coordinate = (FilmCard) other;
        return title.equals(coordinate.title) && releaseYear == coordinate.releaseYear;
    }
    @Override
    public int hashCode(){
        return 31 * title.hashCode() + releaseYear;
    }
}
