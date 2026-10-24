package learning.task008;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task008GameWalletTest {

    @Test
    void startsWithTheInitialBalance() {
        GameWallet wallet = new GameWallet(5_000);

        assertEquals(5_000, wallet.getBalanceRubles());
    }

    @Test
    void acceptsZeroInitialBalance() {
        GameWallet wallet = new GameWallet(0);

        assertEquals(0, wallet.getBalanceRubles());
    }

    @Test
    void depositsMoneyAndUpdatesTheBalance() {
        GameWallet wallet = new GameWallet(5_000);

        assertTrue(wallet.deposit(1_000));
        assertEquals(6_000, wallet.getBalanceRubles());
    }

    @Test
    void spendsMoneyAndUpdatesTheBalance() {
        GameWallet wallet = new GameWallet(5_000);

        assertTrue(wallet.spend(1_200));
        assertEquals(3_800, wallet.getBalanceRubles());
    }

    @Test
    void canSpendTheExactBalance() {
        GameWallet wallet = new GameWallet(5_000);

        assertTrue(wallet.spend(5_000));
        assertEquals(0, wallet.getBalanceRubles());
    }

    @Test
    void allowsBalanceToReachTheMaximum() {
        GameWallet wallet = new GameWallet(999_000);

        assertTrue(wallet.deposit(1_000));
        assertEquals(1_000_000, wallet.getBalanceRubles());
    }

    @Test
    void rejectsDepositThatWouldExceedMaximumAndKeepsBalance() {
        GameWallet wallet = new GameWallet(999_000);

        assertFalse(wallet.deposit(1_001));
        assertEquals(999_000, wallet.getBalanceRubles());
    }

    @Test
    void rejectsZeroDepositAndKeepsBalance() {
        GameWallet wallet = new GameWallet(5_000);

        assertFalse(wallet.deposit(0));
        assertEquals(5_000, wallet.getBalanceRubles());
    }

    @Test
    void rejectsNegativeDepositAndKeepsBalance() {
        GameWallet wallet = new GameWallet(5_000);

        assertFalse(wallet.deposit(-1));
        assertEquals(5_000, wallet.getBalanceRubles());
    }

    @Test
    void rejectsZeroSpendAndKeepsBalance() {
        GameWallet wallet = new GameWallet(5_000);

        assertFalse(wallet.spend(0));
        assertEquals(5_000, wallet.getBalanceRubles());
    }

    @Test
    void rejectsNegativeSpendAndKeepsBalance() {
        GameWallet wallet = new GameWallet(5_000);

        assertFalse(wallet.spend(-1));
        assertEquals(5_000, wallet.getBalanceRubles());
    }

    @Test
    void rejectsSpendingMoreThanTheBalanceAndKeepsBalance() {
        GameWallet wallet = new GameWallet(5_000);

        assertFalse(wallet.spend(5_001));
        assertEquals(5_000, wallet.getBalanceRubles());
    }

    @Test
    void walletsKeepIndependentBalances() {
        GameWallet firstWallet = new GameWallet(1_000);
        GameWallet secondWallet = new GameWallet(8_000);

        assertTrue(firstWallet.deposit(500));
        assertTrue(secondWallet.spend(2_000));

        assertEquals(1_500, firstWallet.getBalanceRubles());
        assertEquals(6_000, secondWallet.getBalanceRubles());
    }

    @Test
    void keepsTheBalanceInPrivateInstanceState() {
        boolean hasPrivateInstanceIntegerField = false;

        for (Field field : GameWallet.class.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                assertTrue(Modifier.isPrivate(modifiers), "Instance fields must be private");
                if (field.getType() == int.class) {
                    hasPrivateInstanceIntegerField = true;
                }
            }
        }

        assertTrue(hasPrivateInstanceIntegerField, "The wallet must store its balance in an instance field");
    }

    @Test
    void exposesWalletOperationsAsInstanceMethods() throws NoSuchMethodException {
        Method getBalance = GameWallet.class.getMethod("getBalanceRubles");
        Method deposit = GameWallet.class.getMethod("deposit", int.class);
        Method spend = GameWallet.class.getMethod("spend", int.class);

        assertFalse(Modifier.isStatic(getBalance.getModifiers()));
        assertFalse(Modifier.isStatic(deposit.getModifiers()));
        assertFalse(Modifier.isStatic(spend.getModifiers()));
    }

    @Test
    void doesNotReadFromOrWriteToTheConsole() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try (PrintStream output = new PrintStream(capturedOutput, true, StandardCharsets.UTF_8)) {
            System.setIn(new InputStream() {
                @Override
                public int read() {
                    throw new AssertionError("The wallet must not read from the console");
                }
            });
            System.setOut(output);

            GameWallet wallet = new GameWallet(5_000);
            assertTrue(wallet.deposit(1_000));
            assertEquals(6_000, wallet.getBalanceRubles());
            assertTrue(wallet.spend(500));
            assertEquals(5_500, wallet.getBalanceRubles());
            assertFalse(wallet.spend(6_000));
            assertEquals(5_500, wallet.getBalanceRubles());
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
