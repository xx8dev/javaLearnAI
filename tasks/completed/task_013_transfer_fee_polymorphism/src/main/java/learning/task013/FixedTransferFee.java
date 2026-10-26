package learning.task013;

public class FixedTransferFee extends TransferFeeRule{
    @Override
    public int calculateFeeRubles(int transferAmountRubles){
        return  30;
    }
}
