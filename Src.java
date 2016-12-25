//JAVA IS JAVA, REMEMBER.

package pl.RomDec;
import java.util.InputMismatchException;
import java.util.Scanner; 

public class RomDec {
	
	int roman_tab[] = {1000, 500, 100, 50, 10, 5, 1};
	String roman_tab1[] = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}; 
	String roman_tab10[] = {"X", "XX", "XXX", "LX", "L", "LX", "LXX", "LXXX", "XC"};
	String roman_tab100[] = {"C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
  
	/*
	 * No to tak, te 3 tablice wyzej przechowuja wszystkie mozliwe kombinacje znakowe odpowiednio dla rzedów: jednosci, dziesiatek i setek
	 * Za pomoca operacji matematycznych (np linia 33) przy dzieleniu przez 100 liczby np. 444 otrzymujemy 4. 
	 * po czym odwolujemy sie do odpowiedniego indekus z tablicy rzedu 100. tj. roman_tab100[4-1] co zwraca nam wartosc CD
	 * takie rozwiazanie zapewnia oszczednosc czasu i linijek kodu. analogicznie dla rzedu 10 i 1.
	 */
	
  static int arabic;
	static String roman;
	
	public String arabic (int arabic_number) {
		String roman = "";
		int count = 0;
		
		if (arabic_number < 1 || arabic_number > 3999) {
			System.out.println("Podano liczbę z niewłaściwego zakresu.");
		} else { //w innym wypadku
			if((arabic_number / 1000) > 0) {		  //a to dziala tak jak czynnosc opisana pod tablicami
				count = arabic_number / 1000;		    //count przyjmuje wynik dzielenia np. 3211/1000 = 3
				for (int i = 0; i < count; i++) {   //wiec dopisze MMM do stringa o nazwie roman.
					roman += "M";
					arabic_number -= 1000;			      //odejmie 1000 za kazdym razem kiedy dopisze M.
				}
			}
			if ((arabic_number / 100) > 0) {	                    //to zostalo opisane juz wyzej, caly czas dzialamy analogicznie
				roman += roman_tab100[(arabic_number/100)-1];				//  444/100 = 4
				arabic_number -= (arabic_number/100) * 100;         //to ciekawa linijka, np 444 - (444/100) * 100 = 444 - 400 = 44. 
			}
			if ((arabic_number / 10) > 0) {
				roman += roman_tab10[(arabic_number/10)-1];
				arabic_number -= (arabic_number/10) * 10;
			}
			if ((arabic_number / 1) > 0) {
				roman += roman_tab1[arabic_number-1];
			}
		}	
		return roman;
	}
	
	public int roman (String roman_number) { 
		int[] roman_to_int = new int[roman_number.length()];  // deklaracja tablicy typu int ktora przechowa wartosci typu int odpowiadajace odpowiednim litera notacji rzymskiej
		char[] roman_c = new char[roman_number.length()];     // tablica znakow ktora przechowa string.
		
		int arabic = 0, max_sideby=0;
		boolean is_roman = true, its_roman = true;
		
		roman_number = roman_number.toUpperCase();		  // zamiana na wielkie litery
		for (int i = 0; i<roman_number.length(); i++) 	// petla sie bedzie krecic tyle razy ile jest znakow (length() zwraca wartosc liczbowa dlugosci stringow, np ala = 3.
			roman_c[i] = roman_number.charAt(i);		      //przypisuje po kolei kazdy znak ze stringa do tablicy znakow.
		
		for (int i = 0; i < roman_number.length(); i++) {   //sprawdza czy wgl to jest zapis z rzymskich znakow, bierze kazdy znak z tablicy znakow roman_c i sprawdza czy jest ktoryms z rzymskich znakow, jesli nie to na tym skonczy)
			if (roman_c[i] != 'M' && roman_c[i] != 'D' && roman_c[i] != 'C' && roman_c[i] != 'L' && roman_c[i] != 'X' && roman_c[i] != 'V' && roman_c[i] != 'I')
				is_roman = false;
		}
		if (is_roman) {
			for (int i = 0; i < roman_number.length(); i++) {   // ta petla konwertuje znaki na liczby int i odpowiadajace im wartosci przypisuje do tablicy roman_to_int
				if (roman_c[i] == 'M')
					roman_to_int[i] = 1000;
				if (roman_c[i] == 'D')
					roman_to_int[i] = 500;
				if (roman_c[i] == 'C')
					roman_to_int[i] = 100;
				if (roman_c[i] == 'L')
					roman_to_int[i] = 50;
				if (roman_c[i] == 'X')
					roman_to_int[i] = 10;
				if (roman_c[i] == 'V')
					roman_to_int[i] = 5;
				if (roman_c[i] == 'I')
					roman_to_int[i] = 1;
			}
			int current_lowest = roman_to_int[0]; //tak wiec zaklada, ze najmniejsza aktualna zmienna to ta na 1 miejscu ciagu przekonwertowanego na int'owe
			if (roman_number.length() > 2)	// jezeli ciag sklada sie z wiecej niz 2 znakow..
				for (int i=2; i < roman_number.length(); i++) { // a ta petla sprawdza czy ktoras ze zmiennych jest wieksza od 1, zaczynajac od 3 (warunek konieczny poprawnego rzymskiego zapisu)
					if (roman_to_int[i]>current_lowest) 
						its_roman = false; // jak nie to nie jest rzymska
				}
			//tutaj dalej impreza
			for (int i = 0; i < roman_number.length(); i++) { //ta petla sprawdza czy... wiele rzeczy sprawdza, opisze nizej										
				if ((i+1) < roman_number.length()) {		// czy sa przynajmniej 2 znaki np. MM, ale bedzie dzialal na przekonwertowanej na inty czyli np. 1000 1000
					if (roman_c[i] == roman_c[i+1]) {		// jezeli 1 i 2 element tablicy sa takie same to zwiekszy max. dopuszczalna liczbe takich samych znakow przy sobie stojacych o 1. jak wiemy moga stac np. tylko 3. MMM
						max_sideby +=1;
						if (roman_c[i] == 'D' || roman_c[i] == 'L' || roman_c[i] == 'V') // jezeli sa 2 obok siebie w tablicy znakow takie same np MM, to jest ok, ale w rzymskim zapisie nie moga stac obok siebie D,L,V
							its_roman = false;
						if (max_sideby > 2) 			// a to sprawdza czy aby nie ma juz wiecej niz 3 takich samych przy sobie dla MM wartosc ta = 1 dla MMM = 2
							its_roman = false;			// jezeli tak to nie jest rzymska		
					} else max_sideby = 0;				// jezeli pojawi sie np. MMMCM to wyzeruje nam ta wartosc do 0 przez co sie nie popsuje przez kolejna M w kodzie
					
					if (roman_to_int[i] < roman_to_int[i+1]) { // jezeli 2 element jest wiekszy od 1. np IX
						if ((i+2) < roman_number.length()) {  // sprawdza czy wgl to jest ciag 3 elementowy
							if (roman_to_int[i] <= roman_to_int[i+2]) // sprawdzi odrazu czy 3 element jest wiekszy rowny, jezeli tak to nie jest rzymska, kolejny warunek konieczny
								its_roman = false; //no jezeeli nie jest to nie jest rzymska
						}
						if (roman_to_int[i+1] == 1000) // to sa warunki konieczne, jakby to opisac. w rzymskim zapisie, jezeli wystepuje liczba wieksza po mniejszej, to jest to prawda tylko w okreslonych przypadkach opisanych nizej
							if (roman_to_int[i] != 100) //np przed M moze stac tylko C, tak samo przed D
								its_roman = false;
						if (roman_to_int[i+1] == 500) //przed D moze stac C
							if (roman_to_int[i] != 100)
								its_roman = false;		// w kazdym innym wypadku to nie jest rzymska, analogicznie dla reszty przypadkow
						if (roman_to_int[i+1] == 100)
							if (roman_to_int[i] != 10)
								its_roman = false;
						if (roman_to_int[i+1] == 50)
							if (roman_to_int[i] != 10)
								its_roman = false;
						if (roman_to_int[i+1] == 10)
							if (roman_to_int[i] != 1)
								its_roman = false;
						if (roman_to_int[i+1] == 5)
							if (roman_to_int[i] != 1)
								its_roman = false;
					}
				}
			}
		if (its_roman) // jezeli wszystko powyrzsze jest prawda to znaczy ze wartosc zostala prawdziwa.
			for (int i=0; i < roman_number.length(); i++) {
				if ((i+1) < roman_number.length()) { //algorytm przeliczanie romanow na arabskie, prosty, nie tlumacze.
					if (roman_to_int[i] >= roman_to_int[i+1]) // jezeli kolejna liczba jest wieksza od poprzedniej to po prostu dodaje odpowiednik liczby rzymskiej do zwracanej wartosci.
						arabic += roman_to_int[i]; // zwiekszamy aktualna wartosc o kolejna wartosci przekonwertowanego na inty stringa. tzn. tab[i] gdzie i=1 to wtedy 2 element tablicy dopiszemy do sumy arabic
					else {
						arabic += roman_to_int[i+1] - roman_to_int[i]; // w innym wypadku okazuje sie ze mamy zapis typu np CM wiec dopisujemy roznice M-C = 900 do arabic
						i++;	// po czym bezwzglednie trzeba zwiekszyc i zeby nie dokonywac obliczen na w tym wypadku M + kolejna, zostala obliczona w korku wyzej jak widac
					} 
				}
				else
					arabic += roman_to_int[i]; // w innym wypadku, to znaczy gdy jest to jedyna liczba np ciag wyglada C lub CMX (CM) sie wykonalo wczesniej wiec dodamy do tego tylko X
			}	
		}
		return arabic;
	}
	// papierosy mi sie koncza
  
	public static void main(String[] argv) {
		Scanner read = new Scanner(System.in);		//wywoluje funkcje oddczytu o zadanej nazwie read
		
		RomDec count_arabic = new RomDec();   //dwa obiekty, nie musialem tak robic, ale moglem, to zrobilem, jeden przechowa wg przepisu klasy romdec wartosci odpowiednio wywolanej metody
		RomDec count_roman = new RomDec();    //ten zrobi to samo
		
		//wypisywanie, interfejs.
		System.out.println("Program do konwersji liczb arabskich/rzymskie oraz rzymskich/arabskie\n");
		System.out.println("Menu:");
		System.out.println("1. Zamiana liczby arabskiej na rzymską");
		System.out.println("2. Zamiana liczby rzymskiej na arabską");
		System.out.print("Wybierz 1 lub 2: ");
		
		//sprawdzanie poprawnosci typu zmiennej.
		int oneortwo=0;
		try {
			oneortwo = read.nextInt();	
		}
		catch(InputMismatchException exception)	{
			System.out.println("Podano zmienna niewlasciwego typu");
		} 
		switch (oneortwo) {
			case 1: {
				System.out.print("\nPodaj liczbe arabska z zakresu od 1-3999 \nPodaj liczbe: ");
				try {
						arabic = read.nextInt(); //pobierze z klawy jakas liczbe arabska
					}
					catch(InputMismatchException exception)	{
						System.out.println("Podano zmienna niewlasciwego typu");
					}
				System.out.print("Zapis wyglada nastepująco: ");
				System.out.println(count_arabic.arabic(arabic));
			} break;
			
			case 2: {
				System.out.println("\nPodaj liczbe rzymska, pamietaj o poprawnym zapisie.");
				System.out.print("Dozwolone sa małe i wielkie litery np. MMmcMxCiX \nPodaj liczbe rzymską: ");
				roman = read.next();
				System.out.print("liczba wynosi: ");
				System.out.print(count_roman.roman(roman));
				
				if (count_roman.roman(roman) == 0)
					System.out.println("\nTo nie jest właściwie zapisana liczba rzymska!");
			} break;
			default : System.out.println("Blad! Wprowadz wartosc od 1 do 2"); 
		}
		read.close();
		System.out.println("\nProgram zakonczył swoje działanie");
	}
}
