[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-purple?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2049/gr2049.git)

# Gr2049

Prosjektet til gruppe 49, liste-applikasjon. 
Kode ligger i src-mappene.



```plantuml
actor Person
Person -> SaveButton: press
SaveButton -> Controller: pressed
Controller -> DataAccess: putShoppingList(ShoppingList)
DataAccess -> RestApi: putShoppingList(ShoppingList)
RestApi -> ShoppingList: setID(newId)
RestApi -> FileHandler: WriteShoppinglist(ShoppingList)
FileHandler -> ShoppingList: getId()
shoppingList -> FileHandler: Return id
FileHandler -> RestApi: Return successful
RestApi -> DataAccess: Return id
```


