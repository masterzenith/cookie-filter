# Most-Active-Cookie
* Task: Process a cookie log and return the most active cookie for a specific day

## Tech-Stack:

- Java 18 OpenJDK(Or any LTS version from 11/ 17), Spring Boot (framework), Maven (build tool)
- IDE: Intellij

## Requirements:

Given a cookie log file in the following format:

~~~
cookie,timestamp
AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00
SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00
5UAVanZf6UtGyKVS,2018-12-09T07:25:00+00:00
AtY0laUfhglK3lC7,2018-12-09T06:19:00+00:00
SAZuXPGUrfbcn5UA,2018-12-08T22:03:00+00:00
4sMM2LxV07bPJzwf,2018-12-08T21:30:00+00:00
fbcn5UAVanZf6UtG,2018-12-08T09:30:00+00:00
4sMM2LxV07bPJzwf,2018-12-07T23:30:00+00:00
~~~

- Write a command line program to process the log file and return the most active cookie for a specific day. 
- Include a -f parameter for the filename to process and a -d parameter to specify the date.

e.g. we’d execute your program like this to obtain the most active cookie for 9th Dec 2018.

~~~
$ ./[command] -f cookie_log.csv -d 2018-12-09
~~~

And it would write to stdout:

~~~
AtY0laUfhglK3lC7
~~~

We define the most active cookie as one seen in the log the most times during a given day.

## Assumptions:

- If multiple cookies meet that criteria, please return all of them on separate lines.
- Only use additional libraries for testing, logging and cli-parsing.
- You can assume -d parameter takes date in UTC time zone.
- You have enough memory to store the contents of the whole file.
- Cookies in the log file are sorted by timestamp (most recent occurrence is the first line of the file).

## Sequence Diagram:
[Sequence Diagram Flow](./images/Sequence-Diagram.puml)
```mermaid
    participant Actor
    activate Actor
    Actor -> ProcessRunner : Program Start
    activate ProcessRunner
    ProcessRunner -> ProcessExecutorImpl: executeProcess
    ProcessExecutorImpl -> LogParser: parseCommandInput
    activate LogParser
    LogParser -> CommandInput: create
    activate CommandInput
    CommandInput --> LogParser
    deactivate CommandInput
    LogParser -> LogParser : parseCommandOption
    activate LogParser
    deactivate LogParser
    LogParser -> LogParser : outputCommandHelp
    activate LogParser
    deactivate LogParser
    LogParser -> LogParsingException : Custom Exception
    activate LogParsingException
    LogParsingException --> LogParser
    deactivate LogParsingException
    LogParser --> ProcessExecutorImpl: commandInput
    ProcessRunner <-- ProcessExecutorImpl: commandInput
    activate ProcessRunner
    ProcessRunner -> CookieFilterImpl: filterMostActiveCookies
    CookieFilterImpl -> CookieFilterImpl: filterMostActiveCookies
    activate CookieFilterImpl
    CookieFilterImpl -> LogParser : parseLog
    activate LogParser
    LogParser -> LogParser: parsing CSV Log
    deactivate LogParser
    activate LogParsingException
    LogParser -> LogParsingException: create
    LogParser <-- LogParsingException
    deactivate LogParsingException
    LogParser --> CookieFilterImpl
    CookieFilterImpl -> CookieFilterImpl: groupCookieByDate
    CookieFilterImpl -> CookieFilterImpl: mostActiveCookieFreq
    CookieFilterImpl -> CookieFilterImpl: outputMostActiveCookies
    deactivate CookieFilterImpl
    ProcessRunner <-- CookieFilterImpl: mostActiveCookieFreq
    ProcessRunner -> ProcessRunner: terminate
    Actor <-- ProcessRunner : Program finish and output result
```

## Build Project:

- Go to directory: cookie-filter
- Using Maven to build and package source code into a jar file: 

~~~
mvn clean package
~~~

[Build Successful](./images/build.png)

## Run from terminal:

- Still stand at the directory: cookie-filter
- Run the compiled jar file from terminal:

~~~
On Windows:
java -jar target\cookie-filter-version-SNAPSHOT.jar -f csv-file-path -d selected-date

On Mac: 
java -jar target/cookie-filter-version-SNAPSHOT.jar -f csv-file-path -d selected-date
~~~

- Example:

~~~
On Windows:
java -jar target\cookie-filter-0.0.1-SNAPSHOT.jar -f src\logs\cookie_log.csv -d 2018-12-09

On Mac:
java -jar target/cookie-filter-0.0.1-SNAPSHOT.jar -f src/logs/cookie_log.csv -d 2018-12-09
~~~

[Run Successful](./images/run_from_terminal.png)

## Or Run from Intellij IDE:

- Before running `CookieFilterApplication`, click on `Edit Configurations`. 
- On `Build and run` 2nd box CLI arguments input the -f xxxx -d xxxx
- Then click run `CookieFilterApplication`.

- Example:
[Arg Config Settings](./images/arg-config.png)

[Run Successful](./images/run.png)