package com.company;

import java.time.LocalDateTime;


public class DateTimeProvider implements IDateTImeProvider
{
    public LocalDateTime Now()
    {
        return LocalDateTime.now();
    }
}
