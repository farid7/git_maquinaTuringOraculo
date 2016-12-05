package turingMachine;
import java.util.*;
import java.util.Random;

public class turingMachine {
	public static int bitState = 6;

	public static void main(String[] args) {
		int[] tm1 = {0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,0,0,1,0,1,1,
		            0,0,0,0,1,1,1,0,
			        0,0,0,0,0,1,1,0,
			        0,0,0,0,1,0,1,1,
	        	    0,0,0,0,1,0,1,0,
                    0,0,0,0,0,0,1,1};
		
		int[] tm2 = {0,0,0,0,0,0,0,0,
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
	                0,0,0,0,1,0,0,1};
		
		Random randomGenerator = new Random(19102016);

		int[] tm3 = new int[64*16];
		for (int i=0; i < tm3.length; i++){
			if(i < 16)
				tm3[i] = 0;
			else
				tm3[i] = Math.round(randomGenerator.nextInt(2));
		}
		String obj = "1111111111";
		int edo = 1;
		int lng= 20;
		int maxStep=1000;
		int head = lng/2;
		int[] tape = new int[lng];
		Arrays.fill(tape, 0);
		
//		int[] inst = leeInst(tm1, head, tape, edo, lng);
//		printVect(inst);
//		edo = inst2edo(Arrays.copyOfRange(inst, 0, 6));
//		System.out.println(edo);
//		int write = inst[6];
//		int move  = inst[7];
		
		
		int[] cosa = runTM(tm1, lng, maxStep);
		int prod = cuentaUnos(cosa);
		//System.out.println("Productividad: "+prod);
		//System.out.println("estado: "+edo+"; write "+write+"; move "+move);
		//System.out.println("head: "+head+" ;edo: "+edo+" ;longitud: "+lng+" ; headValue: "+tape[head]);
		//System.out.println("Hola Java " + tm1.length );
		//printVect(cosa);
		//System.out.println("Diferencias: "+ distancias(cosa, obj));
	}
	
	public static void printVect(int[] vect){
		for(int i=0; i<vect.length; i++)
			System.out.print(" "+ vect[i]);
		System.out.println("");
	}
	
	public static int[] leeInst(int[] tm, int head, int[] tape, int edo, int lng){
		if(head < 0 || head > lng) System.out.println("Error en cabezal");
		int init =16*edo;
		int fin = 16*(edo+1);
		int[] aux = Arrays.copyOfRange(tm, init, fin);
//		printVect(aux);
		int[] instr = new int[8];
		if (tape[head] == 0) instr = Arrays.copyOfRange(aux, 0, 8);
		else instr = Arrays.copyOfRange(aux, 8, 16);
		return instr;
	}
	
	public static int inst2edo(int[] inst){
		int edo = 0;
		int aux = 0;
		for(int i = bitState; i > 0; i--){
			aux = i-bitState;
//			System.out.println(6 + " " + i + " : " + Math.pow(2, Math.abs(aux)) + " ");
			edo += inst[i-1] * Math.pow(2, Math.abs(aux));
		}
//		System.out.println("");
		return edo;
	}
	
	public static int cuentaUnos(int[] tape){
		int cont = 0;
		for (int i = 0; i < tape.length; i++){
			if(tape[i] == 1){
				cont += 1;
//				System.out.print(tape[i] + ";"+cont+"  ");
			}
		}
		return cont;
	}
	
	public static int[] runTM(int[] tm, int lng, int maxStep){
		int edo = 1;
		int head = lng/2;
		int halt = 0;
		int steps = 0;
		
		int[] inst = new int[8];
		int[] tape = new int[lng];
		Arrays.fill(tape, 0);
		
		while(true){
		if(edo == 0 || steps > maxStep || head == 0 || head == (lng-1)){
			break;
			}
//		System.out.println("si llega; "+" edo: "+edo );
		steps++;
		inst = leeInst(tm, head, tape, edo, lng);
		edo = inst2edo(inst);
		//write is inst[6]
		if(inst[bitState]==0) tape[head] = 0;
		else tape[head] = 1;
		//move is inst[7]
		if(inst[bitState+1]==0) head--;
		else head++;
		//System.out.println("edo: "+edo+"; head: "+head+"; tape[head]:"+tape[head]+"; steps: "+steps);
		}
		return tape;
	}
	
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
	
	public static int distancias(int[] tape, String real){
		int l = tape.length;
		int inicio = l/2;
		l = real.length();
		int[] aux = string2array(real);
		int cont = 0;
		for(int i=0; i < l; i++){
			if(tape[inicio] != aux[i]){
				cont += 1;
			}
			inicio++;
		}
		return cont;
	}
}

