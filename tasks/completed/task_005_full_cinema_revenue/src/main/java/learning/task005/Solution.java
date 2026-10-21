package learning.task005;

public class Solution {
    public static int calculateFullSeatRevenue(int rowCount, int seatsPerRow, int ticketPriceRubles){
        if (seatsPerRow > 100 || rowCount > 100 ||  ticketPriceRubles > 1000 || seatsPerRow < 0 || rowCount < 0 ||  ticketPriceRubles < 0){
            return -1;}
        if (seatsPerRow <= 0 || rowCount <= 0 || ticketPriceRubles <= 0){return 0;}
        int balance = 0;
        int row = 0;
        while (row < rowCount){
            int seats = 0;
            while(seats < seatsPerRow){
                balance = balance + ticketPriceRubles;
                seats += 1;
            }
            row += 1;
        }
        return balance;


    }
}
