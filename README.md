### AUTHOR
Micha Meier, Thiago Miranda
 

 
### COAUTHORS
Douglas, Gustavo, Paulo



### ABOUT
This application is used to find recipes by it's name, ingredients, max. calories and/or recipe types. To use the application you have to register yourself. The application written for portuguese users.



### ARCHITECTURE
ReceitaCerta is a front- and backend separated application. The communication occur over HTTP with a JSON string.

####Frontend: 
Written in Javascript by Thiago Miranda
####Backend: 
Written in Java by Micha Meier 

-

Further informations about the communication protocol are found in Google Docs: https://docs.google.com/document/d/1Xp3Nceu0wsi5M2YRxPj2_xhjqEw75kp12YE5SEMwHbI/edit?usp=sharing


###EXECUTION
This project is written in PHP and can be executed v.5.6+.


###DEPENDENCIES 
####Local Mysql Database: 
* username: receita_certa
* password: nosestamosonline75113

* The sql to create the database you find in the base folder.

####MessageWebService
MessageWebService a PHP API, you can clone it from my Bitbucket repository or use my (for tests) running on my Raspberry PI. To define the API URL you can change the config.txt file in the WebContent folder.
URL: http://naymic.dlinkddns.com:55030/msgWebService/
Repository: git@bitbucket.org:micthix/msgwebservice.git


###CONTACT FOR QUESTIONS OR FEEDBACK
micha.meier.siueg@gmail.com