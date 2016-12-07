# introsde-2016-assignment-3-client
**Third assignment | University of Trento**

Documentation about assignment 03: SOAP Web Services

## Client structure
The client is divided into 3 packages:

* ```introsde.assignment.client```: it contains all the files in order to make requests to both my server and Bof's one;
* ```introsde.assignment.soap```: it contains all the generated files with wsimport of my server;
* ```introsde.document.ws```: it contains all the generated files with wsimport of Bof's server.

## Configuration files

The configuration files are:

* ```build.xml```: it contains all the targets to run the code;
* ```ivy.xml```: it contains all the dependencies needed to run the project and it downloads them.

## Setup

In order to clone the project and run it against the server deployed on Heroku:
* ```git clone https://github.com/SaraGasperetti/introsde-2016-assignment-3-client.git```

#### How to run the client

I worked in pair with Michele Bof (his server repository is at https://github.com/michelebof/introsde-2016-assignment-3).  
His server wsdl file is at: https://introsde2016-assignment3.herokuapp.com/ws/people?wsdl  
My server wsdl file is at: https://introsde2016-assignment-3.herokuapp.com/soap/people?wsdl  

In order to get response from my own server deployed on Heroku, execute: 
* ```cd introsde-2016-assignment-3-client```
* ```ant execute.myclient```


In order to get response from Michele Bof's server deployed on Heroku, execute: 
* ```cd introsde-2016-assignment-3-client```
* ```ant execute.client```
