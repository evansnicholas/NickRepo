import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ReadConfigFile readConfigFile = new ReadConfigFile();
		readConfigFile.readInConfigFile();
		//readConfigFile.printAServiceConfiguration(0);
		//readConfigFile.printAServiceConfiguration(1);
		
		ExcelFileWriter excelFileWriter = new ExcelFileWriter();
		excelFileWriter.createExcelFile(readConfigFile.getParsedServiceConfigurations(), readConfigFile.getAllGenerateInternalHeaderValues(), "C:\\Users\\iggo\\Desktop\\TEST.xlsm");
		
		
		/*try{
					
				String excelFileLocation = args[0];
				String configFileLocation = args[1];
				
				XMLFileCreator xmlFileCreator = new XMLFileCreator(configFileLocation);
				
				try{
				OPCPackage pkg = OPCPackage.open(new File(args[0]));
				XSSFWorkbook wb = new XSSFWorkbook(pkg);
				
				Sheet sheet1 = wb.getSheetAt(1);
				
				
				for (Row row : sheet1){ 
				
					ArrayList<String> rowContent = new ArrayList<String>();
					//Row firstRow = sheet1.getRow(1);
				
				//Cell testCell = firstRow.getCell(6);
				//System.out.println(testCell.getCellType());
				
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
							//System.out.println(cellValueBooleanInString);	
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
					}
					
					xmlFileCreator.addRow(rowContent);
					
					
				}	
				//pkg.close();
				
				}catch(IllegalStateException e){
					//System.out.println(e.getMessage());
					System.out.println("\n"+"An error occured with one of the files.  Check the path to the Excel file and the config file set in GenerateConfigFile.bat.");
					System.exit(-1);
				}
				
				//remove last 4 entries because we don't want them.
				
				xmlFileCreator.removeLast4ElementsFromRowsAndFirstRow();
				//xmlFileCreator.printSizeOfRows();
				xmlFileCreator.makeXMLFile();
				
				System.out.println("\n"+"The config file has been generated. It can be found at: "+configFileLocation);
				
				
			}catch(Exception e){	
				if (args.length < 2){
					
					System.out.println("\n"+"You have not entered valid parameters. First parameter: Excel file location. Second parameter: Config file location.");
				}
				else{
					e.printStackTrace();
				}
			}*/
	}

}
