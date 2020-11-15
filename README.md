[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-purple?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2049/gr2049.git)

# Gr2049

Prosjektet til gruppe 49, liste-applikasjon klar for tredje og siste innlevering. 
Kode ligger i src-mappene.


```plantuml
actor Person
Person -> SaveButton: press
SaveButton -> Controller: pressed
Controller -> FileHandler: write(shoppingList)
FileHandler -> shoppingList: getId()
shoppingList -> FileHandler: Return
FileHandler -> Controller: Return
```

