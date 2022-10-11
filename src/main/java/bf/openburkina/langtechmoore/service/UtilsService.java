package bf.openburkina.langtechmoore.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UtilsService {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    /**
     *
     * @param sheet
     * @return Iterator<Row>
     */
    public Iterator<Row> getRows(final Sheet sheet)  {
        return sheet.rowIterator();
    }

    /**
     *
     * @param row
     * @return Iterator<Cell>
     */
    public Iterator<Cell> getCells(final Row row)  {
        return row.cellIterator();
    }

    /**
     *
     * @param s
     * @return boolean
     */
    public boolean verifyConvert(final String s) {
        try {
            BigDecimal b = new BigDecimal(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * @param cell
     * @return c
     */
    public String getCellValue(final Cell cell) {
        String s = "";
        if (cell != null && !(cell.getCellType() == CellType.BLANK)) {
            if (cell.getCellType() == CellType.FORMULA)  {
                switch (cell.getCachedFormulaResultType())  {
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)){
                            s = simpleDateFormat.format(cell.getLocalDateTimeCellValue());
                        } else {
                            s = new BigDecimal(Double.toString(cell.getNumericCellValue())).toPlainString();
                        }
                        break;
                    case STRING:
                        if (this.verifyConvert(String.valueOf(cell.getRichStringCellValue()))) {
                            s = new BigDecimal(String.valueOf(cell.getRichStringCellValue())).toPlainString();
                        } else {
                            s = cell.getRichStringCellValue().getString();
                        }
                        break;
                    default:
                }

            } else if (cell.getCellType() == CellType.STRING) {
                s = String.valueOf(cell.getStringCellValue());

            } else {
                s = new BigDecimal(Double.toString(cell.getNumericCellValue())).toPlainString();
            }
        }
        return  s;
    }

    /**
     *
     * @param s
     * @return boolean
     */
    public boolean verifyCell(Sheet s) {
        for (Row r: s){
            List<Cell> cells = cellOfRow(r);
            for (Cell c: cells) {
                try {
                    BigDecimal b = new BigDecimal(getCellValue(c));
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param r
     * @return List<Cell>
     */
    public List<Cell> cellOfRow(Row r){
        List<Cell> cellList = new ArrayList<>();
        getCells(r).forEachRemaining(cellList::add);
        return cellList;
    }

    /**
     *
     * @param r
     * @param c
     * @return boolean
     * Pour retirer les entêtes de colonnes avant de faire les calculs
     */
    public boolean eliminateCellOrRow(Row r, Cell c) {
        if (r != null){
            if (r.getRowNum() == 1) return false;
        }
        if (c != null){
            if (c.getColumnIndex() == 1) return false;
        }
        return true;
    }

    /**
     *
     * @param doc
     * @return Iterator<Cell>
     */
    public List<Cell> getValuesNew(final String sheetName, final String columnName, final byte[] doc) throws IOException {

        InputStream fis = bytesToInputStream(doc);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowSize = sheet.getLastRowNum();

        int columnIndex = getColumnIndexByColumnName(sheet, columnName);

        List<Cell> cellList = new ArrayList<>();

        for (int i = 1; i <= rowSize; i++) {
            Optional<Cell> cell = cellOfRow(sheet.getRow(i))
                .stream()
                .filter(c -> c.getColumnIndex() == columnIndex)
                .findFirst();

            if (cell.isPresent()){
                cellList.add(cell.get());
            }
        }
        return cellList;
    }

    public Integer getColumnIndexByColumnName(final Sheet sheet, final String columnName){
        List<Cell> cellList = cellOfRow(sheet.getRow(0));
        for (Cell c: cellList) {
            if (getCellValue(c).equals(columnName)){
                return c.getColumnIndex();
            }
        }
        return null;
    }

    public List<BigDecimal> getListValue(List<Cell> cells){

        List<BigDecimal> values = new ArrayList<>();

        for (Cell c: cells) {
            String s = getCellValue(c);
            if (verifyConvert(s)){
                values.add(new BigDecimal(s));
            } else {
                return new ArrayList<>();
            }
        }

        return values;
    }


    public String replaceEtTrim(final String s) {
        return s.replaceAll(" ", "").trim();
    }

    /**
     *
     * @param excelfile
     * @return ByteArrayInputStream
     */
    public InputStream bytesToInputStream(final byte[] excelfile) {
        return new ByteArrayInputStream(excelfile);
    }

}
