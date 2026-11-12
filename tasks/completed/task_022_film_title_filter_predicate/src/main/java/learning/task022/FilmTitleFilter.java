package learning.task022;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilmTitleFilter {
    public static List<String> filter(List<String> titles, Predicate<String> condition){
        List<String> filters = new ArrayList<>();
        if(titles == null){
            throw new IllegalArgumentException();
        }
        if(condition == null){
            throw new IllegalArgumentException();
        }
        int index = 0;
        while(index < titles.size()){
            String titl = titles.get(index);
            if(titl == null){
                throw new IllegalArgumentException();
            }
            if(condition.test(titl)){
                filters.add(titl);
            }
        index++;
        }
        return filters;
    }
}
