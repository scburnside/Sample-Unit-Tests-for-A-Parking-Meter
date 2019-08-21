package com.company;

public interface IMeterTill
{
    boolean AddQuarter();
    int GetTillValue();
    boolean IsTillFull();
    void EmptyTill();
    int GetTillCapacity();
}
