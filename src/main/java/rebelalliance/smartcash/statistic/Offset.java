package rebelalliance.smartcash.statistic;

import rebelalliance.smartcash.account.Account;

public class Offset {
    private double offset;

    public Offset() {
        this.offset = 0.0;
    }

    public double getOffset() {
        return this.offset;
    }

    public void offsetOffset(double offset) {
        this.offset += offset;
    }

    public String toString() {
        return String.format("%.2f", this.offset);
    }
}
