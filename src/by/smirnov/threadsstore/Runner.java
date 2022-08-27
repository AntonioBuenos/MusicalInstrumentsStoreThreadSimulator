package by.smirnov.threadsstore;

import by.smirnov.threadsstore.entities.Manager;
import by.smirnov.threadsstore.entities.Store;
import by.smirnov.threadsstore.entities.StoreQueue;
import by.smirnov.threadsstore.services.StoreWorker;
import by.smirnov.threadsstore.repo.Wordings;

public class Runner {
    public static void main(String[] args) {
        Manager manager = new Manager(100);
        StoreQueue storeQueue = new StoreQueue();
        Store store = new Store(Wordings.STORE_01, storeQueue, manager);
        StoreWorker storeWorker = new StoreWorker(store);
        storeWorker.start();
    }
}
