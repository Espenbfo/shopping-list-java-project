# Kjernemodulen (core)

Denne modulen inneholder domene- og persistenslaget

## Domenelaget
I domenelaget ligger logikken knyttet til applikasjonen. 
Her ligger logikk for:
- Person 
- Password
- MeasurementType
- ShoppingList
- ShoppingElement
Domenelaget inneholder klassene for å opprette bruker, handlelister og elementer til handlelistene.

## Persistenslaget 
Persistenslaget inneholder logikk for lagring med JSON. Det kan da produseres tekstfiler med et ShoppingList-objekt, med tilhørende Person (evt. flere) og ShoppingElement(s).
Logikken støtter også opphenting av slike filer. 

## Kodekvalitetsanalyse
Jacoco produserer en rapport for testdekningsgrad av koden. Kjøres av mvn test, og produserer rapport ved mvn jacoco:report. Denne kan hentes ut under core. Spotbugs brukes for å gjennkjenne bugs og bugsmønster i koden. 
Checkstyle produserer en rapport for Kodekvalitetsanalyse og detekterer syntax-feil og brudd på kodekonvensjon.
Spotbugs detekterer koden for bugs og klassifiserer de etter grad av grovhet. 