package learning.task025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransferFeeCalculatorTest {
    @Test
    void commissionCalculation(){
        assertEquals(1250, TransferFeeCalculator.calculateFeeRubles(12500, 10));
    }
    @Test
    void fractionalPart(){
        assertEquals(9,TransferFeeCalculator.calculateFeeRubles(99,10));

    }
    @Test
    void values(){
        assertThrows(IllegalArgumentException.class,() -> TransferFeeCalculator.calculateFeeRubles(-1,10));
    }
    @Test
    void values1(){
        assertThrows(IllegalArgumentException.class,() -> TransferFeeCalculator.calculateFeeRubles(1_000_001,10));
    }
    @Test
    void values2(){
        assertThrows(IllegalArgumentException.class,() -> TransferFeeCalculator.calculateFeeRubles(14,101));

    }
    @Test
    void values3(){
        assertThrows(IllegalArgumentException.class,() -> TransferFeeCalculator.calculateFeeRubles(15,-1));
    }
    @Test
    void values4(){
        assertEquals(500,TransferFeeCalculator.calculateFeeRubles(500,100 ));
    }
    @Test
    void values5(){
        assertEquals(0,TransferFeeCalculator.calculateFeeRubles(100,0));
    }
    @Test
    void values6(){
        assertEquals(0,TransferFeeCalculator.calculateFeeRubles(0,10));
    }
    @Test
    void values7(){
        assertEquals(100_000,TransferFeeCalculator.calculateFeeRubles(1_000_000,10));
    }
}
