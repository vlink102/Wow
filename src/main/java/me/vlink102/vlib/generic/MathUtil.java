package me.vlink102.generic;

import me.vlink102.generic.math.MatrixTransformation;
import me.vlink102.generic.math.Vector3D;

public class MathUtil {
    public static final double DEGREES_TO_RADIANS = 0.017453292519943295;
    public static final double RADIANS_TO_DEGREES = 57.29577951308232;

    public enum Fundamentals {
        ELEMENTARY_CHARGE(0.0000000000000000001602176634D, "e", 0d, "Elementary Charge"),
        G(9.7803267715D, "G", 0.000022D, "Newtonian Constant of Gravitation"),
        PLANCK_CONSTANT(0.000000000000000000000000000000000662607015D, "h", 0d, "Planck Constant"),
        c(299792458D, "c", 0d, "Speed of Light"),
        VACUUM_ELECTRIC_PERMITTIVITY(0.000000000008854187818814d, "ε_0", 0.00000000016d, "Vacuum Electric Permittivity"),
        VACUUM_MAGNETIC_PERMEABILITY(0.000001256637061272d, "μ_0", 0.00000000016d, "Vacuum Magnetic Permeability"),
        ELECTRON_MASS(0.0000000000000000000000000000009109383713928d, "m_e", 0.00000000031d, "Electron Mass"),
        FINE_STRUCTURE_CONSTANT(0.007297352564311d, "α", 0.00000000016d, "Fine-Structure Constant"),
        JOSEPHSON_CONSTANT(483597848400000D, "K_J", 0d, "Josephson Constant"),
        RYDBERG_CONSTANT(10973731.56815712D, "R_∞", 0.0000000000011d, "Rydberg Constant"),
        VON_KLITZING_CONSTANT(25812.80745D, "R_K", 0d, "von Klitzing Constant");

        private final Double value;
        private final String symbol;
        private final Double relativeUncertainty;
        private final String name;

        Fundamentals(Double value, String symbol, Double relativeUncertainty, String name) {
            this.value = value;
            this.symbol = symbol;
            this.relativeUncertainty = relativeUncertainty;
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public Double getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public Double getRelativeUncertainty() {
            return relativeUncertainty;
        }
    }

    public enum Constants {
        PI(Math.PI, 'π', "pi"),
        E(Math.E, 'e', "Euler's Number"),
        TAU(Math.TAU, ' ', "tau"),
        SQRT_2(1.4142135623730950488D, ' ', "Pythagoras' Constant"),
        SQRT_3(1.7320508075688772935D, ' ', "Theodorus' Constant"),
        PHI(1.6180339887498948482D, 'φ', "Golden Ratio"),
        FEIGENBAUM_DELTA(4.669201609102990671853203820466D, 'δ', "Feigenbaum Delta"),
        FEIGENBAUM_ALPHA(2.502907876D, 'α', "Feigenbaum Alpha"),
        ;


        private final Double value;
        private final char symbol;
        private final String name;

        Constants (Double value, char symbol, String name) {
            this.value = value;
            this.symbol = symbol;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public char getSymbol() {
            return symbol;
        }

        public Double getValue() {
            return value;
        }
    }

    public static int summation(int limit, int init, int scalar) {
        int sum = 0;
        for (int n = init; n <= limit; n++) {
            sum += scalar * n;
        }
        return sum;
    }

    public static int product(int limit, int init, int scalar) {
        int product = 1;
        for (int n = init; n <= limit; n++) {
            product *= scalar * n;
        }
        return product;
    }

    public static double sec(double theta) {
        return 1 / Math.cos(theta);
    }

    public static double csc(double theta) {
        return 1 / Math.sin(theta);
    }

    public static double cot(double theta) {
        return 1 / Math.tan(theta);
    }

    public static double sin2(double theta) {
        return Math.pow(Math.sin(theta), 2);
    }

    public static double cos2(double theta) {
        return Math.pow(Math.cos(theta), 2);
    }

    public static double tan2(double theta) {
        return Math.pow(Math.tan(theta), 2);
    }

    public Vector3D crossProduct(Vector3D a, Vector3D b) {
        return new Vector3D(
                (a.uY() * b.uZ()) - (a.uZ() * b.uY()),
                (a.uZ() * b.uX()) - (a.uX() * b.uZ()),
                (a.uX() * b.uY()) - (a.uY() * b.uX())
        );
    }


}
