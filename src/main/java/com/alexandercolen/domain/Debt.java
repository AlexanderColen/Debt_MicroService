package com.alexandercolen.domain;

import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Alexander
 */
@Entity
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String date;
    private String description;
    private String type;
    private double interest;
    private double amount;
    private String currency;
    
    @OneToMany (mappedBy = "debt")
    private List<Payment> payments;

    public Debt() {
        this.payments = new ArrayList<>();
    }

    public Debt(String date, String description, String type, double interest, double amount, String currency) {
        this.date = date;
        this.description = description;
        this.type = type;
        this.interest = interest;
        this.amount = amount;
        this.currency = currency;
        this.payments = new ArrayList<>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }    

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public JsonObject toJson() {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        
        jsonBuilder.add("id", this.id)
                    .add("date", this.date)
                    .add("description", this.description)
                    .add("type", this.type)
                    .add("interest", this.interest)
                    .add("amount", this.amount)
                    .add("currency", this.currency);
        
        return jsonBuilder.build();
    }
}