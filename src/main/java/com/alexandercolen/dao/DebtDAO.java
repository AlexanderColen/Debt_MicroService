package com.alexandercolen.dao;

import com.alexandercolen.domain.Debt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alex
 */
@Repository
public interface DebtDAO extends CrudRepository<Debt, Long> { 

}
