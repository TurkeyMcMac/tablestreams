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
    rowNum = 1;
    sheetNames = new ArrayList<String>();
    writingTable = false;
  }

  private void getColumnLetters(StringBuilder to, int col)
  {
    if (col < 26)
    {
      to.append((char) ('A' + col));
    }
    else
    {
      do
      {
        to.append((char) ('A' + (col % 26)));
        col /= 26;
      }
      while (col >= 26);
    }
  }

  public void beginTable(String name) throws IOException
  {
    sheetNames.add(name);
    output.putNextEntry(
      new ZipEntry("xl/worksheets/sheet" + sheetNames.size() + ".xml"));
    output
      .write(("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<worksheet"
        + " xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\""
        + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
        + " xmlns:mx=\"http://schemas.microsoft.com/office/mac/excel/2008/main\""
        + " xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\""
        + " xmlns:mv=\"urn:schemas-microsoft-com:mac:vml\""
        + " xmlns:x14=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/main\""
        + " xmlns:x14ac=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac\""
        + " xmlns:xm=\"http://schemas.microsoft.com/office/excel/2006/main\">"
        + "<sheetViews></sheetViews>" + "<sheetFormatPr/>" + "<sheetData>")
          .getBytes());
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
      getColumnLetters(print, colNum);
      ++colNum;
      print.append(rowNum);
      print.append("\"><v>");
      print.append(cell);
      print.append("</v></c>");
    }
    print.append("</row>");
    output.write(print.toString().getBytes());
    ++rowNum;
  }

  public void finishWriting() throws IOException
  {
    output.write("</sheetData></worksheet>".getBytes());
    output.closeEntry();
    writingTable = false;
  }

  public void close() throws IOException
  {
    StringBuilder print = new StringBuilder();
    if (writingTable)
    {
      finishWriting();
    }
    output.putNextEntry(new ZipEntry("xl/workbook.xml"));
    print.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
      + "<workbook"
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
      print.append(sheetNames.get(i));
      print.append("\" sheetId=\"");
      print.append(i + 1);
      print.append("\" r:id=\"rId");
      print.append(i + 3);
      print.append("\"/>");
    }
    print.append("</sheets><definedNames/><calcPr/></workbook>");
    output.write(print.toString().getBytes());
    output.closeEntry();
    
    output.putNextEntry(new ZipEntry("xl/_rels/workbook.xml.rels"));
    print.setLength(0);
    print.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
      + "<Relationship Id=\"rId3\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\" Target=\"worksheets/sheet1.xml\"/></Relationships>");
    output.write(print.toString().getBytes());
    output.closeEntry();
    
    //
    // output.putNextEntry(new ZipEntry("xl/styles.xml"));
    // print.setLength(0);
    // print.append("<?xml version=\"1.0\" encoding=\"UTF-8\"
    // standalone=\"yes\"?><Relationships></Relationships>");
    // output.write(print.toString().getBytes());
    // output.closeEntry();

    output.putNextEntry(new ZipEntry("_rels/.rels"));
    print.setLength(0);
    print.append(
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\"><Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"xl/workbook.xml\"/></Relationships>");
    output.write(print.toString().getBytes());
    output.closeEntry();

    output.putNextEntry(new ZipEntry("[Content_Types].xml"));
    print.setLength(0);
    print.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
      + "<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">"
      + "<Default ContentType=\"application/xml\" Extension=\"xml\"/>"
      + "<Default ContentType=\"application/vnd.openxmlformats-package.relationships+xml\" Extension=\"rels\"/>"
      + "<Override ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\" PartName=\"/xl/worksheets/sheet1.xml\"/>"
      + "<Override ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml\" PartName=\"/xl/workbook.xml\"/>"
      + "</Types>");
    output.write(print.toString().getBytes());
    output.closeEntry();

    output.close();
  }

}
