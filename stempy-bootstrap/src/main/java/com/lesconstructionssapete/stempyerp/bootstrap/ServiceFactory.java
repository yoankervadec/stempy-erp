package com.lesconstructionssapete.stempyerp.bootstrap;

import com.lesconstructionssapete.stempyerp.service.auth.AuthorizationProxyFactory;

public class ServiceFactory {

  private final Container container;
  private final AuthorizationProxyFactory authorizationProxyFactory;

  public ServiceFactory(
      Container container,
      AuthorizationProxyFactory authorizationProxyFactory) {
    this.container = container;
    this.authorizationProxyFactory = authorizationProxyFactory;
  }

  public <T> T secured(Class<T> iface, Class<? extends T> impl) {
    T raw = container.get(impl);
    return authorizationProxyFactory.create(raw, iface);
  }

}
