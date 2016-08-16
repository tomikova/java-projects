package hr.fer.zemris.java.tecaj.hw3;

/**
 * Klasa koja pruza podrsku za rad sa CString stringovima.
 * @author Tomislav
 */

public final class CString {
	
	private final char[] data;
	private final int offset;
	private final int length;
	
	/**
	 * Konstruktor koji prima tri parametra.
	 * @param data Polje znakova.
	 * @param offset Pomak u odnosu na pocetak polja.
	 * @param length Duljina znakova koji predstavljaju string.
	 * @throws CStringException Ako je pomak ili duljina manja od nule ili
	 * ako je duljina veca duljine polja znakova.
	 */
	
	 public CString(char[] data, int offset, int length) {
		 if (offset+length > data.length || offset < 0 || length < 0){
			 throw new CStringException("Invalid parameters");
		 }
		 this.data = data;
		 this.offset = offset;
		 this.length = length;
	 }
	 
	 /**
	  * Konstruktor koji prima jedan parametar.
	  * @param data Polje znakova.
	  */
	 
	 public CString(char[] data) {
		 this.data = data;
		 this.offset = 0;
		 this.length = data.length;
	 }
	 
	 /**
	  * Konstruktor koji prima jedan parametar.
	  * @param original CString objekt na osnovu kojeg se stvara novi
	  * objekt razreda CString.
	  */
	 
	 public CString(CString original) {
		 int originalLength = original.length;
		 this.length = originalLength;
		 this.offset = 0;
		 if (original.data.length > originalLength){
			char[] newDataArray = new char[originalLength];
			for (int i = 0; i < originalLength; i++){
				newDataArray[i] = original.charAt(i);
			}
			this.data = newDataArray;
		 }
		 else{
			 this.data = original.data;
		 }
	 }
	 
	 /**
	  * Konstruktor koji prima jedan parametar.
	  * @param string String vrijednost na osnovu koje se stvara
	  * objekt razreda CString.
	  */
	 
	 public CString(String string) {
		this.data = string.toCharArray();
		this.offset = 0;
		this.length = string.length();
	 }
	 
	 /**
	  * Metoda koja vraca duljinu stringa.
	  * @return Duljina stringa.
	  */

	 public int length(){
		 return length;
	 }
	 
	 /**
	  * Metoda koja vraca jedan znak na zadanom indeksu.
	  * @param index Indeks na kojem se nalazi trazeni znak.
	  * @return Znak na zadanom indeksu.
	  * @throws CStringException Ako je indeks manji od nule ili ako je veci od duljine stringa.
	  */
	 
	 public char charAt(int index){
		 if (index < 0 || index > length-1){
			 throw new CStringException("Invalid index");
		 }
		 return data[offset+index];
	 }
	 
	 /**
	  * Metoda koja pretvara string u polje znakova.
	  * @return Polje znakova.
	  */
	 
	 public char[] toCharArray(){
		 char[] newCharArray = new char[length];
		 for (int i = 0; i < length; i++){
			 newCharArray[i] = data[offset+i];
		 }
		 return newCharArray;
	 }
	 
	 /**
	  * Metoda koja pretvara CString u String vrijednost.
	  * @return String vrijednost od CString.
	  */
	 
	 public String toString(){
		 StringBuilder stringBuilder = new StringBuilder(length);
		 for (int i = 0; i < length; i++){
			 stringBuilder.append(data[offset+i]);
		 }
		 return stringBuilder.toString();
	 }
	 
	 /**
	  * Metoda koja vraca indeks na kojem se nalazi prva pojava zadanog znaka.
	  * @param character Znak za koji se trazi indeks.
	  * @return Indeks na kojem se znak pojavio prvi puta, inace ako znak ne postoji
	  * vraca se vrijednost -1.
	  */
	 
	 public int indexOf(char character){
		 int index = -1;
		 for (int i = 0; i < length; i++){
			 if (data[offset+i] == character){
				 index = i;
				 break;
			 }
		 }
		 return index;
	 }
	 
