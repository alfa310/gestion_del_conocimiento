package sesionrdfs;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDFS;

public class Tarea2 {
  static final String paradigmURI =
    "https://raw.githubusercontent.com/andres-melgar/rdf/master/paradigmas.rdf";
  static final String algorighmURI =
    "https://raw.githubusercontent.com/andres-melgar/rdf/master/algoritmos.rdf";
  static final String machineLearningURI =
    "https://raw.githubusercontent.com/andres-melgar/rdf/master/aprendizaje_maquina.rdf";

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println(
        "ERROR: add a paradigmURI in order to get the algorithms for that paradigm."
      );
      return;
    }

    Model model = loadModel();
    InfModel infModel = ModelFactory.createRDFSModel(model);
    String paradigmResourceURI = args[0];
    getAlgorithmsFromParadigmURI(infModel, paradigmResourceURI);
  }

  public static void printArrayList(List list) {
    System.out.println("Number of Elements: " + list.size());
    for (int i = 0; i < list.size(); i++) {
      System.out.println("Elements " + i + ": " + list.get(i).toString());
    }
  }

  public static void getAlgorithmsFromParadigmURI(
    Model model,
    String paradigmResourceURI
  ) {
    Resource paradigmResource = model.getResource(paradigmResourceURI);
    ArrayList<Resource> paradigmSubtypeResources = getParadigmSubTypesFromParadigmTypeResource(
      model,
      paradigmResource
    );
    printArrayList(paradigmSubtypeResources);
  }

  public static ArrayList<Resource> getParadigmSubTypesFromParadigmTypeResource(
    Model model,
    Resource paradigmResource
  ) {
    Selector selector = new SimpleSelector(
      null,
      null,
      (RDFNode) paradigmResource
    );
    ArrayList<Resource> paradigmSubtypeResources = new ArrayList<Resource>();
    StmtIterator iter = model.listStatements(selector);
    while (iter.hasNext()) {
      paradigmSubtypeResources.add(iter.nextStatement().getSubject());
    }
    return paradigmSubtypeResources;
  }

  public static Model loadModel() {
    Model paradignModel = ModelFactory.createDefaultModel();
    Model algorithmModel = ModelFactory.createDefaultModel();
    Model machineLearningModel = ModelFactory.createDefaultModel();
    paradignModel.read(paradigmURI);
    algorithmModel.read(algorighmURI);
    machineLearningModel.read(machineLearningURI);
    Model model = machineLearningModel.union(
      algorithmModel.union(paradignModel)
    );
    return model;
  }
}
