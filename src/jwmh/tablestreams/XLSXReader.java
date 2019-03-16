package jwmh.tablestreams;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.*;

public class XLSXReader
{
  private List<String> sharedStrings;
  private Map<String, Spreadsheet> sheets;
  private ZipFile archive;

  public XLSXReader(File file)
    throws IOException, SAXException, ParserConfigurationException
  {
    this(new ZipFile(file));
  }

  public XLSXReader(ZipFile file)
    throws IOException, SAXException, ParserConfigurationException
  {
    ZipEntry sharedStringEntry = file.getEntry("xl/sharedStrings.xml");
    archive = file;
    if (sharedStringEntry != null)
    {
      sharedStrings =
        parseSharedStrings(file.getInputStream(sharedStringEntry));
    }
    sheets = collectSheets(file);
  }

  private static List<String> parseSharedStrings(InputStream inputStream)
    throws IOException, SAXException, ParserConfigurationException
  {
    ArrayList<String> strings;
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(inputStream);
    Node root = doc.getDocumentElement();
    int uniqueCount =
      Integer.parseInt(
        root.getAttributes().getNamedItem("uniqueCount").getNodeValue());
    strings = new ArrayList<String>(uniqueCount);
    NodeList sis = root.getChildNodes();
    for (int i = 0; i < sis.getLength(); ++i)
    {
      Node si = sis.item(i);
      if (si.getNodeName() == "si")
      {
        Node value = si.getFirstChild();
        if (value.getNodeName() == "t")
        {
          strings.add(value.getTextContent());
        }
      }
    }
    strings.trimToSize();
    return strings;
  }

  private static final Pattern SHEET_PATTERN =
    Pattern.compile("^xl/worksheets/(.+)\\.xml$");

  private static Map<String, Spreadsheet> collectSheets(ZipFile archive)
  {
    Map<String, Spreadsheet> sheets = new HashMap<String, Spreadsheet>();
    Enumeration<? extends ZipEntry> entries = archive.entries();
    ZipEntry sheetEntry;
    while (entries.hasMoreElements())
    {
      sheetEntry = entries.nextElement();
      Matcher nameMatch = SHEET_PATTERN.matcher(sheetEntry.getName());
      if (nameMatch.matches())
      {
        String name = nameMatch.group(1);
        sheets.put(name, new Spreadsheet(sheetEntry));
      }
    }
    return sheets;
  }

  private static class Spreadsheet
  {
    private ZipEntry entry;
    private List<String[]> rows;

    public Spreadsheet(ZipEntry entry)
    {
      this.entry = entry;
      rows = null;
    }

    public TableReader getTable(XLSXReader context)
      throws IOException, ParserConfigurationException, SAXException
    {
      if (rows == null)
      {
        rows = parseTable(context.archive.getInputStream(entry), context);
      }
      return new TableFeeder(rows);
    }

    private static List<String[]> parseTable(InputStream input,
      XLSXReader context)
      throws ParserConfigurationException, SAXException, IOException
    {
      NodeList rows = null;
      Document doc = null;
      Node sheetData, dimension;
      try
      {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(input);
      }
      catch (ParserConfigurationException e)
      {
        // Hopefully this never happens.
        assert "Unreachable" == null;
        return null;
      }
      sheetData = doc.getElementsByTagName("sheetData").item(0);
      if (sheetData != null)
      {
        rows = sheetData.getChildNodes();
      }
      int width = 0;
      dimension = doc.getElementsByTagName("dimension").item(0);
      if (dimension != null)
      {
        Node dimAttr = dimension.getAttributes().getNamedItem("ref");
        if (dimAttr != null)
        {
          List<int[]> dimensions = parseDimensions(dimAttr.getNodeValue());
          if (dimensions.size() >= 2)
          {
            width = dimensions.get(1)[1] - dimensions.get(0)[1];
            if (width > 0)
            {
              width += 1;
            }
          }
        }
      }
      List<String[]> parsedRows = new ArrayList<String[]>();
      int i = 0;
      String[] row;
      while ((row = parseRow(rows, context.sharedStrings, width, i++)) != null)
      {
        parsedRows.add(row);
      }
      return parsedRows;
    }

    private static String[] parseRow(NodeList rows, List<String> sharedStrings,
      int width, int index)
    {
      List<String> parsedRow;
      String[] rowArray;
      Node row = rows.item(index);
      if (row == null)
      {
        return null;
      }
      NodeList cells = row.getChildNodes();
      parsedRow = new ArrayList<String>(width);
      for (int i = 0; i < cells.getLength(); ++i)
      {
        Node cell = cells.item(i);
        if (cell.getNodeName().equals("c"))
        {
          Node value = cell.getFirstChild();
          if (value.getNodeName().equals("v"))
          {
            String rawValue = value.getTextContent();
            String realValue;
            Node type = cell.getAttributes().getNamedItem("t");
            if (type != null && type.getNodeValue().equals("s"))
            {
              realValue = sharedStrings.get(Integer.parseInt(rawValue));
            }
            else
            {
              realValue = rawValue;
            }
            parsedRow.add(realValue);
          }
        }
      }
      rowArray = new String[parsedRow.size()];
      return parsedRow.toArray(rowArray);
    }

    private static List<int[]> parseDimensions(String dimension)
    {
      List<int[]> dims = new ArrayList<int[]>(2);
      String[] dimensions = dimension.split(":");
      for (int i = 0; i < Math.min(dimensions.length, 2); ++i)
      {
        dims.add(parseLocation(dimensions[i]));
      }
      return dims;
    }

    private static int[] parseLocation(String string)
    {
      int column = 0, row = 0;
      char nextChar;
      int i;
      for (i = 0; i < string.length(); ++i)
      {
        nextChar = string.charAt(i);
        if (Character.isAlphabetic(nextChar))
        {
          column += Character.toLowerCase(nextChar) - 'a' + 1;
        }
        else
        {
          break;
        }
      }
      for (; i < string.length(); ++i)
      {
        nextChar = string.charAt(i);
        if (Character.isDigit(nextChar))
        {
          row *= 10;
          row += nextChar - '0';
        }
      }
      int[] rowColumn = {row, column};
      return rowColumn;
    }
  }

  public TableReader getTable(String name)
    throws IOException, ParserConfigurationException, SAXException
  {
    Spreadsheet sheet = sheets.get(name);
    if (sheet != null)
    {
      return sheet.getTable(this);
    }
    return null;
  }
}
