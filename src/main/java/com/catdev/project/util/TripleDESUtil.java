package com.catdev.project.util;

import org.apache.commons.codec.CharEncoding;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class TripleDESUtil {
    private static final String UNICODE_FORMAT = CharEncoding.UTF_8;
    private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    private SecretKey key;

    public TripleDESUtil(String keyStr) throws Exception {
        myEncryptionKey = keyStr;
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        byte[] arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        byte[] keyBytes = Arrays.copyOf(arrayBytes, 24);

        ks = new DESedeKeySpec(keyBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }

    public String encrypt(String unencryptedString)  throws Exception{
        String encryptedString = null;
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
        byte[] encryptedText = cipher.doFinal(plainText);
        encryptedString = new String(Base64.encodeBase64(encryptedText));
        return encryptedString;
    }


    public String decrypt(String encryptedString) throws Exception{
        String decryptedText=null;
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedText = Base64.decodeBase64(encryptedString.getBytes(UNICODE_FORMAT));
        byte[] plainText = cipher.doFinal(encryptedText);
        decryptedText= new String(plainText);
        return decryptedText;
    }
}
