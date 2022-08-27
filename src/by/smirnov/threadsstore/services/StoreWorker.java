package by.smirnov.threadsstore.services;

import by.smirnov.threadsstore.entities.Cashier;
import by.smirnov.threadsstore.entities.Manager;
import by.smirnov.threadsstore.entities.Store;
import by.smirnov.threadsstore.entities.customer_types.Customer;
import by.smirnov.threadsstore.entities.customer_types.Pensioner;
import by.smirnov.threadsstore.entities.customer_types.Student;
import by.smirnov.threadsstore.utils.Randomizer;
import by.smirnov.threadsstore.utils.Sleeper;
import by.smirnov.threadsstore.repo.Wordings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static by.smirnov.threadsstore.utils.Report.printReport;
import static java.lang.System.out;

public class StoreWorker extends Thread {
    private final Store store;
    private int cashierNumber;
    public static final int CASH_THREADS_NUMB = 5;
    private ExecutorService threadPool;

    public StoreWorker(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return store.toString();
    }

    @Override
    public void run() {
        out.printf(Wordings.OPEN, store);
        int counter = 0;
        Manager manager = store.getManager();
        threadPool = Executors.newFixedThreadPool(CASH_THREADS_NUMB);

        for (int time = 0; manager.storeOpened(); time++) {
            if(time%30 == 0 && time != 0) printReport(store);
            openCashiers();
            int inFlowBase = Randomizer.get(5, 15);
            for (int i = 0; manager.storeOpened() && i < inFlowPerSec(inFlowBase, time) &&
                    manager.getInStoreCustomers() < entranceLimit(time); i++) {
                Customer customer = getCustomer(++counter);
                CustomerWorker customerWorker = new CustomerWorker(customer, store);
                customerWorker.start();
            }
            Sleeper.sleep(1000);
        }

        threadPool.shutdown();
        waitTermination();
        out.printf(Wordings.CLOSE, store);
        printReport(store);
    }

    private void waitTermination() {
        try {
            do {
                Thread.onSpinWait();
            } while (!threadPool.awaitTermination(1, TimeUnit.MINUTES));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void openCashiers() {
        while (store.cashiersNotEnough() && store.cashiers.size()<5) {
            store.cashiersAtWork++;
            cashierNumber++;
            Cashier cashier = new Cashier(cashierNumber);
            CashierWorker cashierWorker = new CashierWorker(cashier, store);
            store.cashiers.add(cashier);
            threadPool.submit(cashierWorker);
        }
    }

    public static Customer getCustomer(int counter) {
        int type = Randomizer.get(1, 4);
        if (type == 1) return new Pensioner(counter);
        else if (type == 2 || type == 4) return new Student(counter);
        return new Customer(counter);
    }

    private static int entranceLimit(int time) {
        if (time == 0) return 15;
        else if (time % 60 <= 30) return 10 + time % 30;
        else return 40 - time % 30;
    }

    private static int inFlowPerSec(int inFlowBase, int time) {
        if (time == 0) return inFlowBase;
        else if (time % 60 <= 30) return inFlowBase + time % 30;
        else return inFlowBase + 30 - time % 30;
    }
}
