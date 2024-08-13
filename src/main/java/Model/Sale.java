package Model;

import java.util.Date;

public class Sale {
    private int id;
    private String items;
    private Date date;
    private int total;

    public Sale(int id, String items, Date date, int total) {
        this.id = id;
        this.items = items;
        this.date = date;
        this.total = total;
    }

    public Sale() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
