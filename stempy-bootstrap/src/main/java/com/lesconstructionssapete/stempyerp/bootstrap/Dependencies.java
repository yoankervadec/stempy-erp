package com.lesconstructionssapete.stempyerp.bootstrap;

import java.time.Duration;

import com.lesconstructionssapete.stempyerp.cache.RedisCache;
import com.lesconstructionssapete.stempyerp.config.db.HikariConnectionProvider;
import com.lesconstructionssapete.stempyerp.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.config.redis.LettuceRedisCache;
import com.lesconstructionssapete.stempyerp.config.redis.RedisProvider;
import com.lesconstructionssapete.stempyerp.constant.ConstantCache;
import com.lesconstructionssapete.stempyerp.constant.RedisConstantCache;
import com.lesconstructionssapete.stempyerp.controller.AuthController;
import com.lesconstructionssapete.stempyerp.controller.RetailProductController;
import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;
import com.lesconstructionssapete.stempyerp.facade.auth.AuthFacade;
import com.lesconstructionssapete.stempyerp.facade.auth.AuthFacadeImpl;
import com.lesconstructionssapete.stempyerp.facade.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.facade.retailproduct.RetailProductFacadeImpl;
import com.lesconstructionssapete.stempyerp.facade.user.UserFacade;
import com.lesconstructionssapete.stempyerp.facade.user.UserFacadeImpl;
import com.lesconstructionssapete.stempyerp.field.DefaultDomainFieldResolver;
import com.lesconstructionssapete.stempyerp.field.DomainFieldResolver;
import com.lesconstructionssapete.stempyerp.http.query.RequestQueryMapper;
import com.lesconstructionssapete.stempyerp.repository.ConstantRepository;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.repository.SequenceRepository;
import com.lesconstructionssapete.stempyerp.repository.UserRepository;
import com.lesconstructionssapete.stempyerp.repository.auth.ApplicationPermissionRepository;
import com.lesconstructionssapete.stempyerp.repository.auth.ApplicationPermissionRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.auth.RefreshTokenRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.auth.UserCredentialRepository;
import com.lesconstructionssapete.stempyerp.repository.auth.UserCredentialRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.constant.ConstantRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.retailproduct.RetailProductMasterRepository;
import com.lesconstructionssapete.stempyerp.repository.retailproduct.RetailProductMasterRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.retailproduct.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.repository.retailproduct.RetailProductRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.sequence.SequenceRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.user.UserRepositoryImpl;
import com.lesconstructionssapete.stempyerp.security.JwtTokenProvider;
import com.lesconstructionssapete.stempyerp.security.PBKDF2PasswordProvider;
import com.lesconstructionssapete.stempyerp.security.PasswordHashProvider;
import com.lesconstructionssapete.stempyerp.security.TokenProvider;
import com.lesconstructionssapete.stempyerp.service.auth.AuthService;
import com.lesconstructionssapete.stempyerp.service.auth.AuthServiceImpl;
import com.lesconstructionssapete.stempyerp.service.auth.AuthorizationModule;
import com.lesconstructionssapete.stempyerp.service.auth.AuthorizationProxyFactory;
import com.lesconstructionssapete.stempyerp.service.auth.AuthorizationService;
import com.lesconstructionssapete.stempyerp.service.constant.ConstantService;
import com.lesconstructionssapete.stempyerp.service.constant.ConstantServiceImpl;
import com.lesconstructionssapete.stempyerp.service.retailproduct.RetailProductService;
import com.lesconstructionssapete.stempyerp.service.retailproduct.RetailProductServiceImpl;
import com.lesconstructionssapete.stempyerp.service.sequence.SequenceService;
import com.lesconstructionssapete.stempyerp.service.sequence.SequenceServiceImpl;
import com.lesconstructionssapete.stempyerp.service.sequence.numbering.DefaultEntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.service.sequence.numbering.EntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.service.user.UserService;
import com.lesconstructionssapete.stempyerp.service.user.UserServiceImpl;
import com.lesconstructionssapete.stempyerp.transaction.TransactionRunner;

import io.github.cdimascio.dotenv.Dotenv;

public class Dependencies {

  private final Container container = new Container();
  private final ServiceFactory serviceFactory;

