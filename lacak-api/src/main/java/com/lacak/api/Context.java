package com.lacak.api;

import com.lacak.api.log.Log;

public final class Context {
  
  //////////////////////////////////////////////////////////////////////////////
  
  private Context() {
    
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  public static void init(String[] args) {
    try {
      
      config = new Config();
      config.init(args[0]);
      
      if (config.getBoolean("logger.enable")) {
        Log.setup(config);
      }
      
      // ...
      
    } catch (Exception e) {
      System.err.println("Failed to init , " + e);
    }
  }
  
  public static void deinit() {
    try {
      
      // ...
      
      config.deinit();
      
    } catch (Exception e) {
      System.err.println("Failed to deinit , " + e);
    }
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  private static Config config;
  
  public static Config config() {
    return config;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
}
