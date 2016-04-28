import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import oracle.jdbc.*;

public class Driver
{
    private Scanner input;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement prepStatement;
    private String query;

    public Driver( Connection connection )
    {
        System.out.println( "Welcome to the CS 1555 Term Project\nBrought to you by John Felen and Elie Perelman" );
        input = new Scanner( System.in );
        this.connection = connection;
    }

    public void run()
    {
        boolean keepPrintingMenu = true;
        while( keepPrintingMenu )
        {
            System.out.println( "FaceSpace Menu" );
            System.out.println( "1: Create User\n" +
                                "2: Initiate Friendship\n" +
                                "3: Establish Friendship\n" +
                                "4: Display Friends\n" +
                                "5: Create Group\n" +
                                "6: Add To Group\n" +
                                "7: Send Message To User\n" +
                                "8: Display Messages\n" +
                                "9: Search For User\n" +
                                "10: Three Degrees\n" +
                                "11: Top Messagers\n" +
                                "12: Drop User" );
            System.out.println( "Enter menu number( 0 to quit ):" );

            int menuItem;
            try
            {
                menuItem = Integer.parseInt( input.nextLine() );
            }

            catch( Exception e )
            {
                System.out.println( "Invalid input, not a number" );
                continue;
            }

            switch( menuItem )
            {
                case 0:
                    System.out.println( "Shutting down..." );
                    keepPrintingMenu = false;
                    break;

                case 1:
                    try
                    {
                        System.out.println( "Enter the user's name: " );
                        String name = input.nextLine();
                        System.out.println( "Enter the user's email: " );
                        String email = input.nextLine();

                        StringBuilder dob = new StringBuilder();
                        System.out.println( "Enter the user's date of birth year:" );
                        dob.append( input.nextLine() );
                        dob.append( "-" );
                        System.out.println( "Enter the user's date of birth month( number ): " );
                        dob.append( input.nextLine() );
                        dob.append( "-" );
                        System.out.println( "Enter the user's date of birth day( number ): " );
                        dob.append( input.nextLine() );

                        if( createUser( name, email, dob.toString() ) )
                        {
                            System.out.println( name + " added to FaceSpace.\n" );
                        }

                        else
                        {
                            System.out.println( "Error in adding " + name + " to FaceSpace.\n" );
                        }
                    }

                    catch( Exception e )
                    {
                        System.out.println( "Error " + e.toString() );
                    }
                    break;

                case 2:
                    initiateFriendship();
                    break;

                case 3:
                    establishFriendship();
                    break;

                case 4:
                    displayFriends();
                    break;

                case 5:
                    createGroup();
                    break;

                case 6:
                    addToGroup();
                    break;

                case 7:
                    sendMessageToUser();
                    break;

                case 8:
                    displayMessages();
                    break;

                case 9:
                    searchForUser();
                    break;

                case 10:
                    threeDegrees();
                    break;

                case 11:
                    topMessagers();
                    break;

                case 12:
                    dropUser();
                    break;

                default:
                    System.out.println( "Invalid menu item inputted" );
            }
        }
    }

    public boolean createUser( String name, String email, String dob )  throws SQLException
    {
        try
        {
            query = "INSERT INTO user_profile " +
                            "VALUES( ?, ?, ?, NULL )";

            prepStatement = connection.prepareStatement( query );
            SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
            java.sql.Date date_reg = new java.sql.Date( df.parse( dob ).getTime() );

            prepStatement.setString( 1, name );
            prepStatement.setString( 2, email );
            prepStatement.setDate( 3, date_reg );

            return prepStatement.executeUpdate() > 0;   //0 is failure
        }

        catch( SQLException e )
        {
            System.out.println( "Error: " + e.toString() );
            return false;
        }

        catch( ParseException e )
        {
            System.out.println( "Error: " + e.toString() );
            return false;
        }
    }

