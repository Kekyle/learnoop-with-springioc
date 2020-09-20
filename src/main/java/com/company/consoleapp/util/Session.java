package com.company.consoleapp.util;

import com.company.entity.User;

import java.util.Objects;

public class Session {
    private static int incId = 1;
    private int id = incId++;
    private User user;
    private Basket basket = new Basket();

    public Session() {
    }

    public Session(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(user, session.user) &&
                Objects.equals(basket, session.basket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, basket);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", user=" + user +
                ", basket=" + basket +
                '}';
    }
}
