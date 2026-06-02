package com.lesconstructionssapete.stempyerp.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public final class ResultSetRowMapper {

  private ResultSetRowMapper() {
  }

  /**
   * Maps a ResultSet row to a FieldValueMap using the provided field set.
   *
   * @param resultSet the ResultSet to map
   * @param fieldSet  the field set class
   * @param <E>       the type of the field set
   * @return a FieldValueMap containing the mapped values
   * @throws SQLException if a database access error occurs
   */
  public static <E extends Enum<E> & DomainFieldProvider> FieldValueMap map(
      ResultSet resultSet,
      Class<E> fieldSet) throws SQLException {

    FieldValueMap row = new FieldValueMap();

    for (E enumValue : fieldSet.getEnumConstants()) {

      DomainField field = enumValue.attribute();

      if (field.isVirtual) {
        continue;
      }
      Object value = ResultSetValueExtractor.extract(resultSet, field);

      row.put(field, value);
    }

    return row;
  }

  public static <E extends Enum<E> & DomainFieldProvider> FieldValueMap map(
      ResultSet resultSet,
      Class<E>[] fieldSets) throws SQLException {
    FieldValueMap row = new FieldValueMap();

    for (Class<E> fieldSet : fieldSets) {
      for (E enumValue : fieldSet.getEnumConstants()) {
        DomainField field = enumValue.attribute();
        if (field.isVirtual) {
          continue;
        }
        Object value = ResultSetValueExtractor.extract(resultSet, field);

        row.put(field, value);
      }
    }
    return row;
  }

}
