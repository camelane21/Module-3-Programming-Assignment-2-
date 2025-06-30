import java.math.BigInteger;

public class Rational extends Number implements Comparable<Rational> {
    // Data fields for numerator and denominator
    private BigInteger numerator = BigInteger.ZERO;
    private BigInteger denominator = BigInteger.ONE;

    /** Construct a rational with default properties 0/1 */
    public Rational() {
        // nothing to do fields already set
    }

    /** Construct a rational with specified numerator and denominator */
    public Rational(BigInteger numerator, BigInteger denominator) {
        if (denominator.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }
        // Normalize sign: keep denominator > 0
        if (denominator.signum() < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
        BigInteger gcd = numerator.gcd(denominator);
        this.numerator = numerator.divide(gcd);
        this.denominator = denominator.divide(gcd);
    }

    /** Return numerator */
    public BigInteger getNumerator() {
        return numerator;
    }

    /** Return denominator */
    public BigInteger getDenominator() {
        return denominator;
    }

    /** Add a rational number to this rational */
    public Rational add(Rational second) {
        BigInteger n = numerator.multiply(second.denominator)
                          .add(second.numerator.multiply(denominator));
        BigInteger d = denominator.multiply(second.denominator);
        return new Rational(n, d);
    }

    /** Subtract a rational number from this rational */
    public Rational subtract(Rational second) {
        BigInteger n = numerator.multiply(second.denominator)
                          .subtract(second.numerator.multiply(denominator));
        BigInteger d = denominator.multiply(second.denominator);
        return new Rational(n, d);
    }

    /** Multiply a rational number to this rational */
    public Rational multiply(Rational second) {
        BigInteger n = numerator.multiply(second.numerator);
        BigInteger d = denominator.multiply(second.denominator);
        return new Rational(n, d);
    }

    /** Divide a rational number from this rational */
    public Rational divide(Rational second) {
        if (second.numerator.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Division by zero");
        }
        BigInteger n = numerator.multiply(second.denominator);
        BigInteger d = denominator.multiply(second.numerator);
        return new Rational(n, d);
    }

     @Override
    public String toString() {
        if (denominator.equals(BigInteger.ONE)) {
            return numerator.toString();
        } else {
            return numerator + "/" + denominator;
        }
    }

    /** Two rationals are equal if their difference has numerator 0 */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Rational)) return false;
        Rational r = (Rational) other;
        return this.subtract(r).numerator.equals(BigInteger.ZERO);
    }

    // Implement abstract methods from Number

    @Override
    public int intValue() {
        return doubleValue() > Integer.MAX_VALUE
             ? Integer.MAX_VALUE
             : (int) doubleValue();
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        return numerator.doubleValue() / denominator.doubleValue();
    }

    // Implement compareTo for Comparable<Rational>

    @Override
    public int compareTo(Rational o) {
        // Compare this − o via numerator of the difference
        BigInteger diffNum = this.subtract(o).numerator;
        return diffNum.signum();  // −1, 0, or 1
    }
}