package com.lesconstructionssapete.stempyerp.repository.auth;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface ApplicationPermissionRepository {

  List<ApplicationPermission> fetchApplicationPermissions(Connection connection, DomainQuery query);

  List<ApplicationRole> fetchUserRoles(Connection connection, DomainQuery query);

  Map<ApplicationPermission, Boolean> fetchRolePermissions(Connection connection, DomainQuery query);

  Map<ApplicationPermission, Boolean> fetchUserPermissions(Connection connection, DomainQuery query);

}
