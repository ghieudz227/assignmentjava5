package edu.poly.assjava5banhang.service;

import java.util.Collection;

import edu.poly.assjava5banhang.model.Item;



public interface ShoppingCartService {

    Item add(String username, Integer id);

    void remove(String username, Integer id);

    Item update(String username, Integer id, int qty);

    void clear(String username);

    Collection<Item> getItems(String username);

    int getCount(String username);

    double getAmount(String username);

    void setCurrentUser(String username);

    
} 