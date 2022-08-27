package by.smirnov.threadsstore.services;

import by.smirnov.threadsstore.entities.Good;
import by.smirnov.threadsstore.entities.ShoppingCart;
import by.smirnov.threadsstore.entities.Store;
import by.smirnov.threadsstore.entities.StoreQueue;
import by.smirnov.threadsstore.entities.customer_types.Customer;
import by.smirnov.threadsstore.interfaces.CustomerAction;
import by.smirnov.threadsstore.interfaces.ShoppingCardAction;
import by.smirnov.threadsstore.repo.PriceListRepo;
import by.smirnov.threadsstore.utils.Randomizer;
import by.smirnov.threadsstore.utils.Sleeper;
import by.smirnov.threadsstore.repo.Wordings;

import java.util.concurrent.Semaphore;

import static java.lang.System.*;

public class CustomerWorker extends Thread implements CustomerAction, ShoppingCardAction {
    private final Customer customer;
    private final Store store;
    private int goodsTaken;
    private static final Semaphore CHOOSE_PERMITS = new Semaphore(20);
    private static final Semaphore CARTS_AVAILIABLE = new Semaphore(50);
    private int chosenGoodsNumber;

    public CustomerWorker(Customer customer, Store store) {
        this.customer = customer;
        this.store = store;
        store.getManager().customerIn();
    }

    @Override
    public String toString() {
        return customer.toString();
    }

    @Override
    public void run() {
        enteredStore();
        try {
            CARTS_AVAILIABLE.acquire();
            takeCart();
            CHOOSE_PERMITS.acquire();
            chosenGoodsNumber = customer.quantityNeed();
            for (int count = 0; count < chosenGoodsNumber; count++) {
                Good good = chooseGood();
                goodsTaken = putToCart(good);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            CHOOSE_PERMITS.release();
            CARTS_AVAILIABLE.release();
        }
        if (chosenGoodsNumber > 0) goToQueue();
        goOut();

        store.getManager().customerOut();
    }

    @Override
    public void enteredStore() {
        out.printf(Wordings.ENTER, customer, store);
    }

    @Override
    public void takeCart() {
        customer.shoppingCart = new ShoppingCart(customer, store);
        int timeout = customer.getSpeed(Randomizer.get(100, 300));
        Sleeper.sleep(timeout);
        out.printf(Wordings.TAKE_CART, customer);
    }

    @Override
    public Good chooseGood() {
        if (goodsTaken == 0) out.printf(Wordings.CHOOSE, customer);
        else out.printf(Wordings.CHOOSE_MORE, customer);
        int timeout = Randomizer.get(500, 2000);
        Sleeper.sleep(timeout);
        Good good = new Good(PriceListRepo.getRandomGoodName());
        out.printf(Wordings.CHOSEN, customer, good.getName());
        return good;
    }

    @Override
    public int putToCart(Good good) {
        customer.shoppingCart.getShoppingCart().add(good);
        int timeout = Randomizer.get(100, 300);
        Sleeper.sleep(timeout);
        out.printf(Wordings.PUT, customer);
        return customer.shoppingCart.getShoppingCart().size();
    }

    @Override
    public void goToQueue() {
        StoreQueue storeQueue = store.getStoreQueue();
        try {
            synchronized (customer.getMonitor()) {
                out.printf(Wordings.QUEUE_IN, customer);
                storeQueue.add(customer);
                customer.setWaiting(true);
                while (customer.isWaiting()) {
                    customer.wait();
                }
                out.printf(Wordings.QUEUE_OUT, customer);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void goOut() {
        if (goodsTaken == 0) {
            out.printf(Wordings.AWAY_SAD, customer);
        } else {
            out.printf(Wordings.AWAY_HAPPY, customer, store, goodsTaken);
        }
    }
}
