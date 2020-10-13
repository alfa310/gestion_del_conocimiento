package sesionrdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class Ejemplo1 {

  public static void main(String[] args) {
    System.out.println("Creando RDF...");
    String personURI = "http://www.pucp.edu.pe/AndresMelgar";
    String fullName = "Andres Melgar";

    Model model = ModelFactory.createDefaultModel();
    // Subject in Jena is Resource
    Resource andresMelgar = model.createResource(personURI);
    // Addproperty adds properties to resource (Resouce, Property, Object)
    andresMelgar.addProperty(VCARD.FN, fullName);

    Resource blankNode = model.createResource();
    blankNode.addProperty(VCARD.Family, "Melgar Sasieta");
    blankNode.addProperty(VCARD.Given, "Hector Andree");
    andresMelgar.addProperty(VCARD.N, blankNode);
    System.out.println("");

    model.write(System.out, "RDF/XML");

    FileOutputStream output = null;
    try {
      output = new FileOutputStream("vCard.rdf");
    } catch (FileNotFoundException e) {
      System.out.println("Ocurrio un error al crear el archivo.");
    }
    model.write(output, "RDF/XML-ABBREV");
  }
}
