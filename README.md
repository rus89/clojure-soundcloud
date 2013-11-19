# ClojureSoundcloud

A Clojure library designed to connect to your Soundcloud account and manage with your settings. With this application you can play some randomly generated music from Soundcloud, search all sounds and manage you account. If you want to use application you have to login, and if you don't have account you can register. This application is made using Clojure, MongoDB and Leiningen. To connect and manage Soundcloud account, search and play music application use Soundcloud API.

***Available pages are:***

* main (login) and registration - initial pages for accessing application
* home - page shows all application provided services; on this page you can play music from Soundcloud, search it, connect and manage your account

/access to home page is restricted if user is not authenticated.

## Usage

It's necessary to start MongoDB before running the application. Database used in this project is MongoDB 2.2.4 (to download, visit http://www.mongodb.org/downloads). Depending your operating system (Windows, Linux,...) you can find installation and running instruction on http://docs.mongodb.org/manual/installation/. The application automaticaly inserts dummy data in database. If you would like to prevent this comment out the first line in the main method.

It's necessary to install Leiningen. On http://leiningen.org/ you can fond all instructions and documentation. The easiest way for Windows users to install Leiningen is to download it from http://leiningen-win-installer.djpowell.net/ and install it. 

After you have installed necessary dependencies, follow steps below to run the project:

* Start MongoDB: (http://docs.mongodb.org/manual/tutorial/install-mongodb-on-windows/)
`mongodb\bin\mongod.exe --dbpath c:\path\to\your\databse\folder`
`mongodb\bin\mongo.exe`

* Start application:
`navigate to project root folder (cd path/to/root/folder)`
`lein run`

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
