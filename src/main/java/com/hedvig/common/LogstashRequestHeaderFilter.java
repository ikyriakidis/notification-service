package com.hedvig.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.logstash.logback.composite.accessevent.HeaderFilter;

/**
 * A simple {@link HeaderFilter} that determines whether or not a header is included based on a set
 * of included header names or excluded header names.
 *
 * <p>If both includes and excludes are empty, then all header names will be included.
 *
 * <p>If includes is not empty and excludes is empty, then only those headers in the includes will
 * be included.
 *
 * <p>If includes is empty and excludes is not empty, then all headers except those in the excludes
 * will be included.
 *
 * <p>If includes is not empty and excludes is not empty, then an exception will be thrown.
 *
 * <p>All comparisons are case-insensitive.
 */
public class LogstashRequestHeaderFilter implements HeaderFilter {

  private Set<String> includes = new HashSet<>();
  private Set<String> excludes = new HashSet<>();

  @Override
  public boolean includeHeader(String headerName, String headerValue) {
    if (includes.isEmpty() && excludes.isEmpty()) {
      return true;
    } else if (excludes.isEmpty()) {
      return includes.contains(headerName.toLowerCase());
    } else if (includes.isEmpty()) {
      return !excludes.contains(headerName.toLowerCase());
    } else {
      throw new IllegalStateException(
          String.format(
              "Both includes (%s) and excludes (%s) cannot be configured at the same time.  Only one or the other, or neither can be configured.",
              includes, excludes));
    }
  }

  public Set<String> getIncludes() {
    return Collections.unmodifiableSet(includes);
  }

  public Set<String> getExcludes() {
    return Collections.unmodifiableSet(excludes);
  }

  public void addInclude(String include) {
    this.includes.add(include.toLowerCase());
  }

  public void removeInclude(String include) {
    this.includes.remove(include.toLowerCase());
  }

  public void addExclude(String exclude) {
    this.excludes.add(exclude.toLowerCase());
  }

  public void removeExclude(String exclude) {
    this.excludes.remove(exclude.toLowerCase());
  }
}
