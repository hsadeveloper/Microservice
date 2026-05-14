# common-lib-multi
Multi-module Gradle project containing:
- `common-lib` : a reusable Spring Security auto-configuration + JWT validation library
- `demo-app`   : a Spring Boot demo app that consumes `common-lib`

How to build:
1. Build and publish locally:
   ./gradlew :common-lib:publishToMavenLocal

2. Run demo:
   ./gradlew :demo-app:bootRun

Notes:
- The Artifactory URL in common-lib's `build.gradle` is a placeholder.
- Update `demo-app/src/main/resources/application.yml` with the path to a real public key.
