package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.authorization;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermissionSet;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.domain.field.auth.ApplicationPermissionField;
import com.lesconstructionssapete.stempyerp.domain.field.auth.ApplicationRoleField;
import com.lesconstructionssapete.stempyerp.domain.field.auth.ApplicationRolePermissionSetField;
import com.lesconstructionssapete.stempyerp.domain.field.auth.ApplicationUserPermissionSetField;
import com.lesconstructionssapete.stempyerp.domain.field.auth.ApplicationUserRoleField;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.auth.ApplicationPermissionRepository;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.ResultSetMapper;
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

    DomainQuerySQLTranslator<ApplicationPermissionField> translator = new DomainQuerySQLTranslator<>(
        ApplicationPermissionField.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationPermission> list = new ArrayList<>();
          ResultSetMapper<ApplicationPermission> rsMapper = new ResultSetMapper<>(ApplicationPermission.class);
          while (rs.next()) {
            try {
              list.add(rsMapper.mapRow(rs));
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          return list;
        });
  }

  @Override
  public List<ApplicationRole> fetchApplicationRoles(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_ROLE);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<ApplicationRoleField> translator = new DomainQuerySQLTranslator<>(
        ApplicationRoleField.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationRole> list = new ArrayList<>();
          ResultSetMapper<ApplicationRole> rsMapper = new ResultSetMapper<>(ApplicationRole.class);
          while (rs.next()) {
            try {
              list.add(rsMapper.mapRow(rs));
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          return list;
        });
  }

  @Override
  public List<ApplicationRole> fetchUserApplicationRoles(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_USER_ROLE);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<ApplicationUserRoleField> translator = new DomainQuerySQLTranslator<>(
        ApplicationUserRoleField.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationRole> list = new ArrayList<>();
          ResultSetMapper<ApplicationRole> rsMapper = new ResultSetMapper<>(ApplicationRole.class);
          while (rs.next()) {
            try {
              list.add(rsMapper.mapRow(rs));
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          return list;
        });
  }

  @Override
  public List<ApplicationPermissionSet> fetchRolePermissions(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_ROLE_PERMISSION);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<ApplicationRolePermissionSetField> translator = new DomainQuerySQLTranslator<>(
        ApplicationRolePermissionSetField.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationPermissionSet> list = new ArrayList<>();
          ResultSetMapper<ApplicationPermissionSet> rsMapper = new ResultSetMapper<>(ApplicationPermissionSet.class);
          while (rs.next()) {
            try {
              list.add(rsMapper.mapRow(rs));
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          return list;
        });
  }

  @Override
  public List<ApplicationPermissionSet> fetchUserPermissions(Connection connection, DomainQuery query) {
    String sql = QueryCache.get(Query.SELECT_AUTH_USER_PERMISSION);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<ApplicationUserPermissionSetField> translator = new DomainQuerySQLTranslator<>(
        ApplicationUserPermissionSetField.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationPermissionSet> list = new ArrayList<>();
          ResultSetMapper<ApplicationPermissionSet> rsMapper = new ResultSetMapper<>(ApplicationPermissionSet.class);
          while (rs.next()) {
            try {
              list.add(rsMapper.mapRow(rs));
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          return list;
        });
  }

}
