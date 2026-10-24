package learning.task008;

public class GameWallet {
    private int balance;
    public GameWallet(int initialBalanceRubles){
        balance = initialBalanceRubles;
    }
    public int getBalanceRubles(){
        return balance;
    }
    public boolean deposit(int amountRubles){
        if (balance < 0 || balance + amountRubles > 1000000 || amountRubles < 0 || amountRubles == 0 || balance + amountRubles <0 ) {
            return false;
        }

        balance = balance + amountRubles;
        return true;





    }

    public boolean spend(int amountRubles){
        if (balance < amountRubles || amountRubles < 0 || amountRubles == 0){
            return false;
        }
        balance = balance - amountRubles;
        return true;


    }



}
