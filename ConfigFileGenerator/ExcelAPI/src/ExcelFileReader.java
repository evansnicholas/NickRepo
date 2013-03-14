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
	    
		Row currentRow;
		Cell currentCell; 
		
		try{
			OPCPackage pkg = OPCPackage.open(new File(excelFileLocation));
			XSSFWorkbook wb = new XSSFWorkbook(pkg);
			
			Sheet sheet1 = wb.getSheetAt(1);
			int numberOfRows = sheet1.getLastRowNum();
			System.out.println("last row number is: "+numberOfRows);
			
			//TEST
			//int numberOfPhysicalRows = sheet1.get();
			//Cell testCell = testRow.getCell(0);
			//int numberOfCells = testRow.getPhysicalNumberOfCells();
			//System.out.println("Number of rows is: "+numberOfRows);
			
			for (int currentRowNumber = 0; currentRowNumber <= numberOfRows; currentRowNumber++){ 
			
				ArrayList<String> rowContent = new ArrayList<String>();
				//Row firstRow = sheet1.getRow(1);
			    
				currentRow = sheet1.getRow(currentRowNumber);
				int numberOfCellsInRow = currentRow.getLastCellNum();
				
				for (int currentCellNumber = 0; currentCellNumber < numberOfCellsInRow ; currentCellNumber++){	
								
					currentCell = currentRow.getCell(currentCellNumber);
				
					int cellType = currentCell.getCellType();
				
					if (cellType == 1 && !currentCell.getStringCellValue().equals("")){
						
						String cellContent = currentCell.getRichStringCellValue().getString();
						rowContent.add(cellContent);
						int cellNumber = currentCell.getColumnIndex();
						String cellName = sheet1.getRow(0).getCell(cellNumber).getRichStringCellValue().getString();
						rowContent.add(cellName);
						//System.out.println(cellContent);
					}
					else if (cellType == 4){
						Boolean cellValueBoolean = (Boolean) currentCell.getBooleanCellValue();
						String cellValueBooleanInString = cellValueBoolean.toString();
						rowContent.add(cellValueBooleanInString);
						int cellNumber = currentCell.getColumnIndex();
						String cellName = sheet1.getRow(0).getCell(cellNumber).getRichStringCellValue().getString();
						rowContent.add(cellName);
		
					}
					else if (cellType == 0){
						int cellValueInt = (int) currentCell.getNumericCellValue();
						Integer cellValueIntInInteger = (Integer) cellValueInt;
						String cellValueIntegerInString = cellValueIntInInteger.toString();
						rowContent.add(cellValueIntegerInString);
						int cellNumber = currentCell.getColumnIndex();
						String cellName = sheet1.getRow(0).getCell(cellNumber).getRichStringCellValue().getString();
						rowContent.add(cellName);
						//System.out.println(cellValueIntegerInString);
					}
					else if (cellType == 3 || currentCell.getStringCellValue().equals("")){
						
						rowContent.add("REPEATED_SERVICE");
						int cellNumber = currentCell.getColumnIndex();
						String cellName = sheet1.getRow(0).getCell(cellNumber).getRichStringCellValue().getString();
						rowContent.add(cellName);
						
					}
				}
				
				for (int i = 0; i<rowContent.size(); i++){
					System.out.print("Row#"+currentRowNumber+" Cell#"+i+":"+rowContent.get(i).toString()+" ");
				}
				
				System.out.println("");
				
				xmlFileCreator.addRow(rowContent);
				
			}	
				
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
