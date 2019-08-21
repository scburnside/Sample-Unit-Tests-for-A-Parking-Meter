package com.company;

public class MeterTill implements IMeterTill
{
    private int tillCapacity;
    private int amountInTill;
    public MeterTill(int capacity)
    {
        tillCapacity = capacity;
        amountInTill = 0;
    }

    public boolean AddQuarter()
    {
        boolean addedToTill = false;

        if(!IsTillFull())
        {
            amountInTill++;
            addedToTill = true;
        }
        return addedToTill;
    }

    public int GetTillValue()
    {
        return amountInTill;
    }

    public boolean IsTillFull()
    {
        return amountInTill == tillCapacity;
    }

    public void EmptyTill()
    {
        amountInTill = 0;
    }

    public int GetTillCapacity()
    {
        return tillCapacity;
    }
}
