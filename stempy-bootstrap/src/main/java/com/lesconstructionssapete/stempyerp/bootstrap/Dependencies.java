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
import com.lesconstructionssapete.stempyerp.repository.retailproduct.RetailProductRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.sequence.SequenceRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.user.UserRepositoryImpl;
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

  private final Container container = new Container();

  public Dependencies() {
    registerInstances();
    bindInfrastructure();
    bindRepositories();
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
  }

  // ---------- Infrastructure ----------

  private void bindInfrastructure() {

    container.bind(ConnectionProvider.class, HikariConnectionProvider.class);
    container.bind(TransactionRunner.class, TransactionManager.class);

    container.bind(EntityNumberGeneratorRegistry.class, DefaultEntityNumberGeneratorRegistry.class);
  }

  // ---------- Repositories ----------

  private void bindRepositories() {

    container.bind(ConstantRepository.class, ConstantRepositoryImpl.class);
    container.bind(RetailProductRepository.class, RetailProductRepositoryImpl.class);
    container.bind(RefreshTokenRepository.class, RefreshTokenRepositoryImpl.class);
    container.bind(UserRepository.class, UserRepositoryImpl.class);
    container.bind(SequenceRepository.class, SequenceRepositoryImpl.class);
  }

  // ---------- Services ----------

  private void bindServices() {

    container.bind(ConstantService.class, ConstantServiceImpl.class);
    container.bind(AuthService.class, AuthServiceImpl.class);
    container.bind(SequenceService.class, SequenceServiceImpl.class);
  }

  // ---------- Cache ----------

  private void bindCache() {

    container.bind(ConstantCache.class, RedisConstantCache.class);
  }

  // ---------- Facades ----------

  private void bindFacades() {

    container.bind(RetailProductFacade.class, RetailProductFacadeImpl.class);
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
