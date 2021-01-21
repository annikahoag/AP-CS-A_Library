import java.util.Scanner;


public class Main {
	//EXTRA CREDIT- allows multiple copies of a book, keep track of the number of copies
	//loaned, returned etc. (including name and date)
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		Library lib = new Library();
		boolean runAgain = true;
		int userInput;
		String titleInput;

			
		System.out.println("Welcome to the Library");
		
		try {
			while (runAgain) {
				titleInput = "";
	
				System.out.println("\nWhat would you like to do? Please type: "
						+ "\n1 if you would like to add books to the database. "
						+ "\n2 if you would like to see all books currently in the library. "
						+ "\n3 if you would like to search for a book by title. "
						+ "\n4 if you would like to loan out a book. "
						+ "\n5 if you would like to return a book. "
						+ "\n6 if you would like to see all borrowed books. "
						+ "\n7 if you would like to see all books that are over 2 weeks late. "
						+ "\n8 if you would like to see the total number of books."
						+ "\n9 if you would like to permanently remove a book from the library."
						+ "\n10 if you would like to end the program.");
				userInput = scn.nextInt();
				

					switch (userInput) {
						//add books
						case 1: userInput = 1;
							lib.addBook();
							runAgain = true;
							break;
							
						//print all books	
						case 2: userInput = 2;
							lib.printAll();
							runAgain = true;
							break;
						
						//search by title
						case 3: userInput = 3;
							scn = new Scanner(System.in);
							System.out.println("Please enter the title of the book.");
							titleInput = scn.nextLine();
							lib.searchBook(titleInput);
							runAgain=true;
							break;
						
						//loan book out
						case 4: userInput = 4;
							scn = new Scanner(System.in);
							System.out.println("Please enter the title of the book you want to loan out.");
							titleInput = scn.nextLine();
							lib.loanBook(titleInput);
							runAgain = true;
							break;
						
						//return book
						case 5: userInput = 5;
							String nameInput;
							scn = new Scanner(System.in);
							
							System.out.println("Please enter the title of the book you want to return.");
							titleInput = scn.nextLine();
							System.out.println("Please enter the name of the borrower.");
							nameInput = scn.nextLine();
							
							lib.returnBook(titleInput, nameInput);
							runAgain = true;
							break;
							
						//print borrowed books
						case 6: userInput = 6;
							lib.printBorrowed();
							runAgain = true;
							break;
						
						//print books over 2 wks late
						case 7: userInput = 7;
							String currentDate;
							scn = new Scanner (System.in);
							
							System.out.println("Please enter the current date. "
									+ "\nPlease enter the date in this format: MM/DD/YYYY.");
							currentDate = scn.nextLine();
							
							lib.twoWeeksLate(currentDate);
							runAgain = true;
							break;
							
						//print total number of books
						case 8: userInput = 8;
							lib.printTotalNumber();
							runAgain = true;
							break;
						
						//permanently remove 
						case 9: userInput = 9;
							scn = new Scanner (System.in);
							System.out.println("Please enter the book you want to permamently remove.");
							titleInput = scn.nextLine();
							lib.permRemove(titleInput);
							runAgain = true;
							break;
							
						//end program
						case 10: userInput = 10;
							System.out.println("Program is ending.");
							runAgain = false;
							break;
						
						
						default: 
							System.out.println("Invalid input. Program is restarting.");
							runAgain = true;
							
					}//end of switch statement 
	
			
			}//end of while 
			
		//catch user entering anything other than numbers	
		}catch (java.util.InputMismatchException e) {
			System.out.println("Please only enter numbers.");
			System.out.println("Program is ending");
		}
		
		scn.close();
	}
}
