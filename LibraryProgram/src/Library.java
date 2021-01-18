import java.util.ArrayList;
import java.util.Scanner;

public class Library {
	
	//instance variables 
	ArrayList<Book> bookList = new ArrayList<Book>();
	Scanner scn = new Scanner (System.in);
	int copyIndex;
	int getCopiesInt;
	String getCopiesString;
	int month, day, year;
	int bookIndex=-1, listIndex;
	final static int dueDate = 21;
	
	
	//constructor
	public Library() {
		
	}
	
	//add books to arrayList
	public void addBook() {
		String titleLong, authorLong;
		String title, author;
		boolean isInSystem;
		
		System.out.println("Please enter the title of the book: ");
		titleLong = scn.nextLine();
		System.out.println("Please enter the author of the book: ");
		authorLong = scn.nextLine();
		
		title = titleLong.trim();
		author = authorLong.trim();
		
		isInSystem = this.inSystem(title, author);
		
		
		//EXTRA CREDIT- determine number of copies 
		if (!isInSystem) {
			bookList.add(new Book(title, author, 1));
		}else{
			
			bookList.get(copyIndex).setCopies();
		}
	}
	
	
	//print all books
	//EXTRA CREDIT- prints number of copies 
	public void printAll() {
		
		System.out.println();
		System.out.printf("%-47s%-30s%s", "Title", "Author", "Copies");
		System.out.println();
		
		for (int i = 0; i<bookList.size(); i++) {
			getCopiesInt = bookList.get(i).getCopies();
			getCopiesString = String.valueOf(getCopiesInt);
			
			System.out.println();
			System.out.printf("%-47s%-30s%s", bookList.get(i).getTitle(), 
					bookList.get(i).getAuthor(), getCopiesString);
		}
		System.out.println();
		System.out.println();
	}
	
	
	//search for a book
	public void searchBook(String titleEntered) {
		boolean inSystem=false;
		int index=0;
		String titleTemp;
		
		for (int i = 0; i<bookList.size(); i++) {
			
			titleTemp = bookList.get(i).getTitle();
			if (titleEntered.equals(titleTemp)) {
				inSystem=true;
				index=i;
				break;
			}
		}
		
		if (inSystem) {
			getCopiesInt = bookList.get(index).getCopies();
			getCopiesString = String.valueOf(getCopiesInt);
			System.out.printf("%-47s%-30s%s", bookList.get(index).getTitle(), 
					bookList.get(index).getAuthor(), "Copies: " + getCopiesString );
		}else {
			System.out.println("Book is not in the library system yet.");
		}
		System.out.println();
	}
	
	
	
