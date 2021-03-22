HelloWorld for App Engine Standard (Java 8)
============================

This sample demonstrates how to deploy an application on Google App Engine.

See the [Google App Engine standard environment documentation][ae-docs] for more
detailed instructions.

[ae-docs]: https://cloud.google.com/appengine/docs/java/


* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/download.cgi) (at least 3.5)
* [Gradle](https://gradle.org/gradle-download/) (optional)
* [Google Cloud SDK](https://cloud.google.com/sdk/) (aka gcloud)

## Setup

• Download and initialize the [Cloud SDK](https://cloud.google.com/sdk/)

    gcloud init

* Create an App Engine app within the current Google Cloud Project

    gcloud app create

## Maven
### Running locally

    mvn appengine:run

To use vist: http://localhost:8080/

### Deploying

    mvn appengine:deploy

To use vist:  https://YOUR-PROJECT-ID.appspot.com

## Gradle
### Running locally

    gradle appengineRun

If you do not have gradle installed, you can run using `./gradlew appengineRun`.

To use vist: http://localhost:8080/

### Deploying

    gradle appengineDeploy

If you do not have gradle installed, you can deploy using `./gradlew appengineDeploy`.

To use vist:  https://YOUR-PROJECT-ID.appspot.com

## Testing

    mvn verify

 or

    gradle test

As you add / modify the source code (`src/main/java/...`) it's very useful to add [unit testing](https://cloud.google.com/appengine/docs/java/tools/localunittesting)
to (`src/main/test/...`).  The following resources are quite useful:

* [Junit4](http://junit.org/junit4/)
* [Mockito](http://mockito.org/)
* [Truth](http://google.github.io/truth/)

## Functionalties:
#### Voter Management (http://localhost:8080/admin/voterlist)
- Creation of new voter
- Import voter list
- Modification of voters
- Deletion of voters
- Sending notifications to voters

#### Candidates Management (http://localhost:8080/admin/candidates)
- Creation of new candidate
- Modification of candidates
- Deletion of candidates

#### Voting Time Management (http://localhost:8080/admin/votingtime)
- Creation of voting time
- Modification of voting time

#### Casting Vote (http://localhost:8080/public/votecastingbooth)
- Casting Vote

#### Election Result (http://localhost:8080/public/votecastingbooth)
- Overview of election result

#### Cron jobs
- sending Reminder email to voters via sendReminderMail()
