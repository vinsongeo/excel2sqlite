package tools.excel2sqlite.beans;

import java.util.List;

public class DataTable {
	private String name;
	private List<Column> columns;
	private String[] dataSheets;
	private boolean isOverwirte;
	private List<List<Object>> tableData;
	
	public boolean isOverwirte() {
		return isOverwirte;
	}
	public void setOverwirte(boolean isOverwirte) {
		this.isOverwirte = isOverwirte;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public String[] getDataSheets() {
		return dataSheets;
	}

	public void setDataSheet(String[] dataSheets) {
		this.dataSheets = dataSheets;
	}
	public List<List<Object>> getTableData() {
		return tableData;
	}
	public void setTableData(List<List<Object>> tableData) {
		this.tableData = tableData;
	}
	public void setDataSheets(String[] dataSheets) {
		this.dataSheets = dataSheets;
	}

}
