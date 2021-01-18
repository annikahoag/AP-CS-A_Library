import java.util.ArrayList;

public class Book {
	
	//instance variables 
	String title, author;
	String date, name;
	int copiesIn, copiesOut;
	ArrayList<Integer> monthList = new ArrayList<Integer>();
	ArrayList<Integer> dayList = new ArrayList<Integer>();
	ArrayList<Integer> yearList = new ArrayList<Integer>();
	ArrayList<String> nameList = new ArrayList<String>();
	
	//constructor
	public Book(String titl, String auth, int cop) {
		title = titl;
		author = auth;
		copiesIn = cop;
		copiesOut = 0;
		date = "";
		name = "";
	}
	
	//set inputed title
	public void setTitle(String titleTemp) {
		title = titleTemp;
	}
	
	//set inputed author
	public void setAuthor(String authTemp) {
		author = authTemp;
	}
	
	//EXTRA CREDIT- add to copies in library 
	public void setCopies() {
		copiesIn++;
	}
	
	
	//return title
	public String getTitle() {
		return title;
	}
	
	//return author
	public String getAuthor() {
		return author;
	}
	
	//return copies in
	public int getCopies() {
		return copiesIn;
	}
	
	//return copies out
	public int getCopiesOut() {
		return copiesOut;
	}
	
	
	//return sum of copies
	public int getTotalCopies() {
		return copiesIn+copiesOut;
	}
	
	
	//adds month to month arrayList
	public void setMonth(int m) {
		int month=m;
		monthList.add(month);
		
	}
	

	//adds day to day arrayList
	public void setDay (int d) {
		dayList.add(d);
	}
	
	
	//adds day to year arrayList
	public void setYear (int y) {
		yearList.add(y);
	}
	
	
	//adds names to name arrayList
	public void setNames (String n) {
		nameList.add(n);
	}
	
	
	//EXTRA CREDIT- remove/add copies when books are loaned out
	public void subCopiesLoan() {
		copiesIn--;
		copiesOut++;
	}
	
	//EXTRA CREDIT- remove/add copies when books are returned
	public void subCopiesReturn() {
		copiesIn++;
		copiesOut--;
	}
	
	
	//returns dayList
	public int getDay(int i) {
		return dayList.get(i);
	}
	
	//sets borrower name to null
	public void nameNull(int i) {
		nameList.set(i, null);
	}
	
