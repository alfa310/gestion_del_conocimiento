package sesionrdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class Ejemplo3 {

  public static void main(String[] args) {
    String url = "https://concytec-pe.github.io/vocabularios/ocde_ford.xml";
    Model model = ModelFactory.createDefaultModel();
    model.read(url);
    model.write(System.out);
  }
}
