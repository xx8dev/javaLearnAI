package learning.task009;

public class CinemaTicket {
    private String movieTitle;
    private int row;
    private int seat;
    private int priceRubles;
    public CinemaTicket(String movieTitle, int row, int seat, int priceRubles){
        this.movieTitle = movieTitle;
        this.row = row;
        this.seat = seat;
        this.priceRubles = priceRubles;
    }
    @Override

    public String toString(){
        return "Фильм: "+movieTitle+" | "+"Ряд: " +  row  + " | " + "Место: " + seat+" | " + "Цена: " + priceRubles+ " руб.";

    }
}
