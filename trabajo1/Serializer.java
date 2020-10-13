package trabajo1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Hashtable;

import org.apache.jena.rdf.model.Model;

public class Serializer {
  
  private class SerializeType {
    public String name;
    public String fileFormat;
    public String rdfFormat;
    public SerializeType(String name, String fileFormat, String rdfFormat){
      this.name = name;
      this.fileFormat = fileFormat;
      this.rdfFormat = rdfFormat;
    }
    public String getFileName(){
      return this.name + "-modelo." + this.fileFormat;
    }

    public String toString(){
      return "@" + this.name + "," + this.fileFormat + "," + this.rdfFormat; 
    }
  }

  public Hashtable<String, SerializeType> formats;
  public Serializer(){
    this.initFormats();
  }

  public void initFormats(){
    this.formats = new Hashtable<String, SerializeType>();
    this.formats.put("RDF-XML", new SerializeType("RDF-XML", "rdf", "RDF/XML-ABBREV"));
    this.formats.put("N-TRIPLE", new SerializeType("N-TRIPLE", "nt", "N-TRIPLE"));
    this.formats.put("TURTLE", new SerializeType("TURTLE", "ttl", "Turtle"));
    this.formats.put("TTL", new SerializeType("TTL", "ttl", "TTL"));
    this.formats.put("N3", new SerializeType("N3", "ttl", "N3"));
  }

  //RDF/XML, N-TRIPLE, TURTLE, TTL y N3
  public String saveModel(Model model,String format) {
    if (!this.formats.containsKey(format)){
      System.out.println("Formato no soportado");
      return null;
    }
    SerializeType type = this.formats.get(format);
    String fileName = type.getFileName();
    FileOutputStream output = null;
    System.out.println(fileName);
    try {
      output = new FileOutputStream(fileName);
    } catch (FileNotFoundException e) {
      System.out.println("Ocurrió un error al crear el archivo.");
    }
    
    model.write(output, type.rdfFormat); // "RDF/XML-ABBREV"
    return fileName;
  }



}
