package com.snapcheck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/payments")
// @RestResource(exported = false)
public interface UserRepository extends JpaRepository<Payment, Integer>, QueryDslPredicateExecutor<Payment> {
	// List<Payment> findAll(String startDate, String endDate);
}