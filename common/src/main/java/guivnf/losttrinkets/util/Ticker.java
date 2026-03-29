package guivnf.losttrinkets.util;

public class Ticker {
    private int ticks;
    private final int max;

    public Ticker(int max) {
        this.max = max;
    }

    public void onward() {
        if (this.ticks < this.max) {
            this.ticks++;
        }
    }

    public void back(int amount) {
        this.ticks -= amount;
        if (this.ticks < 0)
            this.ticks = 0;
    }

    public void add(int amount) {
        this.ticks = Math.min(this.ticks + amount, this.max);
    }

    public boolean ended() {
        return this.ticks >= this.max;
    }

    public boolean isEmpty() {
        return this.ticks <= 0;
    }

    public void reset() {
        this.ticks = 0;
    }

    public int getTicks() {
        return this.ticks;
    }

    public int getMax() {
        return this.max;
    }
}
