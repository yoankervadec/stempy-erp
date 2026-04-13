package com.lesconstructionssapete.stempyerp.port.transaction;

public interface TransactionRunner {

  <T> T execute(
      TransactionPropagation propagation,
      TransactionCallback<T> callback);

  void executeVoid(
      TransactionPropagation propagation,
      TransactionVoidCallback callback);

}
