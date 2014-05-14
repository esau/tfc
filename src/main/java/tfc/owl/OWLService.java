package tfc.owl;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import tfc.dto.CoordinatesDTO;
import tfc.dto.TweetDTO;
import tfc.dto.UserDTO;
import tfc.owl.dao.*;

import java.io.File;
import java.util.Set;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:53
 */
@Service
public class OWLService {
    

    //TODO: load from file!!!
    private final static String twitterOntology ="<?xml version=\"1.0\"?>\n" +
            "<rdf:RDF\n" +
            "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
            "    xmlns:protege=\"http://protege.stanford.edu/plugins/owl/protege#\"\n" +
            "    xmlns:xsp=\"http://www.owl-ontologies.com/2005/08/07/xsp.owl#\"\n" +
            "    xmlns=\"http://www.owl-ontologies.com/OntologyTwitter.owl#\"\n" +
            "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n" +
            "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n" +
            "    xmlns:swrl=\"http://www.w3.org/2003/11/swrl#\"\n" +
            "    xmlns:swrlb=\"http://www.w3.org/2003/11/swrlb#\"\n" +
            "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" +
            "  xml:base=\"http://www.owl-ontologies.com/OntologyTwitter.owl\">\n" +
            "  <owl:Ontology rdf:about=\"\"/>\n" +
            "  <owl:Class rdf:ID=\"Media\">\n" +
            "    <rdfs:subClassOf>\n" +
            "      <owl:Class rdf:ID=\"Entity\"/>\n" +
            "    </rdfs:subClassOf>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:ID=\"Hashtag\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:ID=\"Url\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:ID=\"UserMention\"/>\n" +
            "    </owl:disjointWith>\n" +
            "  </owl:Class>\n" +
            "  <owl:Class rdf:ID=\"Place\">\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:ID=\"Tweet\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:ID=\"User\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:about=\"#Entity\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:ID=\"Coordinates\"/>\n" +
            "    </owl:disjointWith>\n" +
            "  </owl:Class>\n" +
            "  <owl:Class rdf:about=\"#Entity\">\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:about=\"#Tweet\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:about=\"#User\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith rdf:resource=\"#Place\"/>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:about=\"#Coordinates\"/>\n" +
            "    </owl:disjointWith>\n" +
            "  </owl:Class>\n" +
            "  <owl:Class rdf:about=\"#Url\">\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:about=\"#Hashtag\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith rdf:resource=\"#Media\"/>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:about=\"#UserMention\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <rdfs:subClassOf rdf:resource=\"#Entity\"/>\n" +
            "  </owl:Class>\n" +
            "  <owl:Class rdf:about=\"#Coordinates\">\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:about=\"#Tweet\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:about=\"#User\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith rdf:resource=\"#Entity\"/>\n" +
            "    <owl:disjointWith rdf:resource=\"#Place\"/>\n" +
            "  </owl:Class>\n" +
            "  <owl:Class rdf:about=\"#UserMention\">\n" +
            "    <rdfs:subClassOf rdf:resource=\"#Entity\"/>\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:about=\"#Hashtag\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith rdf:resource=\"#Media\"/>\n" +
            "    <owl:disjointWith rdf:resource=\"#Url\"/>\n" +
            "  </owl:Class>\n" +
            "  <owl:Class rdf:about=\"#Hashtag\">\n" +
            "    <rdfs:subClassOf rdf:resource=\"#Entity\"/>\n" +
            "    <owl:disjointWith rdf:resource=\"#Media\"/>\n" +
            "    <owl:disjointWith rdf:resource=\"#Url\"/>\n" +
            "    <owl:disjointWith rdf:resource=\"#UserMention\"/>\n" +
            "  </owl:Class>\n" +
            "  <owl:Class rdf:about=\"#Tweet\">\n" +
            "    <owl:disjointWith>\n" +
            "      <owl:Class rdf:about=\"#User\"/>\n" +
            "    </owl:disjointWith>\n" +
            "    <owl:disjointWith rdf:resource=\"#Entity\"/>\n" +
            "    <owl:disjointWith rdf:resource=\"#Place\"/>\n" +
            "    <owl:disjointWith rdf:resource=\"#Coordinates\"/>\n" +
            "  </owl:Class>\n" +
            "  <owl:Class rdf:about=\"#User\">\n" +
            "    <owl:disjointWith rdf:resource=\"#Coordinates\"/>\n" +
            "    <owl:disjointWith rdf:resource=\"#Tweet\"/>\n" +
            "    <owl:disjointWith rdf:resource=\"#Entity\"/>\n" +
            "    <owl:disjointWith rdf:resource=\"#Place\"/>\n" +
            "  </owl:Class>\n" +
            "  <owl:ObjectProperty rdf:ID=\"inPlace\">\n" +
            "    <rdfs:range rdf:resource=\"#Place\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Coordinates\"/>\n" +
            "    <owl:inverseOf>\n" +
            "      <owl:ObjectProperty rdf:ID=\"boundingBox\"/>\n" +
            "    </owl:inverseOf>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:ID=\"isFollowedBy\">\n" +
            "    <owl:inverseOf>\n" +
            "      <owl:ObjectProperty rdf:ID=\"follows\"/>\n" +
            "    </owl:inverseOf>\n" +
            "    <rdfs:range rdf:resource=\"#User\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:ID=\"mentionsUser\">\n" +
            "    <owl:inverseOf>\n" +
            "      <owl:ObjectProperty rdf:ID=\"mentionedIn\"/>\n" +
            "    </owl:inverseOf>\n" +
            "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#UserMention\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      The User mentioned&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"#User\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:ID=\"inTweet\">\n" +
            "    <rdfs:domain rdf:resource=\"#Coordinates\"/>\n" +
            "    <rdfs:range rdf:resource=\"#Tweet\"/>\n" +
            "    <owl:inverseOf>\n" +
            "      <owl:ObjectProperty rdf:ID=\"hasCoordinates\"/>\n" +
            "    </owl:inverseOf>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:ID=\"tweetRelated\">\n" +
            "    <rdfs:domain rdf:resource=\"#Place\"/>\n" +
            "    <rdfs:range rdf:resource=\"#Tweet\"/>\n" +
            "    <owl:inverseOf>\n" +
            "      <owl:ObjectProperty rdf:ID=\"relatedToPlace\"/>\n" +
            "    </owl:inverseOf>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:about=\"#hasCoordinates\">\n" +
            "    <owl:inverseOf rdf:resource=\"#inTweet\"/>\n" +
            "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Tweet\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Geographic location of this Tweet as reported by the User&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"#Coordinates\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:about=\"#follows\">\n" +
            "    <rdfs:range rdf:resource=\"#User\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "    <owl:inverseOf rdf:resource=\"#isFollowedBy\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:ID=\"isAuthor\">\n" +
            "    <owl:inverseOf>\n" +
            "      <owl:ObjectProperty rdf:ID=\"authoredBy\"/>\n" +
            "    </owl:inverseOf>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "    <rdfs:range rdf:resource=\"#Tweet\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:ID=\"inReplyOfUser\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      If Tweet is a reply, contains the Tweet replied&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/>\n" +
            "    <rdfs:range rdf:resource=\"#User\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Tweet\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:ID=\"comesFrom\">\n" +
            "    <rdfs:range rdf:resource=\"#Tweet\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Entity\"/>\n" +
            "    <owl:inverseOf>\n" +
            "      <owl:ObjectProperty rdf:ID=\"hasEntities\"/>\n" +
            "    </owl:inverseOf>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:ID=\"hasUrl\">\n" +
            "    <owl:inverseOf>\n" +
            "      <owl:ObjectProperty rdf:ID=\"comesFromUser\"/>\n" +
            "    </owl:inverseOf>\n" +
            "    <rdfs:range rdf:resource=\"#Url\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:about=\"#authoredBy\">\n" +
            "    <owl:inverseOf rdf:resource=\"#isAuthor\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      User who authored this Tweet&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/>\n" +
            "    <rdfs:range rdf:resource=\"#User\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Tweet\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:about=\"#mentionedIn\">\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "    <rdfs:range rdf:resource=\"#UserMention\"/>\n" +
            "    <owl:inverseOf rdf:resource=\"#mentionsUser\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:about=\"#comesFromUser\">\n" +
            "    <owl:inverseOf rdf:resource=\"#hasUrl\"/>\n" +
            "    <rdfs:range rdf:resource=\"#User\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Url\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:ID=\"inReplyOfTweet\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      If Tweet is a reply, contains the Tweet replied&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Tweet\"/>\n" +
            "    <rdfs:range rdf:resource=\"#Tweet\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:about=\"#relatedToPlace\">\n" +
            "    <rdfs:range rdf:resource=\"#Place\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      The Tweet is related to this Place&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/>\n" +
            "    <owl:inverseOf rdf:resource=\"#tweetRelated\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Tweet\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:about=\"#boundingBox\">\n" +
            "    <owl:inverseOf rdf:resource=\"#inPlace\"/>\n" +
            "    <rdfs:range rdf:resource=\"#Coordinates\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Coordinates making the bounding box that encloses the Place&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain rdf:resource=\"#Place\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:about=\"#hasEntities\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Entities parsed out from the text of the Tweet&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain rdf:resource=\"#Tweet\"/>\n" +
            "    <rdfs:range rdf:resource=\"#Entity\"/>\n" +
            "    <owl:inverseOf rdf:resource=\"#comesFrom\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:ObjectProperty rdf:ID=\"isRetweet\">\n" +
            "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      If this Tweet is a retweet, contains the original Tweet&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"#Tweet\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Tweet\"/>\n" +
            "  </owl:ObjectProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"tweetCount\">\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Number of Tweet published by User&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#int\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"attributes\">\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Place\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Additional information about the Place, like address etc.&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"listedCount\">\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#int\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Number of public lists where this User is member&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"type\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain>\n" +
            "      <owl:Class>\n" +
            "        <owl:unionOf rdf:parseType=\"Collection\">\n" +
            "          <owl:Class rdf:about=\"#Media\"/>\n" +
            "          <owl:Class rdf:about=\"#Place\"/>\n" +
            "          <owl:Class rdf:about=\"#Coordinates\"/>\n" +
            "        </owl:unionOf>\n" +
            "      </owl:Class>\n" +
            "    </rdfs:domain>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"friendsCount\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Number of User this User is following&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#int\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"timeZone\">\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      User defined time zone&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"id\">\n" +
            "    <rdfs:domain>\n" +
            "      <owl:Class>\n" +
            "        <owl:unionOf rdf:parseType=\"Collection\">\n" +
            "          <owl:Class rdf:about=\"#Tweet\"/>\n" +
            "          <owl:Class rdf:about=\"#User\"/>\n" +
            "          <owl:Class rdf:about=\"#Media\"/>\n" +
            "          <owl:Class rdf:about=\"#Place\"/>\n" +
            "        </owl:unionOf>\n" +
            "      </owl:Class>\n" +
            "    </rdfs:domain>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Unique id of this Individual\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"countryName\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Place's country name&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Place\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"indexA\">\n" +
            "    <rdfs:domain rdf:resource=\"#Entity\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Initial position of the entity in the text field&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#int\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"screenName\">\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      User pseudonym&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain>\n" +
            "      <owl:Class>\n" +
            "        <owl:unionOf rdf:parseType=\"Collection\">\n" +
            "          <owl:Class rdf:about=\"#User\"/>\n" +
            "          <owl:Class rdf:about=\"#UserMention\"/>\n" +
            "        </owl:unionOf>\n" +
            "      </owl:Class>\n" +
            "    </rdfs:domain>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"description\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      User defined description&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"protected\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      has the User protected his tweets?&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#boolean\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"location\">\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      User location, defined by himself&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"createdAt\">\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#dateTime\"/>\n" +
            "    <rdfs:domain>\n" +
            "      <owl:Class>\n" +
            "        <owl:unionOf rdf:parseType=\"Collection\">\n" +
            "          <owl:Class rdf:about=\"#Tweet\"/>\n" +
            "          <owl:Class rdf:about=\"#User\"/>\n" +
            "        </owl:unionOf>\n" +
            "      </owl:Class>\n" +
            "    </rdfs:domain>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      UTC time of the creation of the Individual\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"longitude\">\n" +
            "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      longitude value for Coordinates&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain rdf:resource=\"#Coordinates\"/>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#float\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"countryCode\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Place's country code&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain rdf:resource=\"#Place\"/>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"mediaUrl\">\n" +
            "    <rdfs:domain>\n" +
            "      <owl:Class>\n" +
            "        <owl:unionOf rdf:parseType=\"Collection\">\n" +
            "          <owl:Class rdf:about=\"#Media\"/>\n" +
            "          <owl:Class rdf:about=\"#Url\"/>\n" +
            "        </owl:unionOf>\n" +
            "      </owl:Class>\n" +
            "    </rdfs:domain>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      URL unique for this resourse.&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"fullName\">\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Place\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"favouritesCount\">\n" +
            "    <rdfs:domain>\n" +
            "      <owl:Class>\n" +
            "        <owl:unionOf rdf:parseType=\"Collection\">\n" +
            "          <owl:Class rdf:about=\"#Tweet\"/>\n" +
            "          <owl:Class rdf:about=\"#User\"/>\n" +
            "        </owl:unionOf>\n" +
            "      </owl:Class>\n" +
            "    </rdfs:domain>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Times this Tweet is favourited&#xD; or this User has favorited\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#int\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"displayUrl\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Url as it appears in the text field&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "    <rdfs:domain>\n" +
            "      <owl:Class>\n" +
            "        <owl:unionOf rdf:parseType=\"Collection\">\n" +
            "          <owl:Class rdf:about=\"#Media\"/>\n" +
            "          <owl:Class rdf:about=\"#Url\"/>\n" +
            "        </owl:unionOf>\n" +
            "      </owl:Class>\n" +
            "    </rdfs:domain>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"lang\">\n" +
            "    <rdfs:domain rdf:resource=\"#Tweet\"/>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">machine detected language of the text</rdfs:comment>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"url\">\n" +
            "    <rdfs:domain rdf:resource=\"#Place\"/>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"indexB\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Final position of the entity text in the text field.&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#int\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Entity\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"isVerified\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Is User a verified individual?&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#boolean\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"name\">\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      User's name&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"followersCount\">\n" +
            "    <rdfs:domain rdf:resource=\"#User\"/>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#int\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      Number of Users following this User&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"source\">\n" +
            "    <rdfs:domain rdf:resource=\"#Tweet\"/>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">Utility used to post the Tweet, HTML-formatted string.</rdfs:comment>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"latitude\">\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#float\"/>\n" +
            "    <rdfs:domain rdf:resource=\"#Coordinates\"/>\n" +
            "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      latitude vale of the Coordinates&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"retweetsCount\">\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#int\"/>\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      number of times this Tweet has been retweetted&#xD;\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain rdf:resource=\"#Tweet\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <owl:DatatypeProperty rdf:ID=\"text\">\n" +
            "    <rdfs:comment xml:lang=\"en\">&lt;p style=\"margin-top: 0\"&gt;&#xD;\n" +
            "      text of the individual\n" +
            "    &lt;/p&gt;</rdfs:comment>\n" +
            "    <rdfs:domain>\n" +
            "      <owl:Class>\n" +
            "        <owl:unionOf rdf:parseType=\"Collection\">\n" +
            "          <owl:Class rdf:about=\"#Tweet\"/>\n" +
            "          <owl:Class rdf:about=\"#Hashtag\"/>\n" +
            "        </owl:unionOf>\n" +
            "      </owl:Class>\n" +
            "    </rdfs:domain>\n" +
            "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "  </owl:DatatypeProperty>\n" +
            "  <UserMention rdf:ID=\"UserMentionAtoB\">\n" +
            "    <indexB rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "    >44</indexB>\n" +
            "    <indexA rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "    >39</indexA>\n" +
            "    <mentionsUser>\n" +
            "      <User rdf:ID=\"UserB\">\n" +
            "        <screenName rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "        >ImUserB</screenName>\n" +
            "        <name rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "        >Amic B</name>\n" +
            "        <isAuthor>\n" +
            "          <Tweet rdf:ID=\"TweetB2\">\n" +
            "            <authoredBy rdf:resource=\"#UserB\"/>\n" +
            "            <createdAt rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\"\n" +
            "            >2014-04-12T22:11:34</createdAt>\n" +
            "            <isRetweet>\n" +
            "              <Tweet rdf:ID=\"TweetA4\">\n" +
            "                <retweetsCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                >1</retweetsCount>\n" +
            "                <authoredBy>\n" +
            "                  <User rdf:ID=\"UserA\">\n" +
            "                    <screenName rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                    >UserARules</screenName>\n" +
            "                    <createdAt rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\"\n" +
            "                    >2014-04-01T20:53:29</createdAt>\n" +
            "                    <friendsCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                    >2</friendsCount>\n" +
            "                    <id rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                    >123456789</id>\n" +
            "                    <protected rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n" +
            "                    >false</protected>\n" +
            "                    <isVerified rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n" +
            "                    >false</isVerified>\n" +
            "                    <name rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                    >Primer user</name>\n" +
            "                    <listedCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                    >0</listedCount>\n" +
            "                    <favouritesCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                    >3</favouritesCount>\n" +
            "                    <description rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                    >UserA is the main sample user</description>\n" +
            "                    <isAuthor rdf:resource=\"#TweetA4\"/>\n" +
            "                    <location rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                    >Barcelona</location>\n" +
            "                    <isAuthor>\n" +
            "                      <Tweet rdf:ID=\"TweetA3\">\n" +
            "                        <source rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >web</source>\n" +
            "                        <hasEntities>\n" +
            "                          <Url rdf:ID=\"UrlA1\">\n" +
            "                            <comesFrom rdf:resource=\"#TweetA3\"/>\n" +
            "                            <displayUrl rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >http://www.ontologytwitter.org</displayUrl>\n" +
            "                            <mediaUrl rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >http://www.ontologytwitter.org</mediaUrl>\n" +
            "                            <indexA rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                            >29</indexA>\n" +
            "                            <indexB rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                            >59</indexB>\n" +
            "                          </Url>\n" +
            "                        </hasEntities>\n" +
            "                        <createdAt rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\"\n" +
            "                        >2014-04-12T22:10:58</createdAt>\n" +
            "                        <hasEntities>\n" +
            "                          <Url rdf:ID=\"UrlA2\">\n" +
            "                            <comesFrom rdf:resource=\"#TweetA3\"/>\n" +
            "                            <displayUrl rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >http://www.owl-ontologies.com/OntologyTwitter.owl</displayUrl>\n" +
            "                            <indexA rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                            >64</indexA>\n" +
            "                            <indexB rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                            >113</indexB>\n" +
            "                            <mediaUrl rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >http://www.owl-ontologies.com/OntologyTwitter.owl</mediaUrl>\n" +
            "                          </Url>\n" +
            "                        </hasEntities>\n" +
            "                        <inReplyOfTweet>\n" +
            "                          <Tweet rdf:ID=\"TweetB1\">\n" +
            "                            <authoredBy rdf:resource=\"#UserB\"/>\n" +
            "                            <text rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >Thank you very much! Can you tell me how to use the ontology?</text>\n" +
            "                            <createdAt rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\"\n" +
            "                            >2014-04-11T21:12:21</createdAt>\n" +
            "                            <lang rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >en</lang>\n" +
            "                            <source rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >Twitter for android</source>\n" +
            "                            <id rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >23434341</id>\n" +
            "                            <inReplyOfUser rdf:resource=\"#UserA\"/>\n" +
            "                            <inReplyOfTweet>\n" +
            "                              <Tweet rdf:ID=\"TweetA2\">\n" +
            "                                <retweetsCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                                >1</retweetsCount>\n" +
            "                                <createdAt rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\"\n" +
            "                                >2014-04-11T21:10:03</createdAt>\n" +
            "                                <text rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                >Welcome to the #TwitterOntology !! http://www.image.jpg No doubt to ask questions...</text>\n" +
            "                                <id rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                >343452345</id>\n" +
            "                                <source rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                >web</source>\n" +
            "                                <lang rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                >en</lang>\n" +
            "                                <hasEntities>\n" +
            "                                  <Media rdf:ID=\"MediaA2\">\n" +
            "                                    <comesFrom rdf:resource=\"#TweetA2\"/>\n" +
            "                                    <mediaUrl rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                    >http://www.image.jpg</mediaUrl>\n" +
            "                                    <id rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                    >6641354543</id>\n" +
            "                                    <displayUrl rdf:datatype=\n" +
            "                                    \"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                    >http://www.image.jpg</displayUrl>\n" +
            "                                    <indexA rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                                    >35</indexA>\n" +
            "                                    <indexB rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                                    >55</indexB>\n" +
            "                                    <type rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                    >photo</type>\n" +
            "                                  </Media>\n" +
            "                                </hasEntities>\n" +
            "                                <favouritesCount rdf:datatype=\n" +
            "                                \"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                                >1</favouritesCount>\n" +
            "                                <hasEntities>\n" +
            "                                  <Hashtag rdf:ID=\"HashtagA2\">\n" +
            "                                    <text rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                    >TwitterOntology</text>\n" +
            "                                    <comesFrom rdf:resource=\"#TweetA2\"/>\n" +
            "                                    <indexB rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                                    >30</indexB>\n" +
            "                                    <indexA rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                                    >0</indexA>\n" +
            "                                  </Hashtag>\n" +
            "                                </hasEntities>\n" +
            "                                <authoredBy rdf:resource=\"#UserA\"/>\n" +
            "                              </Tweet>\n" +
            "                            </inReplyOfTweet>\n" +
            "                          </Tweet>\n" +
            "                        </inReplyOfTweet>\n" +
            "                        <authoredBy rdf:resource=\"#UserA\"/>\n" +
            "                        <inReplyOfUser rdf:resource=\"#UserB\"/>\n" +
            "                        <id rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >456373</id>\n" +
            "                        <text rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >Sure, you just need to visit http://www.ontologytwitter.org and http://www.owl-ontologies.com/OntologyTwitter.owl. Thanks for coming! </text>\n" +
            "                      </Tweet>\n" +
            "                    </isAuthor>\n" +
            "                    <tweetCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                    >6</tweetCount>\n" +
            "                    <follows rdf:resource=\"#UserB\"/>\n" +
            "                    <timeZone rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                    >GMT+1 Madrid</timeZone>\n" +
            "                    <follows>\n" +
            "                      <User rdf:ID=\"UserC\">\n" +
            "                        <createdAt rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\"\n" +
            "                        >2014-04-10T21:01:17</createdAt>\n" +
            "                        <favouritesCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                        >0</favouritesCount>\n" +
            "                        <friendsCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                        >0</friendsCount>\n" +
            "                        <listedCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                        >0</listedCount>\n" +
            "                        <name rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >Die C user</name>\n" +
            "                        <isVerified rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n" +
            "                        >false</isVerified>\n" +
            "                        <location rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >Berlin</location>\n" +
            "                        <isFollowedBy rdf:resource=\"#UserA\"/>\n" +
            "                        <id rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >123456789</id>\n" +
            "                        <protected rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n" +
            "                        >false</protected>\n" +
            "                        <screenName rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >UserC</screenName>\n" +
            "                        <timeZone rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >GMT+1 Madrid</timeZone>\n" +
            "                        <isAuthor>\n" +
            "                          <Tweet rdf:ID=\"TweetC1\">\n" +
            "                            <relatedToPlace>\n" +
            "                              <Place rdf:ID=\"Place1\">\n" +
            "                                <boundingBox>\n" +
            "                                  <Coordinates rdf:ID=\"Coordinates3\">\n" +
            "                                    <type rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                    >point</type>\n" +
            "                                    <latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\"\n" +
            "                                    >12.584111</latitude>\n" +
            "                                    <inPlace rdf:resource=\"#Place1\"/>\n" +
            "                                    <longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\"\n" +
            "                                    >-69.12512</longitude>\n" +
            "                                  </Coordinates>\n" +
            "                                </boundingBox>\n" +
            "                                <countryName rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                >Spain</countryName>\n" +
            "                                <id rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                >41354351435</id>\n" +
            "                                <tweetRelated rdf:resource=\"#TweetC1\"/>\n" +
            "                                <boundingBox>\n" +
            "                                  <Coordinates rdf:ID=\"Coordinates4\">\n" +
            "                                    <latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\"\n" +
            "                                    >12.584113</latitude>\n" +
            "                                    <longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\"\n" +
            "                                    >-69.125</longitude>\n" +
            "                                    <inPlace rdf:resource=\"#Place1\"/>\n" +
            "                                    <type rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                    >point</type>\n" +
            "                                  </Coordinates>\n" +
            "                                </boundingBox>\n" +
            "                                <fullName rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                >Madrid, ES</fullName>\n" +
            "                                <attributes rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                >Gran via, 23</attributes>\n" +
            "                                <boundingBox>\n" +
            "                                  <Coordinates rdf:ID=\"Coordinates2\">\n" +
            "                                    <type rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                    >point</type>\n" +
            "                                    <latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\"\n" +
            "                                    >12.584111</latitude>\n" +
            "                                    <longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\"\n" +
            "                                    >-69.125</longitude>\n" +
            "                                    <inPlace rdf:resource=\"#Place1\"/>\n" +
            "                                  </Coordinates>\n" +
            "                                </boundingBox>\n" +
            "                                <countryCode rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                >ES</countryCode>\n" +
            "                                <boundingBox>\n" +
            "                                  <Coordinates rdf:ID=\"Coordinates5\">\n" +
            "                                    <longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\"\n" +
            "                                    >-69.12345</longitude>\n" +
            "                                    <inPlace rdf:resource=\"#Place1\"/>\n" +
            "                                    <type rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                    >point</type>\n" +
            "                                    <latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\"\n" +
            "                                    >12.584111</latitude>\n" +
            "                                  </Coordinates>\n" +
            "                                </boundingBox>\n" +
            "                                <type rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                                >city</type>\n" +
            "                              </Place>\n" +
            "                            </relatedToPlace>\n" +
            "                            <authoredBy rdf:resource=\"#UserC\"/>\n" +
            "                            <source rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >Twitter for iosx</source>\n" +
            "                            <text rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >Hi all! I'm new in Twitter, My name is UserC and I'm in holidays in Madrid</text>\n" +
            "                            <createdAt rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\"\n" +
            "                            >2014-04-01T22:12:41</createdAt>\n" +
            "                          </Tweet>\n" +
            "                        </isAuthor>\n" +
            "                        <followersCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                        >0</followersCount>\n" +
            "                        <description rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >third user</description>\n" +
            "                        <tweetCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                        >2</tweetCount>\n" +
            "                      </User>\n" +
            "                    </follows>\n" +
            "                    <followersCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                    >1</followersCount>\n" +
            "                    <isAuthor rdf:resource=\"#TweetA2\"/>\n" +
            "                    <isFollowedBy rdf:resource=\"#UserB\"/>\n" +
            "                    <isAuthor>\n" +
            "                      <Tweet rdf:ID=\"TweetA1\">\n" +
            "                        <favouritesCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                        >1</favouritesCount>\n" +
            "                        <createdAt rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\"\n" +
            "                        >2014-04-10T21:05:53</createdAt>\n" +
            "                        <hasCoordinates>\n" +
            "                          <Coordinates rdf:ID=\"Coordinates1\">\n" +
            "                            <latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\"\n" +
            "                            >12.584688</latitude>\n" +
            "                            <longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\"\n" +
            "                            >-69.12549</longitude>\n" +
            "                            <type rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                            >point</type>\n" +
            "                            <inTweet rdf:resource=\"#TweetA1\"/>\n" +
            "                          </Coordinates>\n" +
            "                        </hasCoordinates>\n" +
            "                        <id rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >1234342454</id>\n" +
            "                        <lang rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >en</lang>\n" +
            "                        <source rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >web</source>\n" +
            "                        <authoredBy rdf:resource=\"#UserA\"/>\n" +
            "                        <text rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                        >Hello there! I'm using Twitter!!!</text>\n" +
            "                      </Tweet>\n" +
            "                    </isAuthor>\n" +
            "                  </User>\n" +
            "                </authoredBy>\n" +
            "                <createdAt rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\"\n" +
            "                >2014-04-12T22:11:24</createdAt>\n" +
            "                <favouritesCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "                >1</favouritesCount>\n" +
            "                <id rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                >5432524356</id>\n" +
            "                <source rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                >web</source>\n" +
            "                <hasEntities rdf:resource=\"#UserMentionAtoB\"/>\n" +
            "                <text rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "                >Welcome to the Twitter Ontology group @UserB</text>\n" +
            "              </Tweet>\n" +
            "            </isRetweet>\n" +
            "          </Tweet>\n" +
            "        </isAuthor>\n" +
            "        <favouritesCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "        >4</favouritesCount>\n" +
            "        <isVerified rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n" +
            "        >false</isVerified>\n" +
            "        <friendsCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "        >1</friendsCount>\n" +
            "        <listedCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "        >1</listedCount>\n" +
            "        <isFollowedBy rdf:resource=\"#UserA\"/>\n" +
            "        <description rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "        >UserB friend of UserA</description>\n" +
            "        <protected rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n" +
            "        >false</protected>\n" +
            "        <isAuthor rdf:resource=\"#TweetB1\"/>\n" +
            "        <tweetCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "        >3</tweetCount>\n" +
            "        <location rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "        >Mataró</location>\n" +
            "        <follows rdf:resource=\"#UserA\"/>\n" +
            "        <createdAt rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\"\n" +
            "        >2014-04-01T20:53:29</createdAt>\n" +
            "        <mentionedIn rdf:resource=\"#UserMentionAtoB\"/>\n" +
            "        <id rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "        >123456777</id>\n" +
            "        <followersCount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "        >1</followersCount>\n" +
            "        <hasUrl>\n" +
            "          <Url rdf:ID=\"UrlB1\">\n" +
            "            <displayUrl rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "            >http://www.iamuserb.org</displayUrl>\n" +
            "            <comesFromUser rdf:resource=\"#UserB\"/>\n" +
            "            <mediaUrl rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "            >http://www.iamuserb.org</mediaUrl>\n" +
            "            <indexB rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "            >0</indexB>\n" +
            "            <indexA rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n" +
            "            >23</indexA>\n" +
            "          </Url>\n" +
            "        </hasUrl>\n" +
            "        <timeZone rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "        >GMT+1 Madrid</timeZone>\n" +
            "      </User>\n" +
            "    </mentionsUser>\n" +
            "    <comesFrom rdf:resource=\"#TweetA4\"/>\n" +
            "    <screenName rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n" +
            "    >UserB</screenName>\n" +
            "  </UserMention>\n" +
            "</rdf:RDF>";

