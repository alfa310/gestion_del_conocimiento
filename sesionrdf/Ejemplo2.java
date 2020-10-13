package sesionrdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.VCARD;

public class Ejemplo2 {

  public static void main(String[] args) {
    System.out.println(" Leyendo RDF ... ");
    Model model = ModelFactory.createDefaultModel();
    String inputFileName = "docs/vCard.ttl";
    model.read(inputFileName);
    //   StmtIterator iter = model.listStatements();
    //   while (iter.hasNext()) {
    //     Statement stmt = iter.nextStatement();
    //     Resource subject = stmt.getSubject();
    //     Property predicate = stmt.getPredicate();
    //     RDFNode object = stmt.getObject();
    //     System.out.println(subject.toString());
    //     System.out.println(predicate.toString());
    //     System.out.println(object.toString());
    //   }
    // }

    // ResIterator resIter = model.listSubjectsWithProperty(VCARD.FN);
    // while (resIter.hasNext()) {
    //   Resource res = resIter.nextResource();
    //   System.out.println(res);
    // }

    String personURI = "http://www.pucp.edu.pe/AndresMelgar";
    Resource person = model.getResource(personURI);
    Selector selector = new SimpleSelector(null, VCARD.TEL, (RDFNode) null);

    StmtIterator iter = model.listStatements(selector);

    while (iter.hasNext()) {
      System.out.println(iter.nextStatement().toString());
    }
  }
}
