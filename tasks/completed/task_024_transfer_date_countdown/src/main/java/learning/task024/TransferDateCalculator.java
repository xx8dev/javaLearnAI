package learning.task024;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

public class TransferDateCalculator {
    public static long daysUntilTransfer(LocalDate today, LocalDate transferDate){
        if(transferDate == null || today == null || transferDate.isBefore(today)){
            throw new IllegalArgumentException();}
        if(today == transferDate){
            return 0;}

        long pastDay = ChronoUnit.DAYS.between(today,transferDate);
        return pastDay;
    }

}
