package tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamSimulator extends ZipOutputStream
{
  private Map<String, String> allExpected;
  private String expecting;

  public ZipOutputStreamSimulator(OutputStream out,
    Map<String, String> expected)
  {
    super(out);
    allExpected = expected;
  }

  @Override
  public void putNextEntry(ZipEntry entry) throws IOException
  {
    String entryName = entry.getName();
    expecting = allExpected.get(entryName);
    if (expecting == null)
    {
      fail("Entry \"" + entryName + "\" was not expected");
    }
    allExpected.remove(entryName);
    super.putNextEntry(entry);
  }

  @Override
  public void write(byte[] bytes) throws IOException
  {
    String str, expectedPart = null;
    str = new String(bytes);
    try
    {
      expectedPart = expecting.substring(0, str.length());
    }
    catch (IndexOutOfBoundsException e)
    {
      fail("Expected string:\n" + expecting + "\nFound string:\n" + str);
    }
    if (!str.equals(expectedPart))
    {
      fail("Expected string:\n" + expectedPart + "\nFound string:\n" + str);
    }
    expecting = expecting.substring(expectedPart.length());
    super.write(bytes);
  }

  @Override
  public void closeEntry() throws IOException
  {
    if (!expecting.isEmpty())
    {
      fail("Expected string:\n" + expecting + "\nFound an empty string");
    }
    super.closeEntry();
  }
}
