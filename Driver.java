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
            prepStatement.setString( 2, name );
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
        return false;
    }

    public boolean establishFriendship()
    {
        return false;
    }

    public boolean displayFriends()
    {
        return false;
    }

    public boolean createGroup()
    {
        return false;
    }

    public boolean addToGroup()
    {
        return false;
    }

    public boolean sendMessageToUser()
    {
        return false;
    }

    public boolean displayMessages()
    {
        return false;
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
        return false;
    }

    public static void main( String args[] )
    {
        Connection connection;
        try
        {
            DriverManager.registerDriver( new oracle.jdbc.driver.OracleDriver() );
            String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
            connection = DriverManager.getConnection( url, "jtf28", "3842858" );
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
