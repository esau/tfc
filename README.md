Treball Final de Carrera
===

This application executes the as a Twitter query the string by param, reads all the resulted Tweets, processes them
and stores them in an OWL Twitter ontology.

Main entry point: tfc.Main

VM params: -Dontology.path=[path to]\TwitterOntology.owl
            Example: -Dontology.path=C:\Users\Esaú\Dropbox\UOC\TFC\PAC3\ontology\TwitterOntology.owl
Program params: "[Query string to execute in Twitter]" [query limit]

To build the executable jar, using maven:
mvn clean compile assembly:single

Execution with jar:
$>java -jar -Dontology.path=[ontology_file_path]/TwitterOntology.owl tfc-1.0-jar-with-dependencies.jar “[Twitter query]” [query limit]
            
