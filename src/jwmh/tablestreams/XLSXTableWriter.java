package jwmh.tablestreams;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class XLSXTableWriter implements TableWriter, Closeable
{
  private List<String> sheetNames;
  private ZipOutputStream output;
  private int rowNum;
  private boolean writingTable;

  public XLSXTableWriter(OutputStream out)
  {
    this(new ZipOutputStream(out));
  }

  public XLSXTableWriter(ZipOutputStream out)
  {
    output = out;
    sheetNames = new ArrayList<String>();
    writingTable = false;
  }

  private static char columnDigitChar(int digit)
  {
    return (char)('A' + digit - 1);
  }

  private static String getColumnLetters(int col)
  {
    StringBuilder letters = new StringBuilder();
    ++col;
    while (col >= 26)
    {
      letters.append(columnDigitChar(col % 26));
      col /= 26;
    }
    letters.append(columnDigitChar(col));
    return letters.reverse().toString();
  }

  private static String escape(char original)
  {
    switch (original)
    {
      case '<':
        return "&lt;";
      case '>':
        return "&gt;";
      case '"':
        return "&quot;";
      case '\'':
        return "&apos;";
      case '&':
        return "&amp;";
      default:
        return Character.toString(original);
    }
  }

  private static void appendEscaped(StringBuilder to, String original)
  {
    for (int i = 0; i < original.length(); ++i)
    {
      to.append(escape(original.charAt(i)));
    }
  }

  private static void appendHeader(StringBuilder to)
  {
    to.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
  }

  public void beginTable(String name) throws IOException
  {
    StringBuilder print = new StringBuilder();
    finishWriting();
    rowNum = 1;
    sheetNames.add(name);
    output.putNextEntry(
      new ZipEntry("xl/worksheets/sheet" + sheetNames.size() + ".xml"));
    appendHeader(print);
    print.append("<worksheet"
      + " xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\""
      + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
      + " xmlns:mx=\"http://schemas.microsoft.com/office/mac/excel/2008/main\""
      + " xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\""
      + " xmlns:mv=\"urn:schemas-microsoft-com:mac:vml\""
      + " xmlns:x14=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/main\""
      + " xmlns:x14ac=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac\""
      + " xmlns:xm=\"http://schemas.microsoft.com/office/excel/2006/main\">"
      + "<sheetViews></sheetViews>" + "<sheetFormatPr/>" + "<sheetData>");
    output.write(print.toString().getBytes());
    writingTable = true;
  }

  public void writeRow(String... row) throws IOException
  {
    int colNum = 0;
    StringBuilder print = new StringBuilder();
    print.append("<row r=\"");
    print.append(rowNum);
    print.append("\">");
    for (String cell : row)
    {
      print.append("<c t=\"inlineStr\" s=\"1\" r=\"");
      print.append(getColumnLetters(colNum));
      ++colNum;
      print.append(rowNum);
      print.append("\"><is><t>");
      appendEscaped(print, cell);
      print.append("</t></is></c>");
    }
    print.append("</row>");
    output.write(print.toString().getBytes());
    ++rowNum;
  }

  public void finishWriting() throws IOException
  {
    if (writingTable)
    {
      output.write("</sheetData></worksheet>".getBytes());
      output.closeEntry();
      writingTable = false;
    }
  }

  private void putWorkbook() throws IOException
  {
    StringBuilder print = new StringBuilder();
    output.putNextEntry(new ZipEntry("xl/workbook.xml"));
    appendHeader(print);
    print.append("<workbook"
      + " xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\""
      + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
      + " xmlns:mx=\"http://schemas.microsoft.com/office/mac/excel/2008/main\""
      + " xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\""
      + " xmlns:mv=\"urn:schemas-microsoft-com:mac:vml\""
      + " xmlns:x14=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/main\""
      + " xmlns:x14ac=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac\""
      + " xmlns:xm=\"http://schemas.microsoft.com/office/excel/2006/main\">"
      + "<workbookPr/><sheets>");
    for (int i = 0; i < sheetNames.size(); ++i)
    {
      print.append("<sheet state=\"visible\" name=\"");
      appendEscaped(print, sheetNames.get(i));
      print.append("\" sheetId=\"");
      print.append(i + 1);
      print.append("\" r:id=\"rId");
      print.append(i + 3);
      print.append("\"/>");
    }
    print.append("</sheets><definedNames/><calcPr/></workbook>");
    output.write(print.toString().getBytes());
    output.closeEntry();
  }

  private void putWorkbookRels() throws IOException
  {
    StringBuilder print = new StringBuilder();
    output.putNextEntry(new ZipEntry("xl/_rels/workbook.xml.rels"));
    appendHeader(print);
    print.append(
      "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">");
    for (int i = 0; i < sheetNames.size(); ++i)
    {
      print.append("<Relationship Id=\"rId");
      print.append(i + 3);
      print.append("\""
        + " Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\""
        + " Target=\"worksheets/sheet");
      print.append(i + 1);
      print.append(".xml\"/>");
    }
    print.append("</Relationships>");
    output.write(print.toString().getBytes());
    output.closeEntry();
  }

  private void putRels() throws IOException
  {

    StringBuilder print = new StringBuilder();
    output.putNextEntry(new ZipEntry("_rels/.rels"));
    appendHeader(print);
    print.append(
      "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
        + "<Relationship Id=\"rId1\""
        + " Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\""
        + " Target=\"xl/workbook.xml\"/>" + "</Relationships>");
    output.write(print.toString().getBytes());
    output.closeEntry();
  }

  private void putContentTypes() throws IOException
  {
    StringBuilder print = new StringBuilder();
    output.putNextEntry(new ZipEntry("[Content_Types].xml"));
    appendHeader(print);
    print.append(
      "<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">"
        + "<Default" + " ContentType=\"application/xml\""
        + " Extension=\"xml\"/>"
        + "<Default"
        + " ContentType=\"application/vnd.openxmlformats-package.relationships+xml\""
        + " Extension=\"rels\"/>"
        + "<Override"
        + " ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml\""
        + " PartName=\"/xl/workbook.xml\"/>");
    for (int i = 0; i < sheetNames.size(); ++i)
    {
      print.append("<Override"
        + " ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\""
        + " PartName=\"/xl/worksheets/sheet");
      print.append(i + 1);
      print.append(".xml\"/>");
    }
    print.append("</Types>");
    output.write(print.toString().getBytes());
    output.closeEntry();
  }

  public void close() throws IOException
  {
    finishWriting();
    putWorkbook();
    putWorkbookRels();
    putRels();
    putContentTypes();
    output.close();
  }

}
