package by.smirnov.threadsstore.entities;

import java.util.ArrayList;
import java.util.List;

public class Store {

    private final String name;
    private final StoreQueue storeQueue;
    public int cashiersAtWork;
    public List<Cashier> cashiers = new ArrayList<>();

    public Manager getManager() {
        return manager;
    }

    private final Manager manager;

    public Store(String name, StoreQueue storeQueue, Manager manager) {
        this.name = name;
        this.storeQueue = storeQueue;
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Store " + name;
    }

    public StoreQueue getStoreQueue() {
        return storeQueue;
    }

    public boolean cashiersNotEnough(){
        return (this.getStoreQueue().queue.size() * 1.0 / this.cashiersAtWork) >= 5 && this.cashiersAtWork<5;
    }

    public boolean cashiersEnough(){
        return (this.getStoreQueue().queue.size() * 1.0 / this.cashiersAtWork) < 5;
    }
}