	//EXTRA CREDIT- find out if book is in system to determine number of copies
	public boolean inSystem(String titleEntered, String authEntered) {
		boolean inSystem;
		copyIndex=0;
		String titleTemp, authTemp;
		
		for (int i = 0; i<bookList.size(); i++) {
			titleTemp = bookList.get(i).getTitle();
			authTemp = bookList.get(i).getAuthor();
			if (titleEntered.equals(titleTemp)==true && authEntered.equals(authTemp)==true) {
				inSystem = true;
				copyIndex = i;
				return inSystem;
			}
		}
		inSystem=false;
		return inSystem;
	}
	
	
	//loan out a book
	public void loanBook(String titleEnteredLong) {
		String titleTemp;
		String titleEntered = titleEnteredLong.trim();
		int copiesLeft;
		String dateLong, nameLong;
		String date, name;
		boolean isValid;
		
		for (int i = 0; i<bookList.size(); i++) {
			titleTemp = bookList.get(i).getTitle();
			
			if (titleEntered.equals(titleTemp)) {
				bookIndex=i;
				break;
			}
		}//end of for loop
		
		
		//error check for book not being in the system
		if(bookIndex!=-1) {
			copiesLeft = bookList.get(bookIndex).getCopies();
			
			//if there's enough copies take in date and borrower 
			//EXTRA CREDIT- take away copies when loaned 
			if (copiesLeft > 0) {
				System.out.println("Please enter the date the book is loaned out. "
						+ "\nPlease type your date in this format MM/DD/YYYY.");
				dateLong = scn.nextLine();
				date = dateLong.trim();
				isValid = this.parseDate(date);
				
				if (isValid) {
					System.out.println("Please enter the name of the borrower.");
					nameLong = scn.nextLine();
					name = nameLong.trim();
					this.setNameList(name);
					
					
					bookList.get(bookIndex).subCopiesLoan();
				}else {
					System.out.println("Error. Invalid Date.");
				}
				
			}else {
				System.out.println("Sorry. There are no copies left of this book.");
				System.out.println();
			}
			
		}else {
			System.out.println("Book is not in the sysetm.");
		}
	}
	
	
	//parses date string into month, day, and year
	//error check for negative day, and not following format when inputting
	public boolean parseDate(String date) {
		String sMonth, sDay, sYear;
		boolean isValid=false;
		
		try {
			sMonth = date.substring(0, 2);
			month = Integer.valueOf(sMonth);
			
			sDay = date.substring(3, 5);
			day = Integer.valueOf(sDay);
			
			sYear = date.substring(6, 10);
			year = Integer.valueOf(sYear);
			
			
			if (month<=0 || day<=0 || year<=0) {
				throw new MyException("Error. Please do not enter negative numbers.");
			}else {
				isValid = this.validDate(month, day, year);
				
				if (isValid) {
					bookList.get(bookIndex).setMonth(month);
					bookList.get(bookIndex).setDay(day);
					bookList.get(bookIndex).setYear(year);
					return isValid;
				}else {
					return isValid;
			
				}
			}
		
			
		}catch (MyException ex) {
			System.out.println(ex.getMessage());
			
		}catch (StringIndexOutOfBoundsException e) {
			System.out.println("You messed up entering the date in the right format.");
			isValid = false;
		}
		return isValid;
	}
	
	
	//check for valid date
	public boolean validDate (int m, int d, int y) {
		m=month;
		d=day;
		y=year;
		boolean isLeap;
		
		if (month==4 || month==6 || month==9 || month==11) {
			if (day>30) {
				return false;
			}
		}else if (month==1 || month==3 || month==5 || month==7 || month==8 
				|| month==10 || month==12) {
			if (day>31) {
				return false;
			}
		}else if (month==2) {
			isLeap = this.isLeapYear(year);
			if (isLeap) {
				if(day>29) {
					return false;
				}
			}else {
				if(day>28) {
					return false;
				}
			}
		}else if (month>12) {
			return false;
		}
		
		return true;
		
	}
	
	

	//check for leap year 
	//code from last year's date class
	public boolean isLeapYear (int y) {	
		y=year;
		
		if (year%4==0) {
			if (year%100 == 0) {
				if (year%400==0) {
					return true;
				}else {
					return false;
				}
			}else {
				return true;
			}
		}else {
			return false;
		}
		
	}
	
	
	//calls setNames method in book class, adds that name to name array list
	public void setNameList(String name) {
		bookList.get(bookIndex).setNames(name);
	}
	
	
	//returns book to library
	public void returnBook(String titleEnteredLong, String nameEnteredLong) {
		
		String titleTemp;
		String titleEntered = titleEnteredLong.trim();
		String nameEntered = nameEnteredLong.trim();
		int nameIndex;
		boolean loaned;
		bookIndex=-1;

		
		for (int i = 0; i<bookList.size(); i++) {
			titleTemp = bookList.get(i).getTitle();
			if (titleEntered.equals(titleTemp)) {
				bookIndex=i;
				break;
			}
		}//end of for loop

		
			if (bookIndex!=-1) {
				if (bookList.get(bookIndex).getCopiesOut() != 0) {
					
				 
					listIndex = (bookList.get(bookIndex).getCopiesOut())-1;
					
					loaned = this.isLoaned(listIndex);
					
					if (!loaned) {
						System.out.println("Error. Book isn't loaned out to anyone.");
					}else {
						nameIndex = bookList.get(bookIndex).getNameIndex(nameEntered);
						
						if (nameIndex != -1) {
							bookList.get(bookIndex).nameNull(nameIndex);
							bookList.get(bookIndex).dateNull(nameIndex);
							bookList.get(bookIndex).subCopiesReturn();
							
							System.out.println("Book has been returned.");
							System.out.println();
						}else {
							System.out.println("The borrower has not taken out this book.");
						}
					}
					
					
					
				}else {
					System.out.println("Book has not been loaned out yet.");
				}
			}else {
				System.out.println("Book is not in the system yet.");
			}	
	}
	
	
	//figures out if book is loaned or not 
	public boolean isLoaned(int index) {
		int totalCopies;
		int copiesLeft;
		
		if (bookList.get(bookIndex).getDay(listIndex)!=0) {
			copiesLeft = bookList.get(bookIndex).getCopies();
			totalCopies = bookList.get(bookIndex).getTotalCopies();
			if (copiesLeft<totalCopies) { 
				return true;
			}else {
				return false;
			}
		}else {
			return true;
		}
	}
	
	
	//print all borrowed books 
	public void printBorrowed() {

		
		for (int i=0; i<bookList.size(); i++) {

			if (bookList.get(i).getCopiesOut() != 0) {
				System.out.printf("%-30s%s", "Title: " + bookList.get(i).getTitle(),
						"Author: " + bookList.get(i).getAuthor());
				System.out.println();
				
				bookList.get(i).loopNames();
				
			}
		}	
	}
	
	
	
