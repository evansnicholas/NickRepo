import java.util.ArrayList;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Logging {

	Element logging;
	ArrayList<Filter> filters = new ArrayList<Filter>();
	
	Logging(Document doc, String nameValue){
		
		this.logging = doc.createElement("cfg:logging");
		Attr nameAttribute = doc.createAttribute("name");
		nameAttribute.setValue(nameValue);
		logging.setAttributeNode(nameAttribute);
		
	}
	
	public Element getLogging(){
		
		return logging;
		
	}
	
	public void addFilter(Filter filter){
		filters.add(filter);
	}
	
	public void addFiltersToLogging(){
		for (Filter i : filters){
			this.logging.appendChild(i.getFilter());
		}
	}
	
}