    private static String OWL_PATH="conf"+File.pathSeparator+"resources"+File.pathSeparator+"TwitterOntology.owl";
    private static String PREFIX = "http://www.owl-ontologies.com/OntologyTwitter.owl#";
    private PrefixManager prefixManager= new DefaultPrefixManager(PREFIX);

    @Autowired
    private CoordinatesDAO coordinatesDAO;
    @Autowired
    private HashtagDAO hashtagDAO;
    @Autowired
    private MediaDAO mediaDAO;
    @Autowired
    private PlaceDAO placeDAO;
    @Autowired
    private TweetDAO tweetDAO;
    @Autowired
    private UrlDAO urlDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserMentionDAO userMentionDAO;

    public OWLService() throws OWLOntologyCreationException {


        //File file = new File(OWL_PATH);
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        OWLOntology twitterOWL = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(twitterOntology));
        //OWLOntology twitterOWL = manager.loadOntologyFromOntologyDocument(file);
        System.out.println(twitterOWL);

        OWLDataFactory owlDataFactory = manager.getOWLDataFactory();
        OWLClass owlTweet = owlDataFactory.getOWLClass(":Tweet",prefixManager);
        Set<OWLIndividual> individualSet = owlTweet.getIndividuals(twitterOWL);
        for (OWLIndividual owlIndividual : individualSet) {
            System.out.println("Individual: " + owlIndividual.toStringID());
        }



