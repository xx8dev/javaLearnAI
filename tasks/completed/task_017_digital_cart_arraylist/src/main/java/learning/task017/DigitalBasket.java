package learning.task017;

import java.util.ArrayList;

public class DigitalBasket {
    private final ArrayList<Integer> basket = new ArrayList<>();
    public DigitalBasket(){

    }
    public void addPrice(int priceRubles){
        if (priceRubles < 0 || priceRubles > 1_000_000){
            throw new IllegalArgumentException();
        }
        basket.add(priceRubles);
    }
    public int getPriceCount(){
        return basket.size();
    }
    public int getPriceAt(int index){
        return basket.get(index);
    }
    public int removePriceAt(int index) {
        int removedPrice = basket.remove(index);
        return removedPrice;
    }

    public long getTotalRubles(){
        long total = 0;
        int index = 0;
        for(index = 0;index < basket.size();index++){
            total += basket.get(index);
        }
        return total;
    }

}
