package trabajo1;

import java.util.ArrayList;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class Trabajo1 {

  public static void main(String[] args) {
    System.out.println("Creando RDF...");
    start();
  }

  public static Property addProp(Model model, String uri, String name) {
    return model.createProperty(uri, name);
  }

  public static Resource addRes(Model model, String uri, String name) {
    return model.createResource(uri + name);
  }

  public static Resource getRes(Model model, String uri, String name) {
    return model.getResource(uri + name);
  }

  public static Resource getRes(Model model, String fullname){
    return model.getResource(fullname);
  }

  public static Property getProp(Model model, String uri, String name) {
    return model.getProperty(uri + name);
  }
  public static Property getProp(Model model, String fullname) {
    return model.getProperty(fullname);
  }

  public static Model mergeInitialModels() {
    Model modelOCDE_Ford = ModelFactory.createDefaultModel();
    modelOCDE_Ford.read(
      "https://concytec-pe.github.io/vocabularios/ocde_ford.xml"
    );
    Model modelGradosAcademicos = ModelFactory.createDefaultModel();
    modelGradosAcademicos.read(
      "https://concytec-pe.github.io/vocabularios/renati_level.xml"
    );
    Model modelTiposTrabajosInvestigacion = ModelFactory.createDefaultModel();
    modelTiposTrabajosInvestigacion.read(
      "https://concytec-pe.github.io/vocabularios/renati_type.xml"
    );
    return modelOCDE_Ford.union(
      modelGradosAcademicos.union(modelTiposTrabajosInvestigacion)
    );
  }

  public static void addClassMetadataToModel(Model model, String uri) {
    Resource tesis = getRes(model,
      "http://purl.org/pe-repo/renati/type#tesis"
    );
    Resource tesisDeBachiller = addRes(model, uri, "TesisDeBachiller");
    Resource tesisDeMaestria = addRes(model, uri, "TesisDeMaestria");
    Resource tesisDeDoctorado = addRes(model, uri, "TesisDeDoctorado");
    Resource tesisFormatoTesis = addRes(model, uri, "TesisFormatoTesis");
    Resource tesisFormatoArticulo = addRes(model, uri, "TesisFormatoArticulo");
    Resource tesisEnDesarrollo = addRes(model, uri, "TesisEnDesarrollo");
    Resource tesisEnEvaluacion = addRes(model, uri, "TesisEnEvaluacion");
    Resource tesisEvaluada = addRes(model, uri, "TesisEvaluada");
    Resource articuloEnDesarrollo = addRes(model, uri, "ArticuloEnDesarrollo");
    Resource articuloEnEvaluación = addRes(model, uri, "ArticuloEnEvaluación");
    Resource articuloPublicado = addRes(model, uri, "ArticuloPublicado");

    model.add(tesisDeBachiller, RDFS.subClassOf, tesis);
    model.add(tesisDeMaestria, RDFS.subClassOf, tesis);
    model.add(tesisDeDoctorado, RDFS.subClassOf, tesis);
    model.add(tesisFormatoTesis, RDFS.subClassOf, tesisDeMaestria);
    model.add(tesisFormatoArticulo, RDFS.subClassOf, tesisDeMaestria);
    model.add(tesisEnDesarrollo, RDFS.subClassOf, tesisFormatoTesis);
    model.add(tesisEnEvaluacion, RDFS.subClassOf, tesisFormatoTesis);
    model.add(tesisEvaluada, RDFS.subClassOf, tesisFormatoTesis);
    model.add(articuloEnDesarrollo, RDFS.subClassOf, tesisFormatoArticulo);
    model.add(articuloEnEvaluación, RDFS.subClassOf, tesisFormatoArticulo);
    model.add(articuloPublicado, RDFS.subClassOf, tesisFormatoArticulo);

    Resource bachiller = getRes(model, "http://purl.org/pe-repo/renati/level#bachiller");
    Resource profesional = getRes(model, "http://purl.org/pe-repo/renati/level#tituloProfesional");
    Resource profesionalConSegundaEspecialidad = getRes(model, "http://purl.org/pe-repo/renati/level#tituloSegundaEspecialidad");
    Resource maestro = getRes(model, "http://purl.org/pe-repo/renati/level#maestro");
    Resource doctor = getRes(model, "http://purl.org/pe-repo/renati/level#doctor");
    

    model.add(bachiller, RDFS.subClassOf, FOAF.Person);
    model.add(profesional, RDFS.subClassOf, FOAF.Person);
    model.add(profesionalConSegundaEspecialidad, RDFS.subClassOf, FOAF.Person);
    model.add(maestro, RDFS.subClassOf, FOAF.Person);
    model.add(doctor, RDFS.subClassOf, FOAF.Person);


  }

  public static void addPropertyMetadataToModel(
    Model model,
    String uri
  ) {
    Property esContribuyenteDe = addProp(model, uri, "esContribuyenteDe");
    Property esAutorDe = addProp(model, uri, "esAutorDe");
    Property esAsesorDe = addProp(model, uri, "esAsesorDe");
    Property esJuradoDe = addProp(model, uri, "esJuradoDe");
    Property esAsesorPrincipalDe = addProp(model, uri, "esAsesorPrincipalDe");
    Property esAsesorSecundarioDe = addProp(model, uri, "esAsesorSecundarioDe");
    addProp(model, uri, "tieneComoCampo");
    addProp(model, uri, "tieneTesis");


    model.add(esAutorDe, RDFS.subPropertyOf, esContribuyenteDe);
    model.add(esAsesorDe, RDFS.subPropertyOf, esContribuyenteDe);
    model.add(esJuradoDe, RDFS.subPropertyOf, esContribuyenteDe);
    model.add(esAsesorPrincipalDe, RDFS.subPropertyOf, esAsesorDe);
    model.add(esAsesorSecundarioDe, RDFS.subPropertyOf, esAsesorDe);
  }

  public static void addInstancias(Model model, String uri) {
    // Tipificando mis datos(data)

    Resource tesisDeBachiller = getRes(model, uri, "TesisDeBachiller");
    Resource tesisEnDesarrollo = getRes(model, uri, "TesisEnDesarrollo");
    Resource tesisEnEvaluacion = getRes(model, uri, "TesisEnEvaluacion");
    Resource tesisFormatoArticulo = getRes(model, uri, "TesisFormatoArticulo");
    Resource tesisDeDoctorado = getRes(model, uri, "TesisDeDoctorado");
    Property esAsesorPrincipalDe = getProp(model, uri, "esAsesorPrincipalDe");
    Property esAsesorSecundarioDe = getProp(model, uri, "esAsesorSecundarioDe");
    Property esAutorDe = getProp(model, uri, "esAutorDe");
    Property esJuradoDe = getProp(model, uri, "esJuradoDe");
    Property tieneComoCampo = getProp(model, uri, "tieneComoCampo");
    Property tieneTesis = getProp(model, uri, "tieneTesis");

    
    Resource fisicaNuclear = getRes(model, "http://purl.org/pe-repo/ocde/ford#1.03.04");// Fisica Nuclear
    Resource ingCivil = getRes(model, "http://purl.org/pe-repo/ocde/ford#2.01.00");// Ingeniería civil
    Resource ingEstructural = getRes(model, "http://purl.org/pe-repo/ocde/ford#2.01.04");// Ingeniería estructural y municipal
    Resource probabilidad = getRes(model, "http://purl.org/pe-repo/ocde/ford#1.01.03");// Estadísticas, Probabilidad
    Resource economia = getRes(model, "http://purl.org/pe-repo/ocde/ford#5.02.01");// Economía

    Resource profesional = getRes(model, "http://purl.org/pe-repo/renati/level#tituloProfesional");
    Resource profesionalConSegundaEspecialidad = getRes(model, "http://purl.org/pe-repo/renati/level#tituloSegundaEspecialidad");
    Resource doctor = getRes(model, "http://purl.org/pe-repo/renati/level#doctor");
    Resource maestro = getRes(model, "http://purl.org/pe-repo/renati/level#maestro");

    model.add(esJuradoDe, RDFS.domain, doctor);
  
    
    Resource tesisExpansion = addRes(model, uri, "Expansión de los Sueños en la Estratosfera");
    Resource tesisSonaja = addRes(model, uri, "Implementación de una Sonaja Paralizadora para el Soporte de Puentes");
    Resource tesisContraste = addRes(model, uri, "Contraste Formal de Estructuras Dinamicas en Municipios");
    Resource tesisLibertad = addRes(model, uri, "La Libertad y la Guerra, como estan Relacionadas y sus Probables Consecuencias");
    Resource tesisEstrellas = addRes(model, uri, "7 Estrellas que Amortizan la Esperanza de Vida y el PBI");
    Resource tesisCompilador = addRes(model, uri, "Construcción de un compilador de asertos de programación metódica");


    Resource diego = addRes(model, uri, "Diego Berolatti Gonzales");
    Resource viktor = addRes(model, uri, "Viktor Khlebnikov");
    Resource grecia = addRes(model, uri, "Grecia Reyes Fernandez");
    Resource luis = addRes(model, uri, "Luis Vives Garnique");
    Resource michael = addRes(model, uri, "Michael Reeves");

    model.add(tesisExpansion, RDF.type, tesisEnDesarrollo);
    model.add(tesisSonaja, RDF.type, tesisEnDesarrollo);
    model.add(tesisContraste, RDF.type, tesisEnEvaluacion);
    model.add(tesisLibertad, RDF.type, tesisFormatoArticulo);
    model.add(tesisEstrellas, RDF.type, tesisDeDoctorado);
    model.add(tesisCompilador, RDF.type, tesisDeBachiller);

    model.add(tesisExpansion, tieneComoCampo, fisicaNuclear);
    model.add(tesisSonaja, tieneComoCampo, ingCivil);
    model.add(tesisContraste, tieneComoCampo, ingEstructural);
    model.add(tesisLibertad, tieneComoCampo, probabilidad);
    model.add(tesisEstrellas, tieneComoCampo, economia);

    model.add(diego, RDF.type, profesional);
    model.add(viktor, RDF.type, doctor);
    model.add(grecia, RDF.type, maestro);
    model.add(luis, RDF.type, profesionalConSegundaEspecialidad);

    model.add(diego, esAutorDe, tesisExpansion);
    model.add(diego, esAutorDe, tesisCompilador);
    model.add(viktor, esAsesorPrincipalDe, tesisExpansion);
    model.add(grecia, esAsesorSecundarioDe, tesisExpansion);
    model.add(luis, esAsesorSecundarioDe, tesisExpansion);
    model.add(michael, esJuradoDe, tesisEstrellas);

    Resource blankNode = model.createResource();
    Property tesisDePregrado = addProp(model, uri, "tesisDePregrado");
    Property tesisDeMaestria = addProp(model, uri, "tesisDeMaestria");
    blankNode.addProperty(tesisDePregrado, tesisCompilador);
    blankNode.addProperty(tesisDeMaestria, tesisExpansion);
    diego.addProperty(tieneTesis, blankNode);
  }


  // 1) Obtener todas las tesis de Maestria
  public static void inferenciaOfSubClass(Model model, String uri) {
    InfModel infModel = ModelFactory.createRDFSModel(model);
    Resource resource = infModel.getResource(uri + "TesisDeMaestria");
    Selector selector = new SimpleSelector(null, RDF.type, (RDFNode) resource);
    StmtIterator iter = infModel.listStatements(selector);
    while (iter.hasNext()) {
      System.out.println(iter.nextStatement().toString());
    }
  }

  // 2) Obtener todos los Asesores de la Tesis "Expansion de los Sueños en la Estratosfera"
  public static void inferenciaOfSubProperty(Model model, String uri) {
    InfModel infModel = ModelFactory.createRDFSModel(model);
    Resource resource = getRes(infModel, uri, "Expansión de los Sueños en la Estratosfera");
    Property esAsesorDe = getProp(infModel, uri, "esAsesorDe");
    Selector selector = new SimpleSelector(null, esAsesorDe, (RDFNode) resource);
    StmtIterator iter = infModel.listStatements(selector);
    while (iter.hasNext()) {
      System.out.println(iter.nextStatement().toString());
    }
  }

  // 3) Obtener todos los Doctores del modelo
  public static void inferenciaOfDomain(Model model, String uri) {
    InfModel infModel = ModelFactory.createRDFSModel(model);
    Resource resource = getRes(model, "http://purl.org/pe-repo/renati/level#doctor");
    Selector selector = new SimpleSelector(null, RDF.type, (RDFNode)resource);
    StmtIterator iter = infModel.listStatements(selector);
    while (iter.hasNext()) {
      System.out.println(iter.nextStatement().toString());
    }
  }

    // 3) Obtener todas las Tesis
    public static void inferenciaOfUnionOrIntersection(Model model, String uri) {
      InfModel infModel = ModelFactory.createRDFSModel(model);
      Resource resource = getRes(model,"http://purl.org/pe-repo/renati/type#tesis");
      Selector selector = new SimpleSelector(null, RDF.type, (RDFNode)resource);
      StmtIterator iter = infModel.listStatements(selector);
      while (iter.hasNext()) {
        System.out.println(iter.nextStatement().toString());
      }
    }


  public static void start() {
    Model model = mergeInitialModels();
    String uri = "http://www.pucp.edu.pe/#";
    addClassMetadataToModel(model, uri);
    addPropertyMetadataToModel(model, uri);
    addInstancias(model, uri);
    // inferenciaOfSubClass(model, uri);
    // inferenciaOfSubProperty(model, uri);
    // inferenciaOfDomain(model, uri);
    inferenciaOfUnionOrIntersection(model, uri);
    // Serializer serializer = new Serializer();
    // serializer.saveModel(model, "RDF-XML");
  }
}
