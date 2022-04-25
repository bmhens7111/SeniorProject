# SeniorProject
How to use
-------------
Stuff

Main.java - starts the application  
LoginScreen.java - creates the login screen to access the application  
HomePage.java - creates the application Home Page, displays table representing the database contents and buttons to navitage to other menus 
NewMenu.java - creates the new item menu, item parameters are entered here  
EditMenu.java - creates the edit menu, type an item id to create an update menu for it  
UpdateMenu.java - creates the update menu with the given item from Edit Menu  
DeleteMenu.java - creates the delete menu, type an item id to delete it from the database  
ExportMenu.java - creates the export menu, export the database contents as a csv file with given name in a given location  
FileMenu.java - creates the file menu, used to edit possible item tags and locations  
SQL.java - helper class used to consolidate SQL statements created in the database for easier debugging and reuseability  
LastAction.java - class used by the Undo Button in HomePage.java, determines whether to undo an update or delete action and which item to perform it on  
Item.java - represents a product in the database: has an id, name, location, quantity, tags, and date added  
Popup.java - creates the a popup window, used for warnings such as low item quantities or error messages  
/res Folder - stores Button Icons  
/files Folder - stores tag and location files
