package com.lesconstructionssapete.stempyerp.app;

import com.lesconstructionssapete.stempyerp.app.controller.AuthController;
import com.lesconstructionssapete.stempyerp.app.controller.RetailProductController;
import com.lesconstructionssapete.stempyerp.app.facade.base.auth.AuthFacade;
import com.lesconstructionssapete.stempyerp.app.facade.base.auth.AuthFacadeImpl;
import com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct.RetailProductFacadeImpl;
import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacadeImpl;
import com.lesconstructionssapete.stempyerp.core.repository.base.auth.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.auth.RefreshTokenRepositoryImpl;
import com.lesconstructionssapete.stempyerp.core.repository.base.constant.ConstantRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct.RetailProductRepositoryImpl;
import com.lesconstructionssapete.stempyerp.core.repository.base.user.UserRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.user.UserRepositoryImpl;

public class Dependencies {

  // Repositories
  public final ConstantRepository constantRepository;
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
    this.constantRepository = new ConstantRepository();
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
