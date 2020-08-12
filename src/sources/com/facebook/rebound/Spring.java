package com.facebook.rebound;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

public class Spring {
    private static int ID = 0;
    private static final double MAX_DELTA_TIME_SEC = 0.064d;
    private static final double SOLVER_TIMESTEP_SEC = 0.001d;
    private final PhysicsState mCurrentState = new PhysicsState();
    private double mDisplacementFromRestThreshold = 0.005d;
    private double mEndValue;
    private final String mId;
    private CopyOnWriteArraySet<SpringListener> mListeners = new CopyOnWriteArraySet<>();
    private boolean mOvershootClampingEnabled;
    private final PhysicsState mPreviousState = new PhysicsState();
    private double mRestSpeedThreshold = 0.005d;
    private SpringConfig mSpringConfig;
    private final BaseSpringSystem mSpringSystem;
    private double mStartValue;
    private final PhysicsState mTempState = new PhysicsState();
    private double mTimeAccumulator = 0.0d;
    private boolean mWasAtRest = true;

    private static class PhysicsState {
        double position;
        double velocity;

        private PhysicsState() {
        }
    }

    Spring(BaseSpringSystem baseSpringSystem) {
        if (baseSpringSystem != null) {
            this.mSpringSystem = baseSpringSystem;
            StringBuilder sb = new StringBuilder();
            sb.append("spring:");
            int i = ID;
            ID = i + 1;
            sb.append(i);
            this.mId = sb.toString();
            setSpringConfig(SpringConfig.defaultConfig);
            return;
        }
        throw new IllegalArgumentException("Spring cannot be created outside of a BaseSpringSystem");
    }

    private double getDisplacementDistanceForState(PhysicsState physicsState) {
        return Math.abs(this.mEndValue - physicsState.position);
    }

    private void interpolate(double d2) {
        PhysicsState physicsState = this.mCurrentState;
        PhysicsState physicsState2 = this.mPreviousState;
        double d3 = 1.0d - d2;
        physicsState.position = (physicsState.position * d2) + (physicsState2.position * d3);
        physicsState.velocity = (physicsState.velocity * d2) + (physicsState2.velocity * d3);
    }

    public Spring addListener(SpringListener springListener) {
        if (springListener != null) {
            this.mListeners.add(springListener);
            return this;
        }
        throw new IllegalArgumentException("newListener is required");
    }

