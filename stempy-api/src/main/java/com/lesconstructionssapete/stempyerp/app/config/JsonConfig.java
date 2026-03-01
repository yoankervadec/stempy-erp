package com.lesconstructionssapete.stempyerp.app.config;

import com.lesconstructionssapete.stempyerp.core.shared.util.JsonUtil;

import io.javalin.json.JavalinJackson;

public class JsonConfig {

  public static JavalinJackson get() {
    return new JavalinJackson(JsonUtil.mapper(), true);
  }
}
