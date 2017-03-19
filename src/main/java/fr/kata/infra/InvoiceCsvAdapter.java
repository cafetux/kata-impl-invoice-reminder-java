package fr.kata.infra;

import fr.kata.customer.Person;
import fr.kata.infra.converter.DateConverter;
import fr.kata.invoice.Invoice;
import fr.kata.invoice.Invoices;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by fmaury on 16/03/17.
 */
public class InvoiceCsvAdapter implements Invoices {

    private String filepath;
    private DateConverter dateConverter = new DateConverter();

    public InvoiceCsvAdapter(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public List<Invoice> all() {
        return read();
    }

    private Invoice toInvoice(String[] line) {
        try{
            if(line[3]!=null && !Objects.equals(line[3], "")) {
                return new Invoice(trim(line[0]), Double.valueOf(line[1]) / 100, new Person(trim(line[4]), trim(line[5]), trim(line[6])), dateConverter.parse(line[2]), dateConverter.parse(line[3]));
            }else{
                return new Invoice(trim(line[0]), Double.valueOf(line[1]) / 100, new Person(trim(line[4]), trim(line[5]), trim(line[6])), dateConverter.parse(line[2]));
            }
        } catch(Exception e){
            return null;
        }
    }

    private String trim(String s) {
        return s!=null?s.trim():null;
    }


    public List<Invoice> read() {
        List<Invoice> inputList;

        BufferedReader br = null;
        try {
            File inputF = new File(filepath);

            InputStream inputFS = new FileInputStream(inputF);

            br = new BufferedReader(new InputStreamReader(inputFS));

            inputList = br.lines()
                    // skip the header of the csv
                    .skip(1)
                    .map(x -> x.split(","))
                    .map(this::toInvoice)
                    .filter(x -> x != null)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(br);
        }

        return inputList;
    }

    private void closeQuietly(BufferedReader br) {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
