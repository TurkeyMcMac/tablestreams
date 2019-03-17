package tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.zip.ZipOutputStream;
import jwmh.tablestreams.*;
import org.junit.jupiter.api.Test;

public class XLSXTableWriterTest
{
  private static final String SINK_PATH = "sink.xlsx";

  @Test
  void test() throws Exception
  {
    String[][] expected =
      {{"xl/worksheets/sheet1.xml",
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<worksheet"
            + " xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\""
            + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
            + " xmlns:mx=\"http://schemas.microsoft.com/office/mac/excel/2008/main\""
            + " xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\""
            + " xmlns:mv=\"urn:schemas-microsoft-com:mac:vml\""
            + " xmlns:x14=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/main\""
            + " xmlns:x14ac=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac\""
            + " xmlns:xm=\"http://schemas.microsoft.com/office/excel/2006/main\">"
            + "<sheetViews></sheetViews>" + "<sheetFormatPr/>" + "<sheetData>"
            + "<row r=\"1\"><c t=\"inlineStr\" s=\"1\" r=\"A1\"><is><t>a</t></is></c><c t=\"inlineStr\" s=\"1\" r=\"B1\"><is><t>b</t></is></c><c t=\"inlineStr\" s=\"1\" r=\"C1\"><is><t>&apos;c&apos;</t></is></c></row>"
            + "<row r=\"2\"><c t=\"inlineStr\" s=\"1\" r=\"A2\"><is><t>&lt;d&gt;</t></is></c><c t=\"inlineStr\" s=\"1\" r=\"B2\"><is><t>&quot;e&quot;</t></is></c><c t=\"inlineStr\" s=\"1\" r=\"C2\"><is><t>f</t></is></c></row>"
            + "</sheetData>" + "</worksheet>"},
          {"xl/worksheets/sheet2.xml",
              "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<worksheet"
                + " xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\""
                + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
                + " xmlns:mx=\"http://schemas.microsoft.com/office/mac/excel/2008/main\""
                + " xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\""
                + " xmlns:mv=\"urn:schemas-microsoft-com:mac:vml\""
                + " xmlns:x14=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/main\""
                + " xmlns:x14ac=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac\""
                + " xmlns:xm=\"http://schemas.microsoft.com/office/excel/2006/main\">"
                + "<sheetViews></sheetViews>" + "<sheetFormatPr/>"
                + "<sheetData></sheetData>" + "</worksheet>"},
          {"xl/worksheets/sheet3.xml",
              "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<worksheet"
                + " xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\""
                + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
                + " xmlns:mx=\"http://schemas.microsoft.com/office/mac/excel/2008/main\""
                + " xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\""
                + " xmlns:mv=\"urn:schemas-microsoft-com:mac:vml\""
                + " xmlns:x14=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/main\""
                + " xmlns:x14ac=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac\""
                + " xmlns:xm=\"http://schemas.microsoft.com/office/excel/2006/main\">"
                + "<sheetViews></sheetViews>" + "<sheetFormatPr/>"
                + "<sheetData>"
                + "<row r=\"1\"><c t=\"inlineStr\" s=\"1\" r=\"A1\"><is><t>a</t></is></c><c t=\"inlineStr\" s=\"1\" r=\"B1\"><is><t>b</t></is></c></row>"
                + "<row r=\"2\"><c t=\"inlineStr\" s=\"1\" r=\"A2\"><is><t>dee</t></is></c><c t=\"inlineStr\" s=\"1\" r=\"B2\"><is><t>eee</t></is></c><c t=\"inlineStr\" s=\"1\" r=\"C2\"><is><t>f</t></is></c></row>"
                + "<row r=\"3\"></row>" + "</sheetData>" + "</worksheet>"},
          {"xl/workbook.xml",
              "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<workbook"
                + " xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\""
                + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
                + " xmlns:mx=\"http://schemas.microsoft.com/office/mac/excel/2008/main\""
                + " xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\""
                + " xmlns:mv=\"urn:schemas-microsoft-com:mac:vml\""
                + " xmlns:x14=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/main\""
                + " xmlns:x14ac=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac\""
                + " xmlns:xm=\"http://schemas.microsoft.com/office/excel/2006/main\">"
                + "<workbookPr/>"
                + "<sheets><sheet state=\"visible\" name=\"ABC\" sheetId=\"1\" r:id=\"rId3\"/>"
                + "<sheet state=\"visible\" name=\"empty\" sheetId=\"2\" r:id=\"rId4\"/>"
                + "<sheet state=\"visible\" name=\"varLen\" sheetId=\"3\" r:id=\"rId5\"/>"
                + "</sheets>" + "<definedNames/>" + "<calcPr/>"
                + "</workbook>"},
          {"xl/_rels/workbook.xml.rels",
              "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
                + "<Relationship Id=\"rId3\""
                + " Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\""
                + " Target=\"worksheets/sheet1.xml\"/>"
                + "<Relationship Id=\"rId4\""
                + " Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\""
                + " Target=\"worksheets/sheet2.xml\"/>"
                + "<Relationship Id=\"rId5\""
                + " Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\""
                + " Target=\"worksheets/sheet3.xml\"/>" + "</Relationships>"},
          {"_rels/.rels",
              "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
                + "<Relationship Id=\"rId1\""
                + " Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\""
                + " Target=\"xl/workbook.xml\"/>" + "</Relationships>"},
          {"[Content_Types].xml",
              "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">"
                + "<Default ContentType=\"application/xml\" Extension=\"xml\"/>"
                + "<Default ContentType=\"application/vnd.openxmlformats-package.relationships+xml\" Extension=\"rels\"/>"
                + "<Override"
                + " ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml\""
                + " PartName=\"/xl/workbook.xml\"/>" + "<Override"
                + " ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\""
                + " PartName=\"/xl/worksheets/sheet1.xml\"/>" + "<Override"
                + " ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\""
                + " PartName=\"/xl/worksheets/sheet2.xml\"/>" + "<Override"
                + " ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\""
                + " PartName=\"/xl/worksheets/sheet3.xml\"/>" + "</Types>"}};
    String[][] rowsABC = {{"a", "b", "'c'"}, {"<d>", "\"e\"", "f"},};
    String[][] rowsVarLen = {{"a", "b"}, {"dee", "eee", "f"}, {}};
    HashMap expectedEntries = new HashMap(expected.length);
    for (String[] entry : expected)
    {
      expectedEntries.put(entry[0], entry[1]);
    }
    ZipOutputStream out =
      new ZipOutputStreamSimulator(new FileOutputStream(SINK_PATH),
        expectedEntries);
    XLSXTableWriter writer = new XLSXTableWriter(out);
    writer.beginTable("ABC");
    writer.writeTable(new NonCloningTableFeeder(Arrays.asList(rowsABC)));
    writer.beginTable("empty");
    writer.finishWriting();
    writer.beginTable("varLen");
    writer.writeTable(new NonCloningTableFeeder(Arrays.asList(rowsVarLen)));
    writer.close();
  }
}
