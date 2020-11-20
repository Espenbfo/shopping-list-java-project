# Modul for REST-API

Modul for logikk til REST-tjeneste-objekt, programmerth med JAX-RS-standarden. 

Person-service gir nødvendige metoder for å hente og legge til ShoppingList- og Person-objekter, med bruk av @GET og @PUT. 
LoginService tar imot LoginResource som inneholder et brukernavn og et passord, og svarer med en bruker hvis det var en gyldig kombinasjon.

## Bruk av JSON
Ved bruk av @Consumes(MediaType.APPLICATION_JSON) og @Produces(MediaType.APPLICATION_JSON) kan metodene bruke logikken for å skrive til og lese fra JSON-filer som er transportert over HTTP.
Hvis de er sent med en PUT så blir de skrevet ned ved sin tilhørende metode i Service klassen via FileHandler, og hvis det blir hentet ved en GET så leser Service klassen de med FileHandler og sender de som svar.


## Sekvensdiagram


```plantuml
client -> PersonService: GET /Persons
PersonService -> client: []
client -> PersonService: GET /Persons/Testindivid
PersonService -> client: {"userName":"TestIndivid","salt":"Ank3D9rM1bgJ7skEz9NiiQ==","shoppingLists":[]}
client -> PersonService: GET /Persons/ShoppingLists/1
PersonService -> client: {"title":"Groceries","id":1,"elementList":[{"name":"milk","measurementType":{"baseName":"L","value":1},"shopped":false,"value":1,"measurementName":"L"}]}
client -> PersonService: POST /Persons/Shoppinglists/1
PersonService -> client: 15

client -> LoginService: PUT /Login/register/Testindivid
LoginService -> client: 1
client -> LoginService: POST /Login/login
LoginService -> client: Testindivid

```
