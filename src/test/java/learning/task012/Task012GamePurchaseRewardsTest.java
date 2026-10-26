package learning.task012;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task012GamePurchaseRewardsTest {
    @Test
    void zeroPurchaseReturnsZeroPoints() {
        assertEquals(0, GamePurchaseRewards.calculateRewardPoints(0));
    }

    @Test
    void amountBelowFirstStepReturnsZeroPoints() {
        assertEquals(0, GamePurchaseRewards.calculateRewardPoints(249));
    }

    @Test
    void exactFirstStepReturnsTenPoints() {
        assertEquals(10, GamePurchaseRewards.calculateRewardPoints(250));
    }

    @Test
    void remainderBelowSecondStepIsIgnored() {
        assertEquals(10, GamePurchaseRewards.calculateRewardPoints(499));
    }

    @Test
    void exactSecondStepReturnsTwentyPoints() {
        assertEquals(20, GamePurchaseRewards.calculateRewardPoints(500));
    }

    @Test
    void remainderBelowThirdStepIsIgnored() {
        assertEquals(20, GamePurchaseRewards.calculateRewardPoints(749));
    }

    @Test
    void exactThirdStepReturnsThirtyPoints() {
        assertEquals(30, GamePurchaseRewards.calculateRewardPoints(750));
    }

    @Test
    void largestAllowedAmountReturnsCorrectPoints() {
        assertEquals(4_000, GamePurchaseRewards.calculateRewardPoints(100_000));
    }

    @Test
    void negativeAmountReturnsZeroPoints() {
        assertEquals(0, GamePurchaseRewards.calculateRewardPoints(-1));
    }

    @Test
    void amountAboveMaximumReturnsZeroPoints() {
        assertEquals(0, GamePurchaseRewards.calculateRewardPoints(100_001));
    }

    @Test
    void rewardStepConstantsHaveRequiredValuesAndModifiers() throws ReflectiveOperationException {
        Field rublesPerStep = GamePurchaseRewards.class.getField("RUBLES_PER_REWARD_STEP");
        Field pointsPerStep = GamePurchaseRewards.class.getField("POINTS_PER_REWARD_STEP");

        assertEquals(int.class, rublesPerStep.getType());
        assertEquals(250, rublesPerStep.getInt(null));
        assertTrue(Modifier.isPublic(rublesPerStep.getModifiers()));
        assertTrue(Modifier.isStatic(rublesPerStep.getModifiers()));
        assertTrue(Modifier.isFinal(rublesPerStep.getModifiers()));

        assertEquals(int.class, pointsPerStep.getType());
        assertEquals(10, pointsPerStep.getInt(null));
        assertTrue(Modifier.isPublic(pointsPerStep.getModifiers()));
        assertTrue(Modifier.isStatic(pointsPerStep.getModifiers()));
        assertTrue(Modifier.isFinal(pointsPerStep.getModifiers()));
    }

    @Test
    void calculationMethodIsPublicAndStatic() throws ReflectiveOperationException {
        var method = GamePurchaseRewards.class.getMethod("calculateRewardPoints", int.class);

        assertEquals(int.class, method.getReturnType());
        assertTrue(Modifier.isPublic(method.getModifiers()));
        assertTrue(Modifier.isStatic(method.getModifiers()));
    }

    @Test
    void classDoesNotStorePurchaseStateInInstanceFields() {
        boolean hasInstanceField = Arrays.stream(GamePurchaseRewards.class.getDeclaredFields())
                .anyMatch(field -> !Modifier.isStatic(field.getModifiers()));

        assertFalse(hasInstanceField);
    }

    @Test
    void repeatedCallsReturnTheSameResult() {
        assertEquals(20, GamePurchaseRewards.calculateRewardPoints(740));
        assertEquals(20, GamePurchaseRewards.calculateRewardPoints(740));
    }

    @Test
    void calculationDoesNotPrintToConsole() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOutput));
            assertEquals(20, GamePurchaseRewards.calculateRewardPoints(740));
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
