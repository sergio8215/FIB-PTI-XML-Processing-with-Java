 import javax.xml.transform.Source; 
import javax.xml.transform.stream.StreamSource; 
import javax.xml.validation.Schema; 
import javax.xml.validation.SchemaFactory; 
import javax.xml.validation.Validator; 
 
public class Program {
	public static void main(String args[]) throws Exception {
		SchemaFactory factory=SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
  		Schema schema=factory.newSchema(new StreamSource("schema.xsd"));
		Validator validator=schema.newValidator();
		Source source=new StreamSource("document.xml");
		validator.validate(source);
	}
}
