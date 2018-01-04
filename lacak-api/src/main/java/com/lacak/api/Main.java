package com.lacak.api;

import java.util.Locale;

public final class Main {
  
  public static void main(String[] args) throws Exception {
    
    Locale.setDefault(Locale.ENGLISH);
    
    if (args.length < 1) {
      throw new RuntimeException("Configuration file is not provided");
    }
    
    Context.init(args);
    
    // ...
    
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        
        Context.deinit();
        
      }
    });
    
  }
  
}
