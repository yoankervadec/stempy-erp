package com.lesconstructionssapete.stempyerp.repository.auth;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.config.db.SQLExecutor;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermissionSet;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationPermissionSQLField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationRolePermissionSetSQLField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationRoleSQLField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationUserPermissionSetSQLField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationUserRoleSQLField;
import com.lesconstructionssapete.stempyerp.mapper.auth.ApplicationPermissionRowMapper;
import com.lesconstructionssapete.stempyerp.mapper.auth.ApplicationRolePermissionSetRowMapper;
import com.lesconstructionssapete.stempyerp.mapper.auth.ApplicationRoleRowMapper;
import com.lesconstructionssapete.stempyerp.mapper.auth.ApplicationUserPermissionSetRowMapper;
import com.lesconstructionssapete.stempyerp.mapper.auth.ApplicationUserRoleRowMapper;
import com.lesconstructionssapete.stempyerp.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public class ApplicationPermissionRepositoryImpl implements ApplicationPermissionRepository {

  @Override
  public List<ApplicationPermission> fetchApplicationPermissions(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_PERMISSION);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(ApplicationPermissionSQLField.all());

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationPermission> list = new ArrayList<>();
          while (rs.next()) {
            list.add(ApplicationPermissionRowMapper.map(rs));
          }
          return list;
        });
  }

  @Override
  public List<ApplicationRole> fetchApplicationRoles(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_ROLE);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(ApplicationRoleSQLField.all());

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationRole> list = new ArrayList<>();
          while (rs.next()) {
            list.add(ApplicationRoleRowMapper.map(rs));
          }
          return list;
        });
  }

  @Override
  public List<ApplicationRole> fetchUserApplicationRoles(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_USER_ROLE);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(ApplicationUserRoleSQLField.all());

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationRole> list = new ArrayList<>();
          while (rs.next()) {
            list.add(ApplicationUserRoleRowMapper.map(rs));
          }
          return list;
        });
  }

  @Override
  public List<ApplicationPermissionSet> fetchRolePermissions(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_ROLE_PERMISSION);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(ApplicationRolePermissionSetSQLField.all());

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationPermissionSet> list = new ArrayList<>();
          while (rs.next()) {
            list.add(ApplicationRolePermissionSetRowMapper.map(rs));
          }
          return list;
        });
  }

  @Override
  public List<ApplicationPermissionSet> fetchUserPermissions(Connection connection, DomainQuery query) {
    String sql = QueryCache.get(Query.SELECT_AUTH_USER_PERMISSION);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(ApplicationUserPermissionSetSQLField.all());

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<ApplicationPermissionSet> list = new ArrayList<>();
          while (rs.next()) {
            list.add(ApplicationUserPermissionSetRowMapper.map(rs));
          }
          return list;
        });
  }

}
