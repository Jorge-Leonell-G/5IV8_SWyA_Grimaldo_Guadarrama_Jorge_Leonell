/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._firmadigital_clavesarchivo;

/**
 *
 * @author leone
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;

import java.security.spec.*;

import javax.crypto.*;

import javax.crypto.interfaces.*;
import javax.crypto.spec.*;
public class FirmaDigitalArchivos {
    
    public static void main(String[] args) throws Exception{
        
        if(args.length!= 1){
            mensajeAyuda();
            System.exit(1);
        }
        
        System.out.println("Crea los archivos: " + args[0] + ".secreta"
        + args[0]+".publica" + args[0]+".privada");
        
        //añadir el proveedor
        Security.addProvider(new BouncyCastleProvider());
        
        //generamos las claves de rsa
        KeyPairGenerator generadorRSA = KeyPairGenerator.getInstance("RSA", "BC");
        
        //inicializamos
        generadorRSA.initialize(1024);
        
        KeyPair clavesRSA = generadorRSA.genKeyPair();
        
        //privda y publica
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        
        PublicKey clavePublica = clavesRSA.getPublic();
        
        
        //keyFactory para la instancia de la llave con RSA
        
        KeyFactory keyfactoryRSA = KeyFactory.getInstance("RSA", "BC");
        
        /*
        vamos a colocar la clave priuvada con el fichero segun la s normas de la firma+
        digital con rsa
        para ello vamos a codificarla con PKCS8
        */
        PKCS8EncodedKeySpec pkcs8spec = new PKCS8EncodedKeySpec(clavePrivada.getEncoded());
        
        //vamos a escribirla en un archivo
        FileOutputStream out = new FileOutputStream(args[0]+".privada");
        out.write(pkcs8spec.getEncoded());
        out.close();
        
        //vamos a recuperar la clave privada del fichero
        
        byte[] bufferpriv = new byte[5000]; 
        FileInputStream in = new FileInputStream(args[0]+".privada");
        in.read(bufferpriv, 0, 5000);
        in.close();
        
        //recuperamos la clave privada desde los datos codificados en formato PKCS8
        PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(bufferpriv);
        //clave privada de firma
        PrivateKey clavePrivada2 = keyfactoryRSA.generatePrivate(clavePrivadaSpec);
        
        //validacion de la clave para saber si el archivo coincide con la clave
        if(clavePrivada.equals(clavePrivada2)){
            System.out.println("Ok, la clave privada ha sido guardada y recuperada");
            
            /*
            vamos a volcar la clave publica en un archivo bajo el estandar como lo establece
            la norma X.509
            */
            
            X509EncodedKeySpec x509spec = new X509EncodedKeySpec(clavePublica.getEncoded());
            
            //vamos a escibir el archivo
            out = new FileOutputStream(args[0]+".publica");
            out.write(x509spec.getEncoded());
            out.close();
            
            /*
            vamos a recuperar la clave publica del archivo
            */
            
            byte[] bufferpub = new byte[5000];
            in = new FileInputStream(args[0]+".publica");
            in.read(bufferpub, 0, 5000);
            in.close();
            
            //recuperamos y validamos que sea la correcta
            
            X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferpub);
            
            PublicKey clavepublica2 = keyfactoryRSA.generatePublic(clavePublicaSpec);
            
            if(clavePublica.equals(clavePrivada2)){
                System.out.println("Ok, la clave publica ha sidfo guardada y recuperada");
            }
            
            //vamos a crear una instancia DES
            
            KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
            generadorDES.init(56);
            SecretKey claveSecretaDES = generadorDES.generateKey();
            
            /*
            vamos a crear un secretkeyfactory usando las transformaciones de la
            clave secreta para poder cifra un mensaje, firmarlo y verificarlo
            */
            SecretKeyFactory secretkeyfactoryDES = SecretKeyFactory.getInstance("DES");
            
            //volcado de claves publicas y privadas
            
            /*
        
            //vamos a escribirla en un archivo
            FileOutputStream out = new FileOutputStream(args[0]+".secreta");
            out.write(claveSecretaDES.getEncoded());
            out.close();
            
            byte[] buffersecret = new byte[5000];
            in = new FileInputStream(args[0]+".secreta");
            in.read(buffersecret, 0, 5000);
            in.close();
            
            DESKeySpec DESspec = new DESKeySpec(buffersecret);
            SecretKey claveSecreta2 = secretkeyfactoryDES.generateSecret(DESspec);
            
            if(claveSecreta.equals(claveSecreta2)){
            
            
            */
        }
    }

    public static void mensajeAyuda() {
        System.out.println("Ejemplo de almacenamiento de llaves");
    }
    
}
