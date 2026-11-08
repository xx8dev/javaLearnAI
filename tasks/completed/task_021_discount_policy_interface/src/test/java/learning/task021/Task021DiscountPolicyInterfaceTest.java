package learning.task021;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task021DiscountPolicyInterfaceTest {
    @Test
    void policyIsPublicInterfaceWithRequiredMethod() throws NoSuchMethodException {
        Method calculate = PurchaseDiscountPolicy.class.getMethod("calculateDiscountRubles", int.class);

        assertTrue(PurchaseDiscountPolicy.class.isInterface());
        assertTrue(Modifier.isPublic(PurchaseDiscountPolicy.class.getModifiers()));
        assertEquals(int.class, calculate.getReturnType());
        assertTrue(Modifier.isPublic(calculate.getModifiers()));
        assertTrue(Modifier.isAbstract(calculate.getModifiers()));
    }

    @Test
    void bothPoliciesArePublicConcreteImplementationsWithPublicConstructors() throws NoSuchMethodException {
        assertPolicyClass(NoDiscountPolicy.class);
        assertPolicyClass(TenPercentDiscountPolicy.class);
    }

    @Test
    void noDiscountPolicyAlwaysReturnsZeroForValidPrice() {
        PurchaseDiscountPolicy policy = new NoDiscountPolicy();

        assertEquals(0, policy.calculateDiscountRubles(0));
        assertEquals(0, policy.calculateDiscountRubles(1_250));
        assertEquals(0, policy.calculateDiscountRubles(1_000_000));
    }

    @Test
    void tenPercentPolicyCalculatesDiscountForWholeRubles() {
        PurchaseDiscountPolicy policy = new TenPercentDiscountPolicy();

        assertEquals(0, policy.calculateDiscountRubles(0));
        assertEquals(125, policy.calculateDiscountRubles(1_250));
        assertEquals(100_000, policy.calculateDiscountRubles(1_000_000));
    }

    @Test
    void tenPercentPolicyDropsFractionalRuble() {
        PurchaseDiscountPolicy policy = new TenPercentDiscountPolicy();

        assertEquals(9, policy.calculateDiscountRubles(99));
    }

    @Test
    void callerCanChooseBetweenPoliciesThroughInterfaceType() {
        PurchaseDiscountPolicy[] policies = {
                new NoDiscountPolicy(),
                new TenPercentDiscountPolicy()
        };

        assertEquals(0, policies[0].calculateDiscountRubles(500));
        assertEquals(50, policies[1].calculateDiscountRubles(500));
    }

    @Test
    void noDiscountPolicyRejectsNegativePrice() {
        PurchaseDiscountPolicy policy = new NoDiscountPolicy();

        assertThrows(IllegalArgumentException.class, () -> policy.calculateDiscountRubles(-1));
    }

    @Test
    void noDiscountPolicyRejectsPriceAboveMaximum() {
        PurchaseDiscountPolicy policy = new NoDiscountPolicy();

        assertThrows(IllegalArgumentException.class, () -> policy.calculateDiscountRubles(1_000_001));
    }

    @Test
    void tenPercentPolicyRejectsNegativePrice() {
        PurchaseDiscountPolicy policy = new TenPercentDiscountPolicy();

        assertThrows(IllegalArgumentException.class, () -> policy.calculateDiscountRubles(-1));
    }

    @Test
    void tenPercentPolicyRejectsPriceAboveMaximum() {
        PurchaseDiscountPolicy policy = new TenPercentDiscountPolicy();

        assertThrows(IllegalArgumentException.class, () -> policy.calculateDiscountRubles(1_000_001));
    }

    private void assertPolicyClass(Class<?> policyClass) throws NoSuchMethodException {
        Constructor<?> constructor = policyClass.getConstructor();
        Method calculate = policyClass.getMethod("calculateDiscountRubles", int.class);

        assertTrue(Modifier.isPublic(policyClass.getModifiers()));
        assertTrue(Modifier.isPublic(constructor.getModifiers()));
        assertTrue(PurchaseDiscountPolicy.class.isAssignableFrom(policyClass));
        assertEquals(int.class, calculate.getReturnType());
        assertTrue(Modifier.isPublic(calculate.getModifiers()));
    }
}
