package maquinaTuring;
import java.util.*;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;


public class simpleTuring {
	public static int bitEdo = 6;                                      //6 bits de estado!    
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH:mm:ss");
		
	public static void main(String[] args) throws Exception{
//		Scanner in = new Scanner(System.in);

//		System.out.println("Ingrese la longitud de la cinta de la máquina de Turing");
//		int lng = in.nextInt();
//		System.out.println("Ingrese la cantidad máxima de pasos de la máquina de Turing");
//		int maxStep = in.nextInt();
//		in.close();
		
		
		int lng = 1000;
		int maxStep = 1000;
		
		System.out.println("Hello Turing machine");
		int[] cinta = new int[lng];
		int prod; 
		
		int[] tm1 = {0,0,0,0,0,0,0,0,         //BB3.btm (six ones)
		             0,0,0,0,0,0,0,0,
		            0,0,0,0,1,0,1,1,
					 0,0,0,0,1,1,1,0,
					0,0,0,0,0,1,1,0,
					 0,0,0,0,1,0,1,1,
			        0,0,0,0,1,0,1,0,
		             0,0,0,0,0,0,1,1};
		
		int[] tm2 = {0,0,0,0,0,0,0,0,          //marxen4.btm (Busy beaver)
						0,0,0,0,0,0,0,0,
					0,0,0,0,1,0,1,0,
						0,0,0,0,0,1,1,0,
					0,0,0,0,1,1,1,1,
						0,0,0,1,0,0,1,0,
					0,0,0,0,0,1,1,1,
						0,0,0,0,1,1,1,1,
					0,0,0,0,0,0,1,0,
						0,0,0,1,0,1,0,0,
					0,0,0,0,1,1,1,1,
						0,0,0,0,1,0,1,0};
		
		int[] tm3 = {0,0,0,0,0,0,0,0,              //Schult.btm
						0,0,0,0,0,0,0,0,
					0,0,0,0,1,0,1,1,
						0,0,0,0,1,1,0,0,
					0,0,0,0,1,1,1,1,
						0,0,0,1,0,0,1,1,
					0,0,0,0,0,1,1,0,
						0,0,0,0,1,0,0,1,
					0,0,0,1,0,1,0,1,
						0,0,0,0,0,0,1,1,
					0,0,0,0,1,1,1,0,
						0,0,0,0,0,1,1,1};
		
		int[] tm4 = {0,0,0,0,0,0,0,0,                     //Uhind.btm
						0,0,0,0,0,0,0,0,
						0,0,0,0,1,0,1,1,
						0,0,0,0,1,1,1,0,
						0,0,0,0,0,1,0,0,
						0,0,0,1,0,0,0,0,
						0,0,0,0,0,1,1,0,
						0,0,0,0,0,0,1,0,
						0,0,0,0,1,0,1,0,
						0,0,0,1,0,1,1,1,
						0,0,0,1,0,0,0,1,
						0,0,0,0,1,0,0,1  };
		
		cinta = runTM(tm4, lng, maxStep, false, "Uhind");       //print steps?, nameFile                         //true: print Stated of turing Machine
		prod = cuentaUnos(cinta);
		System.out.println("Productividad: "+prod+"; Tamaño cinta: "+cinta.length);
		System.out.println("");
		System.out.print("Cinta de Turing: ");
		printVect(cinta);
	}
	
	//función para imprimir un vector dado
	public static void printVect(int[] vect){
		for(int i=0; i<vect.length; i++)
			System.out.print(" "+ vect[i]);
		System.out.println("");
	}
	public static void printVect(double[] vect){
		for(int i=0; i<vect.length; i++)
			System.out.print(" "+ vect[i]);
		System.out.println("");
	}
	
	//función que convierte una cadena de binarios a arreglo de enteros
	public static int[] string2array(String cadena){
		int l = cadena.length();
		int[] res = new int[l];
		for(int i = 0; i < l; i++){
			if(cadena.charAt(i)=='0')
				res[i] = 0;
			else
				res[i] = 1;
		}
		return res;
	}
	public static String array2string(int[] vect){
		String cadena = "";
		for (int i=0; i < vect.length; i++){
			cadena += Integer.toString(vect[i]);
		}
		return cadena;
	}
	
