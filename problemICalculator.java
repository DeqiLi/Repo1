/*
 *   NZPC 2014, Problem I: Approximation
 *   Time Limit: 10sec
 *   Points: 30
 *   
 *   Deqi Li@UoA, Aug 9, 2014
 */

import java.io.BufferedReader;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class problemICalculator {

	static boolean debugMode = false;
	static boolean debugMode2 = true;
	static String line;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader br = new BufferedReader(new FileReader("C:\\Coding\\Java\\NZPC\\src\\input.txt"));
		int loop = Integer.parseInt(br.readLine());
		String input;
		while (loop > 0) {
			input= br.readLine();
			line = trim(input);
			if(debugMode) System.out.print("\nline="+line);
			while(extract()!=""); // when extract(s)=="", it means s is a plain string without '(' or ')' 
			int res = calulator(line);
			if(debugMode2) System.out.print("\nRESULT="+res);			
			System.out.print("\n");			
			loop--;
		}
	}
	
	private static String trim(String s){
		String result ="";
		for (int i=0;i<s.length();i++){
			if (s.charAt(i)!=' '){
				result+=s.charAt(i);
			}
		}
		return result;
	}
	
	private static int round(int a){
		if(a<10) return a;
	
		String s=""+a;
		int x=s.charAt(0)-48;
		int y=s.charAt(1)-48;
		if(y>=3) {
			if(x<9) {
				x += 1;
				for(int j=0; j<s.length()-1; j++) x*=10;
			}
			else{
				x = 1;
				for(int j=0; j<s.length(); j++) x*=10;
			}
		}
		else {
			for(int j=0; j<s.length()-1; j++) x*=10;	
		}
		return x;
	}
	
	// extract one of the most inner segment bracketed by only one pair brackets like (12+6) in the following expression:
	// input: line, a complex arithmetic expression with brackets
	// output: one of the most inner segment without being bracketed
	// eg. 
	//     input: line = 51 + 18*(10+ (30 + (12+6)*(4*21*60+5*13+29))*2) + 20    ---- no space in fact
	//     output: 12+6
	public static String extract() { //
		//System.out.print("\nS="+line);
		int p=0, q=0;
		do {
			if(line.charAt(q)!=')') {
				if(line.charAt(q)=='(') p = q; 
				q++;
			}
			else break; // one of the most inner segment is found; it is between line[p] and line[q]
		}while(q<line.length());
		
		if(q==line.length()) { // this implies that line has been fully extracted so that no segments are bracketed. 
			if(debugMode) System.out.println("\nplainS:(Empty)"); 
			return ""; 
		}
		
		String plainS = line.substring(p+1, q); // an extracted segment between line[p] and line[q]; non-bracketed
		line = line.substring(0, p) + calulator(plainS) + line.subSequence(q+1, line.length()); // update line: remain of line is assigned to line
		if(debugMode) { 
			System.out.print("\nS:"+line);
			System.out.print("\nplainS:"+plainS);
		}
		return plainS;
	}
	
	// input: an arithmetic expression, plainS, extracted by extract()
	// output: the arithmetic result of the expression plainS
	// eg.
	//    input: 12+6 
	//    output: 20    -- 12+6=10+6=16=20 (vars and result are rounded)
	public static int calulator(String s){ // s: without '(' and ')', like: 4+50*6*8+2*3
		int res=0;
		ArrayList<Integer>data = new ArrayList<Integer>();
		System.out.print("\nplainS="+s);
		int i=0; 
		// we simulate two stacks: data and s 
		// (s:original expression including operators; data: integers from s).
		while(i<s.length()){ // extract integers from s, add them into data[]
			int d=0;
			while(i<s.length() && (s.charAt(i)=='+' || s.charAt(i)=='*')) i++;
			while(i<s.length() && s.charAt(i)!='+' && s.charAt(i)!='*'){
				d = d*10 + (s.charAt(i)-48);
				i++;
			}
			data.add(round(d));
		}
		if(debugMode) {
			System.out.print("\ndata=");//plainS: "+s);
			for(Integer x: data) {
				System.out.print(" "+ x);
			}
		}

		if(debugMode) System.out.print("\n"+s);
		int p=0; // do multiplications when encounter '*' in s; sum these results together in res
		i=0;
		for(p=0; p<s.length(); p++){ // s: like 4+50*6*8+2*3
			if(s.charAt(p) =='*' || s.charAt(p) =='+') i++;
			if(s.charAt(p) =='*'){
				int x=round(data.get(i-1));
				int y=round(data.get(i));
				int z=round(x*y);
				data.set(i-1, 0); 
				data.set(i, z); 
				if(debugMode) {
					System.out.print("\ni="+i+", x="+x+", y="+y+", z="+z);//+", res="+res);
				}
			}
		}
		if(debugMode) {
			System.out.print("\ndata=");//plainS: "+s);
			for(Integer x: data) {
				System.out.print(" "+ x);
			}
		}	

		for(i=0; i<data.size(); i++){
			res = round(res+data.get(i));
		}
		if(debugMode) {
			System.out.print("\nplain="+res);
		}
		return round(res);
	}
}
/*

1
8*(5)

1
8 * ((3+2))

1
8 * ((3+2))*3+13*22

1
51 + 18*(10+ (30 + (12+6)*(4*21*60+5*13+29))*2) + 20

1
120000 + 90009 + 89*78 * 66 + 889900 + 899900 + 929900 + 939900

8

4
100 +   29
135 + 235
120 + 120+120
4 + 89 * 99

8 * ((3+2))
8 * ((3+2))*3+13*22

3
8 * ((3+2))*3+13*22
51 + 18*(10+ (30 + (12+6)*(4*21*60+5*13+29))*2) + 20
(89 * 19 + 400) * (510 + 2*(10+ (30 + (12+6)*(4*21*6+5*13+29))*2) + 20000)

 */
 
