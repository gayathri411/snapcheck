package com.snapcheck;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

public class PaymentDataManager {
	private static final Logger logger = Logger.getLogger(PaymentDataManager.class.getName());
	private Connection conn = null;

	public PaymentDataManager(boolean clearDB) {
		conn = openDatabaseConnection();
		if (clearDB) {
			clearDbTable();
		}
	}

	private Connection openDatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			logger.info("MySQL JDBC Driver Registered");
		} catch (ClassNotFoundException e) {
			logger.error("Couldn't found JDBC driver");
			e.printStackTrace();
			return null;
		}
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/snapcheck", "root", "root");
			if (conn != null) {
				logger.info("Connection Successful!");
			} else {
				logger.error("Failed to make connection!");
			}
			return conn;
		} catch (SQLException e) {
			logger.error("MySQL Connection Failed!");
			e.printStackTrace();
			return null;
		}
	}

	private void closeStatement(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void closeDatabaseConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearDbTable() {
		PreparedStatement statement = null;
		try {
			String clearQuery = "DELETE FROM payment";
			statement = conn.prepareStatement(clearQuery);
			// execute delete SQL statement
			statement.executeUpdate();
			// logger.info("Cleared the database table successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(statement);
		}
	}

	public void addDataToDB(double amount, Date randomDate) {
		PreparedStatement statement = null;
		try {
			String insertQueryStatement = "INSERT  INTO  payment (amount, date)  VALUES  (?,?)";
			statement = conn.prepareStatement(insertQueryStatement);
			statement.setDouble(1, amount);
			statement.setDate(2, randomDate);
			// execute insert SQL statement
			statement.executeUpdate();
			// logger.info(amount + " added successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(statement);
		}
	}

	public Date createRandomDate(int startYear, int endYear) {
		int day = createRandomIntBetween(1, 28);
		int month = createRandomIntBetween(1, 12);
		int year = createRandomIntBetween(startYear, endYear) - 1900;
		return new Date(year, month, day);
	}

	private static int createRandomIntBetween(int start, int end) {
		Random rand = new Random();
		int delta = rand.nextInt(end - start);
		// System.out.println("start = " + start + " delta = " + delta + " end =
		// " + end + " output = " + (start + delta));
		return start + delta;
	}

	private static Date convertToSQLDate(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateStr = formatter.parse(date);
		java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
		return dateDB;
	}

	public List<Payment> getDataFromDB() {
		List<Payment> paymentList = new ArrayList<>();
		PreparedStatement statement = null;
		try {
			String getQueryStatement = "SELECT * FROM payment";
			statement = conn.prepareStatement(getQueryStatement);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Payment p = new Payment();
				double amount = rs.getDouble("Amount");
				p.setAmount(amount);
				String date = rs.getString("Date");
				p.setDate(date);
				// System.out.println(amount + ", " + date);
				paymentList.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(statement);
		}
		return paymentList;
	}
}