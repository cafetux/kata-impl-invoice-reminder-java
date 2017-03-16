package fr.kata.invoice;

import fr.kata.customer.Person;

import java.time.LocalDate;

/**
 * Created by fmaury on 16/03/17.
 */
public class Invoice {

    private String id;
    private double price;
    private Person contact;
    private LocalDate dueDate;
    private LocalDate paymentDate;

    public Invoice(String id, double price, Person contact, LocalDate dueDate) {
        this.id = id;
        this.price = price;
        this.contact = contact;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void payedAt(LocalDate paymentDate) {
        this.paymentDate=paymentDate;
    }

    public boolean isNotPayed() {
        return paymentDate==null;
    }
}
