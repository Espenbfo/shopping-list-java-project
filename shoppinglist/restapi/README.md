# Modul for REST-API

Modul for logikk til REST-tjeneste-objekt, programmerth med JAX-RS-standarden. 

Service-klassene gir nødvendige metoder for å hente og legge til ShoppingList- og Person-objekter, med bruk av @GET og @PUT. 

## Bruk av JSON
Ved bruk av @Consumes(MediaType.APPLICATION_JSON) og @Produces(MediaType.APPLICATION_JSON) kan metodene bruke logikken for å skrive til og lese fra JSON-filer som er implementert i core-modulen
