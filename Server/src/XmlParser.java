import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/*
 * The class XmlParser is used to parse through the patients.xml file on the server system
 * The purpose of the xml file is to provide a readout on the client window providing loosely relevant medical data
 * From outside this class, the only accessible method (besides the constructor) is getPatientInfo(IP),
 * which will return the medical data in a string formatted specifically for displaying correctly in the client window
 * It matches the IP address given with the IP attribute in the xml file to select the patient
 */
public class XmlParser 
{
	/*Fields relevant for file reading and parsing*/
	private File fXmlFile;
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	
	/*This list will contain patient elements from the XML file*/
	private NodeList nList;
	
	/*
	 * Constructor for the parser
	 * Finds the file in the system directory and parses it in DOM mode
	 */
	public XmlParser()
	{
		try {
			/*Initialize local Fields*/
			fXmlFile = new File("src/Patients.xml");
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();	//This needs to be done, for reasons that are complex and hard to explain
			
			nList = doc.getElementsByTagName("patient");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *The only method accessible externally. Returns the info of the patient corresponding to an IP address
	 *@param String IP - The IP address of the patient
	 *@return returns a nicely formatted string ready to be inserted into the GUI of the client window
	 */
	public String getPatientInfo(String IP)
	{
		Element patient = findPatient(IP);
		return getName(patient) + getPhysical(patient) + getRHR(patient);
	}
	
	/*
	 *@param String IP - The IP address of the patient we want
	 *@return Returns the patient element in the XML file, with all of its stats ready to be gathered
	 */
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
	
	/*
	 * Gets the name of the patient
	 * @param Element patient - the patient we need the name of
	 * @return the first and last name of the patient
	 */
	private String getName(Element patient)
	{
		String first = patient.getElementsByTagName("firstname").item(0).getTextContent();
		String last = patient.getElementsByTagName("lastname").item(0).getTextContent();
		return " " + first + " " + last + "\n\n";
		
	}
	
	/*
	 *Gets the age, height and weight of the patient
	 *@param Element patient - The patient we need physical info from
	 *@return the age, height and weight of the patient in a string with labels
	 */
	private String getPhysical(Element patient)
	{
		String height = patient.getElementsByTagName("height").item(0).getTextContent() + " cm";
		String weight = patient.getElementsByTagName("weight").item(0).getTextContent() + " lbs";
		String age = patient.getElementsByTagName("age").item(0).getTextContent();
		return " Age: " + age + "  Height: " + height + "  Weight: " + weight + "\n";
	}
	
	/*
	 *Gets the resting heart rate of the patient
	 *@param Element patient - the patient we want the heart rate of
	 *@return Returns the heart rate of the patient in a string, labelled
	 */
	private String getRHR(Element patient)
	{
		return " Resting heart rate: " + patient.getElementsByTagName("heartrate").item(0).getTextContent() + " beats per minute";
	}
	
}
