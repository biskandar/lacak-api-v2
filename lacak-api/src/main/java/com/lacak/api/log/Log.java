package com.lacak.api.log;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.Charset;

import org.apache.log4j.Appender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.NullAppender;

import com.lacak.api.Config;

public final class Log {
  
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static final String LOGGER_NAME = "lacak-api";
  private static final String STACK_PACKAGE = "com.lacak.api";
  private static final int STACK_LIMIT = 3;
  
  private static Logger logger = null;
  
  //////////////////////////////////////////////////////////////////////////////
  
  private Log() {
    
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  public static void setup(Config config) throws IOException {
    
    Layout layout = new PatternLayout("%d{" + DATE_FORMAT + "} %5p: %m%n");
    
    Appender appender = new DailyRollingFileAppender(layout,
        config.getString("logger.file"), "'.'yyyyMMdd");
    
    LogManager.resetConfiguration();
    LogManager.getRootLogger().addAppender(new NullAppender());
    
    logger = Logger.getLogger(LOGGER_NAME);
    logger.addAppender(appender);
    logger.setLevel(Level.toLevel(config.getString("logger.level"), Level.ALL));
    
    Log.info("Version: " + config.getString("app.version"));
    Log.logSystemInfo();
    
  }
  
  public static void error(String msg) {
    logger().error(msg);
  }
  
  public static void warning(String msg) {
    logger().warn(msg);
  }
  
  public static void info(String msg) {
    logger().info(msg);
  }
  
  public static void debug(String msg) {
    logger().debug(msg);
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  private static Logger logger() {
    if (logger == null) {
      logger = Logger.getLogger(LOGGER_NAME);
      logger.setLevel(Level.OFF);
    }
    return logger;
  }
  
  private static String getAppVersion() {
    return Log.class.getPackage().getImplementationVersion();
  }
  
  private static void logSystemInfo() {
    try {
      
      OperatingSystemMXBean operatingSystemBean = ManagementFactory
          .getOperatingSystemMXBean();
      Log.info("Operating system" + " name: " + operatingSystemBean.getName()
          + " version: " + operatingSystemBean.getVersion() + " architecture: "
          + operatingSystemBean.getArch());
      
      RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
      Log.info("Java runtime" + " name: " + runtimeBean.getVmName()
          + " vendor: " + runtimeBean.getVmVendor() + " version: "
          + runtimeBean.getVmVersion());
      
      MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
      Log.info("Memory limit" + " heap: "
          + memoryBean.getHeapMemoryUsage().getMax() / (1024 * 1024) + "mb"
          + " non-heap: "
          + memoryBean.getNonHeapMemoryUsage().getMax() / (1024 * 1024) + "mb");
      
      Log.info("Character encoding: " + System.getProperty("file.encoding")
          + " charset: " + Charset.defaultCharset());
      
    } catch (Exception error) {
      Log.warning("Failed to get system info");
    }
  }
  
}
