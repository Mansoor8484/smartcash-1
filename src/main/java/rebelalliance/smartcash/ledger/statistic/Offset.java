package rebelalliance.smartcash.ledger.statistic;

public record Offset(double offset, OffsetType offsetType) {
    public double getAdjustmentOffset(double balance) {
        return this.offset - balance;
    }

    public String toString() {
        return String.format("%.2f", this.offset);
    }
}
