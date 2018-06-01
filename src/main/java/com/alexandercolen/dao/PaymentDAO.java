package com.alexandercolen.dao;

import com.alexandercolen.domain.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander
 */
@Repository
public interface PaymentDAO extends CrudRepository<Payment, Long> { 

}