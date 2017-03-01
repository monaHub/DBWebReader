import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	// static BufferedReader inf;
	Page[] page = new Page[10];
	static String fileAddress;

	Control() {
		fileAddress = "C:\\Users\\Karimi\\Desktop\\viratech\\workspace\\DBWebReader\\data\\1.txt"; // you
																									// can
																									// also
																									// scan
																									// this
																									// .

	}

	public static void main(String[] args) throws IOException, SQLException {

		int wordsLength = 0;
		String url = "jdbc:mysql://localhost/web?user=root&password=";
		Connection conn = DriverManager.getConnection(url);
		// setPageTbale( conn);
		setPages(conn);
		setFromToTable(conn);
		// String line1 =
		// Files.readAllLines(Paths.get("C:\\Users\\Karimi\\Desktop\\viratech\\workspace\\DBWebReader\\data\\","1.txt")).get(7);

		
		
	}

	// ************************************* Set From-To Table
	public static void setFromToTable(Connection conn) throws SQLException, IOException {
		Statement stmt = conn.createStatement();
		Statement stmt1 = conn.createStatement();


		String line1;
		for (int i = 1; i < 9; i++) {
			FileReader in = new FileReader(
					"C:\\Users\\Karimi\\Desktop\\viratech\\workspace\\DBWebReader\\data\\" + i + ".txt");
			BufferedReader inf1 = new BufferedReader(in);
			int j = 7;

			while (!(line1 = Files.readAllLines(
					Paths.get("C:\\Users\\Karimi\\Desktop\\viratech\\workspace\\DBWebReader\\data\\", i+".txt"))
					.get(j)).isEmpty()) {

//			String line = Files.readAllLines(
//						Paths.get("C:\\Users\\Karimi\\Desktop\\viratech\\workspace\\DBWebReader\\data\\", "1.txt"))
//						.get(j);
				j++;
				ResultSet rs1 = stmt.executeQuery("SELECT * FROM page WHERE address='" + line1 + "'");
				rs1.beforeFirst();
				if(!rs1.next()){
					System.out.println(line1);
					String query1 = "INSERT INTO Page ( address ) " + "VALUES ('"  + line1 + "')";
					
					stmt1.executeUpdate(query1);
					
					
				} else {
				
				while (rs1.next()) {
					System.out.println("mona");
						String query3 = "INSERT INTO from_to ( id_from , id_to , Page_idPage) " + "VALUES (?,?,?)";
						PreparedStatement s = conn.prepareStatement(query3);
						s.setInt(1, page[i].getId());
						s.setInt(2, rs1.getInt(1));
						s.setInt(3,  page[i].getId());

						
						int result = s.executeUpdate();
						
					} // end of while rs1


			} // end of while line
			} //end else
		} // end of for

	}

	// ******************************** Set Page information
	public static void setPages(Connection conn) throws SQLException {

		Statement stmt1 = conn.createStatement();

		ResultSet rs2 = stmt1.executeQuery("SELECT * FROM page ");
		rs2.beforeFirst();
		int i = 0;
		while (rs2.next()) {

			page[i] = new Page();
			page[i].setId(rs2.getInt(1));
			page[i].setAddress(rs2.getString(2));
			page[i].setFech_time(rs2.getString(3));
			page[i].setLengths(rs2.getString(4));
			page[i].setModified_time(rs2.getString(5));
			page[i].setType(rs2.getString(6));

			i++;
		}

	}

	// ****************************************** Set Page Table
	public static void setPageTbale(Connection conn) throws SQLException, IOException {

		for (int i = 1; i < 10; i++) {

			FileReader in1 = new FileReader(
					"C:\\Users\\Karimi\\Desktop\\viratech\\workspace\\DBWebReader\\data\\" + i + ".txt");
			BufferedReader inf2 = new BufferedReader(in1);
			String query1 = "INSERT INTO Page ( address ,Fech_time,length ,modified_time,type) " + "VALUES (?,?,?,?,?)";
			int lineNum = 0;
			String line;
			PreparedStatement sb = conn.prepareStatement(query1);

			while ((line = inf2.readLine()) != null) {

				if (lineNum < 5) {
					lineNum++;
					sb.setString(lineNum, line);

				} // end of if
				else
					break;
			} // end of while

			int result = sb.executeUpdate();
			inf2.close();

		} // End of for

	}
	//**************************Test

}
