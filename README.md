# excel2sqlite
Make sqlite database from excel.

# Table of contents
- [Usage](#usage)
- [Example](#example)


### Usage
args:
- Excel file name : file name can split by comma. Example "1.xlsx,2.xlsx"
- Database file name: test.db
- System table name: this name is linked to an excel sheet which contains tables definition.

### Example

java -jar e2s.jar "/Users/me/output/1.xlsx,/Users/me/output/2.xlsx" "/Users/me/output/test.db" "SYS_TABLES"
