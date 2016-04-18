# LoadX
LoadX is a load generation tool to test complex application performance scenarios involving intricate logic and state manipulation.

Engine runs Java based test cases from a single host. It can scale to 1000 concurrent users.

Test cases are easy to configure using Javascript execution jobs.

## Sample Invocation

From LoadX project: invoke LoadX class as Java application with argument "sample/sample_job.js".

From other project:
  - Gradle dependency:   testRuntime 'org.royp:loadx:1.0.0'
  - Copy sample_job.js in resource directory
  - Invoke org.roy.loadx.invocation.LoadX from Java application run configuration with sample_job.js

## Development Environment - Eclipse
Install "Gradle for Eclipse : Buildship: Eclipse Plug-ins for Gradle:
    http://download.eclipse.org/buildship/updates/e45/releases

From Eclipse:
    File -> Import... -> Gradle -> Gradle Project
