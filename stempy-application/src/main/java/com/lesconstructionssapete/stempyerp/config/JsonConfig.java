package com.lesconstructionssapete.stempyerp.config;

import com.lesconstructionssapete.stempyerp.util.JsonUtil;

import io.javalin.json.JavalinJackson;

public class JsonConfig {

  public static JavalinJackson get() {
    return new JavalinJackson(JsonUtil.mapper(), true);
  }
}
