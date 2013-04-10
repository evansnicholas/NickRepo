
public class Service {

	private String serviceName;
	private Integer serviceVersion;
	private Endpoint endpoint;
	
	public String getServiceName(){
		return this.serviceName;
	}
	
	public String getServiceVersionAsString(){
		return this.serviceVersion.toString();
	}
	
	public Endpoint getEndpoint(){
		return this.endpoint;
	}
	
	
}
