/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leone
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;

//la libreria para la firma
import java.security.Signature;

//la codificacion
import sun.misc.BASE64Encoder;

public class FirmaDigitalSimple {
    
    public static void main(String[] args) throws Exception{
        
        //con la generacion de la instancia y las llaves
        KeyPairGenerator kppp = KeyPairGenerator.getInstance("RSA");
        
        //inicializamos la lleva
        kppp.initialize(1024);
        
        KeyPair generadorLlave = kppp.genKeyPair();
        
        //vamos a tener la cadena a firmar
        
        byte[] data = "Prueba de una firma digital".getBytes("UTF8");
        
        //vamos a realizar la firma
        Signature firma = Signature.getInstance("MD5WithRSA");
        
        //inicializamos la firma
        firma.initSign(generadorLlave.getPrivate());
        
        //firmamos o actualizamos el data
        firma.update(data);
        
        //vamos a guardar la firma en un byte
        byte[] firmabytes = firma.sign();
        
        System.out.println("Ejemplo de firma: " + new BASE64Encoder().encode(firmabytes));
        
        /*
        La verificación de la firma solo nos devuelve true o false para saber si el documento
        es original o ha sido modificado
        */
        
        //VERIFICACION
        firma.initVerify(generadorLlave.getPublic());
        
        //actualizamos el estado de verificacion de la firma
        firma.update(data);
        
        System.out.println("Y el resultado de la verificación es: " + firma.verify(firmabytes));
    }
    
}
