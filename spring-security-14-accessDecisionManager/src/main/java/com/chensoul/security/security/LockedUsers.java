package com.chensoul.security.security;

import java.util.HashSet;
import java.util.Set;

public final class LockedUsers {
  private static final Set<String> lockedUsersSets = new HashSet<>();

  private LockedUsers() {
  }

  public static final boolean isLocked(final String username) {
    return lockedUsersSets.contains(username);
  }

  public static final void lock(final String username) {
    lockedUsersSets.add(username);
  }

}
