package learning.task025;

public class TransferFeeCalculator {
    public static int calculateFeeRubles(int transferAmountRubles, int feePercent){
        if(transferAmountRubles < 0 || transferAmountRubles > 1_000_000 || feePercent < 0 || feePercent > 100){
            throw new IllegalArgumentException();
        }
        int fee = transferAmountRubles * feePercent / 100;
        return fee;


    }
}