    public boolean initiateFriendship()
    {

        try
		{
			boolean validEmail = true;
			statement = connection.createStatement(); //create an instance
			// PROMPT THE USER FOR THEIR EMAIL ADDRESS
			System.out.println("Enter your email address");
			Scanner scan = new Scanner(System.in);
			String senderEmail = scan.nextLine().trim();

			// CONSTRUCT THE QUERY TO DETERMINE IF THE SENDER EMAIL IS CONTAINED IN THE DATABASE
	   		String validataSenderQuery = "SELECT * FROM user_profile ";
	   		validataSenderQuery+= "WHERE email = '" + senderEmail + "' ";

			// OBTAIN THE QUERY SET
	   		resultSet = statement.executeQuery(validataSenderQuery); //run the query on the DB table
	   		// We will issue a query to determine if a record exists
	   		if(resultSet.next())
	   		{

	   		}
	   		else{
	   			System.out.println("Invalid Sender email, there is no such email in the database");
	   			validEmail = false;
	   		}

	   		statement = connection.createStatement(); //create an instance
	   		// PROMPT THE USER FOR RECIPIENT EMAIL
	   		System.out.println("Enter the email that you would like to become a friend with");
	   		String recipientEmail = scan.nextLine().trim();

	   		// CONSTRUCT THE QUERY TO DETERMINE IF THE RECIPIENT EMAIL IS CONTAINED IN THE DATABASE
			String validateRecipientQuery = "SELECT * FROM user_profile ";
	   		validateRecipientQuery+= "WHERE email = '" + recipientEmail + "' ";

	   		// OBTAIN THE QUERY SET
	   		resultSet = statement.executeQuery(validateRecipientQuery); //run the query on the DB table
	   		// We will issue a query to determine if a record exists
	   		if(resultSet.next())
	   		{

	   		}
	   		else{
	   			System.out.println("Invalid Recipient email, there is no such email in the database");
	   			validEmail = false;
	   		}

	   		// ONLY ISSUE THE INSERT IF BOTH EMAILS EXIST
	   		if(validEmail)
	   		{
	   			 String query = "INSERT INTO friendship " +
                            "VALUES( ?, ?, ?, NULL )";

           		 try
           		 {
           		 	prepStatement = connection.prepareStatement(query);
            		prepStatement.setString(1,senderEmail);
           		 	prepStatement.setString(2,recipientEmail);
            	 	prepStatement.setInt(3,0);

            	 	if(prepStatement.executeUpdate() > 0)
            	 	{
            	 		System.out.println("Inserted successfully");
            	 	}
            	 }
            	 catch(SQLException e)
            	 {
            	 	System.out.println( "Error: " + e.toString() );
            	 }
	   		}


	    }
		catch(SQLException Ex)
		{
	    	System.out.println("Error running the sample queries.  Machine Error: " +
			       Ex.toString());
		}
		finally
		{
			try
			{
				if (statement != null) statement.close();
				if (prepStatement != null) prepStatement.close();
			} catch (SQLException e) {
			System.out.println("Cannot close Statement. Machine error: "+e.toString());
			}
		}

        return true;

    }

    public boolean establishFriendship()
    {

    	Scanner scan = new Scanner(System.in);

    	System.out.println("Enter your email address that you would to accept the friend request from");
		String senderEmail = scan.nextLine().trim();
		System.out.println("Enter you email address");
		String recipientEmail = scan.nextLine().trim();


		// CREATE THE QUERY STRING TO BE SENT TO THE DATABASE

		String query = String.format("SELECT * FROM friendship WHERE sender_email = '%s' AND accepter_email = '%s'",senderEmail,recipientEmail);

		try
		{
			statement = connection.createStatement(); //create an instance
			resultSet = statement.executeQuery(query); //run the query on the DB table


			// You have found a friendship
			if(resultSet.next()){

				// Determine if the value is 0
				int status = resultSet.getInt(3);

				// if the status == 1 then you dont need to store it again
				if(status == 0){

					System.out.println("Establishing the Friendship...");
					// Use the string format first
					query = String.format("UPDATE friendship SET status=1,date_established=? WHERE sender_email='%s' AND accepter_email='%s'",senderEmail,recipientEmail);

					prepStatement = connection.prepareStatement(query);

					// Get current date
					//SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
					java.util.Date currentTime =  new java.util.Date();

            		java.sql.Date date_reg = new java.sql.Date( currentTime.getTime() );
					// Set the query paramter
    				prepStatement.setDate(1,date_reg);

					if(prepStatement.executeUpdate() > 0)   //0 is failure
					{
						System.out.println("Established Successfully");
					}

				}


			}

		}
		catch(SQLException Ex)
		{
	    	System.out.println("Error running the sample queries.  Machine Error: " +
			       Ex.toString());
		}
		finally
		{
			try
			{
				if (statement != null) statement.close();
				if (prepStatement != null) prepStatement.close();
			} catch (SQLException e) {
			System.out.println("Cannot close Statement. Machine error: "+e.toString());
			}
		}
        return false;
    }