	public void twoWeeksLate(String currentDate) {
		boolean isValid;
		String date=currentDate;
		String sMonth, sDay, sYear;
		int iMonth, iDay, iYear;
		
		try {
			sMonth = date.substring(0, 2);
			iMonth = Integer.valueOf(sMonth);
			
			sDay = date.substring(3, 5);
			iDay = Integer.valueOf(sDay);
			
			sYear = date.substring(6, 10);
			iYear = Integer.valueOf(sYear);
			
			
			if (iMonth<=0 || iDay<=0 || iYear<=0) {
				throw new MyException("Error. Please do not enter negative numbers.");
			}else {
				isValid = this.validDate(iMonth, iDay, iYear);
				
				if (isValid) {
					
					for (int i=0; i<bookList.size(); i++) {
						bookList.get(i).loopDates(iMonth, iDay, iYear, dueDate);
					}
					
					
				}else {
					System.out.println("Invalid date.");
				}
			}//end of else
	
		}catch (MyException ex) {
			System.out.println(ex.getMessage());
		}
		

	}
	
	
	//print total number of books
	public void printTotalNumber() {
		int totalNumber=0;
		
		for (int i=0; i<bookList.size(); i++) {
			totalNumber = totalNumber + bookList.get(i).getTotalCopies();
		}
		
		System.out.println();
		System.out.println("There are " + totalNumber + " books in the library database.");
		System.out.println();
	}
	
	
	//permanently remove a book
	//EXTRA CREDIT- removes only one copy at a time
	public void permRemove(String titleEnteredLong) {
		String titleEntered, titleTemp;
		int copiesLeft;
		
		titleEntered = titleEnteredLong.trim();
		
		for (int i=0; i<bookList.size(); i++) {
			titleTemp = bookList.get(i).getTitle();
			if (titleTemp.equals(titleEntered)) {
				if (bookList.get(i).getCopies() > 1) {
					if (bookList.get(i).getCopies() > 0) {
						bookList.get(i).subCopiesIn();
					}
					System.out.println(titleEntered + " has been removed.");
					System.out.println("There are " + bookList.get(i).getCopies() + " copies left.");
				}else if (bookList.get(i).getCopies() == 1) {
					copiesLeft = bookList.get(i).getCopies()-1;
					bookList.remove(i);
					
					System.out.println(titleEntered + " has been removed.");
					System.out.println("There are " + copiesLeft + " copies left.");
				}else {
					System.out.println("All copies are checked out or the book is not in the system yet.");
				}
			}
		}//end of for 
		
	}
	
	
	

	

	
	
	
	
	
	
	
}//end of class
