package sesionrdf;

import java.util.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.VCARD;

public class Tarea1Ordenada {

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println(
        "Error: action must be either: codigo, nombre or grupo"
      );
      return;
    }
    Model model = loadModel();
    String action = args[0];
    if (action.equals("codigo")) {
      String codigo = args[1];
      String nombre = getNombreFromCodigo(model, codigo);
      ArrayList<String> correos = getCorreosFromCodigo(model, codigo);
      System.out.println("nombre: " + nombre);
      int i = 1;
      for (String correo : correos) {
        System.out.println("correo " + i + ": " + correo);
        i += 1;
      }
      return;
    }

    if (action.equals("grupo")) {
      String grupo = args[1];
      ArrayList<String> correos = getCorreosFromGrupo(model, grupo);
      int i = 1;
      for (String correo : correos) {
        System.out.println("correo " + i + ": " + correo);
        i += 1;
      }
      return;
    }

    if (action.equals("nombre")) {
      int i = 2;
      String nombre = args[1];
      while (i < args.length) {
        nombre += " " + args[i];
        i++;
      }
      String grupo = getGrupoFromNombre(model, nombre);
      System.out.println("grupo: " + grupo);
      return;
    }
  }

  public static Model loadModel() {
    Model modeloAlumnos = ModelFactory.createDefaultModel();
    Model modeloCorreos = ModelFactory.createDefaultModel();
    Model modeloGrupos = ModelFactory.createDefaultModel();
    modeloAlumnos.read(
      "https://raw.githubusercontent.com/andres-melgar/rdf/master/alumnos.rdf"
    );
    modeloCorreos.read(
      "https://raw.githubusercontent.com/andres-melgar/rdf/master/correos.rdf"
    );
    modeloGrupos.read(
      "https://raw.githubusercontent.com/andres-melgar/rdf/master/grupos.rdf"
    );

    Model model = modeloGrupos.union(modeloAlumnos.union(modeloCorreos));
    return model;
  }

  public static Resource getCodigoAsResourceFromNombre(
    Model model,
    String nombre
  ) {
    Selector selector = new SimpleSelector(
      null,
      null,
      (RDFNode) model.createLiteral(nombre)
    );
    StmtIterator iter = model.listStatements(selector);
    if (iter.hasNext()) {
      return iter.nextStatement().getSubject();
    }
    return null;
  }

  public static Resource getGrupoAsResourceFromCodigoAndProperty(
    Model model,
    Resource resource,
    Property property
  ) {
    Selector selector = new SimpleSelector(resource, property, (RDFNode) null);
    StmtIterator iter = model.listStatements(selector);
    if (iter.hasNext()) {
      return iter.nextStatement().getObject().asResource();
    }
    return null;
  }

  public static String getGrupoFromNombre(Model model, String nombre) {
    Resource codigo = getCodigoAsResourceFromNombre(model, nombre);
    Property pertenece = model.getProperty(
      "https://raw.githubusercontent.com/andres-melgar/rdf/master/grupos.rdf#pertenece"
    );
    Resource grupo = getGrupoAsResourceFromCodigoAndProperty(
      model,
      codigo,
      pertenece
    );
    Selector selector = new SimpleSelector(grupo, VCARD.GROUP, (RDFNode) null);
    StmtIterator iter = model.listStatements(selector);
    if (iter.hasNext()) {
      return iter.nextStatement().getObject().toString();
    }
    return null;
  }

  public static String getNombreFromCodigo(Model model, String codigo) {
    Resource recurso = model.getResource("alum:" + codigo);
    Selector selector = new SimpleSelector(recurso, VCARD.FN, (RDFNode) null);
    StmtIterator iter = model.listStatements(selector);
    if (iter.hasNext()) {
      return iter.nextStatement().getObject().toString();
    }
    return null;
  }

  public static ArrayList<String> getCorreosFromCodigo(
    Model model,
    String codigo
  ) {
    Resource recurso = model.getResource("alum:" + codigo);
    Selector selector = new SimpleSelector(
      recurso,
      VCARD.EMAIL,
      (RDFNode) null
    );
    ArrayList<String> correos = new ArrayList<String>();
    StmtIterator iter = model.listStatements(selector);
    while (iter.hasNext()) {
      correos.add(iter.nextStatement().getObject().toString());
    }
    return correos;
  }

  public static ArrayList<String> getCorreosFromGrupo(
    Model model,
    String grupo
  ) {
    ArrayList<String> correos = new ArrayList<String>();
    Resource grupoResource = getGrupoAsResourceFromGrupo(model, grupo);
    ArrayList<String> codigos = getCodigosFromGrupoAsResource(
      model,
      grupoResource
    );

    for (String codigo : codigos) {
      Resource codigoResource = model.getResource(codigo);
      Selector selector = new SimpleSelector(
        codigoResource,
        VCARD.EMAIL,
        (RDFNode) null
      );
      StmtIterator iter = model.listStatements(selector);
      while (iter.hasNext()) {
        correos.add(iter.nextStatement().getObject().toString());
      }
    }
    return correos;
  }

  public static ArrayList<String> getCodigosFromGrupoAsResource(
    Model model,
    Resource grupo
  ) {
    ArrayList<String> codigos = new ArrayList<String>();
    Selector selector = new SimpleSelector(null, null, (RDFNode) grupo);
    StmtIterator iter = model.listStatements(selector);
    while (iter.hasNext()) {
      codigos.add(iter.nextStatement().getSubject().toString());
    }
    return codigos;
  }

  public static Resource getGrupoAsResourceFromGrupo(
    Model model,
    String grupo
  ) {
    Selector selector = new SimpleSelector(
      null,
      null,
      (RDFNode) model.createLiteral(grupo)
    );
    StmtIterator iter = model.listStatements(selector);
    if (iter.hasNext()) {
      return iter.nextStatement().getSubject();
    }
    return null;
  }
}
