package edu.lehigh.cse216.teamname.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;

import com.sendgrid.*;

/**
 * App is our basic admin app. For now, it is a demonstration of the six key
 * operations on a database: connect, insert, update, query, delete, disconnect
 */
public class App {

    /**
     * Print the menu for our program
     */
    static void menu() {
        System.out.println("Main Menu");
        System.out.println("  [T] Create tblData");
        System.out.println("  [D] Drop tblData");
        System.out.println("  [1] Query for a specific row");
        System.out.println("  [*] Query for all rows");
        System.out.println("  [-] Delete a row");
        System.out.println("  [+] Insert a new row");
        System.out.println("  [~] Update a row");
        System.out.println("  [q] Quit Program");
        System.out.println("  [?] Help (this message)");
        System.out.println("  [L] Increment likes for a specific row");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char prompt(BufferedReader in) {
        // The valid actions:
        String actions = "VTD1*-+~q?L";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char promptTables(BufferedReader in) {
        // The valid actions:
        String actions = "MUCLDq";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char promptViews(BufferedReader in) {
        // The valid actions:
        String actions = "LDldq";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a String message
     * 
     * @param in      A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The string that the user provided. May be "".
     */
    static String getString(BufferedReader in, String message) {
        String s;
        try {
            System.out.print(message + " :> ");
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * Ask the user to enter an integer
     * 
     * @param in      A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The integer that the user provided. On error, it will be -1
     */
    static int getInt(BufferedReader in, String message) {
        int i = -1;
        try {
            System.out.print(message + " :> ");
            i = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
         * NumerFormatException is already handled by parseInt() catch
         * (NumberFormatException e) { e.printStackTrace(); }
         */
        return i;
    }

    /**
     * Method to send a email with email address
     */
    static boolean sendEmail(String from_email, String to_email, String password) {
        Email from = new Email(from_email);
        String subject = "[Buzz] Welcome to Buzz!";
        Email to = new Email(to_email);
        Content content = new Content("text/plain", "Your Buzz password is: " + password
                + "\n\nTo reset your password, please login with your email address at: "
                + "https://arcane-refuge-67249.herokuapp.com" + " and reset your password in the profile page.");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * function to generate a random string of length n
     */
    static String randomPassword(int n) {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz"
                + ",./;-=+!@#$%^&*";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    /**
     * The main routine runs a loop that gets a request from the user and processes
     * it
     * 
     * @param argv Command-line options. Ignored by this program.
     */
    public static void main(String[] argv) {
        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();
        String db_url = env.get("DATABASE_URL");

        // Get a fully-configured connection to the database, or exit
        // immediately
        Database db = Database.getDatabase(db_url);
        if (db == null)
            return;

        // Start our basic command-line interpreter:
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Get the user's request, and do it
            //
            // NB: for better testability, each action should be a separate
            // function call
            char action = prompt(in);
            if (action == '?') {
                menu();
            } else if (action == 'q') {
                break;
            } else if (action == 'V') {
                action = promptViews(in);
                if (action == 'L') {
                    db.createViewForLike();
                } else if (action == 'D') {
                    db.createViewForDislike();
                } else if (action == 'l') {
                    db.dropViewForLike();
                } else if (action == 'd') {
                    db.dropViewForDislike();
                } else if (action == 'q') {
                    break;
                }
            } else if (action == 'T') {
                action = promptTables(in);
                if (action == 'M') {
                    db.createData();
                } else if (action == 'U') {
                    db.createUser();
                } else if (action == 'C') {
                    db.createComment();
                } else if (action == 'L') {
                    db.createLike();
                } else if (action == 'D') {
                    db.createDislike();
                } else if (action == 'q') {
                    break;
                }
            } else if (action == 'D') {
                action = promptTables(in);
                if (action == 'M') {
                    db.dropData();
                } else if (action == 'U') {
                    db.dropUser();
                } else if (action == 'C') {
                    db.dropComment();
                } else if (action == 'L') {
                    db.dropLike();
                } else if (action == 'D') {
                    db.dropDislike();
                } else if (action == 'q') {
                    break;
                }
            } else if (action == '1') {
                int id = getInt(in, "Enter the row ID");
                if (id == -1)
                    continue;
                Database.RowData res = db.selectOneFromData(id);
                if (res != null) {
                    System.out.println("  [" + res.mId + "] " + res.mSubject);
                    System.out.println("  --> " + res.mMessage);
                    System.out.println("  --> " + res.mlikes);
                    System.out.println("  --> " + res.mDate);
                }
            } else if (action == '*') {
                ArrayList<Database.RowData> res = db.selectAllFromData();
                if (res == null)
                    continue;
                System.out.println("  Current Database Contents");
                System.out.println("  -------------------------");
                for (Database.RowData rd : res) {
                    System.out.println("  [" + rd.mId + "] " + rd.mSubject);
                }
            } else if (action == '-') {
                action = promptTables(in);
                if (action == 'M') {
                    int id = getInt(in, "Enter the row ID");
                    if (id == -1)
                        continue;
                    int res = db.deleteRowFromData(id);
                    if (res == -1)
                        continue;
                    System.out.println("  " + res + " rows deleted");
                } else if (action == 'U') {
                    int id = getInt(in, "Enter the user ID");
                    if (id == -1)
                        continue;
                    int res = db.deleteRowFromUser(id);
                    if (res == -1)
                        continue;
                    System.out.println("  " + res + " rows deleted");
                } else if (action == 'C') {
                    db.dropComment();
                } else if (action == 'L') {
                    db.dropLike();
                } else if (action == 'D') {
                    db.dropDislike();
                } else if (action == 'q') {
                    break;
                }
            } else if (action == '+') {
                action = promptTables(in);
                if (action == 'M') {
                    String subject = getString(in, "Enter the subject");
                    String message = getString(in, "Enter the message");
                    int uid = getInt(in, "Enter the uid");
                    if (subject.equals("") || message.equals("") || uid <= 0)
                        continue;
                    int res = db.insertRowToData(uid, subject, message);
                    System.out.println(res + " rows added");
                } else if (action == 'U') {
                    String email = getString(in, "Enter the email");
                    if (email.equals("")) {
                        continue;
                    }
                    String password = randomPassword(8);

                    int res = db.insertRowToUser(email, password);
                    System.out.println(sendEmail("admin@buzz.com", email, password));
                    System.out.println(res + " rows added");
                } else if (action == 'C') {
                    db.dropComment();
                } else if (action == 'L') {
                    db.dropLike();
                } else if (action == 'D') {
                    db.dropDislike();
                } else if (action == 'q') {
                    break;
                }
            } else if (action == '~') {
                int id = getInt(in, "Enter the row ID");
                if (id == -1)
                    continue;
                String newMessage = getString(in, "Enter the new message");
                int res = db.updateOneInData(id, newMessage);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows updated");
            } else if (action == 'L') {
                int id = getInt(in, "Enter the row ID");
                if (id == -1)
                    continue;
                Database.RowData res = db.incrementLikes(id, id);
                if (res != null) {
                    System.out.println("  [" + res.mId + "] " + res.mSubject);
                    System.out.println("  --> " + res.mMessage);
                    System.out.println("  --> " + res.mlikes);
                    System.out.println("  --> " + res.mDate);
                }
            }
        }
        // Always remember to disconnect from the database when the program
        // exits
        db.disconnect();
    }
}