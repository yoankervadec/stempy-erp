package com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth;

import java.sql.ResultSet;

import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class UserCredentialMapper {

  private UserCredentialMapper() {
  }

  public static UserCredential fromResultSet(ResultSet rs) {
    return new UserCredential(
        EntityMapper.read(rs, UserCredential.Fields.ID),
        EntityMapper.read(rs, UserCredential.Fields.USER_ID),
        EntityMapper.read(rs, UserCredential.Fields.PASSWORD),
        EntityMapper.read(rs, UserCredential.Fields.ENABLED),
        EntityMapper.read(rs, UserCredential.Fields.CREATED_AT));
  }

}
