package fr.kata;

import fr.kata.invoice.Invoice;
import fr.kata.invoice.Invoices;
import fr.kata.time.Clock;

/**
 * Created by fmaury on 16/03/17.
 */
public class InvoiceReminder {

    private Invoices invoices;
    private Notifications notificationSender;
    private Clock clock;

    public void remind(){
        invoices.all().stream().filter(Invoice::isNotPayed).forEach(invoice ->
                {
                    if (dueDateIsSoon(invoice)) {
                        notificationSender.sendDueDateNotification(invoice);
                    }
                    if (dueDateAlreadyPassed(invoice) && isDueDateMonthAnniversary(invoice)) {
                            notificationSender.sendPaymentDelayReminder(invoice);
                    }
                }
        );

    }

    private boolean dueDateIsSoon(Invoice invoice) {
        return clock.now().plusDays(10).equals(invoice.getDueDate());
    }

    private boolean dueDateAlreadyPassed(Invoice invoice) {
        return clock.now().isAfter(invoice.getDueDate());
    }

    private boolean isDueDateMonthAnniversary(Invoice invoice) {
        return isSameDayOfMonth(invoice) || (isLastDayOfMonth() && needToRemindLastDayOfMonth(invoice));
    }

    private boolean needToRemindLastDayOfMonth(Invoice invoice) {
        return invoice.getDueDate().lengthOfMonth()==invoice.getDueDate().getDayOfMonth();
    }

    private boolean isLastDayOfMonth() {
        return clock.now().getDayOfMonth()==clock.now().lengthOfMonth();
    }

    private boolean isSameDayOfMonth(Invoice invoice) {
        return clock.now().getDayOfMonth()==(invoice.getDueDate().getDayOfMonth());
    }
}
