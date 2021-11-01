

package RSA;

/**
 *
 * @author leone 
 */

import java.math.BigInteger;
import java.util.*;

public class RSA {
    
    //tamaño de los primos
    int tamPrimo;
    //BigInteger para los numeros utilizados por el algoritmo RSA
    private BigInteger n, q, p;
    private BigInteger fi;
    private BigInteger e, d;

    /** Constructor de la clase RSA */
    public RSA(int tamPrimo) {
        this.tamPrimo = tamPrimo;
        generaPrimos();             //Genera p y q
        generaClaves();             //Genera e y d

    }

    public RSA(BigInteger p, BigInteger q, int tamPrimo) {
        this.tamPrimo=tamPrimo;
        this.p=p;
        this.q=q;
        generaClaves();             //Genera e y d (claves publica y privada)
    }
    
     //metodo para generar los numeros primos
    public void generaPrimos()
    {
        p = new BigInteger(tamPrimo, 100, new Random());
        do q = new BigInteger(tamPrimo, 100, new Random());
            //comparacion para obtener numeros primos distintos entre si y de cero 
            while(q.compareTo(p)==0);
    }
    
    //metodo para la generacion de las claves
    //n = p* q
    //fi = (p-1) * (q-1)
    //De ahi hay que elegir el numero e y d eligiendo e como un coprimo 
    //y menor que n

    public void generaClaves()
    {
        // n = p * q
        n = p.multiply(q);
        //fi = (p-1) * (q-1)
        fi = p.subtract(BigInteger.valueOf(1));
        fi = fi.multiply(q.subtract(BigInteger.valueOf(1)));
        // Elegimos un e coprimo de y menor que n
        // 1 < e < fi (inversa multiplicativa)
        do e = new BigInteger(2 * tamPrimo, new Random());
            while((e.compareTo(fi) != -1) ||
		 (e.gcd(fi).compareTo(BigInteger.valueOf(1)) != 0));
        //calcular d
        //d = e^1 mod fi
        //necesitamos del multiplo inversor
        d = e.modInverse(fi);
    }

    /* Inicio del cifrado utilizando la clave publica */
    public BigInteger[] cifrar(String mensaje)
    {
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];

        //iteracion de los numeros grandes
        for(i=0; i<bigdigitos.length;i++){
            //almacenamiento de los digitos en la variable temporal inicializada en la posicion cero
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        /*formula del cifrado
        C = M^e mod(fi)
        */
        BigInteger[] encriptado = new BigInteger[bigdigitos.length];

        for(i=0; i<bigdigitos.length; i++)
            encriptado[i] = bigdigitos[i].modPow(e,n);

        return(encriptado);
    }
    
    /* Inicio del descifrado utilizando la clave privada */
    public String descifrar(BigInteger[] cifrado) {
        BigInteger[] descifrado = new BigInteger[cifrado.length];
        
        //iteracion de la longitud del mensaje cifrado
        //M = C^d mod n
        for(int i=0; i< descifrado.length; i++){
            descifrado[i] = cifrado[i].modPow(d,n);
        }

        char[] charArray = new char[descifrado.length];
        
        //construccion de la nueva cadena
        for(int i=0; i<charArray.length; i++){
            charArray[i] = (char) (descifrado[i].intValue());
        }

        return(new String(charArray));
    }

    public BigInteger getP() {return(p);}
    public BigInteger getQ() {return(q);}
    public BigInteger getFi() {return(fi);}
    public BigInteger getN() {return(n);}
    public BigInteger getE() {return(e);}
    public BigInteger getD() {return(d);}
}