package by.smirnov.threadsstore.entities;

import by.smirnov.threadsstore.entities.customer_types.Customer;

import java.util.concurrent.ArrayBlockingQueue;

public class StoreQueue {

    public final ArrayBlockingQueue<Customer> queue;
    public static final int QUEUE_MAX_CAPACITY = 30;

    public StoreQueue() {
        queue = new ArrayBlockingQueue<>(QUEUE_MAX_CAPACITY);
    }

    public void add(Customer customer) throws InterruptedException {
        queue.put(customer);
    }

    public Customer extract() {
        return queue.poll();
    }
}
