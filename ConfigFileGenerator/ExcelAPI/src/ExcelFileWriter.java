import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileWriter {

	public void createExcelFile(ArrayList<ArrayList<String>> configFileContent, ArrayList<String> generateInternalHeaderValues, String excelFile){
		
		int distinctServiceConfigurationCount = 0;
		String currentServiceName = "";
		
		try{
			
			try{
							
				//OPCPackage pkg = OPCPackage.open(new File(excelFile));
				//XSSFWorkbook wb = new XSSFWorkbook(pkg);
				//pkg.close();
				XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));
				
				
			for (int i = 1; i < configFileContent.size()+1; i++){
				
				String severity_INFO = "";
				String info_Details = "";
				String severity_DEBUG = "";
				String debug_Details = "";
				String severity_ERROR = "";
				String error_Details = "";
				String severity_WARNING = "";
				String warning_Details = "";
			    String severity_FATAL = "";
			    String fatal_Details = "";
			    String severity_TRACE = "";
			    String trace_Details = "";
				
				boolean addGenerateInternalHeaderAndServiceName = true;
				
				ArrayList<String> currentServiceConfiguration = (ArrayList<String>) configFileContent.get(i-1);
				
				if (i==1){
				  currentServiceName = currentServiceConfiguration.get(0).toString();
				}else if(currentServiceConfiguration.get(0).toString().equals(currentServiceName)){
					addGenerateInternalHeaderAndServiceName = false;
				}else{
					currentServiceName=currentServiceConfiguration.get(0).toString();
					addGenerateInternalHeaderAndServiceName = true;
					distinctServiceConfigurationCount++;
				}
				
				String serviceName = currentServiceConfiguration.get(0).toString();
				String serviceVersion = currentServiceConfiguration.get(1).toString();		
				String target = currentServiceConfiguration.get(2).toString();
				
				for (int j = 0; j < currentServiceConfiguration.size(); j++){
										
					if (currentServiceConfiguration.get(j).toString().equals("INFO")){
						
						severity_INFO = currentServiceConfiguration.get(j-2).toString();
						info_Details = currentServiceConfiguration.get(j-1).toString();
						
					}else if (currentServiceConfiguration.get(j).toString().equals("DEBUG")){
						
						severity_DEBUG = currentServiceConfiguration.get(j-2).toString();
						debug_Details = currentServiceConfiguration.get(j-1).toString();
						
					}else if (currentServiceConfiguration.get(j).toString().equals("ERROR")){
						
						severity_ERROR = currentServiceConfiguration.get(j-2).toString();
						error_Details = currentServiceConfiguration.get(j-1).toString();
												
					}else if (currentServiceConfiguration.get(j).toString().equals("FATAL")){
						
						severity_FATAL = currentServiceConfiguration.get(j-2).toString();
						fatal_Details = currentServiceConfiguration.get(j-1).toString();
						
					}else if (currentServiceConfiguration.get(j).toString().equals("TRACE")){
						
						severity_TRACE = currentServiceConfiguration.get(j-2).toString();
						trace_Details = currentServiceConfiguration.get(j-1).toString();
						
					}else if (currentServiceConfiguration.get(j).toString().equals("WARNING")){
						
						severity_WARNING = currentServiceConfiguration.get(j-2).toString();
						warning_Details = currentServiceConfiguration.get(j-1).toString();
						
					}
			
				}			
					
				Sheet configInfoSheet = wb.getSheetAt(1);
				
				Row referenceRow = configInfoSheet.getRow(0);
				Row row = configInfoSheet.createRow(i);
				
				//Component cell is created but not filled.
				Cell componentName = row.createCell(0);
				componentName.setCellType(3);
				
				if (addGenerateInternalHeaderAndServiceName == true){
					String isInternalHeader = generateInternalHeaderValues.get(distinctServiceConfigurationCount).toString();
					Cell generateInternalHeaderCell = row.createCell(2);
					generateInternalHeaderCell.setCellValue(Boolean.valueOf(isInternalHeader));
					Cell serviceNameCell = row.createCell(1);
					serviceNameCell.setCellValue(serviceName);
					
				}else{
					Cell generateInternalHeaderCell = row.createCell(2);
					generateInternalHeaderCell.setCellType(3);
					Cell serviceNameCell = row.createCell(1);
					serviceNameCell.setCellType(3);
					
				}
				
				Cell serviceVersionCell = row.createCell(3);
				serviceVersionCell.setCellValue(serviceVersion);
				//System.out.println("\n Cell type: "+serviceVersionCell.getCellType());
				
				Cell targetCell = row.createCell(4);
				targetCell.setCellValue(target);
				
				for (int k = 5; k < 17; k = k + 2){
					
					if (referenceRow.getCell(k).getRichStringCellValue().getString().equals("Severity_INFO")){
						
						Cell severityInfoCell = row.createCell(k);
						severityInfoCell.setCellValue(Boolean.valueOf(severity_INFO));
						Cell infoDetailsCell = row.createCell(k + 1);
						infoDetailsCell.setCellValue(Integer.parseInt(info_Details));
					
					}else if (referenceRow.getCell(k).getRichStringCellValue().getString().equals("Severity_DEBUG")){
					
						Cell severityDebugCell = row.createCell(k);
						severityDebugCell.setCellValue(Boolean.parseBoolean(severity_DEBUG));
						Cell debugDetailsCell = row.createCell(k+1);
						debugDetailsCell.setCellValue(Integer.parseInt(debug_Details));
					
					}else if(referenceRow.getCell(k).getRichStringCellValue().getString().equals("Severity_FATAL")){
					
						Cell severityFatalCell = row.createCell(k);
						severityFatalCell.setCellValue(Boolean.valueOf(severity_FATAL));
						
						Cell fatalDetailsCell = row.createCell(k+1);
						fatalDetailsCell.setCellValue(Integer.parseInt(fatal_Details));
					
					}else if (referenceRow.getCell(k).getRichStringCellValue().getString().equals("Severity_ERROR")){
					
					Cell severityErrorCell = row.createCell(k);
					severityErrorCell.setCellValue(Boolean.valueOf(severity_ERROR));					
					Cell errorDetailsCell = row.createCell(k+1);
					errorDetailsCell.setCellValue(Integer.parseInt(error_Details));
					
					}else if (referenceRow.getCell(k).getRichStringCellValue().getString().equals("Severity_WARNING")){
					
						Cell severityWarningCell = row.createCell(k);
						severityWarningCell.setCellValue(Boolean.valueOf(severity_WARNING));						
						Cell warningDetailsCell = row.createCell(k+1);
						warningDetailsCell.setCellValue(Integer.parseInt(warning_Details));
					
					}else if (referenceRow.getCell(k).getRichStringCellValue().getString().equals("Severity_TRACE")){
					
						Cell severityTraceCell = row.createCell(k);
						severityTraceCell.setCellValue(Boolean.valueOf(severity_TRACE));
						Cell traceDetailsCell = row.createCell(k+1);
						traceDetailsCell.setCellValue(Integer.parseInt(trace_Details));
					
					}
					
				};
						
			};
			
			
			FileOutputStream out = new FileOutputStream(excelFile);
			wb.write(out);
			out.close();
			
			
				
		
			
			}catch(FileNotFoundException e){
				System.out.println("\n"+"The excel file was not found");
			}				
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	

}
