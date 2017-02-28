import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Control {
	static // ArrayList<FileReader> in=new ArrayList();
	// static FileReader[]in=new FileReader[9];
	// static BufferedReader[] inf=new BufferedReader[9];
	FileReader in;
	static BufferedReader inf;
	static Page[] page = new Page[10];

	public static void main(String[] args) throws IOException, SQLException {

		int wordsLength = 0;
		String url = "jdbc:mysql://localhost/web?user=root&password=";
		Connection conn = DriverManager.getConnection(url);
	//	 setPageTbale( conn);
		 setPages( conn) ;
		setFromToTable( conn) ;

	}

	// ************************************* Set From-To Table
	public static void setFromToTable(Connection conn) throws SQLException, IOException {
		BufferedReader inf1 = new BufferedReader(in);
		Statement stmt = conn.createStatement();

		String line1;
		for (int i = 0; i < 10; i++) {

			while ((line1 = inf1.readLine()) != null) {
				if (line1.equals("outliks:")) {
					ResultSet rs1 = stmt.executeQuery("SELECT * FROM page WHERE address='" + line1 + "'");
					rs1.beforeFirst();
					rs1.next();

					if (rs1.getString(2) != null) {
						String query3 = "INSERT INTO from_to ( id_from ,id_to) " + "VALUES (?,?)";
						PreparedStatement s = conn.prepareStatement(query3);
						s.setInt(1, page[i].getId());
						s.setInt(2, rs1.getInt(1));
						int result = s.executeUpdate();

					}

				} // end of if
			} // end of while
		} // end of for
	}

	// ******************************** Set Page information
	public static void setPages(Connection conn) throws SQLException {

		Statement stmt1 = conn.createStatement();

		ResultSet rs1 = stmt1.executeQuery("SELECT * FROM page ");

		for (int i = 0; i < 10; i++) {
			page[i] = new Page();
			page[i].setId(rs1.getInt(1));
			page[i].setAddress(rs1.getString(2));
			page[i].setFech_time(rs1.getString(3));
			page[i].setLengths(Integer.parseInt(rs1.getString(4)));
			page[i].setModified_time(rs1.getString(5));
			page[i].setType(rs1.getString(6));
		}

	}

	// ****************************************** Set Page Tabel
	public static void setPageTbale(Connection conn) throws SQLException, IOException {

		for (int i = 1; i < 10; i++) {

			in = new FileReader("C:\\Users\\Karimi\\Desktop\\viratech\\workspace\\DBWebReader\\data\\" + i + ".txt");
			inf = new BufferedReader(in);
			String query1 = "INSERT INTO Page ( address ,Fech_time,length ,modified_time,type) " + "VALUES (?,?,?,?,?)";
			int lineNum = 0;
			String line;
			PreparedStatement sb = conn.prepareStatement(query1);

			while ((line = inf.readLine()) != null) {

				if (lineNum < 5) {
					lineNum++;
					sb.setString(lineNum, line);

				} // end of if
				else
					break;
			} // end of while

			int result = sb.executeUpdate();
			inf.close();

		} // End of for

	}

}
