package by.smirnov.threadsstore.entities.customer_types;

import by.smirnov.threadsstore.entities.ShoppingCart;
import by.smirnov.threadsstore.utils.Randomizer;

public class Customer {

    public final String name;

    public ShoppingCart shoppingCart;

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    private boolean waiting;
    public Customer(int number) {
        this.name = "Customer №" + number;
    }

    @Override
    public String toString() {
        return name;
    }

    public int quantityNeed() {
        return Randomizer.get(2,5);
    }

    public int getSpeed(int millis) {
        return millis;
    }

    public Object getMonitor(){
        return this;
    }
}
