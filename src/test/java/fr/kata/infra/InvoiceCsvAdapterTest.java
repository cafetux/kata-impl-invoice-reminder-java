package fr.kata.infra;

import org.junit.Test;

/**
 * Created by fmaury on 16/03/17.
 */
public class InvoiceCsvAdapterTest {

    private InvoiceCsvAdapter adapter = new InvoiceCsvAdapter("src/test/resources/input.csv");

    @Test
    public void should_load_datas_from_csv(){
            adapter.all();
    }

 }