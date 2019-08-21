package com.company;

/// A simple Parking Meter class

/// A user can add money to the meter which will add more time
/// The user and attendant can see how much remaining time is left
/// The user and attendant can see if the meter is expired
/// The attendant can see if the meters till is full
/// The attendant can see the capacity of the till and how much is in it
/// The attendant can empty the till
// A quarter adds 15 minutes

import java.time.Duration;
import java.time.LocalDateTime;
import com.company.IDateTImeProvider;
import com.company.IMeterTill;

public class ParkingMeter
{
    private LocalDateTime expiringTime;

    private IDateTImeProvider DateTimeProvider;
    private IMeterTill MeterTill;

    public ParkingMeter(IDateTImeProvider dateTimeProvider, IMeterTill meterTill)
    {
        DateTimeProvider = dateTimeProvider;
        MeterTill = meterTill;

        expiringTime = LocalDateTime.MIN;
    }

    public boolean AddQuarter() //This simple Meter only takes quarters
    {
        boolean addedToTill = false;

        if(MeterTill.AddQuarter())
        {
            if(IsExpired())
            {
                expiringTime = DateTimeProvider.Now().plusMinutes(15);
            }
            else
            {
                expiringTime.plusMinutes(15);
            }
            addedToTill = true;
        }
        return addedToTill;
    }

    public double GetRemainingTime()
    {
        double minutesRemaining = 0;
        if(!IsExpired())
        {
            Duration remainingTime = Duration.between(expiringTime, DateTimeProvider.Now()); //Subtract DateTimeProvider.Now from expiringTime
            minutesRemaining = remainingTime.toMinutes(); //Total minutes
        }
        return minutesRemaining;
    }

    public boolean IsExpired()
    {
        return DateTimeProvider.Now().isAfter(expiringTime);//Edge Condition Bug here
    }
}
