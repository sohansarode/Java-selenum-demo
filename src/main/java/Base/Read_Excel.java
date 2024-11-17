package Base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Read_Excel extends Browser_Setup {

	private static XSSFWorkbook Workbook;
	private static XSSFSheet Sheet;

//----------------------------------------------------------------------------------------------------------//

	// This method is use to get excel file
	protected static void Excel_Connect() {
		FileInputStream fis;
		try {
			// Provide the file path to the Excel file
			String filePath = "";

			// Create a FileInputStream to read the Excel file
			fis = new FileInputStream(filePath);

			// Initialize the workbook with the Excel file
			Workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			// Handle exceptions if any (e.g., file not found or invalid format)
			e.printStackTrace();
		}
	}
// ----------------------------------------------------------------------------------------------------------//

	// Retrieves the column number based on the specified column name.
	protected static int Get_Column_Number1(String Column_Name) {
		// Initialize the starting column index
		int columnIndex = 1;

		// Iterate through cells in the first row to find the specified column name
		for (Cell cell : Sheet.getRow(1)) {
			if (cell.getStringCellValue().equalsIgnoreCase(Column_Name)) {
				// Return the column number if the column name is found
				return columnIndex;
			}
			columnIndex++;
		}

		// Return 1 if the column name is not found
		return 1;
	}

// ----------------------------------------------------------------------------------------------------------//

	public static String Get_String_Cell_Data(String SheetName, String TestCaseName, String ColumnName)
			throws IOException {
		// Establish connection to the Excel file
		Excel_Connect();

		// Initialize variable to store retrieved value
		String value = "";

		// Get the specified sheet from the workbook
		Sheet = Workbook.getSheet(SheetName);

		// Get the column index based on the specified column name
		int columnIndex = Get_Column_Number1(ColumnName);

		// Iterate through rows to find the specified test case
		for (Row row : Sheet) {
			// Check if the first cell value in the row matches the test case name
			if (row.getCell(0).getStringCellValue().equalsIgnoreCase(TestCaseName)) {
				// Retrieve the cell value based on the specified column index
				switch (row.getCell(columnIndex).getCellType()) {
				case STRING:
					return value = row.getCell(columnIndex).getRichStringCellValue().getString();
				case NUMERIC:
					return value = new DataFormatter().formatCellValue(row.getCell(columnIndex));
				case BLANK:
					return "";
				default:
					return "";
				}
			}
		}

		// Return the retrieved value
		return value;
	}

// ----------------------------------------------------------------------------------------------------------//
	// Retrieves cell data from the specified Excel sheet, column number, and row
	// number.

	public static String Get_Cell_Data1(String SheetName, int ColNum, int RowNum) throws IOException {
		// Establish connection to the Excel file
		Excel_Connect();

		// Check if the specified row number is valid
		if (RowNum <= 0) {
			return "";
		}

		// Get the specified sheet from the workbook
		Sheet = Workbook.getSheet(SheetName);

		// Check if the sheet exists
		if (Sheet == null) {
			return "";
		}

		// Get the specified row based on the provided row number
		Row row = Sheet.getRow(RowNum - 1);

		// Check if the row exists
		if (row == null) {
			return "";
		}

		// Get the specified cell based on the provided column number
		Cell cell = row.getCell(ColNum);

		// Check if the cell exists
		if (cell == null) {
			return "";
		}

		// Return the formatted cell value as a trimmed string
		return new DataFormatter().formatCellValue(cell).trim();
	}
// ----------------------------------------------------------------------------------------------------------//

	public static String Get_Random_TestCase_Name(String SheetName) throws IOException {
		Excel_Connect();
		Random random = new Random();
		Sheet = Workbook.getSheet(SheetName);
		int rowCount = Sheet.getLastRowNum() - Sheet.getFirstRowNum(); // Excluding header row

		// Check if sheet is empty
		if (rowCount <= 0) {
			throw new IOException("No test cases found in sheet: " + SheetName);
		}

		String testCaseName = null;
		do {
			// Generate random row index
			int randomRowIndex = random.nextInt(rowCount);
			Row randomRow = Sheet.getRow(randomRowIndex + 1); // Add 1 to skip header row

			// Check if randomRow is not null
			if (randomRow != null) {
				// Extract testcase name from first column
				Cell cell = randomRow.getCell(0);
				if (cell != null && cell.getCellType() == CellType.STRING) {
					testCaseName = cell.getStringCellValue();
				}
			}
		} while (testCaseName == null || testCaseName.trim().isEmpty());

		return testCaseName;
	}
// ----------------------------------------------------------------------------------------------------------//

	public static void readall() throws IOException {

		// Path to your Excel file
		String filePath = "";

		// Open the Excel file
		FileInputStream file = new FileInputStream(filePath);
		Workbook = new XSSFWorkbook(file); // For .xlsx files

		// Get the first sheet
		Sheet = Workbook.getSheetAt(0);

		// Loop through each row and cell
		for (Row row : Sheet) {
			for (Cell cell : row) {
				// Print each cell value
				System.out.print(cell.toString() + "\t");
			}
			System.out.println(); // Move to the next row
		}

		// Close the workbook and file
		Workbook.close();
		file.close();
	}

}
