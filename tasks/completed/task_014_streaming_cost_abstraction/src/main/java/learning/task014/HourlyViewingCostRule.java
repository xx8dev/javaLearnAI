package learning.task014;

public class HourlyViewingCostRule extends ViewingCostRule{
    @Override
    public int calculateCostRubles(int viewingHours){
        return viewingHours * 12;
    }
}
