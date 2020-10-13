package sesionrdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class Ejemplo4 {

  public static void main(String[] args) {
    Model model = ModelFactory.createDefaultModel();
    String ns = "pucp";
    String uri = "http://www.pucp.edu.pe/"; // Todas las URIS terminan con un / o un #
    model.setNsPrefix(ns, uri);

    Property married = model.createProperty(uri, "married");
    Property wrote = model.createProperty(uri, "wrote");
    Property liveIn = model.createProperty(uri, "liveIn");
    Property isIn = model.createProperty(uri, "isIn");
    Property partOf = model.createProperty(uri, "partOf");
    Property setIn = model.createProperty(uri, "setIn");

    Resource AnneHathaway = model.createResource("pucp:AnneHathaway");
    Resource Shakespeare = model.createResource("pucp:Shakespeare");
    Resource Macbeth = model.createResource("pucp:Macbeth");
    Resource Stratford = model.createResource("pucp:Stratford");
    Resource Scotland = model.createResource("pucp:Scotland");
    Resource England = model.createResource("pucp:England");
    Resource KingLear = model.createResource("pucp:KingLear");
    Resource UK = model.createResource("pucp:UK");

    AnneHathaway.addProperty(married, Shakespeare);
    Shakespeare.addProperty(wrote, KingLear);
    Shakespeare.addProperty(wrote, Macbeth);
    Shakespeare.addProperty(liveIn, Stratford);
    Macbeth.addProperty(setIn, Scotland);
    Stratford.addProperty(isIn, England);
    Scotland.addProperty(partOf, UK);
    England.addProperty(partOf, UK);

    FileOutputStream output = null;
    try {
      output = new FileOutputStream("Shakespeare.rdf");
    } catch (FileNotFoundException e) {
      System.out.println("Ocurrio un error al crear el archivo.");
    }
    model.write(output, "RDF/XML-ABBREV");
  }
}
