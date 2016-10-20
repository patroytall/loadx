package org.roy.loadx.priv.transaction.print;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public class TablePrinter {
  private final List<String> headerRow = new ArrayList<>();
  private final List<List<String>> dataRows = new ArrayList<>();
  private final List<Integer> columnMaxWidths = new ArrayList<>();

  public TablePrinter(String... columnHeaders) {
    for (String columnHeader : columnHeaders) {
      this.headerRow.add(columnHeader);
    }
  }

  public void addRow(String... columnValues) {
    if (columnValues.length != headerRow.size()) {
      throw new RuntimeException("row number of columns " + columnValues.length
          + " much match header number of columns " + headerRow.size());
    }
    List<String> dataRow = new ArrayList<>();
    for (String columnValue : columnValues) {
      dataRow.add(columnValue);
    }
    dataRows.add(dataRow);
  }

  private void updateColumnMaxWidthsForRow(List<String> row) {
    int index = 0;
    for (String value : row) {
      if (index >= columnMaxWidths.size()) {
        columnMaxWidths.add(value.length());
      } else {
        if (columnMaxWidths.get(index) < value.length()) {
          columnMaxWidths.set(index, value.length());
        }
      }
      index++;
    }
  }

  private void setColumnMaxWidths() {
    updateColumnMaxWidthsForRow(headerRow);
    for (List<String> dataRow : dataRows) {
      updateColumnMaxWidthsForRow(dataRow);
    }
  }

  private String getRowFormat(String separator) {
    StringBuilder rowFormat = new StringBuilder();
    boolean first = true;
    for (int columnMaxWidth : columnMaxWidths) {
      if (!first) {
        rowFormat.append(separator);
      }
      rowFormat.append("%" + (first ? "-" : "") + columnMaxWidth + "s");
      first = false;
    }
    return rowFormat.toString();
  }

  private static void println(String str) {
    System.out.println(str);
    System.out.flush();
  }

  private List<String> getLayoutRow() {
    List<String> layoutRow = new ArrayList<>();
    for (int columnMaxWidth : columnMaxWidths) {
      layoutRow.add(Strings.repeat("-", columnMaxWidth));
    }
    return layoutRow;
  }

  private void printRow(String rowFormat, List<String> row) {
    println(String.format(rowFormat, row.toArray()));
  }

  private void printTableFirstAndLastRow() {
    printRow(getRowFormat("---"), getLayoutRow());
  }

  public void print() {
    setColumnMaxWidths();

    printTableFirstAndLastRow();

    String separatedRowFormat = getRowFormat(" | ");
    printRow(separatedRowFormat, headerRow);
    printRow(getRowFormat("-|-"), getLayoutRow());

    for (List<String> dataRow : dataRows) {
      printRow(separatedRowFormat, dataRow);
    }

    if (dataRows.size() > 0) {
      printTableFirstAndLastRow();
    }
  }
}