  public Dependencies() {
    registerInstances();
    bindInfrastructure();
    bindRepositories();
    bindAuthorization();

    this.serviceFactory = new ServiceFactory(
        container,
        container.get(AuthorizationProxyFactory.class));

    bindServices();
    bindCache();
    bindFacades();

  }

  public Container container() {
    return container;
  }

  // ---------- Manual Instances ----------

  private void registerInstances() {

    Dotenv dotenv = Dotenv.load();
    container.instance(Dotenv.class, dotenv);

    RedisProvider redisProvider = new RedisProvider();
    container.instance(RedisProvider.class, redisProvider);

    RedisCache redisCache = new LettuceRedisCache(redisProvider.getConnection().sync());
    container.instance(RedisCache.class, redisCache);

    TokenProvider tokenProvider = new JwtTokenProvider(
        dotenv.get("JWT_SECRET"),
        Duration.ofMinutes(30).toMillis(),
        Duration.ofDays(7).toMillis());

    container.instance(TokenProvider.class, tokenProvider);

    container.bind(DomainFieldResolver.class, DefaultDomainFieldResolver.class);

    container.instance(RequestQueryMapper.class, new RequestQueryMapper(container.get(DomainFieldResolver.class)));

  }

  // ---------- Infrastructure ----------

  private void bindInfrastructure() {

    container.bind(ConnectionProvider.class, HikariConnectionProvider.class);
    container.bind(TransactionRunner.class, TransactionManager.class);

    container.bind(EntityNumberGeneratorRegistry.class, DefaultEntityNumberGeneratorRegistry.class);
  }

  // ---------- Repositories ----------

  private void bindRepositories() {

    // Constant
    container.bind(ConstantRepository.class, ConstantRepositoryImpl.class);

    // Retail Product
    container.bind(RetailProductRepository.class, RetailProductRepositoryImpl.class);
    container.bind(RetailProductMasterRepository.class, RetailProductMasterRepositoryImpl.class);

    // Auth / User
    container.bind(RefreshTokenRepository.class, RefreshTokenRepositoryImpl.class);
    container.bind(UserCredentialRepository.class, UserCredentialRepositoryImpl.class);
    container.bind(UserRepository.class, UserRepositoryImpl.class);
    container.bind(ApplicationPermissionRepository.class, ApplicationPermissionRepositoryImpl.class);

    // Security
    container.bind(PasswordHashProvider.class, PBKDF2PasswordProvider.class);

    // Sequence
    container.bind(SequenceRepository.class, SequenceRepositoryImpl.class);
  }

  // ---------- Authorization ----------

  private void bindAuthorization() {
    container.instance(AuthorizationService.class, AuthorizationModule.initialize(
        container.get(ConnectionProvider.class),
        container.get(ApplicationPermissionRepository.class),
        container.get(RedisCache.class)));

    container.instance(
        AuthorizationProxyFactory.class,
        new AuthorizationProxyFactory(container.get(AuthorizationService.class)));
  }

  // ---------- Services ----------

  private void bindServices() {

    // Constant
    container.bind(ConstantService.class, ConstantServiceImpl.class);

    // Retail Product
    container.instance(
        RetailProductService.class,
        serviceFactory.secured(RetailProductService.class, RetailProductServiceImpl.class));

    // Auth / User
    container.bind(AuthService.class, AuthServiceImpl.class);
    container.bind(UserService.class, UserServiceImpl.class);

    // Sequence
    container.bind(SequenceService.class, SequenceServiceImpl.class);
  }

  // ---------- Cache ----------

  private void bindCache() {

    container.bind(ConstantCache.class, RedisConstantCache.class);
  }

  // ---------- Facades ----------

  private void bindFacades() {

    // Retail Product
    container.bind(RetailProductFacade.class, RetailProductFacadeImpl.class);

    // Auth / User
    container.bind(UserFacade.class, UserFacadeImpl.class);
    container.bind(AuthFacade.class, AuthFacadeImpl.class);
  }

  // ---------- Controllers ----------

  public AuthController authController() {
    return container.get(AuthController.class);
  }

  public RetailProductController retailProductController() {
    return container.get(RetailProductController.class);
  }
}
