package fr.kata.infra.converter;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by fmaury on 18/03/17.
 */
public class DateConverterTest {


    private DateConverter dateConverter = new DateConverter();

    @Test
    public void should_parse_date(){
        assertThat(convertion_of("D22M09Y2016")).isEqualTo(date(22, 9, 2016));
    }

    @Test
    public void should_accept_lower_case(){
        assertThat(convertion_of("D22m09Y2016")).isEqualTo(date(22, 9, 2016));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_reject_invalid_format(){
        assertThat(convertion_of("22092016")).isEqualTo(date(22, 9, 2016));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_reject_null_string(){
        assertThat(convertion_of(null)).isEqualTo(date(22, 9, 2016));
    }

    @Test
    public void should_not_reject_date_with_spaces(){
        assertThat(convertion_of("D 22M09 Y2016")).isEqualTo(date(22, 9, 2016));
    }

    private LocalDate date(int dayOfMonth, int month, int year) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    private LocalDate convertion_of(String dateString) {
        return dateConverter.parse(dateString);
    }
}