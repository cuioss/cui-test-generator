# Command Configuration

## ./mvnw -Ppre-commit clean install

### Last Execution Duration
- **Duration**: 60000ms (1 minute)
- **Last Updated**: 2025-10-16

### Acceptable Warnings
- `[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources`
- `[WARNING] Parameter 'session' is deprecated`
- Logging/Log-record specific warnings at type level (with provided suppression comments)

## handle-pull-request

### CI/Sonar Duration
- **Duration**: 300000ms (5 minutes)
- **Last Updated**: 2025-10-16

### Notes
- This duration represents the time to wait for CI and SonarCloud checks to complete
- Includes buffer time for queue delays
