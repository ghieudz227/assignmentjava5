package edu.poly.assjava5banhang.service.Impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import edu.poly.assjava5banhang.dao.ProductDAO;
import edu.poly.assjava5banhang.model.Item;
import edu.poly.assjava5banhang.model.Product;
import edu.poly.assjava5banhang.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;

@Service
public class ShoppingServiceImpl implements ShoppingCartService {

    // Map chứa giỏ hàng của từng user
    private Map<String, Map<Integer, Item>> userCarts = new HashMap<>();
    private String currentUser; // User hiện tại

    @Autowired
    ProductDAO dao;

    @Autowired 
    HttpSession session;

    // Lấy giỏ hàng theo username, nếu chưa có thì tạo mới
    private Map<Integer, Item> getUserCart(String username) {
        return userCarts.computeIfAbsent(username, k -> new HashMap<>());
    }

    @Override
    public Item add(String username, Integer id) {
        Map<Integer, Item> cart = getUserCart(username);
        Product product = dao.findById(id).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        if (cart.containsKey(id)) {
            Item item = cart.get(id);
            item.setQty(item.getQty() + 1);
            return item;
        } else {
            Item item = new Item();
            item.setId(product.getId());
            item.setName(product.getName());
            item.setPrice(product.getPrice());
            item.setImage(product.getImage());
            item.setQty(1);
            cart.put(id, item);
            return item;
        }
    }

    @Override
    public void remove(String username, Integer id) {
        Map<Integer, Item> cart = getUserCart(username);
        if (!cart.containsKey(id)) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại trong giỏ hàng");
        }
        Item item = cart.get(id);
        if (item.getQty() > 1) {
            item.setQty(item.getQty() - 1);
        } else {
            cart.remove(id);
        }
    }

    @Override
public void removeCompletely(String username, Integer id) {
    Map<Integer, Item> cart = getUserCart(username);
    cart.remove(id);  // Xoá bất kể số lượng là bao nhiêu
}


    @Override
    public void clear(String username) {
        getUserCart(username).clear();
    }

    @Override
    public Collection<Item> getItems(String username) {
        return getUserCart(username).values();
    }

    @Override
    public int getCount(String username) {
        return getUserCart(username).values().stream().mapToInt(Item::getQty).sum();
    }

    @Override
    public double getAmount(String username) {
        return getUserCart(username).values().stream()
            .mapToDouble(item -> item.getPrice() * item.getQty()).sum();
    }

    @Override
    public Item update(String username, Integer id, int qty) {
    Map<Integer, Item> cart = getUserCart(username);
    if (!cart.containsKey(id)) {
        throw new IllegalArgumentException("Sản phẩm không tồn tại trong giỏ hàng");
    }
    if (qty <= 0) {
        cart.remove(id); // Nếu số lượng <= 0, xóa sản phẩm khỏi giỏ hàng
        return null;
    } else {
        Item item = cart.get(id);
        item.setQty(qty); // Cập nhật số lượng
        return item;
    }
}


    @Override
    public void setCurrentUser(String username) {
        this.currentUser = username;
        session.setAttribute("username", username);
    }
}
