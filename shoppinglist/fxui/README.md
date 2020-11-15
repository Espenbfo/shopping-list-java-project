
# Brukergrensesnittmodulen

Dette prosjektet inneholder brukergrensesnittlaget for shoppinglist-applikasjonen.

## Brukergrensesnittlaget

Brukergrensesnittlaget inneholder alle klasser og logikk knyttet til visning og handlinger på dataene i domenelaget.
Brukergrensesnittet viser ulik funksjonalitet knyttet til inn og ut-logging av handleliste-modus.
Som innlogget på den personlige siden gir brukergrensensnittet mulighet for å hente andres lister, lage nye, og fjerne/legge til elementer. 

Brukergrensesnittet er laget med FXML, ligger i src/main/resources/shoppinglist/gui og JavaFX og ligger i src/main/java/shoppinglist/gui
## Kodekvalitetsanalyse
Jacoco produserer en rapport for testdekningsgrad av koden. Kjøres av mvn test, og produserer rapport ved mvn jacoco:report. Denne kan hentes ut under core. Spotbugs brukes for å gjennkjenne bugs og bugsmønster i koden. 
Checkstyle produserer en rapport for Kodekvalitetsanalyse og detekterer syntax-feil og brudd på kodekonvensjon.
Spotbugs detekterer koden for bugs og klassifiserer de etter grovhetsgrad. 