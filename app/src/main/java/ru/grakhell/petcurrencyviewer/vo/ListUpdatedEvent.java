package ru.grakhell.petcurrencyviewer.vo;

import com.facebook.litho.annotations.Event;

import java.util.List;

@Event
public class ListUpdatedEvent {
    public List<Currency> list;
}
