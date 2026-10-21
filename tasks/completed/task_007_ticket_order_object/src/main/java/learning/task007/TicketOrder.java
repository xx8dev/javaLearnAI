package learning.task007;

public class TicketOrder {
    private int ticket;
    private int price;
    public TicketOrder(int ticketCount, int ticketPriceRubles){
        ticket = ticketCount;
        price = ticketPriceRubles;}
    public int totalCost(){
         return ticket * price;

    }
    public boolean fitsBudget(int budgetRubles){
        if(budgetRubles < totalCost()){
            return false;
        }
        else{
            return true;
        }
    }


}
