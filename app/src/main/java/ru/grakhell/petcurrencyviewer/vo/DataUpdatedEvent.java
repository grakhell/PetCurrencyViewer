package ru.grakhell.petcurrencyviewer.vo;

import com.facebook.litho.annotations.Event;

@Event
public class DataUpdatedEvent {
    public CurrencyList data;
}
