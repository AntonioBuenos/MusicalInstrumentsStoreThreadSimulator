package by.smirnov.threadsstore.entities;

import java.util.concurrent.atomic.AtomicInteger;

public class Manager {

    private final int plan;
    private final AtomicInteger countIn = new AtomicInteger(0);
    private final AtomicInteger countOut = new AtomicInteger(0);

    public Manager(int plan) {
        this.plan = plan;
    }

    public boolean storeOpened() {
        return countIn.intValue() != plan;
    }

    public boolean storeClosed() {
        return countOut.intValue() == plan;
    }

    public synchronized void customerIn() {
        countIn.getAndIncrement();
    }

    public synchronized void customerOut() {
        countOut.getAndIncrement();
    }

    public int getInStoreCustomers() {
        return countIn.intValue() - countOut.intValue();
    }
}
