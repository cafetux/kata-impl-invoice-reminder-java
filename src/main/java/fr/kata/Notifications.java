package fr.kata;

import fr.kata.invoice.Invoice;

/**
 * Created by fmaury on 16/03/17.
 */
public interface Notifications {

    void sendDueDateNotification(Invoice invoice);

    void sendPaymentDelayReminder(Invoice invoice);

}
