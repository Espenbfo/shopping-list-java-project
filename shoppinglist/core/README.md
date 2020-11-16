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


## Persistenslaget 
Persistenslaget inneholder logikk for lagring med JSON. Det kan da produseres tekstfiler med et ShoppingList-objekt, med tilhørende Person (evt. flere) og ShoppingElement(s). Det er også implementert lagring av personer med tilhørende data.
Passordinformasjon lagres også på JSON-format.
Logikken støtter også opphenting av slike filer. 

## Klassediagram 
