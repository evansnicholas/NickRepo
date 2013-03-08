import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelFileWriter {

	public void createExcelFile(ArrayList configFileContent, ArrayList generateInternalHeaderValues, String excelFile){
		
		int distinctServiceConfigurationCount = 0;
		String currentServiceName = "";
		
		try{
			
			try{
				
			
				//OPCPackage pkg = OPCPackage.open(new File(excelFile));
				//XSSFWorkbook wb = new XSSFWorkbook(pkg);
				//pkg.close();
				XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));
				
				
			for (int i = 1; i < configFileContent.size()+1; i++){
				
				boolean addGenerateInternalHeader = true;
				ArrayList<String> currentServiceConfiguration = (ArrayList<String>) configFileContent.get(i-1);
				
				if (i==1){
				  currentServiceName = currentServiceConfiguration.get(0).toString();
				}else if(currentServiceConfiguration.get(0).toString().equals(currentServiceName)){
					addGenerateInternalHeader = false;
				}else{
					currentServiceName=currentServiceConfiguration.get(0).toString();
					addGenerateInternalHeader = true;
					distinctServiceConfigurationCount++;
				}
				
					for (int j = 0; j < currentServiceConfiguration.size(); j++){
					
						String serviceName = currentServiceConfiguration.get(0).toString();
						String serviceVersion = currentServiceConfiguration.get(1).toString();		
						String target = currentServiceConfiguration.get(2).toString();
						String Severity_INFO = currentServiceConfiguration.get(3).toString();
						String INFO_Details = currentServiceConfiguration.get(4).toString();
						String Severity_DEBUG = currentServiceConfiguration.get(6).toString();
						String DEBUG_Details = currentServiceConfiguration.get(7).toString();
						String Severity_ERROR = currentServiceConfiguration.get(9).toString();
						String ERROR_Details = currentServiceConfiguration.get(10).toString();
						String Severity_FATAL = currentServiceConfiguration.get(12).toString();
						String FATAL_Details = currentServiceConfiguration.get(13).toString();
						String Severity_TRACE = currentServiceConfiguration.get(15).toString();
						String TRACE_Details = currentServiceConfiguration.get(16).toString();
						String Severity_WARNING = currentServiceConfiguration.get(18).toString();
						String WARNING_Details = currentServiceConfiguration.get(19).toString();	
						
						Sheet configInfoSheet = wb.getSheetAt(1);
						
						Row row = configInfoSheet.createRow(i);
						
						Cell serviceNameCell = row.createCell(1);
						serviceNameCell.setCellValue(serviceName);
						
						if (addGenerateInternalHeader == true){
							String isInternalHeader = generateInternalHeaderValues.get(distinctServiceConfigurationCount).toString();
							Cell generateInternalHeaderCell = row.createCell(2);
							generateInternalHeaderCell.setCellValue(Boolean.valueOf(isInternalHeader));
						}
						
						Cell serviceVersionCell = row.createCell(3);
						serviceVersionCell.setCellValue(serviceVersion);
						//System.out.println("\n Cell type: "+serviceVersionCell.getCellType());
						
						Cell targetCell = row.createCell(4);
						targetCell.setCellValue(target);
						
						Cell severityInfoCell = row.createCell(5);
						severityInfoCell.setCellValue(Boolean.valueOf(Severity_INFO));
						
						Cell infoDetailsCell = row.createCell(6);
						infoDetailsCell.setCellValue(Integer.parseInt(INFO_Details));
						
						Cell severityDebugCell = row.createCell(7);
						severityDebugCell.setCellValue(Boolean.parseBoolean(Severity_DEBUG));
						
						Cell debugDetailsCell = row.createCell(8);
						debugDetailsCell.setCellValue(Integer.parseInt(DEBUG_Details));
						
						Cell severityFatalCell = row.createCell(9);
						severityFatalCell.setCellValue(Boolean.valueOf(Severity_FATAL));
						
						Cell fatalDetailsCell = row.createCell(10);
						fatalDetailsCell.setCellValue(Integer.parseInt(FATAL_Details));
						
						Cell severityErrorCell = row.createCell(11);
						severityErrorCell.setCellValue(Boolean.valueOf(Severity_ERROR));
						
						Cell errorDetailsCell = row.createCell(12);
						errorDetailsCell.setCellValue(Integer.parseInt(ERROR_Details));
						
						Cell severityWarningCell = row.createCell(13);
						severityWarningCell.setCellValue(Boolean.valueOf(Severity_WARNING));
						
						Cell warningDetailsCell = row.createCell(14);
						warningDetailsCell.setCellValue(Integer.parseInt(WARNING_Details));
						
						Cell severityTraceCell = row.createCell(15);
						severityTraceCell.setCellValue(Boolean.valueOf(Severity_TRACE));
						
						Cell traceDetailsCell = row.createCell(16);
						traceDetailsCell.setCellValue(Integer.parseInt(TRACE_Details));
				}
			}
			
			
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
