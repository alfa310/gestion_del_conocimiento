package sesionrdfs2;

import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Derivation;

public class Tarea2 {

  public static void main(String[] args) {
    String inputFileName = "relaciones_trabajadores.rdf";
    Model model = ModelFactory.createDefaultModel();
    model.read(inputFileName);

    String resourceURI = model.expandPrefix("pucp:Goldman");
    Resource goldman = model.getResource(resourceURI);
    resourceURI = model.expandPrefix("pucp:TheFirm");
    Resource TheFirm = model.getResource(resourceURI);
    String propertyURI = model.expandPrefix("pucp:worksFor");
    Property worksFor = model.getProperty(propertyURI);

    Selector selector = new SimpleSelector(goldman, worksFor, TheFirm);
    StmtIterator iter = model.listStatements(selector);
    while (iter.hasNext()) {
      System.out.println(iter.nextStatement().toString());
    }

    InfModel infModel = ModelFactory.createRDFSModel(model);
    iter = infModel.listStatements(selector);
    while (iter.hasNext()) {
      System.out.println(iter.nextStatement().toString());
    }
  }

  public static void mostrarDerivaciones(
    InfModel model,
    Resource sujeto,
    Property predicado,
    Resource objeto
  ) {
    model.setDerivationLogging(true);
    PrintWriter out = new PrintWriter(System.out);
    for (
      StmtIterator i = model.listStatements(sujeto, predicado, objeto);
      i.hasNext();
    ) {
      Statement s = i.nextStatement();
      System.out.println(" Statement is " + s);
      for (Iterator id = model.getDerivation(s); id.hasNext();) {
        Derivation deriv = (Derivation) id.next();
        deriv.printTrace(out, true);
      }
    }
    out.flush();
  }
}
