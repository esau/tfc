Treball Final de Carrera
===

This application executes the as a Twitter query the string by param, reads all the resulted Tweets, processes them
and stores them in an OWL Twitter ontology.

Main entry point: tfc.Main

VM params: -Dontology.path=[path to]\TwitterOntology.owl
            Example: -Dontology.path=C:\Users\Esaú\Dropbox\UOC\TFC\PAC3\ontology\TwitterOntology.owl
Program params: "[Query string to execute in Twitter]"
            Example: "final liga BBVA"