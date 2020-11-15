# Modul for serveren
Vi bruker Grizzly 2 rammeverket til å lage en Jersey-server. Siden vi gjør all persistensen i Service-klassene i restapi, så har har ikke Personconfig noe spesielt med metoder.
TestPage er der for å kunne gi en konstant, og enkel, sjekk på om serveren er oppe eller ikke.

Serveren startes ved å bruke "mvn exec:java -f restapiserver" fra rotmappen til maven prosjektet. Dette kjører da mainmetoden i ShoppingGrizzlyApp.

