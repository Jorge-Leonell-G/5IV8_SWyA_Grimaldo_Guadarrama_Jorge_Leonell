/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;



/**
 *
 * @author leone
 */
public class EjemploDES {
    
    public static void main(String [] args) throws Exception{
        
        
        if(args.length!= 1){
            mensajeAyuda();
            System.exit(1);
        }
        
        /*
        Lo primero que debemos hacer es cargar el tipo de instancia o proveedor
        del cifrado simetrico que se va a urilizar
        */
    
        System.out.println("1.- Generar la clave DES: ");
        //la llave la podmos crear a partir de una funcion generica llamda funcion hash MD5
        //La funcion hash es una secuencia de numeros psuedoaleatorios
        KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
        
        //inicializar la llave
        //llave de DES 56 bits
        
        //tamaño de la llave
        generadorDES.init(56);
        
        //vamos a generar la llave secreta para que entre al proceso iterativo de rondas
        
        //en el proceso del DES se entra a una etapa de generacion de llaves
        //16 rondas con las subllaves
        SecretKey subllave = generadorDES.generateKey();
        
        System.out.println("La clave: "+ subllave);
        
        //vamos a empezar con el algoritmo
        //vamos a crear las subllaves a su formato binario
        mostrarBytes(subllave.getEncoded());
        
        System.out.println("\n");
        
        //vamos a cifrar
        //Aqui vamos a definir el tipo de cifrado
        //si se debe agregar padding (relleno) para aumentar la seguridad dekl mismo
        //si va a tener algun estándar PKCS
        Cipher cifrado = Cipher.getInstance("DES/ECB/PKCS5Padding");
        /*
        Vamos a decir que todos los bloques de 64 bits que se conformen para cifrar con 
        DES si el bloque no alcanza a rellenarse con el tecto plano pueda aplicar padding 
        para acompletar el bloque
        
        ALGORITMO: DES
        MODO: ECB (ELECTRONIC CODE BOOK)
        PADDING: PKCS5
        */
        
        System.out.println("2.- Cifrar con DES el fichero" + args[0] + ", dejar el resultado en: " +
                args[0]+".cifrado");
        
        cifrado.init(Cipher.ENCRYPT_MODE, subllave);
        
        //leer el archivo o fichero y tener el buffer para la lectura, el tamaño 
        //y que entre en un bucle hasta terminar de leer el tamaño del archivo
        
        //el archivo o fichero lo transformamos a bits y hay que leerlo y cifrarlo/descifrarlo
        
        byte[] buffer = new byte[1000]; //quiero ir leyendo de 1000 en 1000 bits del fichero
        
        byte[] bufferCifrado; //aqui voy a almacenar el resultado
        
        FileInputStream in = new FileInputStream(args[0]);
        
        FileOutputStream out = new FileOutputStream(args[0]+".cifrado");
        
        //proceso de lectura
        int bytesleidos = in.read(buffer, 0, 1000);
        while(bytesleidos != -1){
            //mientras no se llegue al final del archivo o fichero
            bufferCifrado = cifrado.update(buffer, 0, bytesleidos);
            //para el texto claro leer y enviarlo al buffer cifrado
            out.write(bufferCifrado);
            //escribir el archivo cifrado
            bytesleidos = in.read(buffer, 0, 1000);
        }
        //acompletar el fichero o archivo con el cifrado
        bufferCifrado = cifrado.doFinal();
        //se termina de escribir
        out.write(bufferCifrado); //escribir el final del texto cifrado si lo hay
        
        //cerramos flujos
        in.close();
        out.close();
        
        System.out.println("3.- Descifrar con DES el fichero "+args[0]+".cifrado"+
                 ", dejar el resultado en: "+args[0]+".descifrado");
        
        //vamos a descifrar
        cifrado.init(Cipher.DECRYPT_MODE, subllave);
        
        byte[] bufferPlano; //aqui voy a almacenar el resultado descifrado
        
        in = new FileInputStream(args[0]+".cifrado");
        
        out = new FileOutputStream(args[0]+".descifrado");
        
        //proceso de lectura
        bytesleidos = in.read(buffer, 0, 1000);
        while(bytesleidos != -1){
            //mientras no se llegue al final del archivo o fichero
            bufferPlano = cifrado.update(buffer, 0, bytesleidos);
            //para el texto claro leer y enviarlo al buffer cifrado
            out.write(bufferPlano);
            //escribir el archivo cifrado
            bytesleidos = in.read(buffer, 0, 1000);
        }
        //acompletar el fichero o archivo con el cifrado
        bufferPlano = cifrado.doFinal();
        //se termina de escribir
        out.write(bufferPlano); //escribir el final del texto cifrado si lo hay
        
        //cerramos flujos
        in.close();
        out.close();
        
    }
    
    public static void mensajeAyuda(){
        System.out.println("Ejemplo de un cifrado DES utilizando librerias Crypto y Security");
        System.out.println("Necesita la carga de un archivo en txt, no se te olvide agregarlo >:D");
        System.out.println("Con amor yo");
    }
    
    public static void mostrarBytes(byte[] buffer){
        System.out.write(buffer, 0, buffer.length);
}
    
    
}
