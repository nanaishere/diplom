package ru.netology.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



    public class DbHelper {
        public static Connection getConnection() throws SQLException {
            String url = System.getProperty("db.url");
            String username = System.getProperty("db.user");
            String password = System.getProperty("db.password");
            try {
                return DriverManager.getConnection(url, username, password);
            } catch (SQLException err) {
                err.printStackTrace();
            }
            return null;
        }


        @SneakyThrows
        public static void cleanDb() {
            val deleteCreditRequest = "DELETE FROM credit_request_entity";
            val deleteOrderEntity = "DELETE FROM order_entity";
            val deletePaymentEntity = "DELETE FROM payment_entity";
            val runner = new QueryRunner();
            try (val conn = getConnection()
            ) {
                runner.update(conn, deleteCreditRequest);
                runner.update(conn, deleteOrderEntity);
                runner.update(conn, deletePaymentEntity);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        @SneakyThrows
        public static String getPaymentEntity() {
            try (val conn = getConnection();
                 val countStmt = conn.createStatement()) {
                val paymentStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
                val resultSet = countStmt.executeQuery(paymentStatus);
                if (resultSet.next()) {
                    return resultSet.getString("status");
                }
            } catch (SQLException err) {
                err.printStackTrace();
            }
            return null;
        }

        @SneakyThrows
        public static String getCreditEntity() {
            try (val conn = getConnection();
                 val countStmt = conn.createStatement()) {
                val creditStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
                val resultSet = countStmt.executeQuery(creditStatus);
                if (resultSet.next()) {
                    return resultSet.getString("status");
                }
            } catch (SQLException err) {
                err.printStackTrace();
            }
            return null;
        }
}