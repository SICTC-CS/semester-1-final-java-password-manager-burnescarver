import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
public class JavaPasswordManager {
    public static void main(String[] args){
        //Creating import objects
        Scanner ui = new Scanner(System.in);
        Random rand = new Random();
        //Creating ArrayLists
        ArrayList<String> usernameList = new ArrayList<String>();
        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<String> passwordList = new ArrayList<String>();
        ArrayList<String> categoryList = new ArrayList<String>();
        ArrayList<String> hintList = new ArrayList<String>();
        boolean userChoiceRepeat = true;
        while(userChoiceRepeat){
            System.out.print("Are you creating an account (1), log into an already existing account (2), update an already existing account (3), delete an already existing account (4),  please make sure to put in the number corresponding to your choice: ");
            int choiceAction = ui.nextInt();
            if(choiceAction == 1){
                userChoiceRepeat = false;
                createUser(nameList, usernameList, passwordList, categoryList, hintList);
            }
            else if(choiceAction == 2){
                userChoiceRepeat = false;
                loginUser(nameList, usernameList, passwordList, categoryList, hintList);
            }
            else if(choiceAction == 4){
                userChoiceRepeat = false;
                updateUser(nameList, usernameList, passwordList, categoryList, hintList);
            }
            else if(choiceAction == 4){
                userChoiceRepeat = false;
                deleteUser(nameList, usernameList, passwordList, categoryList, hintList);
            }
            else{
                System.out.print("I don't think thats a given action, please try again");
            }
        }
    }
    public static void createUser(ArrayList<String> nameList, ArrayList<String> usernameList, ArrayList<String> passwordList, ArrayList<String> categoryList, ArrayList<String> hintList){
        //Creating import objects
        Scanner ui = new Scanner(System.in);
        //repeat variables
        boolean userCreateInfoRepeat = true;
        boolean userCreateHintRepeat = true;
        //hint variables
        int hintChoice = 0;
        String hintS = "";
        boolean createUserCheck = true;
        //info variables
        String nameC;
        String usernameC;
        String passwordC;
        String categoryC;
        //Asking for password hint system
        while(userCreateHintRepeat){
            System.out.print("Please pick a number for your hint on your password: \n\t 1. Your mothers maiden name \n\t 2. Your first pets name \n\t 3. What highschool you went to");
            hintChoice = ui.nextInt();
            if(hintChoice == 1){
                hintS = "Your password is your mothers maiden name";
                userCreateHintRepeat = false;
            }
            else if(hintChoice == 2){
                hintS = "Your password is your first pets name";
                userCreateHintRepeat = false;
            }
            else if(hintChoice == 3){
                hintS = "Your password is the name of the highschool you went to";
                userCreateHintRepeat = false;
            }
            else{
                System.out.println("That is not one of the presented options, you may have not put one of the numbers, try putting the associated numbers of the picked option");
            }
        }
        while(userCreateInfoRepeat){
            //Asking for name
            System.out.print("What is your name: ");
            nameC = ui.nextLine();
            //Asking for username
            System.out.print("What is your username: ");
            usernameC = ui.nextLine();
            //Asking for password
            System.out.print("What will your password be: ");
            passwordC = ui.nextLine();
            //Asking for password
            System.out.print("What will your category be: ");
            categoryC = ui.nextLine();
            //checking for password requirements
            //got help from Stack Overflow (https://stackoverflow.com/questions/40336374/how-do-i-check-if-a-java-string-contains-at-least-one-capital-letter-lowercase)
            char ch;
            boolean capitalFlag = false;
            boolean specialCaseFlag = false;
            boolean numberFlag = false;
            boolean lengthFlag = false;
            for(int i=0;i < passwordC.length();i++) {
                ch = passwordC.charAt(i);
                if(Character.isDigit(ch)) {
                    numberFlag = true;
                }
                else if (Character.isUpperCase(ch)) {
                    capitalFlag = true;
                } else if (Character.isWhitespace(ch)) {
                    //Got the checker above from (https://www.geeksforgeeks.org/character-iswhitespace-method-in-java-with-examples/)
                    specialCaseFlag = true;
                } else if (passwordC.length() >= 8){
                    lengthFlag = true;
                }
                if(numberFlag && capitalFlag && specialCaseFlag && lengthFlag){
                    //end while loop
                    userCreateInfoRepeat = false;
                }
            }
        }
        //adding name to nameList, username to usernameList, password to passwordList, category to categoryList, and hintS to hintList
        nameList.add(nameC);
        usernameList.add(usernameC);
        passwordList.add(passwordC);
        categoryList.add(categoryC);
        hintList.add(hintS);
    }

