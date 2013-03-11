
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			
			if (args[0].equals("excelToXML")){
				
				String excelFileLocation = args[1];
				String configFileLocation = args[2];
				
				ExcelFileReader excelFileReader = new ExcelFileReader();
				excelFileReader.createXMLFileFromExcelFile(excelFileLocation, configFileLocation);
				
				System.out.println("\n"+"The config file has been generated. It can be found at: "+configFileLocation);
				
			}else if (args[0].equals("xmlToExcel")){
			
			String excelFileLocation = args[1];	
			String configFileLocation = args[2];
				
			ReadConfigFile readConfigFile = new ReadConfigFile();
			readConfigFile.readInConfigFile(configFileLocation);
			//readConfigFile.printAServiceConfiguration(0);
			//readConfigFile.printAServiceConfiguration(1);
			
			ExcelFileWriter excelFileWriter = new ExcelFileWriter();
			excelFileWriter.createExcelFile(readConfigFile.getParsedServiceConfigurations(), readConfigFile.getAllGenerateInternalHeaderValues(), excelFileLocation);
			
			System.out.println("The excel sheet "+excelFileLocation+" has been filled on the basis of the config file "+configFileLocation);
			
			}
		
		}catch(Exception e){	
					
			System.out.println("\n"+"Invalid paramters have probably been entered.");
	
			e.printStackTrace();
				
		}
	}

}
