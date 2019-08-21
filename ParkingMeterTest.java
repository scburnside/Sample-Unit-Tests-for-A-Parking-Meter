package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ParkingMeterTest
{
    private IDateTImeProvider dateTimeProvider;
    private IMeterTill meterTill;

    @BeforeEach
    public void setUp()
    {
        dateTimeProvider = new DateTimeProvider();
        meterTill = new MeterTill(20); //Till can hold 20 quarters (no other coins)
    }

    //Mock a DateTime with an additional 15 minutes (A quarter buys 15 minutes)
    @Mock
    private int dateTimeCalls = 0;
    LocalDateTime mockNow;
    public DateTimeProvider createFakeDateTime()
    {
        DateTimeProvider mockDateTimeProvider = Mockito.mock(DateTimeProvider.class); //CreateMock

        Mockito.when(mockDateTimeProvider.Now()).thenCallRealMethod().thenAnswer(new Answer<LocalDateTime>()
        {
            @Override
            public LocalDateTime answer(InvocationOnMock invocation) throws Throwable {
                LocalDateTime now = LocalDateTime.now();
                mockNow = now.plusMinutes(dateTimeCalls * 15);  //Add 15 minutes to now
                dateTimeCalls++;
                return mockNow;
            }
        });
        return mockDateTimeProvider;
    }

    //Test that ParkingMeter can be properly initialized
    @Test
    public void ParkingMeterCanInitialize_ReturnsValidObject()
    {
        ParkingMeter parkingMeter = new ParkingMeter(dateTimeProvider, meterTill);

        assertNotNull(parkingMeter);
    }

    //Test that a Quarter Can Be Added to an Empty Till
    @Test
    public void ParkingMeterCanAddQuarterWhenTillNotFull_ReturnsTrue()
    {
        IMeterTill mockMeterTill = Mockito.mock(IMeterTill.class); //Mock a till that is always empty
        Mockito.when(mockMeterTill.AddQuarter()).thenReturn(true);

        ParkingMeter parkingMeter = new ParkingMeter(dateTimeProvider, mockMeterTill);

        boolean isAdded = parkingMeter.AddQuarter();
        assertTrue(isAdded);
    }

    //Test that a Quarter Cannot be Added to a Full Till
    @Test
    public void ParkingMeterCanNotAddQuarterWhenTillIsFull_ReturnsFalse()
    {
        //Mock a till that is always full
        IMeterTill mockMeterTill = Mockito.mock(IMeterTill.class);
        Mockito.when(mockMeterTill.AddQuarter()).thenReturn(false);

        ParkingMeter parkingMeter = new ParkingMeter(dateTimeProvider, mockMeterTill);

        boolean badAdd = parkingMeter.AddQuarter();
        assertFalse(badAdd);
    }

    // Test that Remaining Time Is Zero when the Parking Meter the Timer is Expired
    @Test
    public void ParkingMeterGetRemainingTimeWhenTimeExpires_IsZero()
    {

        IDateTImeProvider fakeClock = createFakeDateTime();
        ParkingMeter parkingMeter = new ParkingMeter(fakeClock, meterTill);

        parkingMeter.AddQuarter();
        fakeClock.Now(); //Add an additional 15 minutes
        double remainingTime = parkingMeter.GetRemainingTime();

        assertTrue(remainingTime == 0);
    }

    //Test that the Remaining Time is Greater Than Zero after a Quarter Has been Added
    @Test
    public void ParkingMeterGetRemainingTimeWhenQuarterIsAdded_IsGreaterThanZero()
    {
        ParkingMeter parkingMeter = new ParkingMeter(createFakeDateTime(), meterTill);

        parkingMeter.AddQuarter();
        double remainingTime = parkingMeter.GetRemainingTime();

        assertTrue(remainingTime > 0);
    }

    //Test that the Remaining Time Is Zero When No Quarters are Added
    @Test
    public void ParkingMeterGetRemainingTimeWhenQuarterIsNotAdded_IsZero()
    {
        ParkingMeter parkingMeter = new ParkingMeter(dateTimeProvider, meterTill);

        double remainingTime = parkingMeter.GetRemainingTime();

        assertEquals(0, remainingTime);
    }

    //Test that the Parking Meter Is NOT Expired When a Quarter Is Added
    @Test
    public void ParkingMeterIsExpiredTimeWhenQuarterIsAdded_ReturnsFalse()
    {
        ParkingMeter parkingMeter = new ParkingMeter(dateTimeProvider, meterTill);

        parkingMeter.AddQuarter();
        boolean isExpired = parkingMeter.IsExpired();

        assertFalse(isExpired);
    }

    //Test that the Parking Meter IS Expired When a No Quarters are Added
    @Test
    public void ParkingMeterIsExpiredTimeWhenQuarterIsNotAdded_ReturnsTrue()
    {
        ParkingMeter parkingMeter = new ParkingMeter(dateTimeProvider, meterTill);

        boolean isExpired = parkingMeter.IsExpired();

        assertTrue(isExpired);
    }

    //Test the status of the Parking Meter is IsExpired when the Timer Runs out
    //This test fails because it indicates an edge condition bug.
    // IsExpired() should be returned if Now >= expiring time
    @Test
    public void ParkingMeterIsExpiredWhenTimeExpires_ReturnsTrue()
    {
        ParkingMeter parkingMeter = new ParkingMeter(createFakeDateTime(), meterTill);

        parkingMeter.AddQuarter();
        boolean isExpired = parkingMeter.IsExpired();

        assertTrue(isExpired);
    }

}