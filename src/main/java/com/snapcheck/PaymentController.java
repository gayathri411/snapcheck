package com.snapcheck;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;

@RestController
@RequestMapping(path = "/payments")
public class PaymentController {
	private static final Logger logger = Logger.getLogger(PaymentController.class.getName());
	@Autowired
	private UserRepository repository;

	@GetMapping
	public Iterable<Payment> get(@RequestParam(name = "start", required = false) String start,
			@RequestParam(name = "end", required = false) String end) {
		logger.info("Start date is " + start + " End date is " + end);
		BooleanExpression booleanExpression = QPayment.payment.date.goe(start).and(QPayment.payment.date.loe(end));
		Iterable<Payment> payments = repository.findAll(booleanExpression);
		// List<Payment> payments = new ArrayList<>();
		return payments;
	}
}
