package sesionrdfs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class SesionRDFS {

  public static void main(String[] args) {
    Model model = ModelFactory.createDefaultModel();
    String uri = "http://www.pucp.edu.pe/";
    String ns = "pucp";
    model.setNsPrefix(ns, uri);
    Resource mensWear = crearRecurso(uri, "MensWear", model);
    Resource shirts = crearRecurso(uri, "Shirts", model);
    Resource tshirts = crearRecurso(uri, "Tshirts", model);
    Resource henleys = crearRecurso(uri, "Henleys", model);
    Resource oxfords = crearRecurso(uri, "Oxfords", model);
    Resource chamoisHenley = crearRecurso(uri, "ChamoisHenley", model);
    Resource classicOxford = crearRecurso(uri, "ClassicOxford", model);

    // Sujeto, Predicado, Objeto
    // Esquema de mi modelo (metadata)
    model.add(shirts, RDFS.subClassOf, mensWear);
    model.add(tshirts, RDFS.subClassOf, shirts);
    model.add(henleys, RDFS.subClassOf, shirts);
    model.add(oxfords, RDFS.subClassOf, shirts);

    // Tipificando mis datos(data)
    model.add(chamoisHenley, RDF.type, henleys);
    model.add(classicOxford, RDF.type, shirts);
    model.add(classicOxford, RDF.type, oxfords);

    FileOutputStream output = null;
    try {
      output = new FileOutputStream("camisas.rdf");
    } catch (FileNotFoundException e) {
      System.out.println("Ocurrio un error al crear el archivo.");
    }
    model.write(output, "RDF/XML-ABBREV");
  }

  private static Resource crearRecurso(String uri, String id, Model model) {
    return model.createResource(uri + id);
  }
}
