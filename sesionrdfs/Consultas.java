package sesionrdfs;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class Consultas {
  
  public static void main(String[] args) {
    String inputFileName = "camisas.rdf";
    Model model = ModelFactory.createDefaultModel();
    model.read(inputFileName);

    InfModel infModel = ModelFactory.createRDFSModel(model);

    consulta2(infModel);
  }

  public static void consulta1(Model model) {
    String resourcedURI = model.expandPrefix("pucp:Henleys");
    Resource resource = model.getResource(resourcedURI);
    Selector selector = new SimpleSelector(resource, RDFS.subClassOf,
    (RDFNode) null);
    StmtIterator iter = model.listStatements(selector);
    while (iter.hasNext()) {
      System.out.println(iter.nextStatement().toString());
    }
  }

  public static void consulta2(Model model) {
    String resourcedURI = model.expandPrefix("pucp:Shirts");
    Resource resource = model.getResource(resourcedURI);
    Selector selector = new SimpleSelector(null, RDF.type,
    (RDFNode) resource);
    StmtIterator iter = model.listStatements(selector);
    while (iter.hasNext()) {
      System.out.println(iter.nextStatement().toString());
    }
  }

}
