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

public class Tarea1 {

  public static void main(String[] args) {
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

    Model modelo = modeloGrupos.union(modeloAlumnos.union(modeloCorreos));

    String action = args[0];

    if (action.equals("codigo")) {
      String codigo = args[1];
      String nombre = obtenerNombreDelCodigo(modelo, codigo);
      ArrayList<String> correos = obtenerCorreosDelCodigo(modelo, codigo);

      System.out.println("nombre: " + nombre);
      int index = 1;
      Iterator itr = correos.iterator(); //getting the Iterator
      while (itr.hasNext()) { //check if iterator has the elements
        System.out.println("correo " + index + ": " + itr.next());
        index += 1; //printing the element and move to next
      }
    } else if (action.equals("nombre")) {
      int i = 2;
      String nombre = args[1];
      while (i < args.length){
        nombre += " " + args[i];
        i++;
      }
      // String nombre = args[1] + " " + args[2] + " " + args[3] + " " + args[4];
      String grupo = obtenerGrupoDelNombre(modelo, nombre);
      System.out.println("grupo: " + grupo);
    } else if (action.equals("grupo")) {
      String grupo = args[1];
      ArrayList<String> correos = obtenerCorreosDelGrupo(modelo, grupo);
      Iterator itr = correos.iterator(); //getting the Iterator
      int index = 1;
      while (itr.hasNext()) { //check if iterator has the elements
        System.out.println("correo " + index + ": " + itr.next());
        index += 1; //printing the element and move to next
      }
    }
  }

  public static ArrayList<String> obtenerCorreosDelGrupo(
    Model modelo,
    String grupo
  ) {
    ArrayList<String> correos = new ArrayList<String>();
    Selector selector1 = new SimpleSelector(
      null,
      null,
      (RDFNode) modelo.createLiteral(grupo)
    );
    StmtIterator iter1 = modelo.listStatements(selector1);
    if (iter1.hasNext()) {
      Resource grupoRes = iter1.nextStatement().getSubject();
      Selector selector2 = new SimpleSelector(null, null, (RDFNode) grupoRes);
      StmtIterator iter2 = modelo.listStatements(selector2);
      ArrayList<String> codigos = new ArrayList<String>();

      while (iter2.hasNext()) {
        codigos.add(iter2.nextStatement().getSubject().toString());
      }

      Iterator itr = codigos.iterator();

      while (itr.hasNext()) {
        Resource codigo = modelo.getResource(itr.next().toString());
        Selector selector3 = new SimpleSelector(
          codigo,
          VCARD.EMAIL,
          (RDFNode) null
        );

        StmtIterator iter3 = modelo.listStatements(selector3);
        while (iter3.hasNext()) {
          correos.add(iter3.nextStatement().getObject().toString());
        }
      }
    }

    return correos;
  }

  public static String obtenerGrupoDelNombre(Model modelo, String nombre) {
    System.out.println("nombre: " + nombre);
    Selector selector = new SimpleSelector(
      null,
      null,
      (RDFNode) modelo.createLiteral(nombre)
    );
    StmtIterator iterNombre = modelo.listStatements(selector);
    if (iterNombre.hasNext()) {
      Resource codigo = iterNombre.nextStatement().getSubject();
      Property pertenece = modelo.getProperty(
        "https://raw.githubusercontent.com/andres-melgar/rdf/master/grupos.rdf#pertenece"
      );
      Selector selectorNombre = new SimpleSelector(
        codigo,
        pertenece,
        (RDFNode) null
      );
      StmtIterator iterGrupo = modelo.listStatements(selectorNombre);
      if (iterGrupo.hasNext()) {
        Resource grupo = iterGrupo.nextStatement().getObject().asResource();
        Selector selector3 = new SimpleSelector(
          grupo,
          VCARD.GROUP,
          (RDFNode) null
        );
        StmtIterator iter3 = modelo.listStatements(selector3);
        if (iter3.hasNext()) {
          return iter3.nextStatement().getObject().toString();
        }
      }
    }
    return null;
  }

  public static String obtenerNombreDelCodigo(Model modelo, String codigo) {
    String codigoUri = "alum:" + codigo;

    Resource recurso = modelo.getResource(codigoUri);
    Selector selectorNombre = new SimpleSelector(
      recurso,
      VCARD.FN,
      (RDFNode) null
    );
    StmtIterator iterNombre = modelo.listStatements(selectorNombre);
    if (iterNombre.hasNext()) {
      return iterNombre.nextStatement().getObject().toString();
    }
    return null;
  }

  public static ArrayList<String> obtenerCorreosDelCodigo(
    Model modelo,
    String codigo
  ) {
    String codigoUri = "alum:" + codigo;
    Resource recurso = modelo.getResource(codigoUri);
    Selector selectorNombre = new SimpleSelector(
      recurso,
      VCARD.EMAIL,
      (RDFNode) null
    );
    ArrayList<String> correos = new ArrayList<String>();
    StmtIterator iter = modelo.listStatements(selectorNombre);
    while (iter.hasNext()) {
      correos.add(iter.nextStatement().getObject().toString());
    }
    return correos;
  }
}
