package sesionrdfs2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;

public class Tarea1 {

  public static void main(String[] args) {
    System.out.println("Creando RDF...");
    start();
  }

  public static void start() {
    Model model = ModelFactory.createDefaultModel();
    String uri = "http://www.pucp.edu.pe/#";
    String ns = "pucp";
    model.setNsPrefix(ns, uri);
    Property worksFor = model.createProperty(uri, "worksFor");
    Property contractsTo = model.createProperty(uri, "contractsTo");
    Property isEmployedBy = model.createProperty(uri, "isEmployedBy");
    Property freeLancesTo = model.createProperty(uri, "freeLancesTo");
    Property indirectlyContractsTo = model.createProperty(
      uri,
      "indirectlyContractsTo"
    );
    model.add(contractsTo, RDFS.subPropertyOf, worksFor);
    model.add(isEmployedBy, RDFS.subPropertyOf, worksFor);
    model.add(freeLancesTo, RDFS.subPropertyOf, contractsTo);
    model.add(indirectlyContractsTo, RDFS.subPropertyOf, contractsTo);

    Resource TheFirm = model.createResource(uri + "TheFirm");
    Resource Goldman = model.createResource(uri + "Goldman");
    Resource Spence = model.createResource(uri + "Spence");
    Resource Long = model.createResource(uri + "Long");

    model.add(Goldman, isEmployedBy, TheFirm);
    model.add(Spence, freeLancesTo, TheFirm);
    model.add(Long, indirectlyContractsTo, TheFirm);

    FileOutputStream output = null;
    try {
      output = new FileOutputStream("relaciones_trabajadores.rdf");
    } catch (FileNotFoundException e) {
      System.out.println("Ocurrió un error al crear el archivo.");
    }
    model.write(output, "RDF/XML-ABBREV");
  }
}
