package com.hog2020.ex60notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickbtn(View view) {

        //알림(notification)을  관리하는 관리자객체 소환
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //알림객체생성(Builder)
        NotificationCompat.Builder builder= null;

        //Oreo 버전 이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //알림채널 객체생성
            NotificationChannel channel=new NotificationChannel("ch01","Mychannel",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);

            //알림채널에 사운드 설정
            Uri uri =Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.get_gem);
            channel.setSound(uri, new AudioAttributes.Builder().build());

            builder =new NotificationCompat.Builder(this,"ch01");
        }else {
            builder=new NotificationCompat.Builder(this,null);
            //빌더에게 사운드 설정
            Uri uri =Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.get_gem);
            builder.setSound(uri);
        }

        //알림객체에게 원하는 알림의 모양을 설정

        //상태표시줄에 보이는 아이콘
        builder.setSmallIcon(R.drawable.ic_noti);

        //상태바를 드래그하여 아래로 내리면 보이는
        //알림창 의 설정
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.hasung);
        builder.setLargeIcon(bm);
        builder.setContentTitle("Title");
        builder.setContentText("Massage");
        builder.setSubText("sub text");

        //알림창을 클릭할때 실행할 작업(새로운 화면 Activity) 설정
        Intent intent =new Intent(this,SecondActivity.class);
        //인텐트 객체에게 바로 실행하지 말고 잠시 보류해달라고..
        //보류 중인 인텐트 객체 로 만들기
        PendingIntent pendingIntent =PendingIntent.getActivity(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //알림을 클릭했을때 자동으로아이콘 없애기
        builder.setAutoCancel(true);

        //알림에 진동의패턴 설정하기-진동은사용자에게 진동을 줄 수 있다고 하기[permission]
        builder.setVibrate(new long[]{0,2000,1000,3000});//0초대기,2초진동,1초대기,3초진동

        //요즘 종종보이는 알림창 스타일
        //1.BigPictureStyle
        NotificationCompat.BigPictureStyle pictureStyle =new NotificationCompat.BigPictureStyle(builder);
        pictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.hasung));

        //2.BigTextStyle

        //3.InBoxStyle:여러메세지를 보여줄때
        NotificationCompat.InboxStyle inboxStyle =new NotificationCompat.InboxStyle();
        inboxStyle.addLine("first");
        inboxStyle.addLine("second");
        inboxStyle.addLine("안녕하세요");

        //4.NodiaStyle :플레이와 스톱버튼이 있는알림장 스타일

        builder.setProgress(100,50,false);


        //설정대로 알림객체 생성해 달라고
        Notification notification =builder.build();

        //알림메니저에게 알림을 요청
        notificationManager.notify(10,notification);//아이디,알림객체
    }
}