	//sets month, day, and year to null
	public void dateNull(int i) {
		monthList.set(i, 0);
		dayList.set(i, 0);
		yearList.set(i, 0);
	}
	
	
	//return index of specified borrower name 
	public int getNameIndex (String name) {
		String nameTemp;
		int index=-1;

		for (int i=0; i<nameList.size(); i++) {
			nameTemp = nameList.get(i);
			if (nameTemp.equals(name)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	
	//loops through nameList, prints borrower name and date
	//EXTRA CREDIT: print number of copies left 
	public void loopNames () {
		String dateString;
		String dayS, monthS, yearS;
		String copyString="";
		
		for (int i=0; i<nameList.size(); i++) {
			if (nameList.get(i) != null) {
				dayS = String.valueOf(dayList.get(i));
				monthS = String.valueOf(monthList.get(i));
				yearS = String.valueOf(yearList.get(i));
				
				dateString = monthS + '/' + dayS + '/' + yearS;
				copyString = String.valueOf(copiesIn);
				
				System.out.printf("%-25s%s", "   Borrower: " + nameList.get(i), 
					"Date: "  + dateString);
				System.out.println();
				
			}
		}
		System.out.println("   Copies Left: " + copyString);
		System.out.println();
		
	}
	
	
	//loop through days 
	public void loopDates(int m, int d, int y, int dueDate) {
		int timeOut;
		boolean isLast;
		String sTimeOut;
		
		System.out.println();
		
		for (int i=0; i<dayList.size(); i++) {
			if (nameList.get(i)!=null) {
				if (i==nameList.size()-1) {
					isLast=true;	
				}else {
					isLast=false;
				}
				
				timeOut = this.daysOut(m, d, y, monthList.get(i), dayList.get(i), 
						yearList.get(i), isLast);
				
				if (timeOut > dueDate+14) {
					sTimeOut = String.valueOf(timeOut);
					System.out.printf("%-25s%-25s%-25s%s", "Title: " + title, "Author: " + author, 
							"Borrower: " + nameList.get(i), "Days Out: " + sTimeOut);	
					System.out.println();
				}
			}
			
		}
		System.out.println();
		
	}
	
	
	//figure out how long book has been out 
	public int daysOut(int monthCurrent, int dayCurrent, int yearCurrent, 
			int monthChecked, int dayChecked, int yearChecked, boolean isLast) {
		
		int daysOut=-1, yearsOut=0;
		boolean isNeg;
		
		//if months and years are the same 
		if (monthChecked==monthCurrent && yearChecked==yearCurrent) {
			daysOut = dayCurrent-dayChecked;
			isNeg = this.isNeg(daysOut);
			
			if (isNeg) {
				if (isLast) {
					System.out.println("Day entered is before " + title + " was checked out.");
				}
			}else {
				return daysOut;
			}
		}
		
		//if months are the same but years are different
		if (monthChecked==monthCurrent && yearChecked != yearCurrent) {
			yearsOut = yearCurrent-yearChecked;
			isNeg = this.isNeg(yearsOut);
			
			if (isNeg) {
				if (isLast) {
					System.out.println("Year entered is before " + title + " was checked out.");
				}
			}else {
				daysOut = dayCurrent-dayChecked + (365*yearsOut);
				isNeg = this.isNeg(daysOut);
				if (isNeg) {
					if (isLast) {
						System.out.println("Day entered is before " + title + " was checked out.");
					}
				}else {
					return daysOut;
				}
			}//end of else
		}
		
		//if months are different but years are the same 
		if (monthChecked != monthCurrent && yearChecked==yearCurrent) {
			int nextMonth;
			int lastDay, lastDayNext;
			
			nextMonth = this.getNextMonth(monthChecked);
			
			lastDay = this.getLastDay(monthChecked, yearChecked);
			daysOut = lastDay - dayChecked;
			while (nextMonth != monthCurrent) {
				
				lastDayNext = this.getLastDay(nextMonth, yearChecked);
				daysOut = daysOut + lastDayNext;
				
				nextMonth ++;
			}
			
			daysOut = daysOut + dayCurrent;
			isNeg = this.isNeg(daysOut);
			
			if (isNeg) {
				if (isLast) {
					System.out.println("Day entered is before " + title + " was checked out.");
				}
			}else {
				return daysOut;
			}
		}
		
		//if months and years are different
		if (monthChecked != monthCurrent && yearChecked != yearCurrent) {
			int nextMonth;
			int lastDay, lastDayNext;
			
			yearsOut = yearCurrent-yearChecked;
			isNeg = this.isNeg(yearsOut);
			
			if (isNeg) {
				if (isLast) {
					System.out.println("Year entered is before " +  title + " was checked out.");
				}
			}else {
				if (yearsOut==1) {
					nextMonth = this.getNextMonth(monthChecked);
					lastDay = this.getLastDay(monthChecked, yearChecked);
					daysOut = lastDay - dayChecked;
					
					if (nextMonth != monthCurrent) {
						while (nextMonth != monthCurrent) {
							lastDayNext = this.getLastDay(nextMonth, yearChecked);
							daysOut = daysOut + lastDayNext;
							nextMonth = this.getNextMonth(nextMonth);
						}
						
						daysOut = daysOut + dayCurrent;
						isNeg = this.isNeg(daysOut);
					}else {
						daysOut = daysOut + dayCurrent + 365;
						isNeg = this.isNeg(daysOut);
					}
					
					if (isNeg) {
						if (isLast) {
							System.out.println("Day entered is before " + title + " was checked out.");
						}
					}else {
						return daysOut;
					}
					

				
				}else{
					nextMonth = this.getNextMonth(monthChecked);
				
					lastDay = this.getLastDay(monthChecked, yearChecked);
					daysOut = lastDay - dayChecked;
					
					while (nextMonth != monthCurrent) {
						
						lastDayNext = this.getLastDay(nextMonth, yearChecked);
						daysOut = daysOut + lastDayNext;
						
						nextMonth = this.getNextMonth(nextMonth);;
					}
					
					daysOut = (daysOut + dayCurrent) + (365*yearChecked);
					isNeg = this.isNeg(daysOut);
					
					if (isNeg) {
						if (isLast) {
							System.out.println("Day entered is before " + title + " was checked out.");
						}
					}else {
						return daysOut;
					}
					
				}//end of else
			}//end of big isNeg else
		}
		
		
		return daysOut;
		
	}
	
	
	//error check for negative days
	public boolean isNeg(int time) {
		if (time<0) {
			return true;
		}else {
			return false;
		}
	}
	
	
	//figures out next month
	public int getNextMonth(int m) {
		int nextMonth=m+1;
		
		if (nextMonth == 13) {
			nextMonth=1;
		}
		
		return nextMonth;
	}
	
	
	//figures out last day of the given month
	public int getLastDay(int m, int y) {
		boolean isLeap;
		
		if (m==4 || m==6 || m==9 || m==11) {
			return 30;
		}else if (m==2) {
			isLeap = this.isLeapYear(y);
			if (isLeap) {
				return 29;
			}else {
				return 28;
			}
		}else {
			return 31;
		}
	}
	
	//figures out if it's a leap year
	public boolean isLeapYear(int year) {
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

	
	//subtract from copies in, when permanently removing a book
	public void subCopiesIn() {
		copiesIn--;
	}
	


}
