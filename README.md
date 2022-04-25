# SeniorProject
How to use
-------------
mySQL Installation:  
Go to https://dev.mysql.com/downloads/  
Click the link to download "Connector/J"  
When finished downloading, open "MySql Installer.exe"  
Click the 'Add' Button  
If it opens automatically, make sure "developer default" is checked and click next  
Click Next to skip the check requirements page, click yes if prompted  
Click execute to download the selected products  
Click execute to install the selected products  
Click next  
Click next, making no changes to the server configuration  
Click next to use the default authentication  
In the "Accounts and Roles" Section: create a root password, optionally create a non root user  
In the "Windows Service" Section: leave options as the defaults and click next  
Click execute to Apply the configuration  
Click Finish  
Click Next to go to rounter configuration  
Leave settings as defaults and click finish  
Click next and enter the root password  
Click next then execute to apply configurations  
Click finish, next, then finish  

mySQL Server Setup:  
Open mySQL workbench and create a new connection, note its name
Close mySQL workbench

Run application:  
Download the Repository as a zip file  
Extract the files  
Double click the invenManager.jar file  
Login using the connection name, and mysql username and password  

Files
------------
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
