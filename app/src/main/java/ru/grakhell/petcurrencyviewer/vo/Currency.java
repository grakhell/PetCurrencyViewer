package ru.grakhell.petcurrencyviewer.vo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "Valute", strict = false)
@Entity(tableName = "currency", indices = {@Index(value = "uid", unique = true)})
public final class Currency {

    @PrimaryKey
    @ColumnInfo(name = "uid")
    @NonNull
    @Attribute(name = "ID")
    //@Element(name = "ID")
    private String id;

    @Element(name = "NumCode")
    private Integer numCode;
    @Element(name = "CharCode")
    private String charCode;
    @Element(name = "Nominal")
    private Integer nominal;
    @Element(name = "Name")
    private String name;
    @Element(name = "Value")
    private String value;

    @ForeignKey(
            entity = CurrencyList.class,
            parentColumns = {"date"},
            childColumns = {"update_date"},
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
    )
    @ColumnInfo(name = "update_date")
    private String date;

    public Currency() {
        this.id = "";
    }

    public Currency(@NonNull String id, Integer numCode, String charCode, Integer nominal,
                    String name, String value) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public Integer getNumCode() {
        return numCode;
    }

    public void setNumCode(Integer numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
