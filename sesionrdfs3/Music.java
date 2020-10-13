package sesionrdfs3;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class Music {
  public static void main(String[] args) {
    String url = "http://purl.org/ontology/mo/";
    Model model = ModelFactory.createDefaultModel();
    model.read(url);
    model.write(System.out);
  }
}
