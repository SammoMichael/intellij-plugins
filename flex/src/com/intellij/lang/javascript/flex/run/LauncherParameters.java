// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.lang.javascript.flex.run;

import com.intellij.ide.browsers.BrowserFamily;
import com.intellij.ide.browsers.WebBrowser;
import com.intellij.ide.browsers.WebBrowserManager;
import com.intellij.ide.browsers.WebBrowserReferenceConverter;
import com.intellij.lang.javascript.flex.FlexBundle;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.xmlb.annotations.OptionTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LauncherParameters implements Cloneable {
  public enum LauncherType {
    OSDefault, Browser, Player
  }

  private @NotNull LauncherType myLauncherType = LauncherType.OSDefault;

  private @NotNull String myPlayerPath = SystemInfo.isMac ? "/Applications/Flash Player Debugger.app"
                                                            : SystemInfo.isWindows ? "FlashPlayerDebugger.exe"
                                                                                     : "/usr/bin/flashplayerdebugger";
  private boolean myNewPlayerInstance = false;

  private @NotNull WebBrowser myBrowser = WebBrowserManager.getInstance().getFirstBrowser(BrowserFamily.FIREFOX);

  public LauncherParameters() {
  }

  public LauncherParameters(final @NotNull LauncherType launcherType,
                            final @NotNull WebBrowser browser,
                            final @NotNull String playerPath,
                            final boolean isNewPlayerInstance) {
    myLauncherType = launcherType;
    myBrowser = browser;
    myPlayerPath = playerPath;
    myNewPlayerInstance = isNewPlayerInstance;
  }

  public String getPresentableText() {
    switch (myLauncherType) {
      case OSDefault -> {
        return FlexBundle.message("system.default.application");
      }
      case Browser -> {
        return myBrowser.getName();
      }
      case Player -> {
        return FileUtil.toSystemDependentName(myPlayerPath);
      }
      default -> {
        assert false;
        return "";
      }
    }
  }

  public @NotNull LauncherType getLauncherType() {
    return myLauncherType;
  }

  @SuppressWarnings("UnusedDeclaration")
  public void setLauncherType(final @NotNull LauncherType launcherType) {
    myLauncherType = launcherType;
  }

  @OptionTag(converter = WebBrowserReferenceConverter.class)
  public @NotNull WebBrowser getBrowser() {
    return myBrowser;
  }

  public void setBrowser(@Nullable WebBrowser browser) {
    myBrowser = browser == null ? WebBrowserManager.getInstance().getFirstBrowser(BrowserFamily.FIREFOX) : browser;
  }

  public @NotNull String getPlayerPath() {
    return myPlayerPath;
  }

  @SuppressWarnings("UnusedDeclaration")
  public void setPlayerPath(final @NotNull String playerPath) {
    myPlayerPath = FileUtil.toSystemIndependentName(playerPath);
  }

  public boolean isNewPlayerInstance() {
    return myNewPlayerInstance;
  }

  @SuppressWarnings("UnusedDeclaration")
  public void setNewPlayerInstance(final boolean newPlayerInstance) {
    myNewPlayerInstance = newPlayerInstance;
  }

  @Override
  public LauncherParameters clone() {
    try {
      return (LauncherParameters)super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final LauncherParameters that = (LauncherParameters)o;

    if (myBrowser != that.myBrowser) return false;
    if (myLauncherType != that.myLauncherType) return false;
    if (!myPlayerPath.equals(that.myPlayerPath)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    assert false;
    return super.hashCode();
  }
}
