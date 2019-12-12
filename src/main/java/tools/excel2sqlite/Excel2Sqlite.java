package tools.excel2sqlite;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.StringUtil;

public class Excel2Sqlite {

	public void operate() throws Exception {
		// java.io.Fileから
		Workbook workbook = WorkbookFactory.create(new File("/Users/ge-n/Documents/祷告词.xlsx"));

		// シート名がわかっている場合
		Sheet sheet = workbook.getSheet("SYS_TABLES");

		if (sheet == null) {
			throw new Exception("There is no sheet called SYS_TABLES.");
		}

		List<Cell> cells = getAllCell(sheet);

		List<DataTable> tables = creatTables(sheet, cells);
		
		for(DataTable t : tables)
		{
			t.create(workbook);
		}
	}

	/**
	 * Get all cells from a sheet
	 * 
	 * @param sheet
	 * @return cells
	 */
	public List<Cell> getAllCell(Sheet sheet) {
		List<Cell> cells = new ArrayList<Cell>();
		// 全行を繰り返し処理する場合
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			for (int i = 0; i < row.getLastCellNum(); i++) {
				cells.add(row.getCell(i));
			}
		}
		return cells;
	}

	public List<DataTable> creatTables(Sheet worksheet, List<Cell> collection) {

		List<DataTable> tables = new ArrayList<DataTable>();
		for (Cell cell : collection) {
			if ("Table".equals(cell.getStringCellValue())) {
				tables.add(creatTable(worksheet, cell));
			}
		}

		return tables;
	}

	public DataTable creatTable(Sheet sheet, Cell cell)
	{
			DataTable table = new DataTable();
			for(int c = 0; c < 10; c++)
			{
				String value = sheet.getRow(cell.getRowIndex()).getCell(c).getStringCellValue();
				if("Table".equals(value))
				{
					table.setName(sheet.getRow(cell.getRowIndex()).getCell(c+1).getStringCellValue());
				}
				else if("Data".equals(value))
				{
					table.setData(sheet.getRow(cell.getRowIndex()).getCell(c+1).getStringCellValue());
				}
				else if("OverWrite".equals(value))
				{
					table.setOverwirte(!"".equals(sheet.getRow(cell.getRowIndex()).getCell(c+1).getStringCellValue()));
				}
			}
			
			int ci = 2;
			String columnName = null;
			List<Column> columns = new ArrayList<Column>();
			// Column	DataType	Length	NotNull	PrimaryKey	ForeignKey	AutoIncrement	Unique	Default	Comment
			do
			{
				int rowIndex = cell.getRowIndex() + ci;
				columnName = sheet.getRow(rowIndex).getCell(0).getStringCellValue();
	
				Column column = new Column();
				column.setName(columnName);
				column.setDataType(sheet.getRow(rowIndex).getCell(1).getStringCellValue());
				column.setLength((int)sheet.getRow(rowIndex).getCell(2).getNumericCellValue());
				column.setNotNull(!"".equals(sheet.getRow(rowIndex).getCell(3).getStringCellValue()));
				column.setPrimaryKey(!"".equals(sheet.getRow(rowIndex).getCell(4).getStringCellValue()));
				column.setForeignKey(sheet.getRow(rowIndex).getCell(5).getStringCellValue());
				column.setAutoIncrement(!"".equals(sheet.getRow(rowIndex).getCell(6).getStringCellValue()));
				column.setUnique(!"".equals(sheet.getRow(rowIndex).getCell(7).getStringCellValue()));
				column.setDefaultValue(sheet.getRow(rowIndex).getCell(8).getStringCellValue());
				column.setComment(sheet.getRow(rowIndex).getCell(9).getStringCellValue());
				columns.add(column);
				ci++;
			}while("".equals(columnName) && columnName != null);

		return table;
	}
}
