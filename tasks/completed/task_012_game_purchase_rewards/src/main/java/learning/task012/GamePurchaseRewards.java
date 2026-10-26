package learning.task012;

public class GamePurchaseRewards {
    public static final int RUBLES_PER_REWARD_STEP = 250;
    public static final int POINTS_PER_REWARD_STEP = 10;
    public static int calculateRewardPoints(int purchaseAmountRubles){
        if(purchaseAmountRubles < 0 || purchaseAmountRubles > 100000){
            return 0;}
        int balance = purchaseAmountRubles / RUBLES_PER_REWARD_STEP;
        int Point = balance * POINTS_PER_REWARD_STEP;
        return Point;
    }
}
