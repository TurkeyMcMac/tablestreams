package tests;

import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OutputStreamSimulator extends OutputStream
{
  String expected;

  public OutputStreamSimulator(String... expected)
  {
    this.expected = String.join("\n", expected);
    //System.out.println(this.expected);
  }

  public void write(byte[] bytes)
  {
    String text = new String(bytes);
    //System.out.println("{" + expected + "} {" + text + "}");
    if (expected.indexOf(text) != 0)
    {
      fail("Unexpected written text: " + text);
    }
    expected = expected.substring(text.length());

  }

  public void write(int b)
  {
    char ch = (char) b;
    //System.out.println("{" + expected + "} {" + ch + "}");
    if (expected.indexOf(ch) != 0)
    {
      fail("Unexpected written text: " + ch);
    }
    expected = expected.substring(1);
  }

}
