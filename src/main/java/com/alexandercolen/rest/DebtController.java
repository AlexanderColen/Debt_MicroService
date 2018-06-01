package com.alexandercolen.rest;

import com.alexandercolen.domain.Debt;
import com.alexandercolen.domain.Payment;
import com.alexandercolen.service.DebtService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alexander
 */
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8090"}, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS })
@RestController
@RequestMapping(value = "/debts")
public class DebtController {

    private static final Logger LOG = Logger.getLogger(DebtController.class.getName());
    
    @Autowired
    private DebtService debtService;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Debt fetchSpecificDebt(@PathVariable("id") long id) {
        LOG.log(Level.INFO, "Fetch Specific Debt.");
        
        return this.getDebtService().getSpecificDebt(id);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Debt> getAllDebts() {
        LOG.log(Level.INFO, "Fetch All Debts.");
        
        List<Debt> debts = this.getDebtService().getAllDebts();
        
//        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//        
//        for (Debt debt : debts) {
//            arrayBuilder.add(debt.toJson());
//        }
//        
//        return arrayBuilder.build();

        return debts;
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Debt postDebt(@RequestParam("description") String description,
                        @RequestParam("date") String date,
                        @RequestParam("interest") double interest,
                        @RequestParam("amount") double amount,
                        @RequestParam("type") String type,
                        @RequestParam("currency") String currency) {
        LOG.log(Level.INFO, "Post New Debt.");
        
        Debt debt = new Debt(date, description, type, interest, amount, currency);
        
        if (this.getDebtService().postDebt(debt)) {
            return debt;
        } else {
            return null;
        }
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean removeDebt(@RequestParam("id") long id) {
        LOG.log(Level.INFO, String.format("Deleting Debt with ID %s", id));
        
        return this.getDebtService().deleteDebt(id);
    }
    
    private DebtService getDebtService() {
        if (this.debtService == null) {
            this.debtService = new DebtService();
        }
        
        return this.debtService;
    }
    
    @RequestMapping(value = "{id}/payments/new", method = RequestMethod.POST)
    public Payment postPayment(@RequestParam("date") String date,
                                @RequestParam("spent") double spent,
                                @RequestParam("currency") String currency,
                                @PathVariable("id") long debtID) {
        LOG.log(Level.INFO, "Post New Payment.");
        
        Debt debt = this.debtService.getSpecificDebt(debtID);
        Payment payment = new Payment(date, spent, currency, debt);
        
        if (this.getDebtService().postPayment(payment)) {
            return payment;
        } else {
            return null;
        }
    }
}