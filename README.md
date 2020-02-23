# Election Voting System
Election Voting System using Google App Engine Environment in Java

### Technologies used:

See the [Google App Engine standard environment documentation][ae-docs].

[ae-docs]: https://cloud.google.com/appengine/docs/java/


* Java 8
* Google App Engine
* Maven
* Google Datastore API
* [Google Cloud SDK](https://cloud.google.com/sdk/) (aka gcloud)
* JSON Web Token API (JWT)

### Features: 
1. Login using your google account
2. Add Candidates for election
3. Add start and end date for election
4. Import list of email addresses of the students (csv format imported) (Students vote for the candidates)
5. Send email to students, for reminding them, 1 day before the election (cron job) (Using Java Mail)
6. Students can only access the ballot paper screen to vote by using the token link (JWT Token) assigned to them via email. If token expires, student is not able to vote
7. Show the result of election after election is ended.
