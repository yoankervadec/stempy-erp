package com.lesconstructionssapete.stempyerp.app;

import com.lesconstructionssapete.stempyerp.app.controller.AuthController;
import com.lesconstructionssapete.stempyerp.app.controller.RetailProductController;
import com.lesconstructionssapete.stempyerp.app.facade.base.auth.AuthFacade;
import com.lesconstructionssapete.stempyerp.app.facade.base.auth.AuthFacadeImpl;
import com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct.RetailProductFacadeImpl;
import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacadeImpl;
import com.lesconstructionssapete.stempyerp.core.config.redis.RedisProvider;
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
import com.lesconstructionssapete.stempyerp.core.service.base.sequence.numbering.DefaultEntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.core.service.base.sequence.numbering.EntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantCache;

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

    // Cache
    this.constantCache = new ConstantCache(redisProvider, constantService);

    // Other Services
    this.sequenceService = new SequenceServiceImpl(sequenceRepository, constantCache);

    // Facades
    this.retailProductFacade = new RetailProductFacadeImpl(sequenceService, entityNumberGeneratorRegistry,
        retailProductRepository);
    this.authFacade = new AuthFacadeImpl(refreshTokenRepository);
    this.userFacade = new UserFacadeImpl(userRepository);

    // Controllers
    this.retailProductController = new RetailProductController(retailProductFacade);
    this.authController = new AuthController(userFacade, authFacade);
  }
}
