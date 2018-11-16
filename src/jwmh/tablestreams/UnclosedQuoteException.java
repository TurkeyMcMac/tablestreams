/**
 * Virtual High School
 * @author type your full name here
 */

package jwmh.tablestreams;

public class UnclosedQuoteException extends TableReadException
{

  String quoted;

  public UnclosedQuoteException(String quoted)
  {
    super();
    this.quoted = quoted;
  }

  @Override
  public String getMessage()
  {
    return "The quoted section '" + quoted + "' is never closed.";
  }

  public String getQuoted()
  {
    return quoted;
  }
}
