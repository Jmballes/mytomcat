package com.jmbo;

public class HtmlTableBuilder {



		 private int columns;
		 private final StringBuilder table = new StringBuilder();
		 public static final String HTML_START = "<html>";
		 public static final String HTML_END = "</html>";
		 public static final String TABLE_START_BORDER = "<table border=\"1\">";
		 public static final String TABLE_START = "<table>";
		 public static final String TABLE_END = "</table>";
		 public static final String HEADER_START = "<th>";
		 public static final String HEADER_END = "</th>";
		 public static final String ROW_START = "<tr>";
		 public static final String ROW_END = "</tr>";
		 public static final String COLUMN_START = "<td>";
		 public static final  String COLUMN_END = "</td>";


		 /**
		  * @param header
		  * @param border
		  * @param rows
		  * @param columns
		 * @return 
		  */
		 public HtmlTableBuilder(String header, boolean border, int rows, int columns) {
		  this.columns = columns;
		  if (header != null) {
		   table.append("<b>");
		   table.append(header);
		   table.append("</b>");
		  }
		  table.append(HTML_START);
		  table.append(border ? TABLE_START_BORDER : TABLE_START);
		  table.append(TABLE_END);
		  table.append(HTML_END);
		 }


		 /**
		  * @param values
		  */
		 public void addTableHeader(String... values) {
		  if (values.length != columns) {
		   System.out.println("Error column lenth");
		  } else {
		   int lastIndex = table.lastIndexOf(TABLE_END);
		   if (lastIndex > 0) {
		    StringBuilder sb = new StringBuilder();
		    sb.append(ROW_START);
		    for (String value : values) {
		     sb.append(HEADER_START);
		     sb.append(value);
		     sb.append(HEADER_END);
		    }
		    sb.append(ROW_END);
		    table.insert(lastIndex, sb.toString());
		   }
		  }
		 }


		 /**
		  * @param values
		  */
		 public void addRowValues(String... values) {
		  if (values.length != columns) {
		   System.out.println("Error column lenth");
		  } else {
		   int lastIndex = table.lastIndexOf(ROW_END);
		   if (lastIndex > 0) {
		    int index = lastIndex + ROW_END.length();
		    StringBuilder sb = new StringBuilder();
		    sb.append(ROW_START);
		    for (String value : values) {
		     sb.append(COLUMN_START);
		     sb.append(value);
		     sb.append(COLUMN_END);
		    }
		    sb.append(ROW_END);
		    table.insert(index, sb.toString());
		   }
		  }
		 }


		 /**
		  * @return
		  */
		 public String build() {
		  return table.toString();
		 }


		 /**
		  * @param args
		  */
		 public  void generate() {
		  //HTMLTableBuilder htmlBuilder = new HTMLTableBuilder(null, true, 2, 3);
		  addTableHeader("1H", "2H", "3H");
		  addRowValues("1", "2", "3");
		  addRowValues("4", "5", "6");
		  addRowValues("9", "8", "7");
		  String table = build();
		  System.out.println(table.toString());
		 }


		}

