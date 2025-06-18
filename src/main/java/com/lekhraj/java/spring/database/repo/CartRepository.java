package com.lekhraj.java.spring.database.repo;

import com.lekhraj.java.spring.database.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//@Transactional(transactionManager = "transactionManager_for_postgres")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long>
{
    /*@Query("select cart from Cart c where c.c")
    Cart findByCustomer(Customer customer);*/
}
