package tools.excel2sqlite;

import java.io.File;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel2Sqlite {

	public void operate() throws Exception
	{
		// java.io.Fileから
		Workbook workbook = WorkbookFactory.create(new File("/Users/ge-n/Documents/祷告词.xlsx"));

		// シート名がわかっている場合
		Sheet sheet = workbook.getSheet("SYS_TABLES");

		if(sheet == null)
		{
			throw new Exception("There is no sheet called SYS_TABLES.");
		}

		// 全行を繰り返し処理する場合
		Iterator<Row> rows = sheet.rowIterator();
		while(rows.hasNext()) {
		  Row row = rows.next();
		}
	}
}
