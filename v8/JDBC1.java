// Forrit úr Fundamentals of Database Systems
// eftir Ramez Elmasri og Shamkant B. Navathe

// Staðfært, leiðrétt og aðlagað af Snorra Agnarssyni 2012.
// Virkar gagnvart SQLite, PostgreSQL og ODBC.

// Þetta forrit má þýða og keyra svona í Windows:
//   javac JDBC1.java
// og síðan (fyrir SQLite)
//   java -cp .;sqlite-jdbc-....jar JDBC1
// eða (fyrir PostgreSQL)
//   java -cp .;postgresql-....jar JDBC1 userid password
// eða (fyrir ODBC)
//   java JDBC1
//   
// Í Unix:
//   javac JDBC1.java
// og síðan (fyrir SQLite)
//   java -cp .:sqlite-jdbc-....jar JDBC1
// eða (fyrir PostgreSQL)
//   java -cp .:postgresql-....jar JDBC1 userid password
// eða (fyrir ODBC)
//   java JDBC1

// Til þess að þetta virki fyrir SQLite þarf sqlite-jdbc-....jar
// og company.db að vera í núverandi möppu, þar sem company.db er 
// COMPANY gagnagrunnurinn í SQLite, og sqlite-jdbc-....jar er 
// SQLite JDBC klasasafnið.
//
// Ef nota skal PostgreSQL þarf postgresql-9.2-1002.jdbc4.jar
// að vera til staðar í möppunni, en þá þarf ekki SQLite
// skrárnar.  Ef postgresql-9.2-1002.jdbc4.jar er til staðar
// (bæði í skipanalínunni og í möppunni) verður reynt að nota
// PostgreSQL tengingu, annars ekki.
// Þrautalendingin er að nota ODBC, en þá þarf að vera til
// staðar ODBC "data source" sem heitir COMPANY og verður
// (eins og í hinum tilfellunum) að innihalda COMPANY
// gagnagrunninn.

import java.io.*;
import java.sql.*;
import java.util.Scanner;

class JDBC1
{
	public static void main( String[] args )
		throws Exception
	{
		Connection conn;
		try
		{
			// Reynum fyrst PostgreSQL
			Class.forName("org.postgresql.Driver");	// fyrir PostgreSQL
			java.util.Properties props = new java.util.Properties();
			props.setProperty("user",args[0]);
			props.setProperty("password",args[1]);
			conn = DriverManager.getConnection("jdbc:postgresql:COMPANY",props);
		}
		catch( Exception e )
		{
			// Höldum bara áfram og reynum SQLite
			try
			{
				Class.forName("org.sqlite.JDBC");		// fyrir SQLite
				conn = DriverManager.getConnection("jdbc:sqlite:company.db");
			}
			catch( Exception e2 )
			{
				// Höldum áfram og reynum ODBC
				conn = DriverManager.getConnection("jdbc:odbc:COMPANY");
			}
		}

		// Þegar hér er komið erum við með tengingu við gagnagrunn
		// (conn) sem er annaðhvort PostgreSQL, SQLite eða ODBC.
		String ssn, lname;
		double salary;
		String stmt1 = "select Lname, Salary from EMPLOYEE where ssn = ?";
		PreparedStatement p = conn.prepareStatement(stmt1);
		System.out.print("Enter a Social Security Number: ");
		Scanner scanner = new Scanner(System.in);
		ssn = scanner.nextLine();
		p.clearParameters();
		p.setString(1,ssn);
		ResultSet r = p.executeQuery();
		while( r.next() )
		{
			lname = r.getString(1);
			salary = r.getDouble(2);
			System.out.println(lname+" "+salary);
		}
		r.close();
		conn.close();
	}
}
