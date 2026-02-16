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
import com.lesconstructionssapete.stempyerp.core.repository.base.constant.ConstantRepositoryImpl;
import com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct.RetailProductRepositoryImpl;
import com.lesconstructionssapete.stempyerp.core.repository.base.sequence.SequenceRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.sequence.SequenceRepositoryImpl;
import com.lesconstructionssapete.stempyerp.core.repository.base.user.UserRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.user.UserRepositoryImpl;
import com.lesconstructionssapete.stempyerp.core.service.base.constant.ConstantService;
import com.lesconstructionssapete.stempyerp.core.service.base.constant.ConstantServiceImpl;
import com.lesconstructionssapete.stempyerp.core.service.base.sequence.SequenceService;
import com.lesconstructionssapete.stempyerp.core.service.base.sequence.SequenceServiceImpl;

public class Dependencies {

  // Repositories
  public final ConstantRepository constantRepository;
  public final RetailProductRepository retailProductRepository;
  public final RefreshTokenRepository refreshTokenRepository;
  public final UserRepository userRepository;
  public final SequenceRepository sequenceRepository;

  // Services
  public final ConstantService constantService;
  public final SequenceService sequenceService;

  // Facades
  public final RetailProductFacade retailProductFacade;
  public final AuthFacade authFacade;
  public final UserFacade userFacade;

  // Controllers
  public final RetailProductController retailProductController;
  public final AuthController authController;

  public Dependencies() {
    // Repositories
    this.constantRepository = new ConstantRepositoryImpl();
    this.retailProductRepository = new RetailProductRepositoryImpl();
    this.refreshTokenRepository = new RefreshTokenRepositoryImpl();
    this.userRepository = new UserRepositoryImpl();
    this.sequenceRepository = new SequenceRepositoryImpl();

    // Services
    this.constantService = new ConstantServiceImpl(constantRepository);
    this.sequenceService = new SequenceServiceImpl(sequenceRepository);

    // Facades
    this.retailProductFacade = new RetailProductFacadeImpl(sequenceService, retailProductRepository);
    this.authFacade = new AuthFacadeImpl(refreshTokenRepository);
    this.userFacade = new UserFacadeImpl(userRepository);

    // Controllers
    this.retailProductController = new RetailProductController(retailProductFacade);
    this.authController = new AuthController(userFacade, authFacade);

  }
}
