# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Set JAVA_HOME for SDKMAN users
export JAVA_HOME=$HOME/.sdkman/candidates/java/current

# Default build command (includes pre-commit checks)
./mvnw -Ppre-commit clean install

# Compile
./mvnw compile

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName

# Run a single test method
./mvnw test -Dtest=ClassName#methodName

# Package JAR
./mvnw package

# Clean build
./mvnw clean

# Install to local repository
./mvnw install
```

## Project Structure and Architecture

### Core Architecture

This is a test data generation framework that provides reproducible random test data for JUnit 5 tests. The architecture follows these principles:

1. **TypedGenerator Interface**: Central abstraction for all generators. Every generator must implement `TypedGenerator<T>` with methods `next()` and `getType()`.

2. **Generators Factory**: The `de.cuioss.test.generator.Generators` class is the main entry point. It provides static factory methods for creating generators for Java built-in types and domain objects.

3. **JUnit 5 Integration**: The framework deeply integrates with JUnit 5 through:
   - `@EnableGeneratorController`: Extension that manages random seed state
   - `@GeneratorSeed`: Annotation for reproducible test data
   - Multiple `@*Source` annotations for parameterized tests

4. **Internal QuickCheck Implementation**: The `de.cuioss.test.generator.internal.net.java.quickcheck` package contains the underlying QuickCheck implementation. This is internal - never use these classes directly.

### Package Organization

- `de.cuioss.test.generator`: Core interfaces and main factory (`Generators`, `TypedGenerator`)
- `de.cuioss.test.generator.impl`: Standard generator implementations (collections, dates, numbers, strings)
- `de.cuioss.test.generator.domain`: Domain-specific generators (email, person, address components)
- `de.cuioss.test.generator.junit`: JUnit 5 integration components
- `de.cuioss.test.generator.junit.parameterized`: Parameterized test support with various `@*Source` annotations
- `de.cuioss.test.generator.internal.net.java.quickcheck`: Internal QuickCheck implementation (DO NOT USE DIRECTLY)

### Key Design Patterns

1. **Factory Pattern**: `Generators` class provides centralized factory methods
2. **Decorator Pattern**: Generators can be wrapped/composed (e.g., `CollectionGenerator` wraps element generators)
3. **Strategy Pattern**: Different generation strategies through various `TypedGenerator` implementations
4. **Extension Pattern**: JUnit 5 extension mechanism for seed management

### Generator Type System

The `GeneratorType` enum provides type-safe references to all available generators, organized as:
- Standard generators (STRINGS, INTEGERS, etc.) - map to methods in `Generators` class
- Domain generators with DOMAIN_ prefix (DOMAIN_EMAIL, DOMAIN_CITY, etc.) - map to domain generator classes

### Parameterized Test Architecture

The framework provides multiple approaches for parameterized tests:
1. `@GeneratorsSource` with `GeneratorType` enum (recommended)
2. `@TypeGeneratorSource` with generator classes
3. `@TypeGeneratorMethodSource` with factory methods
4. `@TypeGeneratorFactorySource` with external factory classes
5. `@CompositeTypeGeneratorSource` for multiple parameters

Each approach uses an `ArgumentsProvider` implementation that:
- Resolves the generator configuration
- Creates generator instances
- Manages seed state for reproducibility
- Provides arguments to test methods

## Development Guidelines

### Adding New Generators

1. Implement `TypedGenerator<T>` interface
2. Place in appropriate package (`impl` for general, `domain` for domain-specific)
3. Add factory method to `Generators` class if commonly used
4. Add corresponding `GeneratorType` enum value if applicable
5. Create comprehensive unit tests

### Testing Generators

- Always use `@EnableGeneratorController` for generator tests
- Test both random generation and seed reproducibility
- Verify generator constraints (ranges, patterns, etc.)
- Test collection variants (list(), set(), etc.) where applicable

### Maven Notes

- Project uses Maven Wrapper (mvnw) - no local Maven installation needed
- Java 21+ required (configured via parent POM)
- Dependencies managed through cui-java-parent POM
- Main dependencies: JUnit 5, Lombok, cui-java-tools

## Git Workflow

All cuioss repositories have branch protection on `main`. Direct pushes to `main` are never allowed. Always use this workflow:

1. Create a feature branch: `git checkout -b <branch-name>`
2. Commit changes: `git add <files> && git commit -m "<message>"`
3. Push the branch: `git push -u origin <branch-name>`
4. Create a PR: `gh pr create --repo cuioss/cui-test-generator --head <branch-name> --base main --title "<title>" --body "<body>"`
5. Wait for CI + Gemini review (waits until checks complete): `gh pr checks --repo cuioss/cui-test-generator <pr-number> --watch`
6. **Handle Gemini review comments** — fetch with `gh api repos/cuioss/cui-test-generator/pulls/<pr-number>/comments` and for each:
   - If clearly valid and fixable: fix it, commit, push, then reply explaining the fix and resolve the comment
   - If disagree or out of scope: reply explaining why, then resolve the comment
   - If uncertain (not 100% confident): **ask the user** before acting
   - Every comment MUST get a reply (reason for fix or reason for not fixing) and MUST be resolved
7. Do **NOT** enable auto-merge unless explicitly instructed. Wait for user approval.
8. Return to main: `git checkout main && git pull`