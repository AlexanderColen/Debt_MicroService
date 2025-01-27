package com.alexandercolen.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Alexander
 */
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String date;
    private double spent;
    private String currency;
    
    @JsonIgnore
    @ManyToOne
    private Debt debt;

    public Payment() { }

    public Payment(String date, double spent, String currency, Debt debt) {
        this.date = date;
        this.spent = spent;
        this.currency = currency;
        this.debt = debt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Debt getDebt() {
        return debt;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }

    public JsonObject toJson() {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        
        jsonBuilder.add("id", this.id)
                    .add("date", this.date)
                    .add("spent", this.spent)
                    .add("currency", this.currency);
        
        return jsonBuilder.build();
    }
}