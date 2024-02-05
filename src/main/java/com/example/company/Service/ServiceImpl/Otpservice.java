package com.example.company.Service.ServiceImpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class Otpservice {
    private static  final Integer EXPIRE_MINS=10;
    private LoadingCache<String,Integer>otpCache;

    public Otpservice(){
        super();
        otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) throws Exception {
                return null;
            }

        });

    }
    public  int generateOtp(String key){
        Random random = new Random();
        int otp = 100000+random.nextInt(900000);
        otpCache.put(key,otp);
        return otp;
    }
    public Integer getOtp(String key){
        try {
            return otpCache.get(key);
        } catch (ExecutionException e) {
            return null;
        }
    }
    public void clearOTP(String key){
        otpCache.invalidate(key);
    }

}