    public boolean displayFriends()
    {
        System.out.println( "Enter the user's email: " );
        String email = input.nextLine();

        try
        {
            query = "SELECT sender_email, accepter_email, status " +
                    "FROM friendship " +
                    "WHERE sender_email = ? OR accepter_email = ? " +
                    "ORDER BY status";


            prepStatement = connection.prepareStatement( query );
            prepStatement.setString( 1, email );
            prepStatement.setString( 2, email );
            resultSet = prepStatement.executeQuery();

            boolean pendingFriendshipPrinted = false;
            boolean establishedFriendshipPrinted = false;
            while( resultSet.next() )
            {
                if( !pendingFriendshipPrinted && resultSet.getInt( 3 ) == 0 )
                {
                    pendingFriendshipPrinted = true;
                    System.out.println( "Pending Friendships:" );
                }

                else if( !establishedFriendshipPrinted && resultSet.getInt( 3 ) == 1 )
                {
                    establishedFriendshipPrinted = true;
                    System.out.println( "Established Friendships:" );
                }

                //the opposite is not the email entered so print out their friendship
                if( resultSet.getString( 2 ).equals( email ) )
                {
                    System.out.println( resultSet.getString( 1 ) );
                }

                else
                {
                    System.out.println( resultSet.getString( 2 ) );
                }
            }

            System.out.println();
        }

        catch(SQLException Ex)
        {
            System.out.println("Error running the sample queries.  Machine Error: " + Ex.toString());
            return false;
        }

        finally
        {
            try
            {
                if (statement != null) statement.close();
                if (prepStatement != null) prepStatement.close();
            }

            catch (SQLException e)
            {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
                return false;
            }
        }

        return true;
    }

    public boolean createGroup()
    {
        
        // Prompt for a name, description, and membership limit
        String groupName, descripton;
        int membershipLimit;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the the name of the group to create"); 
        groupName = scan.nextLine().trim();
        System.out.println("Enter descripton for the group");
        descripton = scan.nextLine().trim();
        System.out.println("Enter the number of members allowed in this group (membership limit)"); 

        if(scan.hasNextInt())
        {
            membershipLimit = scan.nextInt();
        }else{
            membershipLimit = 50; // If the user enters a non numberical value set to 50
        }

        // CREATE THE NEW GROUP 

        try{

            query = "INSERT INTO group_profile VALUES(?,?,?)";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, groupName);
            prepStatement.setString(2, descripton);
            prepStatement.setInt(3, membershipLimit);
       
            prepStatement.executeUpdate();
        }

        catch( SQLException Ex )
        {
            System.out.println( "Error running the sample queries.  Machine Error: " + Ex.toString() );
            return false;
        } 
        finally
        {
            try
            {
                if ( statement != null ) statement.close();
                if ( prepStatement != null ) prepStatement.close();
            }

            catch ( SQLException e )
            {
                System.out.println( "Cannot close Statement. Machine error: "+e.toString() );
                return false;
            }
        }
        return true;
    }

    public boolean addToGroup()
    {
        
        String groupName, userEmail;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the name of the group that you would like to join");
        groupName = scan.nextLine().trim(); 
        System.out.println("Enter your email address");
        userEmail = scan.nextLine().trim();


        // OBTAIN THE current GROUP Total FROM THE DATABASE  - CREATE THE QUERY 
        String query =  "SELECT COUNT(*), group_profile " + 
                            "FROM user_group " + 
                             "WHERE group_profile = '%s' "+ 
                                "GROUP BY(group_profile)";

        // Pass in the query with the updated value 
        query = String.format(query,groupName);

        try{

            statement = connection.createStatement(); //create an instance
            resultSet = statement.executeQuery(query); //run the query on the DB table

            // check if there is a group name in the database
            if(resultSet.next()){
                // obtain the current amount of users in the current group 
                int currentMemberTotal = resultSet.getInt(1);
                // Close the last statement 
                statement.close();

                // Create the query string to obtain the membership total
                query = "SELECT member_limit FROM group_profile WHERE gname = '%s' ";
                query = String.format(query,groupName);

                // Create query 
                statement = connection.createStatement();
                // Obtain the membership limit allowed in the current group
    
                resultSet = statement.executeQuery(query); //run the query on the DB table

                if(resultSet.next()){

                    int maxMembersTotal = resultSet.getInt(1);

                    // CHECK IF YOU HAVE EXCEEDED THE TOTAL AMOUNT
                    if(currentMemberTotal >= maxMembersTotal){
                        System.out.println("You have reached the max total of users allowed in this group");

                    }else{
                        // Close the last statement 
                        statement.close();
                        // INSERT THE NEW GROUP
                        query = "INSERT INTO user_group VALUES(?,?)";
                        prepStatement = connection.prepareStatement(query);

                        prepStatement.setString(1, groupName);
                        prepStatement.setString(2, userEmail);

                        prepStatement.executeUpdate();

                        System.out.println("Added " + userEmail + " to group " + groupName );
                    }

                }else{
                    System.out.println("Some internal error --- DEBUG");
                }

            }else{
                System.out.println("There is no such group in the database");
            }

        } 
        catch( SQLException Ex )
        {
            System.out.println( "Error running the sample queries.  Machine Error: " + Ex.toString() );
            return false;
        }

        finally
        {
            try
            {
                if ( statement != null ) statement.close();
                if ( prepStatement != null ) prepStatement.close();
            }

            catch ( SQLException e )
            {
                System.out.println( "Cannot close Statement. Machine error: "+e.toString() );
                return false;
            }
        }




        return false;
    }

    public boolean sendMessageToUser()
    {
        System.out.println( "Enter the sender's email: " );
        String senderEmail = input.nextLine();
        System.out.println( "Enter the recipient's email: " );
        String recipientEmail = input.nextLine();
        System.out.println( "Enter the subject of the message: " );
        String subject = input.nextLine();
        System.out.println( "Enter the message: " );
        String body = input.nextLine();

        try
        {
            query = "INSERT INTO message VALUES( ?, ?, ?, ?, ? )";
    	    prepStatement = connection.prepareStatement( query );

    	    prepStatement.setString( 1, senderEmail );
            prepStatement.setString( 2, recipientEmail );
            prepStatement.setString( 3, subject );
            prepStatement.setString( 4, body );

            //get current date the message was created on
            prepStatement.setTimestamp( 5, new Timestamp( System.currentTimeMillis() ) );

    	    prepStatement.executeUpdate();
        }
        catch( SQLException Ex )
        {
            System.out.println( "Error running the sample queries.  Machine Error: " + Ex.toString() );
            return false;
        }

        finally
        {
            try
            {
                if ( statement != null ) statement.close();
                if ( prepStatement != null ) prepStatement.close();
            }

            catch ( SQLException e )
            {
                System.out.println( "Cannot close Statement. Machine error: "+e.toString() );
                return false;
            }
        }

        return true;
    }

