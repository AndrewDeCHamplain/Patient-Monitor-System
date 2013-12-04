import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XmlParser 
{
	private File fXmlFile;
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	
	private NodeList nList;
	
	public XmlParser()
	{
		try {
			fXmlFile = new File("src/Patients.xml");
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			nList = doc.getElementsByTagName("patient");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getPatientInfo(String IP)
	{
		Element patient = findPatient(IP);
		return getName(patient) + getPhysical(patient) + getRHR(patient);
	}
	
	private Element findPatient(String IP)
	{
		for (int i = 0; i < nList.getLength(); i++) {
			
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element patient = (Element) nNode;
				if (patient.getAttribute("IP").equals(IP)) {
					return patient;
				}
			}
		}
		System.out.println("Could not find data for IP address: " + IP);
		return null;
	}
	
	private String getName(Element patient)
	{
		String first = patient.getElementsByTagName("firstname").item(0).getTextContent();
		String last = patient.getElementsByTagName("lastname").item(0).getTextContent();
		return " " + first + " " + last + "\n\n";
		
	}
	
	private String getPhysical(Element patient)
	{
		String height = patient.getElementsByTagName("height").item(0).getTextContent() + " cm";
		String weight = patient.getElementsByTagName("weight").item(0).getTextContent() + " lbs";
		String age = patient.getElementsByTagName("age").item(0).getTextContent();
		return " Age: " + age + "  Height: " + height + "  Weight: " + weight + "\n";
	}
	
	private String getRHR(Element patient)
	{
		return " Resting heart rate: " + patient.getElementsByTagName("heartrate").item(0).getTextContent() + " beats per minute";
	}
	
}
