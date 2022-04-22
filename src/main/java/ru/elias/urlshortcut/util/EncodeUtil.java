package ru.elias.urlshortcut.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
@UtilityClass
public final class EncodeUtil {

    public static String generateToken() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException exception) {
            log.error("codeing error. ", exception);
        }
        StringBuilder hexString = new StringBuilder();
        if (md != null) {
            byte[] data = md.digest(RandomStringUtils.randomAlphabetic(10).getBytes());
            for (byte datum : data) {
                hexString.append(Integer.toHexString((datum >> 4) & 0x0F));
                hexString.append(Integer.toHexString(datum & 0x0F));
            }
        }
        return hexString.toString();
    }

    public static String encryptMD5(String url) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(url.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException exception) {
            log.error("codeing error", exception);
        }
        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder hash = new StringBuilder(bigInt.toString(16));
        while (hash.length() < 32) {
            hash.insert(0, "0");
        }
        return hash.toString();
    }

}
