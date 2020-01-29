package com.adaptris.fxinstaller.helpers;

import java.util.Properties;

import com.adaptris.fxinstaller.utils.PropertiesUtils;
import com.adaptris.fxinstaller.utils.VersionUtils;

public class InstallerProperties {

  private static final String REPOSITORY_RELEASE = "repository.release";
  private static final String REPOSITORY_SNAPSHOT = "repository.snapshot";
  public static final String PROPERTIES_FILE = "installer.properties";
  public static final String INTERLOK_VERSION = "interlok.version";
  public static final String INSTALL_DIR_WINDOWS = "install.directory.windows";
  public static final String INSTALL_DIR_LINUX = "install.directory.linux";

  public static final String ADDITIONAL_NEXUS_BASE_URL = "additionalNexusBaseUrl";

  private static InstallerProperties INSTANCE = new InstallerProperties();

  protected final Properties properties;

  public static InstallerProperties getInstance() {
    return INSTANCE;
  }

  protected InstallerProperties() {
    properties = PropertiesUtils.loadFromStreamQuietly(getClass().getClassLoader(), PROPERTIES_FILE);
  }

  public String getVersion() {
    return getProperty(INTERLOK_VERSION);
  }

  public String getRepository() {
    return getRepository(getVersion());
  }

  public String getRepository(String version) {
    if (VersionUtils.isSnapshot(version)) {
      return getProperty(REPOSITORY_SNAPSHOT);
    } else {
      return getProperty(REPOSITORY_RELEASE);
    }
  }

  public String getInstallDir() {
    return getInstallDir(System.getProperty("os.name"));
  }

  private String getInstallDir(String osName) {
    return osName.toLowerCase().contains("win") ? getWindowsInstallDir() : getLinuxInstallDir();
  }

  public String getWindowsInstallDir() {
    return getProperty(INSTALL_DIR_WINDOWS);
  }

  public String getLinuxInstallDir() {
    return getProperty(INSTALL_DIR_LINUX);
  }

  public String getAdditionalNexusBaseUrl() {
    return System.getProperty(ADDITIONAL_NEXUS_BASE_URL);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public String getProperty(String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }

  public Properties getProperties() {
    Properties newProperties = new Properties();
    properties.forEach((key, value) -> {
      newProperties.put(key, value);
    });
    return newProperties;
  }

}
