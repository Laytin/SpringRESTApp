# SpringRESTApp
# Intro
This is an REST application which is similar to the service popytka_by.
# Stack
The following frameworks were used during the project:
- Spring 
  - WEB: mappings
  - Data: to interact with database entities
  - Security: For access control (in particular JWT filter with tokens)
- Hibernate: for working with ORM and hql queries
- Log4j: control of making IMPORTANT changes to the database
- ModelMapper: working with DTO and some another cases

A secure environment was organized using JWT TOKEN, the number of DTO objects and requests for obtaining the necessary information was minimized. 
Writed some custom queries.
Plans: enable sending notifications to users.
# About
In the app, users can create their own "journeys" and other users can join them. When creating a trip, the user selects the time, number of available seats, car, time and place of departure / arrival.

The second user can search for those routes that he is interested in (as well as with the right number of places, with the right date). When he has found a suitable ride, he can send a request to join the driver. The driver decides whether he agrees to take a passenger with him or not. He is given the opportunity to see the current phone number of the "employer" to clarify questions. After confirmation in the application, the number of seats is edited according to the action (acceptance or refusal).

The user can also view not only current trips, but also trips created by him, see the history of all trips.
