package learning.task006;

public class Solution {
    public static int calculateTotalRevenue(int[] ticketsSoldBySession, int ticketPriceRubles) {
        if (ticketPriceRubles < 0){return -1;}
        if(ticketsSoldBySession == null){return -1;}
        if(ticketPriceRubles > 10000){return -1;}
        int index = 0;
        while (index < ticketsSoldBySession.length) {
            if (ticketsSoldBySession.length > 100 ||  ticketsSoldBySession[index] < 0 || ticketsSoldBySession[index] > 1000){return -1;}
            index += 1;}
        int count = 0;
        int index1 = 0;
        while (index1 < ticketsSoldBySession.length){
            count = count + sesion(ticketsSoldBySession[index1],ticketPriceRubles);
            index1 += 1;}
    return count;
    }

    private static int sesion(int sesi, int ticketPriceRubles) {
        return sesi * ticketPriceRubles;}
}