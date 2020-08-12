package com.facebook.rebound;

public class BouncyConversion {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final double mBounciness;
    private final double mBouncyFriction;
    private final double mBouncyTension;
    private final double mSpeed;

    public BouncyConversion(double d2, double d3) {
        this.mSpeed = d2;
        this.mBounciness = d3;
        double project_normal = project_normal(normalize(d3 / 1.7d, 0.0d, 20.0d), 0.0d, 0.8d);
        this.mBouncyTension = project_normal(normalize(d2 / 1.7d, 0.0d, 20.0d), 0.5d, 200.0d);
        this.mBouncyFriction = quadratic_out_interpolation(project_normal, b3_nobounce(this.mBouncyTension), 0.01d);
    }

    private double b3_friction1(double d2) {
        return ((Math.pow(d2, 3.0d) * 7.0E-4d) - (Math.pow(d2, 2.0d) * 0.031d)) + (d2 * 0.64d) + 1.28d;
    }

    private double b3_friction2(double d2) {
        return ((Math.pow(d2, 3.0d) * 4.4E-5d) - (Math.pow(d2, 2.0d) * 0.006d)) + (d2 * 0.36d) + 2.0d;
    }

    private double b3_friction3(double d2) {
        return ((Math.pow(d2, 3.0d) * 4.5E-7d) - (Math.pow(d2, 2.0d) * 3.32E-4d)) + (d2 * 0.1078d) + 5.84d;
    }

    private double b3_nobounce(double d2) {
        if (d2 <= 18.0d) {
            return b3_friction1(d2);
        }
        if (d2 > 18.0d && d2 <= 44.0d) {
            return b3_friction2(d2);
        }
        if (d2 > 44.0d) {
            return b3_friction3(d2);
        }
        return 0.0d;
    }

    private double linear_interpolation(double d2, double d3, double d4) {
        return (d4 * d2) + ((1.0d - d2) * d3);
    }

    private double normalize(double d2, double d3, double d4) {
        return (d2 - d3) / (d4 - d3);
    }

    private double project_normal(double d2, double d3, double d4) {
        return d3 + (d2 * (d4 - d3));
    }

    private double quadratic_out_interpolation(double d2, double d3, double d4) {
        return linear_interpolation((2.0d * d2) - (d2 * d2), d3, d4);
    }

    public double getBounciness() {
        return this.mBounciness;
    }

    public double getBouncyFriction() {
        return this.mBouncyFriction;
    }

    public double getBouncyTension() {
        return this.mBouncyTension;
    }

    public double getSpeed() {
        return this.mSpeed;
    }
}
