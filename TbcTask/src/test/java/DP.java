import org.testng.annotations.DataProvider;

public class DP {

	@DataProvider (name = "user-data")
	public Object[][] dpMethod()
	{
		return new Object[][] {
			{"automation","Automation@!@123", "1204", "User exists!" },
			{"automation","Auto@2", "1300", "Passwords must have at least one non alphanumeric character,"
					+ " one digit ('0'-'9'), one uppercase ('A'-'Z'), "
					+ "one lowercase ('a'-'z'), "
					+ "one special character and Password must be eight characters or longer." },

			{"automation", "","1200","UserName and Password required."}

		};


	}

	// SCENARIO 2 DataProvider
	@DataProvider (name = "authorize-data")
	public Object[][]  authorizeDP(){
		return new Object[][] {
			{"DataKhukhua", "Automation@!@123","false","Success", "User authorized successfully.", "true"},
		};
	}

}







