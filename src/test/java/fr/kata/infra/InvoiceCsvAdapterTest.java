package fr.kata.infra;

import fr.kata.invoice.Invoice;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by fmaury on 16/03/17.
 */
public class InvoiceCsvAdapterTest {

    private InvoiceCsvAdapter adapter = new InvoiceCsvAdapter("src/test/resources/input.csv");

    @Test
    public void should_load_datas_from_csv(){
        List<Invoice> invoices = adapter.all();
        assertThat(invoices).isNotEmpty().hasSize(7);
        assertThat(invoices.get(0).getId()).isEqualTo("12EDF432");
        assertThat(invoices.get(0).getPrice()).isEqualTo(23.00);
        assertThat(invoices.get(0).isNotPayed()).isTrue();
        assertThat(invoices.get(0).getDueDate()).isEqualTo(LocalDate.of(2017, 2, 12));
        assertThat(invoices.get(0).getContact().getFirstName()).isEqualTo("Doe");
        assertThat(invoices.get(0).getContact().getLastName()).isEqualTo("John");
        assertThat(invoices.get(0).getContact().getEmail()).isEqualTo("john.doe@foobar.com");

        assertThat(invoices.get(3).isNotPayed()).isFalse();
    }

    @Test
    public void should_skip_invalid_invoices(){
        List<Invoice> invoices = new InvoiceCsvAdapter("src/test/resources/input_with_invalid_invoice.csv").all();
        assertThat(invoices).isNotEmpty().hasSize(6);
        assertThat(invoices.get(0).getId()).isEqualTo("12EDF432");
        assertThat(invoices.get(0).getPrice()).isEqualTo(23.00);
        assertThat(invoices.get(0).isNotPayed()).isTrue();
        assertThat(invoices.get(0).getDueDate()).isEqualTo(LocalDate.of(2017, 2, 12));
    }
 }