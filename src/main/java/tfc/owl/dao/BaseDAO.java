package tfc.owl.dao;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;

import java.io.File;
import java.util.Set;

/**
 * TDF
 * User: Esaú González
 * Date: 18/05/14
 * Time: 20:28
 * DAO with the basic OWL ontologies operations
 */
public abstract class BaseDAO {

    private static String PREFIX = "http://www.owl-ontologies.com/OntologyTwitter.owl#";
    private PrefixManager prefixManager= new DefaultPrefixManager(PREFIX);

    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
    OWLOntology twitterOWL;
    private OWLDataFactory owlDataFactory;

    protected BaseDAO() throws OWLOntologyCreationException {
        File file = new File(System.getProperty("ontology.path"));

        twitterOWL = manager.loadOntologyFromOntologyDocument(file);
        System.out.println(twitterOWL);

        owlDataFactory = manager.getOWLDataFactory();
    }

    public OWLIndividual createNewClassIndividual(String pClassName, String pIndividualName){
        OWLClass owlClass = owlDataFactory.getOWLClass(":"+pClassName, prefixManager);
        OWLNamedIndividual owlIndividual = owlDataFactory.getOWLNamedIndividual(":" + pIndividualName, prefixManager);
        OWLClassAssertionAxiom classAssertion = owlDataFactory.getOWLClassAssertionAxiom(owlClass, owlIndividual);
        manager.addAxiom(twitterOWL, classAssertion);
        return owlIndividual;
    }

    public Set<OWLIndividual> findAllClassIndividuals(String pClassName){
        OWLClass userMentionClass = owlDataFactory.getOWLClass(":"+pClassName, prefixManager);
        return userMentionClass.getIndividuals(twitterOWL);
    }

    public void setObjectProperty(String pObjectPropertyName, OWLIndividual pIndividualA, OWLIndividual pIndividualB){
        OWLObjectProperty objectProperty = owlDataFactory.getOWLObjectProperty(pObjectPropertyName,prefixManager);
        OWLObjectPropertyAssertionAxiom objectPropertyAssertion = owlDataFactory.getOWLObjectPropertyAssertionAxiom(objectProperty,pIndividualA, pIndividualB);
        manager.addAxiom(twitterOWL, objectPropertyAssertion);
    }

    public void setFloatDataTypeProperty(String pPropertyName, float pPropertyValue, OWLIndividual pIndividual){
        OWLDataPropertyExpression idProperty = owlDataFactory.getOWLDataProperty(pPropertyName,prefixManager);
        OWLDataPropertyAssertionAxiom assertion = owlDataFactory.getOWLDataPropertyAssertionAxiom(idProperty, pIndividual, pPropertyValue);
        manager.addAxiom(twitterOWL,assertion);
    }

    public void setBooleanDataTypeProperty(String pPropertyName, boolean pPropertyValue, OWLIndividual pNamedIndividual) {
        OWLDataPropertyExpression idProperty = owlDataFactory.getOWLDataProperty(pPropertyName,prefixManager);
        OWLDataPropertyAssertionAxiom assertion = owlDataFactory.getOWLDataPropertyAssertionAxiom(idProperty, pNamedIndividual, pPropertyValue);
        manager.addAxiom(twitterOWL,assertion);
    }

    public void setIntDataTypeProperty(String pPropertyName, int pPropertyValue, OWLIndividual pNamedIndividual) {
        OWLDataPropertyExpression idProperty = owlDataFactory.getOWLDataProperty(pPropertyName,prefixManager);
        OWLLiteral propertyValue = owlDataFactory.getOWLLiteral(pPropertyValue);
        OWLDataPropertyAssertionAxiom assertion = owlDataFactory.getOWLDataPropertyAssertionAxiom(idProperty, pNamedIndividual, propertyValue);
        manager.addAxiom(twitterOWL,assertion);
    }

    public void setStringDataTypeProperty(String pPropertyName, String pPropertyValue, OWLIndividual pNamedIndividual){
        if (pPropertyValue==null) pPropertyValue="";
        OWLDataPropertyExpression idProperty = owlDataFactory.getOWLDataProperty(pPropertyName,prefixManager);
        OWLDataPropertyAssertionAxiom assertion = owlDataFactory.getOWLDataPropertyAssertionAxiom(idProperty, pNamedIndividual, pPropertyValue);
        manager.addAxiom(twitterOWL,assertion);
    }

    public void setDateTimeDataProperty(String pPropertyName, String pPropertyValue, OWLIndividual pNamedIndividual){
        OWLDataPropertyExpression createdAtProperty = owlDataFactory.getOWLDataProperty(pPropertyName,prefixManager);
        OWLDatatype dateTimeType = owlDataFactory.getOWLDatatype(OWL2Datatype.XSD_DATE_TIME.getIRI());
        OWLLiteral createdAtLiteral = owlDataFactory.getOWLLiteral(pPropertyValue, dateTimeType);
        OWLDataPropertyAssertionAxiom createdAtAssertion = owlDataFactory.getOWLDataPropertyAssertionAxiom(createdAtProperty, pNamedIndividual,createdAtLiteral);
        manager.addAxiom(twitterOWL,createdAtAssertion);
    }

    public OWLDataPropertyImpl findDataProperty(String pPropertyName){
        return new OWLDataPropertyImpl(prefixManager.getIRI(":"+pPropertyName));
    }

    public Set<OWLLiteral> findAllDataPropertyLiterals(OWLIndividual pIndividual, OWLDataPropertyImpl pDataProperty){
        return pIndividual.getDataPropertyValues(pDataProperty,twitterOWL);
    }

}
