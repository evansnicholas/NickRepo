import java.io.File;
import java.util.ArrayList;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelFileReader {

	
	public void createXMLFileFromExcelFile(String excelFileLocation, String configFileLocation){
	
		XMLFileCreator xmlFileCreator = new XMLFileCreator(configFileLocation);
		
		try{
			OPCPackage pkg = OPCPackage.open(new File(excelFileLocation));
			XSSFWorkbook wb = new XSSFWorkbook(pkg);
			
			Sheet sheet1 = wb.getSheetAt(1);
			
			
			for (Row row : sheet1){ 
			
				ArrayList<String> rowContent = new ArrayList<String>();
				//Row firstRow = sheet1.getRow(1);
			
				for (Cell cell : row){
				
					int cellType = cell.getCellType();
				
					if (cellType == 1){
						String cellContent = cell.getRichStringCellValue().getString();
						rowContent.add(cellContent);
						//System.out.println(cellContent);
					}
					else if (cellType == 4){
						Boolean cellValueBoolean = (Boolean) cell.getBooleanCellValue();
						String cellValueBooleanInString = cellValueBoolean.toString();
						rowContent.add(cellValueBooleanInString);
		
					}
					else if (cellType == 0){
						int cellValueInt = (int) cell.getNumericCellValue();
						Integer cellValueIntInInteger = (Integer) cellValueInt;
						String cellValueIntegerInString = cellValueIntInInteger.toString();
						rowContent.add(cellValueIntegerInString);
						//System.out.println(cellValueIntegerInString);
					}
					else if (cellType == 3){
						
						rowContent.add("REPEATED_SERVICE");
						
					}
				};
				
				xmlFileCreator.addRow(rowContent);
				
			};	
				
			}catch(IllegalStateException e){
				//System.out.println(e.getMessage());
				System.out.println("\n"+"An error occured with one of the files.  Check the path to the Excel file and the config file set in GenerateConfigFile.bat.");
				System.exit(-1);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		
		//System.out.println("Number of elements in rows :"+xmlFileCreator.getRows().size());
		//remove last 4 entries because we don't want them.
		xmlFileCreator.removeLast4ElementsFromRowsAndFirstRow();
		//xmlFileCreator.printSizeOfRows();
		xmlFileCreator.makeXMLFile();
	
		
	}
		
		
	
	
	
}
