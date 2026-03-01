package com.lesconstructionssapete.stempyerp.core.shared.constant;

import java.util.List;

public class ConstantUtil {

  public static <T extends ConstantEntity> T findById(List<T> list, int id) {
    return enabledOnly(list).stream()
        .filter(e -> e.getId() == id)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown or disabled config id: " + id));
  }

  public static <T extends ConstantEntity> T findByName(List<T> list, String name) {
    return enabledOnly(list).stream()
        .filter(e -> e.getName().equals(name.toUpperCase()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown or disabled config name: " + name.toUpperCase()));
  }

  public static <T extends ConstantEntity> List<T> enabledOnly(List<T> list) {
    return list.stream()
        .filter(ConstantEntity::isEnabled)
        .toList();
  }

}
