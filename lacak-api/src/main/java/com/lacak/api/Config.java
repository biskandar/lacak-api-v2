package com.lacak.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
  
  //////////////////////////////////////////////////////////////////////////////
  
  private final Properties properties;
  
  //////////////////////////////////////////////////////////////////////////////
  
  public Config() {
    
    properties = new Properties();
    
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  public void init(String configFileName) throws IOException {
    
    // clear properties
    
    properties.clear();
    
    // setup main properties
    
    Properties mainProperties = load(configFileName);
    
    // setup default properties
    
    Properties defaultProperties = null;
    if (mainProperties != null) {
      defaultProperties = load(mainProperties.getProperty("config.default"));
    }
    
    // load into properties
    
    if (defaultProperties != null) {
      properties.putAll(defaultProperties);
      System.out.println("Loaded default properties : "
          + defaultProperties.size() + " item(s)");
    }
    if (mainProperties != null) {
      properties.putAll(mainProperties);
      System.out.println(
          "Loaded main properties : " + mainProperties.size() + " item(s)");
    }
    
    for (String key : properties.stringPropertyNames()) {
      System.out.println("Loaded property : field = " + key + " , value = "
          + properties.getProperty(key));
    }
    
  }
  
  public void deinit() {
    
    properties.clear();
    
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  public boolean hasKey(String key) {
    return properties.containsKey(key);
  }
  
  public String getString(String key) {
    return properties.getProperty(key);
  }
  
  public String getString(String key, String defaultValue) {
    return hasKey(key) ? getString(key) : defaultValue;
  }
  
  public boolean getBoolean(String key) {
    return Boolean.parseBoolean(getString(key));
  }
  
  public boolean getBoolean(String key, boolean defaultValue) {
    return hasKey(key) ? getBoolean(key) : defaultValue;
  }
  
  public int getInteger(String key) {
    return Integer.parseInt(getString(key));
  }
  
  public int getInteger(String key, int defaultValue) {
    return hasKey(key) ? getInteger(key) : defaultValue;
  }
  
  public long getLong(String key) {
    return Long.parseLong(getString(key));
  }
  
  public long getLong(String key, long defaultValue) {
    return hasKey(key) ? getLong(key) : defaultValue;
  }
  
  public double getDouble(String key) {
    return Double.parseDouble(getString(key));
  }
  
  public double getDouble(String key, double defaultValue) {
    return hasKey(key) ? getDouble(key) : defaultValue;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  private Properties load(String configFileName) throws IOException {
    Properties properties = null;
    if ((configFileName == null) || (configFileName.equals(""))) {
      return properties;
    }
    properties = new Properties();
    try (InputStream is = new FileInputStream(configFileName)) {
      properties.loadFromXML(is);
    }
    return properties;
  }
  
}