        //Ejemplo de crear un individuo de una clase
        OWLClass person = owlDataFactory.getOWLClass(":Person", prefixManager);
        OWLNamedIndividual mary = owlDataFactory.getOWLNamedIndividual(":Mary", prefixManager);
        OWLClassAssertionAxiom classAssertion = owlDataFactory.getOWLClassAssertionAxiom(person, mary);
        manager.addAxiom(twitterOWL, classAssertion);

        //Ejemplo de rellenar propiedades de un individuo
        OWLIndividual matthew = owlDataFactory.getOWLNamedIndividual("matthew",prefixManager);
        OWLIndividual peter = owlDataFactory.getOWLNamedIndividual("peter",prefixManager);
        OWLObjectProperty hasFather = owlDataFactory.getOWLObjectProperty("hasFather",prefixManager);
        OWLObjectPropertyAssertionAxiom assertion = owlDataFactory.getOWLObjectPropertyAssertionAxiom(hasFather, matthew, peter);

        //ejemplo de buscar un individuo igual en la ontologia
        OWLNamedIndividual pepe = owlDataFactory.getOWLNamedIndividual(":Pepe",prefixManager);
        Set<OWLIndividual> pepes = pepe.getSameIndividuals(twitterOWL);


    }

    public void store(TweetDTO tweet) {
        //TODO: process Tweet and store it and all it's inner classes into OWL:
        //todo: store User
        storeUser(tweet.getAuthor());

        //todo: store list of Media
        //todo: store list of Hashtag
        //todo: store list of Url
        //todo: store list of UserMention
        //todo: store Tweet replied (with all its entities)
        //todo: store Tweet retweeted (with all its entities)
        //todo: store Place
        //todo: store Tweet
        //todo: store Coordinates and link with Tweet
        storeCoordinates(tweet.getCoordinates());
        //todo: link Tweet with: - List of Media
        //todo:                  - List of Hashtag
        //todo:                  - List of Url
        //todo:                  - List of UrlMention
        //todo:                  - Tweet replied or retweeted
        //todo:                  - Place




        System.out.println("Storing Tweet in OWL");
    }

    private void storeCoordinates(CoordinatesDTO coordinates) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void storeUser(UserDTO author) {
      /*  private String id;
        private Date created;
        private String description;
        private int favouritesCount;
        private int followersCount;
        //user id of users followed
        private List<String> userFollowed;
        private int friendsCount;
        private String url;
        //user id of followers
        private List<String> followers;
        private boolean verified;
        private int listedCount;
        private String location;
        //id of tweets where it's mentioned in
        private List<String> mentionedIn;
        private String name;
        private boolean protectedUser;
        private String screenName;
        private String timeZone;
        private int tweetCount;*/

        //todo: create user in OWL with all fields
        //todo: find followers in OWL (by id) and assign them as followers of this user
        //todo: find friends in OWL (by id) and assign them as friends of this user
        //todo: find any mention of this user in OWL and assign it

    }

    public static void main(String[] args){
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Application-Spring-conf.xml");
        context.start();
    }
}
