package learning.task004;

public class Solution {
    public static int totalTicketRevenue(int[] sessionRevenueRubles){
         int total = 0;
         int index = 0;
         while (index < sessionRevenueRubles.length){
             if(sessionRevenueRubles[index] < 0) {
                 return -1;
             }
             total = total + sessionRevenueRubles[index];
             index += 1;
         }

         while(index < sessionRevenueRubles.length){
             total = total + sessionRevenueRubles[index];
             index += 1;
         }
         return total;
    }
}
