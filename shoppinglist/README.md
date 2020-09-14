# ShoppingList

## Om appen
Applikasjonen skal være en applikasjon for å dele handlelister.
Flere brukere vil ha tilgang til samme handleliste, og her kunne legge til og sjekke av elemeneter.
Brukere vil kunne ha tilgang til flere handlelister, og hver handleliste vil kunne deles av flere brukere. 

## Organisering av koden

### Domenelaget

### Brukergrensesnittlaget
Brukergrensesnittet er laget med JavaFX og FXML. Filene App.fxml, App.java og AppController.java danner brukergrensesnittet av handlelisten.
Her vises handlelistelementene, samt knapper for å legge til og fjerne elementer. I brukergrensesnittet vil det også være felt og knapper for innhenting av tidligere lagrede handlelister. 

### Persistenslaget 
Persistenslaget inneholder logikk for lagring av handlelistene. Per nå lagres og hentes handlelistene som tekstfiler.

### Bygging med maven 


## Brukerhistorie
Vi ønsker at en bruker skal kunne åpne en eksisterende handleliste, enten en den selv har laget tidligere, eller en del har fått tildelt av en annen bruker.
Deretter vil brukeren kunne se alle tidligere elementer, samt kunne legge til nye, fjerne og sjekke av elementer. 


![](screenshot.png)