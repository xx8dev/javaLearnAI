package learning.task011;

public class CinemaScreeningPrice {
    private ProjectionFormat format;
    private int basePriceRubles;
    public CinemaScreeningPrice(ProjectionFormat format, int basePriceRubles){
        this.format = format;
        this.basePriceRubles = basePriceRubles;


    }
    public int getTotalPriceRubles(){
        if (format == ProjectionFormat.THREE_D){
            return basePriceRubles + 120;
        }
        if (format == ProjectionFormat.IMAX){
            return basePriceRubles + 250;
        }
        return basePriceRubles;
    }
}
