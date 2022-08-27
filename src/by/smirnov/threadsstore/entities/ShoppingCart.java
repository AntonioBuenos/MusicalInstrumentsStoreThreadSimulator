package by.smirnov.threadsstore.entities;

import by.smirnov.threadsstore.entities.customer_types.Customer;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final Customer customer;
    private final Store store;
    private List<Good> shoppingCart;


    public ShoppingCart(Customer customer, Store store) {
        this.customer = customer;
        this.store = store;
        shoppingCart = new ArrayList<>();
    }

    public List<Good> getShoppingCart() {
        return shoppingCart;
    }
}
