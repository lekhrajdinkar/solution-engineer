package com.lekhraj.java.spring.database.repo;


import com.lekhraj.java.spring.database.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//@Transactional(transactionManager = "transactionManager_for_postgres")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