    public static void loginUser(ArrayList<String> nameList, ArrayList<String> usernameList, ArrayList<String> passwordList, ArrayList<String> categoryList, ArrayList<String> hintList){
        //Creating import objects
        Scanner ui = new Scanner(System.in);
        //Creating variables
        int attemptCount = 0;
        int attemptLeft;
        int usernameIndex;
        String username = "";
        String password = "";
        String usernameQ;
        String passwordQ;
        boolean usernameCheck = false;
        boolean passwordCheck = false;
        while(attemptCount != 3){
            //Asking for username
            System.out.print("What is your username: ");
            usernameQ = ui.nextLine();

            //If the usernameQ is in usernameList set usernameIndex to the index of usernameQ
            if(usernameList.contains(usernameQ)){
                usernameIndex = usernameList.indexOf(usernameQ);
                if(usernameQ == usernameList.get(usernameIndex)){
                    usernameCheck = true;
                    //Asking for password
                    System.out.print("What is your password ("+hintList.get(usernameIndex)+"): ");
                    passwordQ = ui.nextLine();
                    attemptLeft = 3 - attemptCount;
                    if(passwordList.contains(passwordQ)){
                        if(passwordQ == passwordList.get(usernameIndex)){
                            passwordCheck = true;
                            attemptCount += attemptLeft;
                        }
                        else{
                            attemptCount += 1;
                        }
                    }
                    else{
                        attemptCount += 1;
                    }
                }
                else{
                    attemptCount += 1;
                }
            }
            else{
                attemptCount += 1;
            }
        }
        ui.close();
        if(passwordCheck && usernameCheck){
            System.out.print("Congragulations, you successfully logged in: ");
        }
        else{
            //if either passwordCheck or username are false close the program
            System.out.print("Unfortunately that is the wrong answer, program shutting down: ");
            System.exit(0);
        }
    }
    public static void updateUser(ArrayList<String> nameList, ArrayList<String> usernameList, ArrayList<String> passwordList, ArrayList<String> categoryList, ArrayList<String> hintList){
        System.out.print("What is the name of the account of the ");
    }
    public static void deleteUser(ArrayList<String> nameList, ArrayList<String> usernameList, ArrayList<String> passwordList, ArrayList<String> categoryList, ArrayList<String> hintList){
        Scanner ui = new Scanner(System.in);
        //Creating variables
        int nameIndex;
        boolean deleteNameCheck = true;
        System.out.print("What is the name of the account of the account you want to delete: ");
        String deleteNameQ = ui.nextLine();
        nameIndex = nameList.indexOf(deleteNameQ);
        if(nameList.contains(deleteNameQ)){
            if(deleteNameQ == nameList.get(nameIndex)){
                deleteNameCheck = true;
            }
            else{
                deleteNameCheck = false;
            }
        }
        else{
            deleteNameCheck = false;
        }
        boolean deleteUsernameCheck = true;
        System.out.print("What is the username of the account of the account you want to delete: ");
        String deleteUsernameQ = ui.nextLine();
        if(usernameList.contains(deleteUsernameQ)){
            nameIndex = nameList.indexOf(deleteNameQ);
            if(deleteUsernameQ == usernameList.get(nameIndex)){
                deleteUsernameCheck = true;
            }
            else{
               deleteUsernameCheck = false;
            }
        }
        else{
            deleteUsernameCheck = false;
        }
        boolean deletePasswordCheck = true;
        System.out.print("What is the password of the account of the account you want to delete: ");
        String deletePasswordQ = ui.nextLine();
        if(passwordList.contains(deletePasswordQ)){
            nameIndex = nameList.indexOf(deleteNameQ);
            if(deletePasswordQ == passwordList.get(nameIndex)){
                deletePasswordCheck = true;
            }
            else{
                deletePasswordCheck = false;
            }
        }
        else{
            deletePasswordCheck = false;
        }
        if(deleteNameCheck && deleteUsernameCheck && deletePasswordCheck){
            System.out.print("Alright, that checks out, deleting that account now");
            //info from (https://www.geeksforgeeks.org/remove-element-arraylist-java/)
            nameList.remove(nameIndex);
            usernameList.remove(nameIndex);
            passwordList.remove(nameIndex);
            categoryList.remove(nameIndex);
        }
    }
    public static void genPassword(ArrayList<String> nameList, ArrayList<String> usernameList, ArrayList<String> passwordList, ArrayList<String> categoryList, ArrayList<String> hintList){
        //Creating import objects
        Scanner ui = new Scanner(System.in);
        Random rand = new Random();
        System.out.print("What is your name (not username): ");
        String givenName = ui.nextLine();
        int nameIndex;
        String namePlaceholder;
        String usernamePlaceholder;
        String categoryPlaceholder;
        String nameSubS;
        String usernameSubS;
        String categorySubS;
        String finalPasswordGen;
        int randomNum = rand.nextInt(10);
        int genRepeat = 1;
        while(genRepeat != 2){
            if(nameList.contains(givenName) == true){
                System.out.print("Alright, I found it, one password coming right up");
                //Substring help from (https://www.geeksforgeeks.org/substring-in-java/)
                //indexOf help from (https://www.geeksforgeeks.org/java-util-arraylist-indexof-java/?ref=oin_asr2)
                //index of given name is set to nameIndex
                nameIndex = nameList.indexOf(givenName);
                //name substring
                namePlaceholder = nameList.get(nameIndex);
                nameSubS = namePlaceholder.substring(0,3);
                //username substring
                usernamePlaceholder = usernameList.get(nameIndex);
                usernameSubS = usernamePlaceholder.substring(0,3);
                //category substring
                categoryPlaceholder = categoryList.get(nameIndex);
                categorySubS = categoryPlaceholder.substring(0,2);
                //Combining them into one password
                finalPasswordGen = nameSubS+usernameSubS+categorySubS+randomNum;
                System.out.print("Alright one idea for a password based off your info is "+finalPasswordGen+"!");
            }
            else{
                System.out.print("Sorry, I don't have that name in my records, maybe check your spelling and captialization and try again.");
            }
        }
        System.out.print("");
        ui.close();
    }
    public static void closeProgram(ArrayList<String> nameList, ArrayList<String> usernameList, ArrayList<String> passwordList, ArrayList<String> categoryList, ArrayList<String> hintList){
        System.out.print("You failed, now shutting down");
        System.exit(0);
    }
}
