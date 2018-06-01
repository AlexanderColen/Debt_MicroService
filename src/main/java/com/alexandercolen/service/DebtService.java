package com.alexandercolen.service;

import com.alexandercolen.dao.DebtDAO;
import com.alexandercolen.dao.PaymentDAO;
import com.alexandercolen.domain.Debt;
import com.alexandercolen.domain.Payment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander
 */
@Service
public class DebtService {

    private static final Logger LOG = Logger.getLogger(DebtService.class.getName());
    
    private static final String EXPENDITURES_URL = "http://localhost:8090/expenditures/";
        
    @Autowired
    DataSource dataSource;
    
    @Autowired
    DebtDAO debtDAO;
    
    @Autowired
    PaymentDAO paymentDAO;

    public DebtService() {
        LOG.log(Level.INFO, String.format("Datasource: %s", this.dataSource));
    }

    public List<Debt> getAllDebts() {
        List<Debt> debts = new ArrayList<>();
        if (this.debtDAO != null) {
            for (Debt d : this.debtDAO.findAll()) {
                debts.add(d);
            }
        } else {
            LOG.log(Level.INFO, "Autowired fail.");
        }
        
        debts.sort(Comparator.comparing(Debt::getId));
        
        return debts;
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        if (this.paymentDAO != null) {
            for (Payment p : this.paymentDAO.findAll()) {
                payments.add(p);
            }
        } else {
            LOG.log(Level.INFO, "Autowired fail.");
        }
        
        payments.sort(Comparator.comparing(Payment::getId));
        
        return payments;
    }
    
    public boolean postDebt(Debt debt) {
        if (this.debtDAO == null) {
            return false;
        }
        
        this.debtDAO.save(debt);
        
        return true;
    }
    
    public Debt getSpecificDebt(long id) {
        if (this.debtDAO == null) {
            return null;
        }
        
        if (this.debtDAO.findById(id).isPresent()) {
            return this.debtDAO.findById(id).get();
        } else {
            return null;
        }
    }

    public boolean deleteDebt(long id) {
        if (this.debtDAO == null) {
            return false;
        }
        
        this.debtDAO.delete(this.debtDAO.findById(id).get());
        
        return true;
    }
    
    public boolean postPayment(Payment payment, String external) {
        try {
            if (this.paymentDAO == null) {
                return false;
            }
            
            if (external.equalsIgnoreCase("yes")) {
                //Create HTTP request for posting payment in Debt microservice.
                HttpClient httpClient = HttpClientBuilder.create().build();

                String postURL = DebtService.EXPENDITURES_URL + "new/no";
                LOG.log(Level.INFO, String.format("Trying to post at URL: %s", postURL));

                HttpPost postRequest = new HttpPost(postURL);

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("description", String.format("%s", String.format("Payment for debt: %s", payment.getDebt().getDescription()))));
                params.add(new BasicNameValuePair("date", String.format("%s", payment.getDate())));
                params.add(new BasicNameValuePair("spent", String.format("%s", payment.getSpent())));
                params.add(new BasicNameValuePair("type", String.format("%s", "DEBT")));
                params.add(new BasicNameValuePair("currency", String.format("%s", payment.getCurrency())));
                params.add(new BasicNameValuePair("debtID", String.format("%s", payment.getDebt().getId())));
                postRequest.setEntity(new UrlEncodedFormEntity(params));

                //Execute request.
                HttpResponse response = httpClient.execute(postRequest);

                if (response.getStatusLine().getStatusCode() != 200) {
                    LOG.log(Level.INFO, String.format("Failed : HTTP error code : %s", response.getStatusLine().getStatusCode()));
                    return false;
                }
            }
            
            this.paymentDAO.save(payment);
            
            return true;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return false;
    }

    public Payment getSpecificPayment(long id) {
        if (this.paymentDAO == null) {
            return null;
        }
        
        if (this.paymentDAO.findById(id).isPresent()) {
            return this.paymentDAO.findById(id).get();
        } else {
            return null;
        }
    }

    public boolean deletePayment(long id) {
        if (this.paymentDAO == null) {
            return false;
        }
        
        this.paymentDAO.delete(this.paymentDAO.findById(id).get());
        
        return true;
    }
}