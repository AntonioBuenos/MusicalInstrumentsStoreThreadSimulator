package by.smirnov.threadsstore.services;

import by.smirnov.threadsstore.entities.*;
import by.smirnov.threadsstore.entities.customer_types.Customer;
import by.smirnov.threadsstore.repo.PriceListRepo;
import by.smirnov.threadsstore.utils.Randomizer;
import by.smirnov.threadsstore.utils.Sleeper;
import by.smirnov.threadsstore.repo.Wordings;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static java.lang.System.*;

public class CashierWorker implements Runnable {

    private final Cashier cashier;
    private final Store store;

    public CashierWorker(Cashier cashier, Store store) {
        this.cashier = cashier;
        this.store = store;
    }

    @Override
    public void run() {
        out.printf(Wordings.OPEN, cashier);
        Manager manager = store.getManager();
        StoreQueue storeQueue = store.getStoreQueue();
        while (true) {
            Customer customer = storeQueue.extract();
            if (Objects.nonNull(customer) || manager.storeOpened()) {
                out.printf(Wordings.CASH_SERVE, cashier, customer);
                int timeout = Randomizer.get(2000, 5000);
                Sleeper.sleep(timeout);
                proceedPayments(customer);
                out.printf(Wordings.CASH_SERVE_OFF, cashier, customer);
                synchronized (customer.getMonitor()) {
                    customer.setWaiting(false);
                    customer.notify();
                }
            } else break;
            if (store.cashiersEnough()) {
                closeCashier();
                while (store.cashiersEnough()) {
                    Sleeper.sleep(1000);
                    if (manager.storeClosed()) break;
                }
                if (store.cashiersNotEnough()) {
                    store.cashiersAtWork++;
                    out.printf(Wordings.OPEN, cashier);
                }
            }
            if (manager.storeClosed() && store.cashiersEnough()) break;
        }
        closeCashier();
    }

    private void proceedPayments(Customer customer) {
        List<Good> cart = customer.shoppingCart.getShoppingCart();
        double total = 0.0;
        double price;
        StringJoiner cheque = new StringJoiner("\n");
        cheque.add(customer + Wordings.CHEQUE).add(Wordings.CHEQUE_DIV);
        for (Good good : cart) {
            price = PriceListRepo.priceList.get(good.getName());
            total += price;
            cheque.add(String.format(Wordings.CHEQUE_POS, good.getName(), price));
        }
        cheque.add(Wordings.CHEQUE_DIV).add(String.format(Wordings.CHEQUE_TOTAL, new DecimalFormat(Wordings.DECIMAL).format(total)))
                .add(Wordings.CHEQUE_DIV);
        out.println(cheque);
        cashier.revenue += total;
    }

    private void closeCashier(){
        store.cashiersAtWork--;
        out.printf(Wordings.CLOSE_REVENUE, cashier, new DecimalFormat(Wordings.DECIMAL).format(cashier.revenue));
    }
}
