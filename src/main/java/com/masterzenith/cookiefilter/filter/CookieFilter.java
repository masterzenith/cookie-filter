package com.masterzenith.cookiefilter.filter;

import com.masterzenith.cookiefilter.exception.LogParsingException;
import com.masterzenith.cookiefilter.parser.CommandInput;

/** Interface for filtering the most active cookies */
public interface CookieFilter {
  void filterMostActiveCookies(CommandInput commandInput) throws LogParsingException;
}
