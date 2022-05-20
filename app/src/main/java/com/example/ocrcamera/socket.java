package com.example.ocrcamera;


import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import android.os.Handler;

public class socket {
    private Handler mHandler;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private final String ip = "10.0.2.2";
    //private String ip = "121.128.170.170";
    private final int port = 7070;
    private String str;
    ByteArrayOutputStream byteArray;

    private String id;
    private String place;
    private String money;
    private String year;
    private String month;
    private String day;


    public String readString(DataInputStream dis) throws IOException {
        int length = dis.readInt();
        byte[] data = new byte[length];
        dis.readFully(data, 0, length);
        String text = new String(data, StandardCharsets.UTF_8);

        return text;
    }

    public ArrayList<String> receiveData() {
        ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.add(id);
        arrayList.add(place);
        arrayList.add(money);
        arrayList.add(year);
        arrayList.add(month);
        arrayList.add(day);


        return arrayList;
    }

    void connect(Bitmap bitmap){
        mHandler = new Handler();
        byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);

        Thread sendImg_getText = new Thread(){

            final byte[] bytes = byteArray.toByteArray();

            @Override
            public void run() {
                super.run();

                try {
                    socket = new Socket(ip, port);
                    Log.d("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.d("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                try {
                    dos = new DataOutputStream(socket.getOutputStream()); // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream()); // input에 받을꺼 넣어짐

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }

                try{
                    dos.writeUTF(Integer.toString(bytes.length));
                    dos.flush();

                    dos.write(bytes);
                    dos.flush();

                    str = readString(dis);

                    String[] array = str.split(",");
                    id = array[0];
                    place = array[1];
                    money = array[2];
                    year = array[3];
                    month = array[4];
                    day = array[5];

                    Log.w("읽어온 문자열", id);

                    socket.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        // Thread 시작
        sendImg_getText.start();

        try{
            sendImg_getText.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }



}
