package fr.kata.time;

import java.time.LocalDate;

/**
 * Created by fmaury on 16/03/17.
 */
public class SystemClock implements Clock{
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
