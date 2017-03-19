package fr.kata.infra.converter;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;

/**
 * Permet de convertir une date depuis le format du fichier CSV: D23M03Y2016 = 23 Mars 2016
 *
 */
public class DateConverter {

    public LocalDate parse(String dateToParse){
        try {
            String[] splitedDate = dateToParse.replace(" ","").replaceAll("[A-Za-z]", " ").split(" ");
            return LocalDate.of(parseInt(splitedDate[3]), parseInt(splitedDate[2]), parseInt(splitedDate[1]));
        }catch(Exception e){
            throw new IllegalArgumentException("Cannot parse date:"+dateToParse,e);
        }
    }
}