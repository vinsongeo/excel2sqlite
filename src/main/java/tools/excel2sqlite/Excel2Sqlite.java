package tools.excel2sqlite;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import tools.excel2sqlite.beans.Column;
import tools.excel2sqlite.beans.DataTable;

public class Excel2Sqlite {

	public void operate(String excel, String database, String systemTable) throws Exception {

		System.out.println("Database file: " + database);
		String[] excels = excel.split(",");
		new File(database).delete();
		for (String e : excels) {
			System.out.println("Excel file: " + e);
			// java.io.Fileから
			Workbook workbook = WorkbookFactory.create(new File(e));
			// シート名がわかっている場合
			Sheet sheet = workbook.getSheet(systemTable);

			if (sheet == null) {
				throw new Exception("There is no sheet called SYS_TABLES.");
			}

			List<Cell> cells = getAllCell(sheet);
			List<DataTable> tables = creatTables(sheet, cells);
			this.fillData(workbook, tables);

			IDatabaseAssistant da = new SqliteAssistant();
			if (da.createDatabase(database)) {
				da.createTables(tables, database);
			}
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

	public DataTable creatTable(Sheet sheet, Cell cell) {
		DataTable table = new DataTable();
		for (int c = 0; c < 10; c++) {
			String value = sheet.getRow(cell.getRowIndex()).getCell(c).getStringCellValue();
			if ("Table".equals(value)) {
				table.setName(sheet.getRow(cell.getRowIndex()).getCell(c + 1).getStringCellValue());
			} else if ("Data".equals(value)) {
				table.setDataSheets(sheet.getRow(cell.getRowIndex()).getCell(c + 1).getStringCellValue().split(","));
			} else if ("OverWrite".equals(value)) {
				table.setOverwirte(!"".equals(sheet.getRow(cell.getRowIndex()).getCell(c + 1).getStringCellValue()));
			}
		}

		System.out.println("Table:" + table.getName());
		int ci = 2;
		String columnName = null;
		List<Column> columns = new ArrayList<Column>();
		// Column DataType Length NotNull PrimaryKey ForeignKey AutoIncrement Unique
		// Default Comment
		while (true) {
			int rowIndex = cell.getRowIndex() + ci;
			if (sheet.getRow(rowIndex) == null) {
				break;
			}
			columnName = sheet.getRow(rowIndex).getCell(0).getStringCellValue();
			if ("".equals(columnName) || columnName == null) {
				break;
			}
			Column column = new Column();
			column.setName(columnName);
			column.setDataType(sheet.getRow(rowIndex).getCell(1).getStringCellValue());
			column.setLength((int) sheet.getRow(rowIndex).getCell(2).getNumericCellValue());
			column.setNotNull(!"".equals(sheet.getRow(rowIndex).getCell(3).getStringCellValue()));
			column.setPrimaryKey(!"".equals(sheet.getRow(rowIndex).getCell(4).getStringCellValue()));
			column.setForeignKey(sheet.getRow(rowIndex).getCell(5).getStringCellValue());
			column.setAutoIncrement(!"".equals(sheet.getRow(rowIndex).getCell(6).getStringCellValue()));
			column.setUnique(!"".equals(sheet.getRow(rowIndex).getCell(7).getStringCellValue()));
			column.setDefaultValue(sheet.getRow(rowIndex).getCell(8).getStringCellValue());
			column.setComment(sheet.getRow(rowIndex).getCell(9).getStringCellValue());
			columns.add(column);
			ci++;
		}
		table.setColumns(columns);

		return table;
	}

	public void fillData(Workbook workbook, List<DataTable> tables) {
		for (DataTable t : tables) {
			List<List<Object>> rows = new ArrayList<List<Object>>();
			int incrementValue = 1;
			for (String sheetName : t.getDataSheets()) {
				Sheet sheet = workbook.getSheet(sheetName);
				if (sheet == null)
					continue;
				System.out.println("Getting data:" + sheetName);
				int columnCount = t.getColumns().size();

				Iterator<Row> rowIterator = sheet.iterator();
				rowIterator.next();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					List<Object> columns = new ArrayList<Object>();
					for (int c = 0; c < columnCount; c++) {
						if (t.getColumns().get(c).isPrimaryKey() && t.getColumns().get(c).isAutoIncrement()) {
							columns.add(getCellValue(row.getCell(c)) == null ? null : incrementValue++);
						} else {
							columns.add(getCellValue(row.getCell(c)));
						}
					}
					int vaidDataCount = 0;
					for (Object o : columns) {
						if (o == null) {
							vaidDataCount++;
						}
					}
					if (vaidDataCount == columns.size()) {
						break;
					}
					rows.add(columns);
				}
			}
			t.setTableData(rows);
		}
	}

	private Object getCellValue(Cell cell) {
		if (cell == null)
			return null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();

		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:
			return null;
		default:
			return null;
		}
	}
}
