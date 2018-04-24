package com.example.usp05.githubtry;

import com.example.usp05.githubtry.temp_backup.DateHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by nathan on 4/22/18.
 */

@RunWith(Parameterized.class)
public class DateUnitTest {

    @Parameterized.Parameter(0)
    public int month;
    @Parameterized.Parameter(1)
    public int day;
    @Parameterized.Parameter(2)
    public int year;
    @Parameterized.Parameter(3)
    public String result;

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        Object[][] data = new Object[][] {{01, 15, 2000, "01/15/2000"},
                {12, 01, 2010, "12/01/2010"}, {05, 05, 1905, "05/05/1905"},
                {10, 10, 2020, "10/10/2020"}, {1, 2, 30, "01/02/2030"}};
        return Arrays.asList(data);
    }

    @Test
    public void dateTest_numericInput_ReturnsCorrectString(){
        DateHandler DH = new DateHandler();

        try {
            DH.intToDate(month, day, year);
        } catch (ParseException e) {
            System.out.println(e);
        } finally {
            String output = DH.itemDateToString();
            assertEquals(result, output);
        }
    }

    @Test
    public void dateTest_CorrectTranslation_ReturnsSameString(){
        String input = "04/20/2018";
        String output;

        DateHandler DH = new DateHandler();

        try {
            DH.itemStringToDate(input);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            output = DH.itemDateToString();
            assertEquals(input,output);
        }
    }
}
