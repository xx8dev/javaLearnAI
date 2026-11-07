package learning.task016;
import java.util.Objects;
public class CatalogSearch {
    public static <T> int findFirstIndex(T[] items, T target) {
        if (items == null) {
            throw new IllegalArgumentException();
        }
        int index = 0;
        for (index = 0; index < items.length; index++) {
            if (Objects.equals(items[index], target)) {
                return index;
            }


        }
        return -1;
    }
}


