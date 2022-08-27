package by.smirnov.threadsstore.interfaces;

import by.smirnov.threadsstore.entities.Good;

public interface ShoppingCardAction {
    void takeCart(); //взял корзину
    int putToCart(Good good); //положил товар в корзину(возвращает число товаров)
}
