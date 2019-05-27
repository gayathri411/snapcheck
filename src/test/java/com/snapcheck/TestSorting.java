package com.snapcheck;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;

public class TestSorting {
	private static final Logger logger = Logger.getLogger(TestSorting.class.getName());
	private PaymentDataManager paymentDataManager = new PaymentDataManager(false);
	private SortPayments sortingAlgorithms = new SortPayments();

	@Before(value = "")
	public void setUp() {
		paymentDataManager.clearDbTable();
	}

	// $120,000
	@After(value = "")
	public void tearDown() {

	}

	@Test
	public void testSortingAlgorithm1() {
		populateDbTableWithRandomData();
		logger.info("---------- Read from DB ----------");
		List<Payment> paymentList = paymentDataManager.getDataFromDB();

		int length = paymentList.size();
		long start = System.currentTimeMillis();
		sortingAlgorithms.quickSort(paymentList, 0, length - 1);

		System.out.println("***** Quicksort took " + (System.currentTimeMillis() - start) + "ms ******");
		sortingAlgorithms.printList(paymentList);

		validateSorting(paymentList);
	}

	private void validateSorting(List<Payment> paymentList) {
		for (int i = 0; i < paymentList.size() - 1; i++) {
			org.junit.Assert.assertTrue(paymentList.get(i).getDate().compareTo(paymentList.get(i + 1).getDate()) < 0);
		}
	}

	private void populateDbTableWithRandomData() {
		logger.info("---------Populate the Database with random data-----------");
		for (int i = 0; i < 1000; i++) {
			Date randomDate = paymentDataManager.createRandomDate(2000, 2018);
			paymentDataManager.addDataToDB(3000.00 + i * 200, randomDate);
		}
	}

	@Test
	public void testSortingAlgorithm2() {
		populateDbTableWithRandomData();
		logger.info("---------- Read from DB ----------");
		List<Payment> paymentList = paymentDataManager.getDataFromDB();

		long start = System.currentTimeMillis();
		sortingAlgorithms.insertionSort(paymentList);

		System.out.println("***** Insertion sort took " + (System.currentTimeMillis() - start) + "ms ******");
		sortingAlgorithms.printList(paymentList);

		validateSorting(paymentList);
	}
}
