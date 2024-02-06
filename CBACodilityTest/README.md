CBACodilityTest (API Framework) - Instruction to run the project

Pre-requisite:
Java 8+ and Maven 

Steps to follow:
1. clone the repository from GitHub into your local directory
2. open command prompt and go to your local repository location using cd command
3. run the mvn command "mvn clean test" in the command prompt

Cucumber HTML Report -> target/cucumber-html-reports/overview-features.html

Test Coverage:
EverythingAboutPet feature contains all the positive and negative test cases with data driven testing for Pet endpoints from PetStore Swagger.
Few negative test cases are not automated due to the mismatches between the expected and actual test results.
Those are clearly updated in the feature file comments section.
