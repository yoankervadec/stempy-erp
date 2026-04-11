package com.lesconstructionssapete.stempyerp.repository.auth;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermissionSet;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface ApplicationPermissionRepository {

  List<ApplicationPermission> fetchApplicationPermissions(Connection connection, DomainQuery query);

  List<ApplicationRole> fetchApplicationRoles(Connection connection, DomainQuery query);

  List<ApplicationRole> fetchUserApplicationRoles(Connection connection, DomainQuery query);

  List<ApplicationPermissionSet> fetchRolePermissions(Connection connection, DomainQuery query);

  List<ApplicationPermissionSet> fetchUserPermissions(Connection connection, DomainQuery query);

}
