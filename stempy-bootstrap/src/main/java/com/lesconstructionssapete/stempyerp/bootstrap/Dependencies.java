package com.lesconstructionssapete.stempyerp.bootstrap;

import java.time.Duration;

import com.lesconstructionssapete.stempyerp.controller.AuthController;
import com.lesconstructionssapete.stempyerp.controller.RetailProductController;
import com.lesconstructionssapete.stempyerp.domain.field.DefaultDomainFieldResolver;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldResolver;
import com.lesconstructionssapete.stempyerp.domain.repository.ConstantRepository;
import com.lesconstructionssapete.stempyerp.domain.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.domain.repository.SequenceRepository;
import com.lesconstructionssapete.stempyerp.domain.repository.UserRepository;
import com.lesconstructionssapete.stempyerp.domain.repository.auth.ApplicationPermissionRepository;
import com.lesconstructionssapete.stempyerp.domain.repository.auth.UserCredentialRepository;
import com.lesconstructionssapete.stempyerp.domain.repository.retailproduct.RetailProductMasterRepository;
import com.lesconstructionssapete.stempyerp.domain.repository.retailproduct.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.facade.impl.authentication.AuthFacadeImpl;
import com.lesconstructionssapete.stempyerp.facade.impl.retailproduct.RetailProductFacadeImpl;
import com.lesconstructionssapete.stempyerp.facade.impl.user.UserFacadeImpl;
import com.lesconstructionssapete.stempyerp.facade.spi.authentication.AuthFacade;
import com.lesconstructionssapete.stempyerp.facade.spi.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.facade.spi.user.UserFacade;
import com.lesconstructionssapete.stempyerp.http.query.RequestQueryMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.HikariConnectionProvider;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.TransactionManager;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.authentication.RefreshTokenRepositoryImpl;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.authentication.UserCredentialRepositoryImpl;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.authorization.ApplicationPermissionRepositoryImpl;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.constant.ConstantRepositoryImpl;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.retailproduct.RetailProductMasterRepositoryImpl;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.retailproduct.RetailProductRepositoryImpl;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.sequence.SequenceRepositoryImpl;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.user.UserRepositoryImpl;
import com.lesconstructionssapete.stempyerp.infrastructure.redis.LettuceRedisCache;
import com.lesconstructionssapete.stempyerp.infrastructure.redis.RedisProvider;
import com.lesconstructionssapete.stempyerp.infrastructure.redis.constant.RedisConstantCache;
import com.lesconstructionssapete.stempyerp.infrastructure.security.JwtTokenProvider;
import com.lesconstructionssapete.stempyerp.infrastructure.security.PBKDF2PasswordProvider;
import com.lesconstructionssapete.stempyerp.port.cache.CacheProvider;
import com.lesconstructionssapete.stempyerp.port.cache.ConstantCache;
import com.lesconstructionssapete.stempyerp.port.persistence.SQLConnectionProvider;
import com.lesconstructionssapete.stempyerp.port.security.PasswordHashProvider;
import com.lesconstructionssapete.stempyerp.port.security.TokenProvider;
import com.lesconstructionssapete.stempyerp.port.transaction.TransactionRunner;
import com.lesconstructionssapete.stempyerp.service.impl.authentication.AuthServiceImpl;
import com.lesconstructionssapete.stempyerp.service.impl.authorization.AuthorizationModule;
import com.lesconstructionssapete.stempyerp.service.impl.authorization.AuthorizationProxyFactory;
import com.lesconstructionssapete.stempyerp.service.impl.constant.ConstantServiceImpl;
import com.lesconstructionssapete.stempyerp.service.impl.retailproduct.RetailProductServiceImpl;
import com.lesconstructionssapete.stempyerp.service.impl.sequence.SequenceServiceImpl;
import com.lesconstructionssapete.stempyerp.service.impl.user.UserServiceImpl;
import com.lesconstructionssapete.stempyerp.service.numbering.DefaultEntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.service.numbering.EntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.service.spi.authentication.AuthService;
import com.lesconstructionssapete.stempyerp.service.spi.authorization.AuthorizationService;
import com.lesconstructionssapete.stempyerp.service.spi.constant.ConstantService;
import com.lesconstructionssapete.stempyerp.service.spi.retailproduct.RetailProductService;
import com.lesconstructionssapete.stempyerp.service.spi.sequence.SequenceService;
import com.lesconstructionssapete.stempyerp.service.spi.user.UserService;

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

    CacheProvider redisCache = new LettuceRedisCache(redisProvider.getConnection().sync());
    container.instance(CacheProvider.class, redisCache);

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

    container.bind(SQLConnectionProvider.class, HikariConnectionProvider.class);
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
        container.get(SQLConnectionProvider.class),
        container.get(ApplicationPermissionRepository.class),
        container.get(CacheProvider.class)));

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
