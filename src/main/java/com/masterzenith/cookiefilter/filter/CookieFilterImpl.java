package com.masterzenith.cookiefilter.filter;

import com.masterzenith.cookiefilter.exception.LogParsingException;
import com.masterzenith.cookiefilter.parser.CommandInput;
import com.masterzenith.cookiefilter.parser.LogEntry;
import com.masterzenith.cookiefilter.parser.LogParser;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/** Implementation for the interface Filter */
public class CookieFilterImpl implements CookieFilter {
  @Override
  public void filterMostActiveCookies(CommandInput commandInput) throws LogParsingException {
    // Parse input and take the list of cookie entries
    List<LogEntry> cookieEntryList = LogParser.parseLog(commandInput.getFileName());

    // Group cookies by selected date
    Map<String, Long> groupCookieByDate =
        groupCookieByDate(commandInput.getSelectedDate(), cookieEntryList);

    // Calculate the frequency of most active cookies
    OptionalLong mostActiveCookieFreq = mostActiveCookieFreq(groupCookieByDate);

    // Scan through the list of cookies and output the ones which have the max frequency value
    mostActiveCookieFreq.ifPresent(maxFreq -> outputMostActiveCookies(groupCookieByDate, maxFreq));
  }

  /** Use HashMap to group cookie entries, <key, value> = <cookie date, number of occurrence> */
  private Map<String, Long> groupCookieByDate(
      LocalDate selectedDate, List<LogEntry> cookieEntryList) {
    return cookieEntryList.stream()
        .filter(x -> selectedDate.isEqual(x.getTimestamp().toLocalDate()))
        .collect(groupingBy(LogEntry::getCookie, counting()));
  }

  /** Return the frequency of most active cookies, which is the max values of all the occurrences */
  private OptionalLong mostActiveCookieFreq(Map<String, Long> groupOfCookieByDate) {
    return groupOfCookieByDate.values().stream().mapToLong(count -> count).max();
  }

  /** Output the most active cookies to the terminal */
  private void outputMostActiveCookies(
      Map<String, Long> groupOfCookieByDate, long mostActiveCookieFreq) {
    groupOfCookieByDate.entrySet().stream()
        .filter(x -> x.getValue().equals(mostActiveCookieFreq))
        .map(Map.Entry::getKey)
        .forEach(System.out::println);
  }
}
