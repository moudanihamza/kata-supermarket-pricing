package domaine;


import exceptions.IllegalOperation;

import java.util.HashMap;
import java.util.Map;


public class Customer {

    private Map<Item, Float> cart;

    public Customer() {
        this.cart = new HashMap<Item, Float>();
    }

    public void addToCart(Item item, float numberToAdd) throws IllegalOperation {
        if (item.getType().equals(ItemType.UNITE) && (numberToAdd - (int) numberToAdd != 0)) {
            throw new IllegalOperation();
        } else {
            if (this.cart.containsKey(item)) {
                this.cart.replace(item, (Float) this.cart.get(item) + numberToAdd);
            } else {
                this.cart.put(item, numberToAdd);
            }
        }
    }

    public void removeFromCart(Item item, float numberToRemove) throws IllegalOperation {
        if (this.cart.containsKey(item)) {
            if ((Float) this.cart.get(item) < numberToRemove) {
                throw new IllegalOperation();
            } else {
                float result = (Float) this.cart.get(item) - numberToRemove;
                if (result == 0) {
                    this.cart.remove(item);
                } else {
                    this.cart.replace(item, (Float) this.cart.get(item) + numberToRemove);
                }
            }
        }
    }

    public Map<Item, Float> getCart() {
        return cart;
    }
}