    /* access modifiers changed from: package-private */
    public void advance(double d2) {
        double d3;
        boolean z;
        boolean isAtRest = isAtRest();
        if (!isAtRest || !this.mWasAtRest) {
            double d4 = MAX_DELTA_TIME_SEC;
            if (d2 <= MAX_DELTA_TIME_SEC) {
                d4 = d2;
            }
            this.mTimeAccumulator += d4;
            SpringConfig springConfig = this.mSpringConfig;
            double d5 = springConfig.tension;
            double d6 = springConfig.friction;
            PhysicsState physicsState = this.mCurrentState;
            double d7 = physicsState.position;
            double d8 = physicsState.velocity;
            PhysicsState physicsState2 = this.mTempState;
            double d9 = physicsState2.position;
            double d10 = physicsState2.velocity;
            boolean z2 = isAtRest;
            while (true) {
                d3 = this.mTimeAccumulator;
                if (d3 < SOLVER_TIMESTEP_SEC) {
                    break;
                }
                this.mTimeAccumulator = d3 - SOLVER_TIMESTEP_SEC;
                if (this.mTimeAccumulator < SOLVER_TIMESTEP_SEC) {
                    PhysicsState physicsState3 = this.mPreviousState;
                    physicsState3.position = d7;
                    physicsState3.velocity = d8;
                }
                double d11 = this.mEndValue;
                double d12 = ((d11 - d9) * d5) - (d6 * d8);
                double d13 = d8 + (d12 * SOLVER_TIMESTEP_SEC * 0.5d);
                double d14 = ((d11 - (((d8 * SOLVER_TIMESTEP_SEC) * 0.5d) + d7)) * d5) - (d6 * d13);
                double d15 = d8 + (d14 * SOLVER_TIMESTEP_SEC * 0.5d);
                double d16 = ((d11 - (d7 + ((d13 * SOLVER_TIMESTEP_SEC) * 0.5d))) * d5) - (d6 * d15);
                double d17 = d7 + (d15 * SOLVER_TIMESTEP_SEC);
                double d18 = d8 + (d16 * SOLVER_TIMESTEP_SEC);
                d7 += (d8 + ((d13 + d15) * 2.0d) + d18) * 0.16666666666666666d * SOLVER_TIMESTEP_SEC;
                d8 += (d12 + ((d14 + d16) * 2.0d) + (((d11 - d17) * d5) - (d6 * d18))) * 0.16666666666666666d * SOLVER_TIMESTEP_SEC;
                d9 = d17;
                d10 = d18;
            }
            PhysicsState physicsState4 = this.mTempState;
            physicsState4.position = d9;
            physicsState4.velocity = d10;
            PhysicsState physicsState5 = this.mCurrentState;
            physicsState5.position = d7;
            physicsState5.velocity = d8;
            if (d3 > 0.0d) {
                interpolate(d3 / SOLVER_TIMESTEP_SEC);
            }
            boolean z3 = true;
            if (isAtRest() || (this.mOvershootClampingEnabled && isOvershooting())) {
                if (d5 > 0.0d) {
                    double d19 = this.mEndValue;
                    this.mStartValue = d19;
                    this.mCurrentState.position = d19;
                } else {
                    this.mEndValue = this.mCurrentState.position;
                    this.mStartValue = this.mEndValue;
                }
                setVelocity(0.0d);
                z2 = true;
            }
            if (this.mWasAtRest) {
                this.mWasAtRest = false;
                z = true;
            } else {
                z = false;
            }
            if (z2) {
                this.mWasAtRest = true;
            } else {
                z3 = false;
            }
            Iterator<SpringListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                SpringListener next = it.next();
                if (z) {
                    next.onSpringActivate(this);
                }
                next.onSpringUpdate(this);
                if (z3) {
                    next.onSpringAtRest(this);
                }
            }
        }
    }

    public boolean currentValueIsApproximately(double d2) {
        return Math.abs(getCurrentValue() - d2) <= getRestDisplacementThreshold();
    }

    public void destroy() {
        this.mListeners.clear();
        this.mSpringSystem.deregisterSpring(this);
    }

    public double getCurrentDisplacementDistance() {
        return getDisplacementDistanceForState(this.mCurrentState);
    }

    public double getCurrentValue() {
        return this.mCurrentState.position;
    }

    public double getEndValue() {
        return this.mEndValue;
    }

    public String getId() {
        return this.mId;
    }

    public double getRestDisplacementThreshold() {
        return this.mDisplacementFromRestThreshold;
    }

    public double getRestSpeedThreshold() {
        return this.mRestSpeedThreshold;
    }

    public SpringConfig getSpringConfig() {
        return this.mSpringConfig;
    }

    public double getStartValue() {
        return this.mStartValue;
    }

    public double getVelocity() {
        return this.mCurrentState.velocity;
    }

    public boolean isAtRest() {
        return Math.abs(this.mCurrentState.velocity) <= this.mRestSpeedThreshold && (getDisplacementDistanceForState(this.mCurrentState) <= this.mDisplacementFromRestThreshold || this.mSpringConfig.tension == 0.0d);
    }

    public boolean isOvershootClampingEnabled() {
        return this.mOvershootClampingEnabled;
    }

    public boolean isOvershooting() {
        return this.mSpringConfig.tension > 0.0d && ((this.mStartValue < this.mEndValue && getCurrentValue() > this.mEndValue) || (this.mStartValue > this.mEndValue && getCurrentValue() < this.mEndValue));
    }

    public Spring removeAllListeners() {
        this.mListeners.clear();
        return this;
    }

    public Spring removeListener(SpringListener springListener) {
        if (springListener != null) {
            this.mListeners.remove(springListener);
            return this;
        }
        throw new IllegalArgumentException("listenerToRemove is required");
    }

    public Spring setAtRest() {
        PhysicsState physicsState = this.mCurrentState;
        double d2 = physicsState.position;
        this.mEndValue = d2;
        this.mTempState.position = d2;
        physicsState.velocity = 0.0d;
        return this;
    }

    public Spring setCurrentValue(double d2) {
        return setCurrentValue(d2, true);
    }

    public Spring setCurrentValue(double d2, boolean z) {
        this.mStartValue = d2;
        this.mCurrentState.position = d2;
        this.mSpringSystem.activateSpring(getId());
        Iterator<SpringListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onSpringUpdate(this);
        }
        if (z) {
            setAtRest();
        }
        return this;
    }

    public Spring setEndValue(double d2) {
        if (this.mEndValue == d2 && isAtRest()) {
            return this;
        }
        this.mStartValue = getCurrentValue();
        this.mEndValue = d2;
        this.mSpringSystem.activateSpring(getId());
        Iterator<SpringListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onSpringEndStateChange(this);
        }
        return this;
    }

    public Spring setOvershootClampingEnabled(boolean z) {
        this.mOvershootClampingEnabled = z;
        return this;
    }

    public Spring setRestDisplacementThreshold(double d2) {
        this.mDisplacementFromRestThreshold = d2;
        return this;
    }

    public Spring setRestSpeedThreshold(double d2) {
        this.mRestSpeedThreshold = d2;
        return this;
    }

    public Spring setSpringConfig(SpringConfig springConfig) {
        if (springConfig != null) {
            this.mSpringConfig = springConfig;
            return this;
        }
        throw new IllegalArgumentException("springConfig is required");
    }

    public Spring setVelocity(double d2) {
        PhysicsState physicsState = this.mCurrentState;
        if (d2 == physicsState.velocity) {
            return this;
        }
        physicsState.velocity = d2;
        this.mSpringSystem.activateSpring(getId());
        return this;
    }

    public boolean systemShouldAdvance() {
        return !isAtRest() || !wasAtRest();
    }

    public boolean wasAtRest() {
        return this.mWasAtRest;
    }
}
