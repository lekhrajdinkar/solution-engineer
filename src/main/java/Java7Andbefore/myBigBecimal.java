package Java7Andbefore;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static util.Print.p;

public class myBigBecimal {
    public static void main(String[] args) {
        // Creating BigDecimal instances
        BigDecimal bd1 = new BigDecimal("123.455");
        BigDecimal bd2 = new BigDecimal(123.455); // May have precision issues
        BigDecimal bd3 = BigDecimal.valueOf(123.455); // Recommended way for doubles
        p("Create",bd1,bd2,bd3);

        bd1 = BigDecimal.valueOf(123.456);
        bd2 = BigDecimal.valueOf(100.00);
        p("Operation", bd1.add(bd2), bd1.subtract(bd2), bd1.multiply(bd2));

        p("scale and Round",bd1.divide(bd2, 10, RoundingMode.DOWN));
        p("scale and Round",bd1.divide(bd2, 100, RoundingMode.DOWN));

        p("Sum: " + bd1.setScale(2, RoundingMode.HALF_UP));

    }
}
