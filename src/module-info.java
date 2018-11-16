/**
 * This is a library for streaming tables in and out of a program. The interface
 * is generic across multiple table formats, and so can also be used to convert
 * between them. Currently supported are TSV, CSV, and DSV (CSV with delimiters
 * other than commas).
 * 
 * @author Jude Melton-Houghton
 */
module jwmh.tablestreams
{
  exports jwmh.tablestreams;

  requires org.junit.jupiter.api;
}