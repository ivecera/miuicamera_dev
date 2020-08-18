package com.facebook.rebound;

public class SynchronousLooper extends SpringLooper {
    public static double SIXTY_FPS = 16.6667d;
    private boolean mRunning;
    private double mTimeStep = SIXTY_FPS;

    public double getTimeStep() {
        return this.mTimeStep;
    }

    public void setTimeStep(double d2) {
        this.mTimeStep = d2;
    }

    @Override // com.facebook.rebound.SpringLooper
    public void start() {
        this.mRunning = true;
        while (!((SpringLooper) this).mSpringSystem.getIsIdle() && this.mRunning) {
            ((SpringLooper) this).mSpringSystem.loop(this.mTimeStep);
        }
    }

    @Override // com.facebook.rebound.SpringLooper
    public void stop() {
        this.mRunning = false;
    }
}
