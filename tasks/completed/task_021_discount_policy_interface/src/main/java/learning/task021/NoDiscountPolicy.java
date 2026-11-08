package learning.task021;

public class NoDiscountPolicy implements  PurchaseDiscountPolicy{
    @Override
    public int calculateDiscountRubles(int priceRubles){
        if(priceRubles > 1_000_000 || priceRubles < 0){
            throw new IllegalArgumentException();
        }
        return 0;
    }
}
