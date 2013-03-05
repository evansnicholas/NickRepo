import java.io.File;

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

		try{
		OPCPackage pkg = OPCPackage.open(new File("C:\\Users\\iggo\\Documents\\ExelAPITest.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(pkg);
		
		Sheet sheet1 = wb.getSheetAt(0);
		Row firstRow = sheet1.getRow(0);
		Cell firstCell = firstRow.getCell(0);
		String cellContent = firstCell.getRichStringCellValue().getString();
		System.out.println(cellContent);
		
		pkg.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		XMLFileCreator xmlFileCreator = new XMLFileCreator();
		xmlFileCreator.makeXMLFile();
		
		
		
		
	}

}
