package learning.task019;

import java.util.HashSet;

public class FilmWatchlist {
    private final HashSet<String> watch = new HashSet<>();
    public FilmWatchlist(){}
    public boolean addFilmCode(String filmCode){
        if(filmCode == null || filmCode.isEmpty()){
            throw new IllegalArgumentException();
        }
        if(watch.add(filmCode)){
            return true;
        };
        return false;

    }
    public boolean containsFilmCode(String filmCode){
        if(filmCode == null || filmCode.isEmpty()){
            throw new IllegalArgumentException();}
        if(watch.contains(filmCode)){
            return true;
        }
        return false;


    }
    public boolean removeFilmCode(String filmCode){
            if(filmCode == null || filmCode.isEmpty()){
                throw new IllegalArgumentException();}
            if(watch.remove(filmCode)){
                return true;
            }
            return false;
    }
    public int getFilmCount(){
        return watch.size();
    }
}