	//función de similitud entre cinta de Turing y cadena objetivo
	public static int hammingDistance(int[] tape, String real){
		int l = real.length();
		int[] aux = string2array(real);
		int cont = 0;
		for(int i=0; i < l; i++){
			if(tape[i] != aux[i]){
				cont += 1;
			}
		}
		return cont;
	}
	
	//convierte un numero binario a entero
	//una instruccion de la MT al siguiente estado de la MT
	public static int inst2edo(int[] inst){
		int edo = 0;
		int aux = 0;
		for(int i = bitEdo; i > 0; i--){         //change number for bitEdo
			aux = i-bitEdo;
			edo += inst[i-1] * Math.pow(2, Math.abs(aux));
		}
		return edo;
	}
	
	//calcula productividad de la MT
	public static int cuentaUnos(int[] tape){
		int cont = 0;
		for (int i = 0; i < tape.length; i++){
			if(tape[i] == 1){
				cont += 1;
			}
		}
		return cont;
	}
	
	public static int[] runTM(int[] tm, int lng, int maxStep, boolean datos, String name) throws Exception{
		Map<Integer, String> TMfinal = new HashMap<Integer, String>();           //diccionario id->cadena
		int edo = 1;                                                            //revisar estado inicial
		int head = lng/2;
		int steps = 0;
		
		int init;
		int fin;
		int x = (bitEdo+2);                //bits used for one state
		int[] inst = new int[x];           //save instruction(edo, write, read)
		int[] aux = new int[x*2];          //save a complete state
		int[] tape = new int[lng];         //empty tape
		Arrays.fill(tape, 0);
		
		System.out.println("estado inicial: " + edo);
		
		//------------Creating CSV file to save data  ------//
				Date date = new Date();                                       //Saving date for fileName
				String string_date = String.valueOf(dateFormat.format(date)); //current date time 
				String fileName = name + " "+"date: " + string_date + ".csv";
				
			    FileWriter writer = new FileWriter(fileName);
		//------------------------------------------------------//	        
				
		while(true){
		if(edo == 0 || steps >= maxStep || head == 0 || head == (lng-1)){
			TMfinal.put(0, array2string(Arrays.copyOfRange(tm, 0, 2*x)));     //Halt state
//------Closing csv file-----------------------------------//
		    writer.flush();
		    writer.close();
//------------------------------------------------------//
			break;
			}
		steps++;

		//System.out.println("Step: "+ steps + " ;Edo: "+ edo + " ;head: " + head + " ;tapeHead: "+ tape[head]);
		
		init =(2*x)*edo;                                                     //begin of instruction
		fin = (2*x)*(edo+1);                                                 //end of instruction
		aux = Arrays.copyOfRange(tm, init, fin);                             //current state in TuringMachine
		if (tape[head] == 0) inst = Arrays.copyOfRange(aux, 0, x);           //next state, next instruction
		else inst = Arrays.copyOfRange(aux, x, x*2);

		if(!TMfinal.containsKey(edo) && (TMfinal.get(edo) != array2string(aux))){
			TMfinal.put(edo, array2string(aux));                                  //update states in dictionary
		}
		
		edo = inst2edo(inst);                                                 //read next state
		
		if(inst[bitEdo]==0) {                                                 //write 0 in tape?
			tape[head] = 0;
		    CSVUtils.writeLine(writer, Arrays.asList("0"));
			}                                                               
		else {
			tape[head] = 1;
			CSVUtils.writeLine(writer, Arrays.asList("1"));
			}

		
		if(inst[bitEdo+1]==0) head--;                                         //move head?
		else head++;
		}
		
		if(datos == true){
		int edosTM = TMfinal.size();
		System.out.println("Estados de máquina de Turing: "+edosTM);
			for(Integer key : TMfinal.keySet())
				System.out.println("    -key: "+key+ "; TM.Inst: "+TMfinal.get(key));
		System.out.println("Complejidad de Kolmogorov: "+edosTM*(bitEdo+2)*2);
		}		
		return tape;
	}

}