	 /**
	  * Metoda koja provjerava da li originalni string zapocinje s ispitivanim stringom. 
	  * @param string String za kojeg se ispituje da li je pocetak originalnog stringa.
	  * @return true/false vrijednost ovisno da li originalni string 
	  * zapocinje s ispitivanim stringom.
	  */
	 
	 public boolean startsWith(CString string){
		 if (string.length > length){
			 return false;
		 }
		 for (int i = 0, length = string.length; i < length; i++ ){
			 if (data[offset+i] != string.charAt(i)){
				 return false;
			 }
		 }
		 return true;
	 }
	 
	 /**
	  * Metoda koja provjerava da li originalni string zavrsava s ispitivanim stringom. 
	  * @param string String za kojeg se ispituje da li je zavrsetak originalnog stringa.
	  * @return true/false vrijednost ovisno da li originalni string 
	  * zavrsava s ispitivanim stringom.
	  */
	 
	 public boolean endsWith(CString string){
		 if (string.length > length){
			 return false;
		 }
		 for (int i = 0, length = string.length, diff = this.length - length; i < length; i++ ){
			 if (data[offset+diff+i] != string.charAt(i)){
				 return false;
			 }
		 }
		 return true;
	 }
	 
	 /**
	  * Metoda koji provjerava da li originalni string sadrzi ispitivani string.
	  * @param string String za kojeg se ispituje da li je sadrzan u originalnom stringu.
	  * @return true/false vrijednost ovisno da li je ispitivani string
	  *  sadrzan u originalnom stringu.
	  */
	  
	 public boolean contains(CString string){
		 if (length < string.length){
			 return false;
		 }
		 char[] original = this.toCharArray();
		 char[] pattern = string.toCharArray();
		 int i = 0;
		 while ( i < length){
			 boolean patternExist = true;
			 int j = 0;
			 while (j < string.length){
				 //ako je preostali dio originalnog niza manji od uzorka to je kraj pretrage
				 if ((length-i) < string.length){
					 return false;
				 }
				 //ako neki od znakova uzorka ne odgovara podnizu u originalnom
				 //nizu, uzorak na ovom mjestu nije pronadjen i pomakni se na sljedeci znak
				 if(original[i+j] != pattern[j]){
					 patternExist = false;
					 break;
				 }
				 j++;
			 }
			 //ako je uzorak pronadjen to je kraj pretrage
			 if(patternExist){
				break;
			 }
			 i++;
		 }
		 return true;
	 }
	 
	 /**
	  * Metoda koja vraca string koji je podniz originalnog stringa.
	  * @param startIndex Indeks prvog znaka podniza.
	  * @param endIndex Indeks kraja podniza. Indeks koji nije dio podniza.
	  * @return String koji je podniz originalnog stringa.
	  * @throws CStringException Ako je pocetni indeks manji od nule.
	  * Ako je pocetni indeks veci od zavrsnog indeksa.
	  * Ako je pocetni indeks veci od duljine stringa umanjenog za 1.
	  * Ako je zavrsni indeks veci od duljine stringa. 
	  */
	 
	 CString substring(int startIndex, int endIndex){
		 if (startIndex < 0 || endIndex < startIndex || startIndex > length-1 || endIndex > length){
			 throw new CStringException("Invalid index");
		 }
		 int indexDiff = endIndex - startIndex;
		 return new CString(data,offset+startIndex,indexDiff);
	 }
	 
	 /**
	  * Metoda koja vraca string koji je pocetni dio originalnog stringa.
	  * @param n Duljina pocetnog stringa.
	  * @return String koji je pocetni dio originalnog stringa.
	  * @throws CStringException Ako je duljina manja od nula ili veca od
	  * duljine originalnog stringa.
	  */
	 
	 CString left(int n){
		 if (n < 0 || n > length){
			 throw new CStringException("Invalid length");
		 }
		 return new CString(data,offset,n);
	 }
	 
