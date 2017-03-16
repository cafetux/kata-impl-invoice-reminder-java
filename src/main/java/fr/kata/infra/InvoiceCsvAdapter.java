package fr.kata.infra;

import fr.kata.invoice.Invoice;
import fr.kata.invoice.Invoices;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by fmaury on 16/03/17.
 */
public class InvoiceCsvAdapter implements Invoices {

    private String filepath;

    public InvoiceCsvAdapter(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public List<Invoice> all() {
        try {
            return read().map(this::toInvoice).collect(toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Invoice toInvoice(List<String> line) {
        return new Invoice(line.get(0),0.0,null,null);
    }


    private Stream<List<String>> read() throws FileNotFoundException {
        return Stream.generate(new CsvLineSupplier(filepath));
    }


}
