package com.company;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(Parameterized.class)

class MeterTillTest
{

    //Test that the value of the Till is Zero when it is Empty
    @Test
    public void MeterTillGetTillValueWhenEmpty_ReturnsZero()
    {
        IMeterTill subject = new MeterTill(20);

        int value = subject.GetTillValue();

        assertEquals(0, value);
    }

    @Test
    //Test the value of the Till when 1 Quarter is Added
    public void MeterTillGetTillValueWhenOneQuarterIsAdded_ReturnsOne()
    {
        IMeterTill subject = new MeterTill(20);

        subject.AddQuarter();
        int value = subject.GetTillValue();

        assertEquals(1, value);
    }

    //Test that the value of the Till is correct and doesn't exceed capacity
    @ParameterizedTest(name = "{index} => a={0}, b={1}")
    @CsvSource({
            "0,0",
            "1, 1",
            "2, 2",
            "10, 10",
            "20, 20",
            "21, 20",
            "100, 20"
    })
    void MeterTillTestCase(int addCount,int tillValue)
    {
        IMeterTill subject = new MeterTill(20);

        for(int x = 0; x < addCount; x++)
        {
            subject.AddQuarter();
        }

        int value = subject.GetTillValue();

        assertEquals(tillValue, value);
    }

    //Test that Till accepts Coins when not full
    @ParameterizedTest(name = "{index} => a={0}, b={1}")
    @CsvSource({
            "0, false",
            "1, false",
            "2, false",
            "10, false",
            "20, true",
            "21, true",
            "100, true"
    })
    void MeterTillIsNotFull_ReturnsFalse(int addCount, boolean isFull)
    {
        IMeterTill subject = new MeterTill(20);

        for(int x = 0; x < addCount; x++)
        {
            subject.AddQuarter();
        }

        boolean isEmpty = subject.IsTillFull();

        assertEquals(isFull, isEmpty);
    }

    //Tests Tills Capacity (20)
    @Test
    public void MeterTillAtCapacity_ReturnsTwenty()
    {
        IMeterTill subject = new MeterTill(20);

        assertEquals(20,subject.GetTillCapacity());
    }


    //Test that the Tills value is Zero when Empty
    @ParameterizedTest(name = "{index} => a={0}")
    @CsvSource({
            "0",
            "1",
            "10",
            "21",
    })
    public void MeterTillEmptyTillTillValue_IsZero(int addCount)
    {
        IMeterTill subject = new MeterTill(20);
        for (int x = 0; x < addCount; x ++)
        {
            subject.AddQuarter();
        }
        subject.EmptyTill();

        int tillValue = subject.GetTillValue();

        assertEquals(0,tillValue);
    }
}