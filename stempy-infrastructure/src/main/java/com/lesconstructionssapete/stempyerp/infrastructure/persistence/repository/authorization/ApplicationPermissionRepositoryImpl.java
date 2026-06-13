package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.authorization;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermissionSet;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRolePermissionSet;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationUserPermissionSet;
import com.lesconstructionssapete.stempyerp.domain.auth.UserRole;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.auth.ApplicationPermissionRepository;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth.ApplicationPermissionMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth.ApplicationRoleMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth.ApplicationRolePermissionSetMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth.ApplicationUserPermissionSetMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth.UserRoleMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.SQLExecutor;
import com.lesconstructionssapete.stempyerp.infrastructure.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.infrastructure.query.Query;
import com.lesconstructionssapete.stempyerp.infrastructure.query.QueryCache;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public class ApplicationPermissionRepositoryImpl implements ApplicationPermissionRepository {

  @Override
  public List<ApplicationPermission> fetchApplicationPermissions(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_PERMISSION);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<ApplicationPermission.Fields> translator = new DomainQuerySQLTranslator<>(
        ApplicationPermission.Fields.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationPermission> list = new ArrayList<>();
          while (rs.next()) {
            list.add(ApplicationPermissionMapper.fromResultSet(rs));
          }
          return list;
        });
  }

  @Override
  public List<ApplicationRole> fetchApplicationRoles(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_ROLE);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<ApplicationRole.Fields> translator = new DomainQuerySQLTranslator<>(
        ApplicationRole.Fields.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationRole> list = new ArrayList<>();
          while (rs.next()) {
            list.add(ApplicationRoleMapper.fromResultSet(rs));
          }
          return list;
        });
  }

  @Override
  public List<ApplicationRole> fetchUserApplicationRoles(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_USER_ROLE);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<UserRole.Fields> translator = new DomainQuerySQLTranslator<>(
        UserRole.Fields.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationRole> list = new ArrayList<>();
          while (rs.next()) {
            list.add(UserRoleMapper.fromResultSet(rs));
          }
          return list;
        });
  }

  @Override
  public List<ApplicationPermissionSet> fetchRolePermissions(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_ROLE_PERMISSION);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<ApplicationRolePermissionSet.Fields> translator = new DomainQuerySQLTranslator<>(
        ApplicationRolePermissionSet.Fields.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationPermissionSet> list = new ArrayList<>();
          while (rs.next()) {
            list.add(ApplicationRolePermissionSetMapper.fromResultSet(rs));
          }
          return list;
        });
  }

  @Override
  public List<ApplicationPermissionSet> fetchUserPermissions(Connection connection, DomainQuery query) {
    String sql = QueryCache.get(Query.SELECT_AUTH_USER_PERMISSION);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<ApplicationUserPermissionSet.Fields> translator = new DomainQuerySQLTranslator<>(
        ApplicationUserPermissionSet.Fields.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationPermissionSet> list = new ArrayList<>();
          while (rs.next()) {
            list.add(ApplicationUserPermissionSetMapper.fromResultSet(rs));
          }
          return list;
        });
  }

}
