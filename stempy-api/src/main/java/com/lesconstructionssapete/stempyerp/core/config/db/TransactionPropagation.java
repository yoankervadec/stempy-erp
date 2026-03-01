package com.lesconstructionssapete.stempyerp.core.config.db;

public enum TransactionPropagation {
  REQUIRED, // Join existing transaction or create new if none exists
  REQUIRES_NEW, // Always create a new transaction, suspending any existing one
  SUPPORTS, // Join existing transaction if exists, otherwise fail
}
