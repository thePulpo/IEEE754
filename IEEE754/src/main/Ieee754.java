package main;

import java.util.Scanner;

public class Ieee754 {

	public static void main(String[] args) {
        // Eingabe einlesen
        float input = readFloatFromCommandLine();
		
        // Fuegen Sie hier Ihre Loesung ein
        printArray(floatToIeee754(input));
    }

    // Gegebene Methoden
    private static float readFloatFromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return Float.parseFloat(input.replace(',', '.'));
    }

    private static void printArray(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            sb.append(i);
        }
        System.out.println(sb);
    }
    // Ende Gegebene Methoden
    
    
    private static int[] floatToIeee754(float input) {
    	int[] ergebnis = new int[32];
    	
    	int signBit = input >= 0 ? 0 : 1;
    	input *= signBit == 0 ? 1 : -1;
    	
    	int[] exponentBits = new int[8];
    	int[] mantissaBits = new int[23];
    	

    	int[][] inputBits = floatToBinary(input);
    	int[] vorKommaBits = inputBits[0];
    	int[] nachKommaBits = inputBits[1];    	
    	
    	int exp = 127 + vorKommaBits.length - 1;
    	
    	exponentBits = intToBinary(exp);
    	
    	
    	for(int i=1; i<vorKommaBits.length; i++) {
    		mantissaBits[i-1] = vorKommaBits[i];
    	}
    	for(int i=0; i<nachKommaBits.length; i++) {
    		mantissaBits[vorKommaBits.length + i - 1] = nachKommaBits[i];
    	}
    	
    	ergebnis[0] = signBit;
    	
    	for(int i=0; i<exponentBits.length; i++) {
    		ergebnis[i+1] = exponentBits[i];
    	}
    	for(int i=0; i<mantissaBits.length; i++) {
    		ergebnis[exponentBits.length + i + 1] = mantissaBits[i];
    	}
    	
    	return ergebnis;
    }
    private static int[] intToBinary(int value) {
    	int[] bits = new int[countBits(value)];
    	int i = bits.length-1;
    	while(value > 0) {
    		bits[i] = value % 2;
    		value /= 2;
    		i--;
    	}
    	return bits;
    }
    
    private static int[][] floatToBinary(float value) {
    	int[][] bits = new int[2][];
    	
    	int vorKomma = (int)value;
    	float nachKomma = value - vorKomma;
    	
    	bits[0] = intToBinary(vorKomma);
    	
    	int[] nachKommaBits = new int[32];
    	
    	int i = 0;
    	while(nachKomma > 0) {
    		nachKomma = nachKomma * 2;
    		nachKommaBits[i] = (int)nachKomma;
    		nachKomma -= (int)nachKomma;
    		i++;
    	}
    	
    	bits[1] = new int[i];
    	for(int j=0; j<i; j++) {
    		bits[1][j] = nachKommaBits[j];
    	}
    	
    	return bits;
    }
    
    private static int countBits(int number)
    {
        return (int)(Math.log(number) / Math.log(2) + 1);
    }
}
