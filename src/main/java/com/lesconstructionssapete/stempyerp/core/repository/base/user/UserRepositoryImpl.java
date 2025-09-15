package com.lesconstructionssapete.stempyerp.core.repository.base.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.constant.TaxRegion;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.UserRole;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.UserCredential;
import com.lesconstructionssapete.stempyerp.core.shared.query.Query;
import com.lesconstructionssapete.stempyerp.core.shared.query.QueryCache;
import com.lesconstructionssapete.stempyerp.core.shared.query.SqlBuilder;

public class UserRepositoryImpl implements UserRepository {

  @Override
  public User validateCredentials(Connection connection, UserCredential userCredential) throws SQLException {
    User user;

    String sqlBase = QueryCache.get(Query.SELECT_FULL_USER);

    SqlBuilder builder = new SqlBuilder(sqlBase)
        .where("us.username_long = :usernameLong", userCredential.getUsernameLong())
        .and("us.user_password = :password", userCredential.getPassword());

    String sqlString = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SqlBuilder.SqlParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("NO USER FOUND");
          return null;
        }
        user = new User(
            rs.getLong("user_seq"),
            rs.getString("user_no"),
            rs.getString("username_short"),
            rs.getString("username_long"),
            new UserRole(
                rs.getInt("user_role_id"),
                rs.getString("role_name"),
                rs.getBoolean("is_user_role_enabled")),
            rs.getString("user_password"),
            rs.getString("user_pin"),
            null,
            new TaxRegion(
                rs.getInt("user_tax_region_id"),
                rs.getString("tax_region"),
                rs.getString("region_name"),
                rs.getDouble("gst_rate"),
                rs.getDouble("pst_rate"),
                rs.getBoolean("is_tax_region_enabled")),
            rs.getBoolean("is_enabled"),
            rs.getLong("created_by"),
            rs.getObject("created_at", LocalDateTime.class));
      }

    }

    return user;
  }

  @Override
  public User findByUserNo(Connection connection, String userNo) throws SQLException {
    User user;

    String sqlBase = QueryCache.get(Query.SELECT_FULL_USER);

    SqlBuilder builder = new SqlBuilder(sqlBase)
        .where("us.user_no = :userNo", userNo);

    String sqlString = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SqlBuilder.SqlParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("NO USER FOUND");
          return null;
        }
        user = new User(
            rs.getLong("user_seq"),
            rs.getString("user_no"),
            rs.getString("username_short"),
            rs.getString("username_long"),
            new UserRole(
                rs.getInt("user_role_id"),
                rs.getString("role_name"),
                rs.getBoolean("is_user_role_enabled")),
            rs.getString("user_password"),
            rs.getString("user_pin"),
            null,
            new TaxRegion(
                rs.getInt("user_tax_region_id"),
                rs.getString("tax_region"),
                rs.getString("region_name"),
                rs.getDouble("gst_rate"),
                rs.getDouble("pst_rate"),
                rs.getBoolean("is_tax_region_enabled")),
            rs.getBoolean("is_enabled"),
            rs.getLong("created_by"),
            rs.getObject("created_at", LocalDateTime.class));
      }

    }

    return user;
  }

}
