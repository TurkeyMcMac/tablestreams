# tablestreams
tablestreams is a tool for streaming tables in and out of a program. The interface is generic across multiple table formats, and so can also be used to convert between them. Currently supported are TSV, CSV, and DSV (CSV with delimiters other than commas). I hope to add more in the future.

#### Examples
Here, a table is read from the TSV format to a CSV format:

```
TableReader reader = new TSVTableReader(new FileInputStream("table.tsv"));
TableWriter writer = new CSVTableWriter(new FileOutputStream("table.csv"));
writer.writeTable(reader);
```

This does the same except that it only reads/writes one line:

```
TableReader reader = new TSVTableReader(new FileInputStream("table.tsv"));
TableWriter writer = new CSVTableWriter(new FileOutputStream("table.csv"));
writer.writeRow(reader.readRow());
```