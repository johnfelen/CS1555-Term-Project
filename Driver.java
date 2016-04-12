import java.io.*;
import java.util.*;

public class Driver
{
    Scanner input;
    public Driver()
    {
        System.out.println( "Welcome to the CS 1555 Term Project\nBrought to you by John Felen and Elie Perelman" );
        input = new Scanner( System.in );
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
                    createUser();
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

    public boolean createUser()
    {
        return false;
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
        Driver driver = new Driver();
        driver.run();
    }

}
