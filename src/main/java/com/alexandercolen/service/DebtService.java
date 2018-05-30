package com.alexandercolen.service;

import com.alexandercolen.dao.DebtDAO;
import com.alexandercolen.domain.Debt;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander
 */
@Service
public class DebtService {

    private static final Logger LOG = Logger.getLogger(DebtService.class.getName());
    
    private static final String URL = "http://localhost:8080/debts";
        
    @Autowired
    DataSource dataSource;
    
    @Autowired
    DebtDAO debtDAO;

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
        
        return debts;
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
}