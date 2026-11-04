package learning.task015;

public class DigitalPurchase <T> {
    private final T item;
    private final int priceRubles;
    public DigitalPurchase(T item, int priceRubles){
        this.item = item;
        this.priceRubles = priceRubles;
        if (item == null){
            throw new IllegalArgumentException();

        }
        if(priceRubles > 1_000_000 || priceRubles < 0){
            throw new IllegalArgumentException();}
    }


    public T getItem(){
        return item;
    }

    public int getPriceRubles(){
        return priceRubles;

    }
}
