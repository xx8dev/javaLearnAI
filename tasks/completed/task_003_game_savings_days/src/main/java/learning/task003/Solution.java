package learning.task003;

public class Solution {
    public static int daysUntilAffordable(int balanceRubles, int priceRubles, int dailyRewardRubles) {
        if (balanceRubles >= priceRubles){
            return 0;
        }
        if (dailyRewardRubles <= 0) {
            return -1;
        }
        int counter = 0;
        while (balanceRubles < priceRubles){
            balanceRubles = balanceRubles + dailyRewardRubles;
            counter += 1;

        }
        return counter;



    }

}
