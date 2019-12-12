package tools.excel2sqlite;

import org.apache.poi.ss.usermodel.Workbook;

public class DataTable {
	private String name;
	private Column[] columns;
	private String data;
	private boolean isOverwirte;

	public boolean isOverwirte() {
		return isOverwirte;
	}

	public void setOverwirte(boolean isOverwirte) {
		this.isOverwirte = isOverwirte;
	}

	public void create(Workbook workbook) {
//
//        Spreadsheet sheet = spreadsheet;
//		if(!string.IsNullOrEmpty(Data.Document))
//        {
//            sheet = new Spreadsheet(Data.Document,spreadsheet.Account,spreadsheet.Password);
//        }
//        
//		if(string.IsNullOrEmpty(this.Provider))
//		{
//			List<CellEntry> cells = sheet.GetCells (Data.Sheet, Data.Range);
//			FieldInfo datas = asset.GetType ().GetField ("Datas");
//			if (exsits) {
//				datas.GetValue (asset).GetType ().GetMethod ("Clear").Invoke (datas.GetValue (asset), null);
//			}
//			for (int r = 0; r < cells.Count; r += Columns.Length) {
//				System.Object data = assembly.CreateInstance (Name + "Data");
//				for (int c = 0; c < Columns.Length; c++) {
//					int i = r + c;
//					object value = cells [i].Value;
//					switch (Columns [c].Type) {
//					case DataType.@int:
//						value = Convert.ToInt32 (value.ToString ());
//						break;
//					case DataType.@float:
//						value = Convert.ToSingle (value.ToString ());
//						break;
//					case DataType.@bool:
//						value = Convert.ToBoolean (value.ToString ());
//						break;
//					case DataType.@List:
//						value = GetStructDatas(assembly,sheet,null,null,Columns [c].Struct,cells[i].Title.Text,cells[i].InputValue);
//						break;
//					case DataType.@enum:
//						value = System.Enum.Parse(assembly.GetType(Columns [c].Enum),Convert.ToString(value));
//						break;
//					}
//	
//					data.GetType ().GetField (Columns [c].Name).SetValue (data, value);
//					EditorUtility.DisplayCancelableProgressBar ("Creating Master Data", "Table: " + Name + " Row: " + (r / Columns.Length + 1) + "/" + (cells.Count / Columns.Length), (float)(i + 1) / (float)cells.Count);
//				}
//				datas.GetValue (asset).GetType ().GetMethod ("Add").Invoke (datas.GetValue (asset), new object[]{data});
//	
//			}
//		}
//		else
//		{
//			IDataProvider dp = Assembly.GetAssembly(typeof(DataTable)).CreateInstance(this.Provider) as IDataProvider;
//		
//			if(dp != null)
//			{
//				dp.FillData(this,sheet,asset);
//			}
//			else
//			{
//				throw new NotImplementedException(this.Provider + " Provider is not implemented.");
//			}
//		}
//		
//		if(this.Type != TableType.Customize)
//		{
//			if (!exsits) {
//				AssetDatabase.CreateAsset (asset, "Assets/GameDatas/Master/" + Name + "Table.asset");
//			} else {
//				EditorUtility.SetDirty (asset);
//			}
//			AssetDatabase.SaveAssets ();
//		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Column[] getColumns() {
		return columns;
	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
