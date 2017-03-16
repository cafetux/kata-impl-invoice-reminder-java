package fr.kata.invoice;

import fr.kata.InvoiceReminder;
import fr.kata.Notifications;
import fr.kata.customer.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import fr.kata.time.Clock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by fmaury on 16/03/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class InvoiceReminderTest {

    @Mock
    Invoices invoices;
    @Mock
    Notifications notifications;
    @Mock
    Clock clock;

    @Before
    public void init(){
        when(clock.now()).thenReturn(LocalDate.of(2017, 2, 12));
    }

    @InjectMocks
    private InvoiceReminder reminder;

    @Test
    public void should_not_remind_customer_when_not_necessary(){
        given_invoices(with_due_date_in_several_months());
        when_we_launch_reminder();
        then_we_not_send_notification();
    }

    @Test
    public void should_remind_customer_10_days_before(){
        given_invoices(with_due_date_in_10_days());
        when_we_launch_reminder();
        then_we_send_deadline_notification();
    }

    @Test
    public void should_remind_customer_month_after(){
        given_invoices(with_due_date_1_month_before());
        when_we_launch_reminder();
        then_we_send_reminder();
    }

    @Test
    public void should_remind_customer_all_month_after(){
        given_invoices(with_due_date_4_month_before());
        when_we_launch_reminder();
        then_we_send_reminder();
    }

    @Test
    public void should_not_remind_customer_if_due_date_already_passed_but_not_anniversary_day(){
        given_invoices(with_due_date_some_days_before());
        when_we_launch_reminder();
        then_we_not_send_notification();
    }

    @Test
    public void should_not_remind_customer_if_invoice_already_payed(){
        given_invoices(payed_with_due_date_1_month_before(),payed_with_due_date_in_10_days());
        when_we_launch_reminder();
        then_we_not_send_notification();
    }

    @Test
    public void should_remind_payment_delay_last_day_of_month_when_reminder_day_not_exist_on_this_month(){
        when(clock.now()).thenReturn(LocalDate.of(2017, 04, 30));
        given_invoices(payed_with_due_date_on(LocalDate.of(2017,03,31)));
        when_we_launch_reminder();
        then_we_send_reminder();
    }

    @Test
    public void should_not_remind_payment_delay_last_day_of_month_when_reminder_day_exist_on_this_month(){
        when(clock.now()).thenReturn(LocalDate.of(2017, 04, 30));
        given_invoices(payed_with_due_date_on(LocalDate.of(2017,03,25)));
        when_we_launch_reminder();
        then_we_not_send_notification();
    }


    private void then_we_send_reminder() {
        verify(notifications,only()).sendPaymentDelayReminder(any(Invoice.class));
    }

    private void then_we_not_send_notification() {
        verify(notifications,never()).sendDueDateNotification(any(Invoice.class));
        verify(notifications,never()).sendPaymentDelayReminder(any(Invoice.class));
    }

    private void when_we_launch_reminder() {
        reminder.remind();
    }

    private OngoingStubbing<List<Invoice>> given_invoices(Invoice... invoices) {
        return when(this.invoices.all()).thenReturn(Arrays.asList(invoices));
    }

    private void then_we_send_deadline_notification() {
        verify(notifications,only()).sendDueDateNotification(any(Invoice.class));
    }

    private Invoice payed_with_due_date_on(LocalDate dueDate) {
        return new Invoice("AZERTY",23.50,new Person("fabien","maury","fabien@arolla.fr"), dueDate);
    }
    private Invoice with_due_date_some_days_before() {
        return new Invoice("AZERTY",23.50,new Person("fabien","maury","fabien@arolla.fr"), clock.now().minusDays(4));
    }
    private Invoice with_due_date_1_month_before() {
        return new Invoice("AZERTY",23.50,new Person("fabien","maury","fabien@arolla.fr"), clock.now().minusMonths(1));
    }
    private Invoice with_due_date_4_month_before() {
        return new Invoice("AZERTY",23.50,new Person("fabien","maury","fabien@arolla.fr"), clock.now().minusMonths(4));
    }
    private Invoice with_due_date_in_several_months() {
        return new Invoice("AZERTY",23.50,new Person("fabien","maury","fabien@arolla.fr"), LocalDate.now().plusMonths(3));
    }
    private Invoice with_due_date_in_10_days() {
        return new Invoice("AZERTY",23.50,new Person("fabien","maury","fabien@arolla.fr"), clock.now().plusDays(10));
    }
    private Invoice payed_with_due_date_in_10_days() {
        Invoice invoice = new Invoice("AZERTY", 23.50, new Person("fabien", "maury", "fabien@arolla.fr"), clock.now().plusDays(10));
        invoice.payedAt(LocalDate.now().minusDays(3));
        return invoice;
    }
    private Invoice payed_with_due_date_1_month_before() {
        Invoice invoice = new Invoice("AZERTY", 23.50, new Person("fabien", "maury", "fabien@arolla.fr"), clock.now().minusMonths(1));
        invoice.payedAt(LocalDate.now().minusDays(3));
        return invoice;
    }

}