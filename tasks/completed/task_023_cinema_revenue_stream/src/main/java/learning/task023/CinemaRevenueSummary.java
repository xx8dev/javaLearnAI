package learning.task023;
import java.util.List;
public class CinemaRevenueSummary {
    public static long sumTicketsAtLeast(List<Integer> ticketPrices, int minimumPrice){

        if(ticketPrices == null || ticketPrices.stream().anyMatch(ticketPrice -> ticketPrice == null || ticketPrice < 0) || minimumPrice <0){
            throw new IllegalArgumentException();
        }
        if(ticketPrices.isEmpty()){
            return 0;}

        long total = ticketPrices.stream()
                .filter(minimumPric -> minimumPric >= minimumPrice)
                .mapToLong(minimumPric -> minimumPric.longValue())
                .sum();

        return total;
    }

}