	 /**
	  * Metoda koja vraca string koji je zavrsni dio originalnog stringa.
	  * @param n Duljina zavrsnog stringa.
	  * @return String koji je zavrsni dio originalnog stringa.
	  * @throws CStringException Ako je duljina manja od nula ili veca od
	  * duljine originalnog stringa.
	  */
	 
	 CString right(int n){
		 if (n < 0 || n > length){
			 throw new CStringException("Invalid length");
		 }
		 return new CString(data,offset+length-n,n);
	 }
	 
	 /**
	  * Metoda koja na originalni string nadodaje drugi string.
	  * @param string String koji se dodaje na kraj originalnog stringa.
	  * @return String nastao nadodavanjem.
	  */
	 
	 CString add(CString string){
		 char[] concatString = new char[length + string.length()];
		 for (int i = 0; i < length; i++){
			 concatString[i] = this.charAt(i);
		 }
		 for (int i = 0; i < string.length; i++){
			 concatString[length+i] = string.charAt(i);
		 }
		 return new CString(concatString);
	 }
	 
	 /**
	  * Metoda koja sve pojave jednog znaka zamjenjuje drugim znakom.
	  * @param oldChar Znak koji ce biti zamijenjen..
	  * @param newChar Znak s kojim ce se izvrsiti zamjena. 
	  * @return String vrijednost s zamijenjenim znakovima.
	  */
	 
	 CString replaceAll(char oldChar, char newChar){	 
		 char[] modifiedArray = this.toCharArray();
		 for(int i = 0; i < length; i++ ){
			 if(modifiedArray[i] == oldChar){
				 modifiedArray[i] = newChar;
			 }
		 }
		 return new CString(modifiedArray);
	 }
	 
	 /**
	  * Metoda koja sve pojave jednog niza zamjenjuje drugim nizom.
	  * @param oldStr Niz koji ce biti zamijenjen..
	  * @param newStr Niz s kojim ce se izvrsiti zamjena. 
	  * @return String vrijednost s zamijenjenim nizovima.
	  */
	 
	 CString replaceAll (CString oldStr, CString newStr){
		 if (length < oldStr.length){
			 return this;
		 }
		 char[] original = this.toCharArray();
		 char[] pattern = oldStr.toCharArray();
		 CString modified = new CString("");
		 boolean exitFlag = false;
		 int i = 0;
		 //indeks kraja zadnjeg pronadjenog uzorka
		 int lastFoundIndex = 0;
		 while (i < length){
			 boolean patternExist = true;
			 int j = 0;
			 while (j < oldStr.length){
				 //ako je preostali dio originalnog niza manji od uzorka to je kraj pretrage
				 if ((length-i) < oldStr.length){
					 exitFlag = true;
				 }
				 //ako neki od znakova uzorka ne odgovara podnizu u originalnom
				 //nizu, uzorak na ovom mjestu nije pronadjen i pomakni se na sljedeci znak
				 if(original[i+j] != pattern[j]){
					 patternExist = false;
					 break;
				 }
				 j++;
			 }
			 if(exitFlag){
				 //ako uopce nije doslo do promjene vrati nepromjenjeni objekt
				 if (lastFoundIndex == 0){
					 modified = this;
				 }
				 //inace nadodaj ostatak originalnog niza od indeksa 
				 //zadnjeg pronalaska uzorka do kraja originalnog niza
				 else{
					 modified = modified.add(this.substring(lastFoundIndex,length));
				 }
				 break;
			 }
			 //ako je uzorak pronadjen zamijeni ga s novim i zapamti indeks kraja
			 //uzorka u originalnom nizu, pomakni se za toliko mjesta unaprijed i
			 //kreni s provjerom od sljedeceg neprovjerenog znaka originalnog niza
			 //inace pomakni se samo na sljedeci znak
			 if(patternExist){
				 modified = modified.add(this.substring(lastFoundIndex,i)).add(newStr);
				 i += oldStr.length;
				 lastFoundIndex = i;
				 continue;
			 }
			 i++;
		 }
		 return modified;
	 }

}
