package com.lesconstructions.sapete.stempyerp.app;

import com.lesconstructions.sapete.stempyerp.app.controller.AuthController;
import com.lesconstructions.sapete.stempyerp.app.controller.RetailProductController;
import com.lesconstructions.sapete.stempyerp.app.facade.base.auth.AuthFacade;
import com.lesconstructions.sapete.stempyerp.app.facade.base.auth.AuthFacadeImpl;
import com.lesconstructions.sapete.stempyerp.app.facade.base.retailproduct.RetailProductFacade;
import com.lesconstructions.sapete.stempyerp.app.facade.base.retailproduct.RetailProductFacadeImpl;
import com.lesconstructions.sapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructions.sapete.stempyerp.app.facade.base.user.UserFacadeImpl;
import com.lesconstructions.sapete.stempyerp.core.repository.base.auth.RefreshTokenRepository;
import com.lesconstructions.sapete.stempyerp.core.repository.base.auth.RefreshTokenRepositoryImpl;
import com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct.RetailProductRepository;
import com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct.RetailProductRepositoryImpl;
import com.lesconstructions.sapete.stempyerp.core.repository.base.user.UserRepository;
import com.lesconstructions.sapete.stempyerp.core.repository.base.user.UserRepositoryImpl;

public class Dependencies {

  // Repositories
  public final RetailProductRepository retailProductRepository;
  public final RefreshTokenRepository refreshTokenRepository;
  public final UserRepository userRepository;

  // Services
  public final RetailProductFacade retailProductService;
  public final AuthFacade authFacade;
  public final UserFacade userFacade;

  // Controllers
  public final RetailProductController retailProductController;
  public final AuthController authController;

  public Dependencies() {
    // Repositories
    this.retailProductRepository = new RetailProductRepositoryImpl();
    this.refreshTokenRepository = new RefreshTokenRepositoryImpl();
    this.userRepository = new UserRepositoryImpl();

    // Services
    this.retailProductService = new RetailProductFacadeImpl(retailProductRepository);
    this.authFacade = new AuthFacadeImpl(refreshTokenRepository);
    this.userFacade = new UserFacadeImpl(userRepository);

    // Controllers
    this.retailProductController = new RetailProductController(retailProductService);
    this.authController = new AuthController(userFacade, authFacade);
  }
}
