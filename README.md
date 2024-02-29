# Tenant Affordability Calculator

A service that runs an affordability check against a list of properties, taking into account rent, income, and expenses.

Full requirements are described in [AffordabilityCheckTest.pdf](docs/AffordabilityCheckTest.pdf).

CSV files for the bank statements and list of properties considered are stored as resources in the application.

See assumptions in [Assumptions.md](docs/Assumptions.md).

## Usage

The application requires JDK 21 and will output the list of affordable properties when run:

- using the Gradle Wrapper: `./gradlew bootRun`
- using the pre-build jar: `java -jar tenantAffordabilityKotlin-1.0.0.jar`

To build use `./gradlew build`.

## Main Dependencies

- Kotlin 1.9 (JDK 21)
- Gradle 8.5

## Note

This is "no external dependencies" version of [tenant-affordability](https://github.com/AlexandruGG/tenant-affordability). The main differences are that no framework is used and the CSV parsing is done manually.
