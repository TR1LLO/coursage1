package ru.sfedu.coursage.model;

/**
 * Shifter-parameterizing bean-package
 */
public class ShifterArgs extends ArgumentPack {
    private float frequency = 0, step = 1;
    private int radius = 24;
    public ShifterArgs() {
        setProcessorId(ProcessorId.SHIFTER);
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }
    public float getFrequency() {
        return frequency;
    }

    public void setStep(float step) {
        this.step = step;
    }
    public float getStep() {
        return step;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    public int getRadius() {
        return radius;
    }
}
