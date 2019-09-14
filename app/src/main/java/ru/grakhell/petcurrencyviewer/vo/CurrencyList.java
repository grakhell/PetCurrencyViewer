package ru.grakhell.petcurrencyviewer.vo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "ValCurs", strict = false)
@Entity(tableName = "currencies", indices = {@Index(value = "date", unique = true)})
public final class CurrencyList {

    @Attribute(name = "Date")
    @PrimaryKey()
    @ColumnInfo(name = "date")
    @NonNull
    private String date;
    @Attribute(name = "name")
    private String name;
    @ElementList(inline = true)
    @Ignore
    private List<Currency> currencies;

    public CurrencyList(){
        this.date = "";
        this.name = "";
        this.currencies = new ArrayList<>();

    }

    public CurrencyList(@NonNull String date, String name, List<Currency> currencies) {
        this.date = date;
        this.name = name;
        this.currencies = currencies;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}
