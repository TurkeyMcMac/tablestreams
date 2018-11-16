package tests;

import java.io.Closeable;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OutputStreamSimulator extends OutputStream implements Closeable
{
  String expected;

  public OutputStreamSimulator(String... expected)
  {
    this.expected = String.join("\n", expected);
  }

  public void write(byte[] bytes)
  {
    String text = new String(bytes);
    if (expected.indexOf(text) != 0)
    {
      fail("Unexpected written text: " + text);
    }
    expected = expected.substring(text.length());

  }

  public void write(int b)
  {
    char ch = (char) b;
    if (expected.indexOf(ch) != 0)
    {
      fail("Unexpected written text: " + ch);
    }
    expected = expected.substring(1);
  }

  public void close() {
    if (!expected.isEmpty()) {
      fail("Less was written than was expected. This was left: " + expected);
    }
  }

}
