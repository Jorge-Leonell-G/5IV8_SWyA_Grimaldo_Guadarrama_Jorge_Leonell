/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa;

/**
 *
 * @author leone
 */

//necesitamos de BigInteger para el calculo de los numeros grandes
import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class RSA {
    //tamaño del primo
    int tamPrimo;
    //BigInteger para n, p y q
    //son los numeros utilizados por el RSA
    BigInteger n, p, q;
    //otro BigInteger para fi
    BigInteger fi;
    //BigInteger para e y d
    BigInteger e, d;
    
    //constructor de la clase
    public RSA(int tamPrimo){
        this.tamPrimo = tamPrimo;
    }
    
    //metodo para generar los numeros primos
    public void generarPrimos(){
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10, new Random());
        //comparacion para obtener numeros primos distintos entre si y de cero 
        while(q.compareTo(p)!=0);
    }
    
    //metodo para la generacion de las claves
    //n = p* q
    //fi = (p-1) * (q-1)
    //De ahi hay que elegir el numero e y d eligiendo e como un coprimo 
    //y menor que n
    
    public void generarClaves(){
        //n = p * q
        n = p.multiply(q);
        
        //fi = (p-1) * (q-1)
        fi = p.subtract(BigInteger.valueOf(1));
        fi = fi.multiply(q.subtract(BigInteger.valueOf(1)));
        
        //eleccion de un coprimo de e
        // 1 < e < fi (inversa multiplicativa)
        do e = new BigInteger(2 * tamPrimo, new Random());
        while((e.compareTo(fi) != -1) || (e.gcd(fi).compareTo(BigInteger.valueOf(1)) != 0));
        
        //calcular d
        //d = e^1 mod fi
        //necesitamos del multiplo inversor
        d = e.modInverse(fi);
        
    }
    
    //inicio del cifrado utilizando la clave publica
    //solo se pueden cifrar numeros
    
    public BigInteger[] cifrar(String mensaje){
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        
        BigInteger[] bigDigitos = new BigInteger[digitos.length];
        
        //iteracion de los numeros grandes
        for(i = 0; i < bigDigitos.length; i++){
            //almacenamiento de los digitos en la variable temporal
            //inicializada en la posicion cero
            temp[0] = digitos[i];
            bigDigitos[i] = new BigInteger(temp);
        }
        //formula del cifrado
        //C = M^e mod(fi)
        BigInteger[] cifrado = new BigInteger[bigDigitos.length];
        
        for(i = 0; i < bigDigitos.length; i++){
            cifrado[i] = bigDigitos[i].modPow(e, n);
        }
        
        return cifrado;
    }
    
    //inicio del descifrado utilizando la clave privada
    
    public String descifrar(BigInteger[] cifrado){
        BigInteger[] descifrado = new BigInteger[cifrado.length];
        //iteracion de la longitud del mensaje cifrado
        //M = C^d mod n
        for(int i = 0; i < descifrado.length; i++){
            descifrado[i] = cifrado[i].modPow(d, n);
        }
        
        char[] charArray = new char[descifrado.length];
        
        //construccion de la nueva cadena
        for(int i = 0; i < charArray.length; i++){
            charArray[i] = (char)(descifrado[i].intValue());
        }
        
        return (new String(charArray));
    }
    
}
