import java.io.File;
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
		
		XMLFileCreator xmlFileCreator = new XMLFileCreator();
		
		try{
		OPCPackage pkg = OPCPackage.open(new File("C:\\Users\\iggo\\Desktop\\ConfigFileGeneratorMaterial\\SeverityConfigurationFile.xlsx"));
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
			
		pkg.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		//remove last 4 entries because we don't want them.
		
		xmlFileCreator.removeLast4ElementsFromRowsAndFirstRow();
		xmlFileCreator.printSizeOfRows();
		xmlFileCreator.makeXMLFile();
		
		
		
		
		
	}

}
