package com.lesconstructionssapete.stempyerp;

import com.lesconstructionssapete.stempyerp.app.controller.AuthController;
import com.lesconstructionssapete.stempyerp.app.controller.RetailProductController;
import com.lesconstructionssapete.stempyerp.config.redis.RedisProvider;
import com.lesconstructionssapete.stempyerp.constant.ConstantCache;
import com.lesconstructionssapete.stempyerp.facade.base.auth.AuthFacade;
import com.lesconstructionssapete.stempyerp.facade.base.auth.AuthFacadeImpl;
import com.lesconstructionssapete.stempyerp.facade.base.auth.UserFacade;
import com.lesconstructionssapete.stempyerp.facade.base.auth.UserFacadeImpl;
import com.lesconstructionssapete.stempyerp.facade.base.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.facade.base.retailproduct.RetailProductFacadeImpl;
import com.lesconstructionssapete.stempyerp.repository.ConstantRepository;
import com.lesconstructionssapete.stempyerp.repository.ConstantRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.repository.RetailProductRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.SequenceRepository;
import com.lesconstructionssapete.stempyerp.repository.SequenceRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.UserRepository;
import com.lesconstructionssapete.stempyerp.repository.UserRepositoryImpl;
import com.lesconstructionssapete.stempyerp.service.base.auth.AuthService;
import com.lesconstructionssapete.stempyerp.service.base.auth.AuthServiceImpl;
import com.lesconstructionssapete.stempyerp.service.base.constant.ConstantService;
import com.lesconstructionssapete.stempyerp.service.base.constant.ConstantServiceImpl;
import com.lesconstructionssapete.stempyerp.service.base.sequence.SequenceService;
import com.lesconstructionssapete.stempyerp.service.base.sequence.SequenceServiceImpl;
import com.lesconstructionssapete.stempyerp.service.base.sequence.numbering.DefaultEntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.service.base.sequence.numbering.EntityNumberGeneratorRegistry;

public class Dependencies {

  // Infrastructure
  public final RedisProvider redisProvider;

  // Repositories
  public final ConstantRepository constantRepository;
  public final RetailProductRepository retailProductRepository;
  public final RefreshTokenRepository refreshTokenRepository;
  public final UserRepository userRepository;
  public final SequenceRepository sequenceRepository;

  // Services
  public final EntityNumberGeneratorRegistry entityNumberGeneratorRegistry;
  public final ConstantService constantService;
  public final AuthService authService;

  // Cache
  public final ConstantCache constantCache;

  // Other Services
  public final SequenceService sequenceService;

  // Facades
  public final RetailProductFacade retailProductFacade;
  public final AuthFacade authFacade;
  public final UserFacade userFacade;

  // Controllers
  public final RetailProductController retailProductController;
  public final AuthController authController;

  public Dependencies() {

    // Infrastructure
    this.redisProvider = new RedisProvider();

    // Repositories
    this.constantRepository = new ConstantRepositoryImpl();
    this.retailProductRepository = new RetailProductRepositoryImpl();
    this.refreshTokenRepository = new RefreshTokenRepositoryImpl();
    this.userRepository = new UserRepositoryImpl();
    this.sequenceRepository = new SequenceRepositoryImpl();

    // Services
    this.entityNumberGeneratorRegistry = new DefaultEntityNumberGeneratorRegistry();
    this.constantService = new ConstantServiceImpl(constantRepository);
    this.authService = new AuthServiceImpl(refreshTokenRepository, userRepository);
    // Cache
    this.constantCache = new ConstantCache(redisProvider, constantService);

    // Other Services
    this.sequenceService = new SequenceServiceImpl(sequenceRepository, constantCache);

    // Facades
    this.retailProductFacade = new RetailProductFacadeImpl(sequenceService, entityNumberGeneratorRegistry,
        retailProductRepository);
    this.authFacade = new AuthFacadeImpl(refreshTokenRepository, userRepository, authService);
    this.userFacade = new UserFacadeImpl(userRepository);

    // Controllers
    this.retailProductController = new RetailProductController(retailProductFacade);
    this.authController = new AuthController(userFacade, authFacade);
  }
}
