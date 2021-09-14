package com.computer.getkeyhashandsha1keyapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Formatter;

public class KeysHashUtility
{
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void getKeys(Context context)
    {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES
            );

            for (Signature sig : packageInfo.signatures)
            {
                MessageDigest msgKeyHashDigest = MessageDigest.getInstance("SHA");
                msgKeyHashDigest.update(sig.toByteArray());

                MessageDigest msgSHA256Digest = MessageDigest.getInstance("SHA-256");
                msgSHA256Digest.update(sig.toByteArray());


                String sha1 = byteToHex(msgKeyHashDigest.digest());
                String sha256 = byteToHex(msgSHA256Digest.digest());
                String keyHash = new String(Base64.getEncoder().encode(msgKeyHashDigest.digest()));


                Log.d("Key Sha1", "SHA-1=" + sha1);
                Log.d("Key Sha256", "SHA-256=" + sha256);
                Log.d("Key Hashes", "KeyHash=" + keyHash);
            }
        }
        catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    //sha1 formatter
    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
