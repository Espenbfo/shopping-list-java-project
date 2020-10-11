##
# Brukergrensesnittmodulen

Dette prosjektet inneholder brukergrensesnittlaget for shoppinglist-applikasjonen.

## Brukergrensesnittlaget

Brukergrensesnittlaget inneholder alle klasser og logikk knyttet til visning og handlinger på dataene i domenelaget.
Brukergrensesnittet viser ulik funksjonalitet knyttet til inn og ut-logging av handleliste-modus. På den personlige siden gir brukergrensensnittet mulighet for å hente andres lister, lage nye, og fjerne/legge til elementer. 


## Kodekvalitetsanalyse
Jacoco produserer en rapport for testdekningsgrad av koden. Kjøres av mvn test, og produserer rapport ved mvn jacoco:report. Denne kan hentes ut under core. Spotbugs brukes for å gjennkjenne bugs og bugsmønster i koden. 