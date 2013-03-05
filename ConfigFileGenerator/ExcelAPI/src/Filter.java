import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Filter {

	Element filter;
	
	Filter(Document doc, String detailValue, String severityValue, String isActiveValue){
		
		this.filter = doc.createElement("cfg:filter");
		Attr detailAttribute = doc.createAttribute("detail");
		detailAttribute.setValue(detailValue);
		filter.setAttributeNode(detailAttribute);
		
		Attr severityAttribute = doc.createAttribute("severity");
		severityAttribute.setValue(severityValue);
		filter.setAttributeNode(severityAttribute);
		
		Attr isActiveAttribute = doc.createAttribute("active");
		isActiveAttribute.setValue(isActiveValue);
		filter.setAttributeNode(isActiveAttribute);
		
	}
	
	public Element getFilter(){
		
		return filter;
		
	}
}
