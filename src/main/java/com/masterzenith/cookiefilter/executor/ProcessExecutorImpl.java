package com.masterzenith.cookiefilter.executor;

import com.masterzenith.cookiefilter.exception.LogParsingException;
import com.masterzenith.cookiefilter.filter.CookieFilter;
import com.masterzenith.cookiefilter.parser.CommandInput;
import com.masterzenith.cookiefilter.parser.LogParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Implementation for the interface ProcessExecutor */
public class ProcessExecutorImpl implements ProcessExecutor {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutorImpl.class);
  private CookieFilter cookieFilter;

  public ProcessExecutorImpl(CookieFilter cookieFilter) {
    this.cookieFilter = cookieFilter;
  }

  @Override
  public int executeProcess(String[] args) {
    try {
      CommandInput commandInput = LogParser.parseCommandInput(args);
      cookieFilter.filterMostActiveCookies(commandInput);
      return ProcessStatus.SUCCESS.getValue();
    } catch (LogParsingException | RuntimeException e) {
      LOGGER.error("Program failed!", e);
    }
    return ProcessStatus.PROGRAM_FAILED.getValue();
  }
}
