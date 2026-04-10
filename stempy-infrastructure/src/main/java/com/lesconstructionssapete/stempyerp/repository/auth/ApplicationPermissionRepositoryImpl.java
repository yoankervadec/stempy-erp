package com.lesconstructionssapete.stempyerp.repository.auth;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermissionSet;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public class ApplicationPermissionRepositoryImpl implements ApplicationPermissionRepository {

  @Override
  public List<ApplicationPermission> fetchApplicationPermissions(Connection connection, DomainQuery query) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ApplicationRole> fetchApplicationRoles(Connection connection, DomainQuery query) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ApplicationPermissionSet> fetchRolePermissions(Connection connection, DomainQuery query) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ApplicationPermissionSet> fetchUserPermissions(Connection connection, DomainQuery query) {
    // TODO Auto-generated method stub
    return null;
  }

}
