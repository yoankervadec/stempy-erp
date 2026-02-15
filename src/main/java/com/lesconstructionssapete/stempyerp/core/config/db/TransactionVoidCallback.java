package com.lesconstructionssapete.stempyerp.core.config.db;

import java.sql.Connection;

/**
 * Functional interface for executing code within a database transaction.
 * This interface is used by the TransactionManager to allow callers to define
 * custom transactional code blocks.
 * The executeInTransaction method takes a Connection object as a parameter,
 * which is provided by the TransactionManager when executing the transaction.
 * The method can throw an exception, which will be handled by the
 * TransactionManager to perform rollback if necessary.
 */

@FunctionalInterface
public interface TransactionVoidCallback {
  void executeInTransaction(Connection connection) throws Exception;
}
