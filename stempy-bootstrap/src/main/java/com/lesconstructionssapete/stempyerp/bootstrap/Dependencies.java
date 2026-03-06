package com.lesconstructionssapete.stempyerp.bootstrap;

import java.time.Duration;

import com.lesconstructionssapete.stempyerp.cache.RedisCache;
import com.lesconstructionssapete.stempyerp.config.db.HikariConnectionProvider;
import com.lesconstructionssapete.stempyerp.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.config.redis.LettuceRedisCache;
import com.lesconstructionssapete.stempyerp.config.redis.RedisProvider;
import com.lesconstructionssapete.stempyerp.constant.ConstantCache;
import com.lesconstructionssapete.stempyerp.controller.AuthController;
import com.lesconstructionssapete.stempyerp.controller.RetailProductController;
import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;
import com.lesconstructionssapete.stempyerp.facade.auth.AuthFacade;
import com.lesconstructionssapete.stempyerp.facade.auth.AuthFacadeImpl;
import com.lesconstructionssapete.stempyerp.facade.auth.UserFacade;
import com.lesconstructionssapete.stempyerp.facade.auth.UserFacadeImpl;
import com.lesconstructionssapete.stempyerp.facade.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.facade.retailproduct.RetailProductFacadeImpl;
import com.lesconstructionssapete.stempyerp.repository.ConstantRepository;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.repository.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.repository.SequenceRepository;
import com.lesconstructionssapete.stempyerp.repository.UserRepository;
import com.lesconstructionssapete.stempyerp.repository.auth.RefreshTokenRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.constant.ConstantRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.sequence.SequenceRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.user.UserRepositoryImpl;
import com.lesconstructionssapete.stempyerp.retailproduct.RetailProductRepositoryImpl;
import com.lesconstructionssapete.stempyerp.security.JwtTokenProvider;
import com.lesconstructionssapete.stempyerp.security.TokenProvider;
import com.lesconstructionssapete.stempyerp.service.auth.AuthService;
import com.lesconstructionssapete.stempyerp.service.auth.AuthServiceImpl;
import com.lesconstructionssapete.stempyerp.service.constant.ConstantService;
import com.lesconstructionssapete.stempyerp.service.constant.ConstantServiceImpl;
import com.lesconstructionssapete.stempyerp.service.sequence.SequenceService;
import com.lesconstructionssapete.stempyerp.service.sequence.SequenceServiceImpl;
import com.lesconstructionssapete.stempyerp.service.sequence.numbering.DefaultEntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.service.sequence.numbering.EntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.transaction.TransactionRunner;

import io.github.cdimascio.dotenv.Dotenv;

public class Dependencies {

  Dotenv dotenv = Dotenv.load();

  // Infrastructure
  public final RedisProvider redisProvider;

  public final ConnectionProvider connectionProvider;

  public final TransactionRunner transactionRunner;

  // Repositories
  public final ConstantRepository constantRepository;
  public final RetailProductRepository retailProductRepository;
  public final RefreshTokenRepository refreshTokenRepository;
  public final UserRepository userRepository;
  public final SequenceRepository sequenceRepository;

  // Services
  public final TokenProvider tokenProvider;
  public final RedisCache redisCache;
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

    this.connectionProvider = new HikariConnectionProvider();

    this.transactionRunner = new TransactionManager(connectionProvider);

    // Repositories
    this.constantRepository = new ConstantRepositoryImpl();
    this.retailProductRepository = new RetailProductRepositoryImpl();
    this.refreshTokenRepository = new RefreshTokenRepositoryImpl();
    this.userRepository = new UserRepositoryImpl();
    this.sequenceRepository = new SequenceRepositoryImpl();

    // Services
    this.tokenProvider = new JwtTokenProvider(
        dotenv.get("JWT_SECRET"),
        Duration.ofMinutes(30).toMillis(),
        Duration.ofDays(7).toMillis());
    this.redisCache = new LettuceRedisCache(redisProvider.getConnection().sync());
    this.entityNumberGeneratorRegistry = new DefaultEntityNumberGeneratorRegistry();
    this.constantService = new ConstantServiceImpl(connectionProvider, constantRepository);
    this.authService = new AuthServiceImpl(refreshTokenRepository, userRepository);
    // Cache
    this.constantCache = new ConstantCache(redisCache, constantService);

    // Other Services
    this.sequenceService = new SequenceServiceImpl(sequenceRepository, constantCache);

    // Facades
    this.retailProductFacade = new RetailProductFacadeImpl(
        transactionRunner,
        sequenceService,
        entityNumberGeneratorRegistry,
        retailProductRepository);
    this.userFacade = new UserFacadeImpl(transactionRunner, userRepository);
    this.authFacade = new AuthFacadeImpl(
        transactionRunner,
        refreshTokenRepository,
        tokenProvider,
        userFacade,
        authService);

    // Controllers
    this.retailProductController = new RetailProductController(retailProductFacade);
    this.authController = new AuthController(userFacade, authFacade);
  }
}
