package com.facebook.rebound;

public class SpringConfig {
    public static SpringConfig defaultConfig = fromOrigamiTensionAndFriction(40.0d, 7.0d);
    public double friction;
    public double tension;

    public SpringConfig(double d2, double d3) {
        this.tension = d2;
        this.friction = d3;
    }

    public static SpringConfig fromBouncinessAndSpeed(double d2, double d3) {
        BouncyConversion bouncyConversion = new BouncyConversion(d3, d2);
        return fromOrigamiTensionAndFriction(bouncyConversion.getBouncyTension(), bouncyConversion.getBouncyFriction());
    }

    public static SpringConfig fromOrigamiTensionAndFriction(double d2, double d3) {
        return new SpringConfig(OrigamiValueConverter.tensionFromOrigamiValue(d2), OrigamiValueConverter.frictionFromOrigamiValue(d3));
    }
}
