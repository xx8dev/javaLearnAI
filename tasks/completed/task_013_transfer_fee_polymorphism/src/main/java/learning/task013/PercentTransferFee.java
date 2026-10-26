package learning.task013;

public class PercentTransferFee extends TransferFeeRule{
    @Override
    public int calculateFeeRubles(int transferAmountRubles){
        return (transferAmountRubles * 5) / 100;

    }
}