//INSERT INTO message VALUES( 'wandacrawford27@gmail.com', 'ranitaverde999@hotmail.com', 'Merry Christmas', 'Merry Christmas 2014', TIMESTAMP '2014-12-25 09:30:30' );

    public boolean displayMessages()
    {
        System.out.println( "Enter the user's email: " );
        String email = input.nextLine();

        try
        {
            query = "SELECT sender_email, subject, text_body, date_sent " +
                    "FROM message " +
                    "WHERE sender_email = ? " +
                    "ORDER BY date_sent";

            prepStatement = connection.prepareStatement( query );
            prepStatement.setString( 1, email );
            resultSet = prepStatement.executeQuery();

            while( resultSet.next() )
            {
                String senderEmail = resultSet.getString( 1 );
                String subject = resultSet.getString( 2 );
                String body = resultSet.getString( 3 );
                java.sql.Date dateSent = resultSet.getDate( 4 );

                System.out.println( subject + " from " + senderEmail + " on " + dateSent.toString() + ":\n" + body );
            }

            System.out.println();
        }

        catch( SQLException Ex )
        {
            System.out.println( "Error running the sample queries.  Machine Error: " + Ex.toString() );
            return false;
        }

        finally
        {
            try
            {
                if ( statement != null ) statement.close();
                if ( prepStatement != null ) prepStatement.close();
            }

            catch ( SQLException e )
            {
                System.out.println( "Cannot close Statement. Machine error: "+e.toString() );
                return false;
            }
        }

        return true;
    }

    public boolean searchForUser()
    {
        return false;
    }

    public boolean threeDegrees()
    {
        return false;
    }

    public boolean topMessagers()
    {
        return false;
    }

    public boolean dropUser()
    {
        
        System.out.println("What is user's emails address to drop");
        Scanner scan = new Scanner(System.in);
        String email = scan.nextLine.trim();




        return false;
    }

    public static void main( String args[] )
    {
        Connection connection;
        try
        {
            DriverManager.registerDriver( new oracle.jdbc.driver.OracleDriver() );
            String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
            //connection = DriverManager.getConnection( url, "ejp37", "4007533" );
            connection = DriverManager.getConnection( url, "ejp37", "4007533" );
            Driver driver = new Driver( connection );
            driver.run();
        }

        catch( Exception e )
        {
            System.out.println( "Error connecting to database" );
            System.exit( 0 );
        }
    }

}
