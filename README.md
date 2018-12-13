# ©AnimeKeeper 
This is an android studio school project

Author : Ledeme Alexandre et Martinot Thibaut

This app allows an user to keep track of his anime. He can add an anime to the database and view a list of all the animes added by users.

## Sidenote

You can use these to login :
- username : "test"
- password : " " (a space)

If you modify the username and / or the password, please put it back to the original when finished.

# Anime Keeper is at V1.0.1

## This is a list of all the functions that have been added:
- On the page with all the animes, you can delete an anime by pressing it a long time (1 second). A popup will ask the user for its confirmation.
- A new test branch has been developed, instead of MVC pattern, I used MVVM to see if it is more efficient or not.

# Anime Keeper is at V1.0.0

## This is a list of all the functions that have been developped:
- login page, the user has to input an username and a password
- Once the user is loged in, he can acess the list of all the anime in the database or in the menu bar, he can access a form to modify his personnal data or launch the Crunchyroll app (it displays the app in play strore if he doesn't have the app installed)
- The user can modify his username and his password. Once the update is done, a notification is displayed warning the user he should login again whit the new data
- A list of all the anime in the database is displayed in a RecyclerView that update itself if a new anime has been inserted in the database.
- The user can fill in a form to add a new anime in the database.
- If the user press the return button on his phone on the main page, the app will need a confirmation so the user can logout.
- note : all query are done with POST / GET requests. I use oracle apex to manage a REST API

## known bugs:
- some landscape view ruins the UX and the design
- if you don't write anything in the password field on loggin, the app will crash when you will press the login button (it will not if you write something, delete it then click the login button)

# Future content

This app will be developed furter and this is the list of functions I will try to add :
- Upgrade the login screen so that a new user can create a new account with his name, username, password, email
- Add a manga section, just like the anime section
- create a new type of user : the mods. They will have the right to delete or update some animes / manga.
- add a reputation system, if a user has been on the app for some time and has good level of reputation, he will be upgraded to the mod status
- add a "news" section, admin and mods can add some news (link to tweets, facebook posts, links, images, ...)
- add some design to the anime list (that's gonna take some time to make it perfect ^^ )


# Fair use

"Copyright Disclaimer Under Section 107 of the Copyright Act 1976, allowance is made for "fair use" for purposes such as criticism, comment, news reporting, teaching, scholarship, and research. Fair use is a use permitted by copyright statute that might otherwise be infringing. Non-profit, educational or personal use tips the balance in favor of fair use."
