//package com.example.service.sms;
//
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//@Slf4j
//@Service
//public class SmsService {
//   /* @Value("${sms.url}")
//    private String smsUrl;
//    @Value("${my.eskiz.uz.email}")
//    private String myEskizUzEmail;
//
//    @Value("${my.eskiz.uz.password}")
//    private String myEskizUzPassword;
//*/
//    public String sendSms(String phone) {
////        String code = RandomUtil.getRandomSmsCode();
//        String message = "This is test from Eskiz";
//        send(phone, message);
//        return null;
//    }
//
//    private void send(String phone, String message) {
//        try {
//            OkHttpClient client = new OkHttpClient();
//
//            RequestBody body = new FormBody.Builder()
//                    .add("mobile_phone", phone)
//                    .add("message", message)
//                    .add("from", "4546")
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url("https://notify.eskiz.uz/api/message/sms/send")
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MzI0NjQyMzUsImlhdCI6MTcyOTg3MjIzNSwicm9sZSI6InRlc3QiLCJzaWduIjoiNWJkYzI3NjQ0YmUxYmFlMTFjYzE4NWMwNzU5MjIzNGUzNmMxMTZhMTU4ZWI2ZWMwZDJiZTgyN2ZjNGU4NDc4OSIsInN1YiI6Ijg1MjIifQ.whMykb4RHHv79k6eoUgfIhq54sAQSWAuEhVCtoCCNn8")
//                    .post(body)
//                    .build();
//            Call call = client.newCall(request);
//            Response response=call.execute();
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
