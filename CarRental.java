import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.Namespace;

public class CarRental {

    /**
     * Read and parse an xml document from the file at CarRental.xml.
     * @return the JDOM document parsed from the file.
     */
    public static Document readDocument() {
        try {
            SAXBuilder builder = new SAXBuilder();
            //Document anotherDocument = builder.build(new File("CarRental.xml"));
            Document anotherDocument = builder.build(new FileReader("CarRental.xml"));

            return anotherDocument;
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method creates a empty JDOM document.
     * @return a JDOM Document that represents the properties of a car.
     */
    public static Document createDocumentEmpty() {
        
        // Create the root element
        Element carElement = new Element("carrental");
        
        Namespace ns = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");    
        carElement.setAttribute("noNAmespaceSchemaLocation", "carrental.xsd", ns);
    
        //create the document
        Document myDocument = new Document(carElement);
        
        return myDocument;
    }
 
    /**
     * This method shows how to use XMLOutputter to output a JDOM document to
     * the stdout.
     * @param myDocument a JDOM document.
     */
    public static void outputDocument(Document myDocument) {
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(myDocument, System.out);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shows how to use XMLOutputter to output a JDOM document to
     * a file located at myFile.xml.
     * @param myDocument a JDOM document.
     */
    public static void outputDocumentToFile(Document myDocument) {
        //setup this like outputDocument
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter();

            //output to a file
            FileWriter writer = new FileWriter("CarRental.xml");
            outputter.output(myDocument, writer);
            writer.close();

        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * The application will read the carrental.xml XML document into memory and 
     * validate it againt carrental.xsd
     */
    public static void validateDocument() {
        try {
            SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
            Document anotherDocument = builder.build(new File("CarRental.xml"));
            System.out.println("Root: " + anotherDocument.getRootElement().getName());
        }catch (JDOMException jdome) {
            // That string was not valid XML
            // do something about it....
            System.out.println("String was not XML: " +
            jdome.getMessage());

        }catch(NullPointerException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes a JDOM document in memory, an XSLT file at CarRental.xslt,
     * and outputs the results to stdout.
     * @param myDocument a JDOM document .
     */
    public static void executeXSLT(Document myDocument) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
            // Make the input sources for the XML and XSLT documents
            org.jdom2.output.DOMOutputter outputter = new org.jdom2.output.DOMOutputter();
            org.w3c.dom.Document domDocument = outputter.output(myDocument);
            javax.xml.transform.Source xmlSource = new javax.xml.transform.dom.DOMSource(domDocument);
            StreamSource xsltSource = new StreamSource(new FileInputStream("CarRental.xslt"));
			//Make the output result for the finished document
            StreamResult xmlResult = new StreamResult(System.out);
			//Get a XSLT transformer
			Transformer transformer = tFactory.newTransformer(xsltSource);
			//do the transform
			transformer.transform(xmlSource, xmlResult);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(TransformerConfigurationException e) {
            e.printStackTrace();
		} catch(TransformerException e) {
            e.printStackTrace();
        } catch(org.jdom2.JDOMException e) {
            e.printStackTrace();
        }
	}

    /**
     * This method read from console tha car data
     * and outputs carElement.
     */
    public static Element askData() {
        
        Element carElement = new Element("rental");
        System.out.print("Make:");
        String input = System.console().readLine();
        Element make = new Element("make");
        make.addContent(input);
        carElement.addContent(make);

        System.out.print("Model:");
        input = System.console().readLine();
        Element model = new Element("model");
        model.addContent(input);
        carElement.addContent(model);

        System.out.print("Number of days:");
        input = System.console().readLine();
        Element numdays = new Element("numdays");
        numdays.addContent(input);
        carElement.addContent(numdays);

        System.out.print("Number of units:");
        input = System.console().readLine();
        Element nounits = new Element("nounits");
        nounits.addContent(input);
        carElement.addContent(nounits);
        
        System.out.print("Discount:");
        input = System.console().readLine();
        Element discount = new Element("discount");
        discount.addContent(input);
        carElement.addContent(discount);

        return carElement;
    }

    /**
     * This method takes a JDOM document in memory, an XSLT file at CarRental.xslt,
     * and outputs the results to stdout.
     */
    public static Document newCarRental() {
        Element carElement = askData();
        Document documento = readDocument();
        Element carRentalElem = documento.getRootElement();

        carRentalElem.addContent(carElement);

        return documento;
    }

    /**
     * Main method that allows the various methods to be used.
     * It takes a single command line parameter.  If none are
     * specified, or the parameter is not understood, it prints
     * its usage.
     */
    public static void main(String argv[]) {
        if(argv.length == 1) {
            String command = argv[0];
            if(command.equals("reset")) outputDocumentToFile(createDocumentEmpty());
            else if(command.equals("new")) outputDocumentToFile(newCarRental());
            else if(command.equals("list")) outputDocument(readDocument());
            else if(command.equals("xslt")) executeXSLT(readDocument());
            else if(command.equals("validate")) validateDocument();
            else {
                System.out.println(command + " is not a valid option.");
                printUsage();
            }
        } else {
            printUsage();
        }
    }

    /**
     * Convience method to print the usage options for the class.
     */
    public static void printUsage() {
        System.out.println("Usage: CarRental [option] \n where option is one of the following:");
        System.out.println("  reset   - The application will create a new XML document (in memory)");
        System.out.println("  new   - Ask the user the data of a new rental  2) Read the carrental.xml XML document into memory 3) Add a new rental element to the document");
        System.out.println("  list   - read and parse a document from CarRental.xml");
        System.out.println("  xslt    - create a new document and transform it to HTML with the XSLT stylesheet in CarRental.xslt");
        System.out.println("  validate   - The application will read the carrental.xml XML document into memory and validate it againt carrental.xsd.");
    }
}
