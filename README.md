# Definitely not Stolen From Acebook

The application uses:
  - `maven` to build the project
  - `thymeleaf` for templating
  - `flyway` to manage `postgres` db migrations
  - `selenium` for feature testing
  - `faker` to generate fake names for testing
  - `junit4` for unit testing
  - `auth0` and `spring-security` for authentication and user management
  - `lombok` to generate getters and setters for us
  
Below, you'll find specific learning objectives for each tool.

## QuickStart Instructions

- Open the codebase in an IDE like InteliJ or VSCode
  - If using IntelliJ, accept the prompt to install the Lombok plugin (if you don't get prompted, press command and comma
  to open the Settings and go to Plugins and search for Lombok made by Jetbrains and install).
- Create two new Postgres databases called `bird_beacon_dev` and `bird_beacon_test`
- Install Maven `brew install maven`
- [Set up Auth0](https://journey.makers.tech/pages/auth0) (you only need the "Create an Auth0 app" section)
- In your Auth0 account, set up username required by: navigation bar on left > Authentication > Database > clickon "Username-Password-Authentication" > Toggle on Requires Username in settings > Save.
- To capture the username in your database, you will need to set up an action in your Auth0 accoiunt:
Navigation bar left > Actions > Triggers > post-login > On the right side of the screen, next to add action click '+' > create custom action > Name: add username to token > Create > On the next screen on the code box replace the existing code with the following: 
```
exports.onExecutePostLogin = async (event, api) => {

  const username = event.user.username || event.user.nickname;
  api.idToken.setCustomClaim(
    "https://birdbeacon.com/username",
    username
  );
};
```
Click Deploy > Back to triggers on the left of the screen > drag the new action you just created on the panel on the right and place it in between 'Start user logged in' and 'Token Issued' > click Apply > Finished 

- Build the app and start the server, using the Maven command `mvn spring-boot:run`
> The database migrations will run automatically at this point
- Visit `http://localhost:8081/` to sign up

## Running the tests

- Install chromedriver using `brew install chromedriver`
- Start the server in a terminal session `mvn spring-boot:run -Dspring-boot.run.profiles=test`
- Open a new terminal session and navigate to the bird_beacon directory
- Run your tests in the second terminal session with `mvn test`

> All the tests should pass. If one or more fail, read the next section.

## Common Setup Issues

### The application is not running

For the feature tests to execute properly, you'll need to have the server running in one terminal session and then use a second terminal session to run the tests.

### Chromedriver is in the wrong place

Selenium uses Chromedriver to interact with the Chrome browser. If you're on a Mac, Chromedriver needs to be in `/usr/local/bin`. You can find out where it is like this `which chromedriver`. If it's in the wrong place, move it using `mv`.

### Chromedriver can't be opened

Your Mac might refuse to open Chromedriver because it's from an unidentified developer. If you see a popup at that point, dismiss it by selecting `Cancel`, then go to `System Preferences`, `Security and Privacy`, `General`. You should see a message telling you that Chromedriver was blocked and, if so, there will be an `Open Anyway` button. Click that and then re-try your tests.

### What is Auth0?
Auth0 is a service that handles user authentication and authorisation for you, it will store data related to that such
as username, passwords, 2 factor codes, oauth details (e.g. log in with google) etc, but you still need to store all the information you want to
about the user e.g. favourite ice cream flavour, pet name etc. It’s not a database of users, it’s an auth handling service.

## Design

This app uses the repository pattern. The repository pattern separates the business logic of models from the responsibility of connecting to the database and making queries. Take a look in the `src/main/java/repository` and you'll find `PostRepository` which generates and executes queries to Create, Read, Update and Delete (CRUD) posts. Depending on what you've built in the past, it might or might not feel familiar to you.

