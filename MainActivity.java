import java.io.ByteArrayOutputStream;

import java.nio.charset.Charset;

import java.util.Locale;

 

import android.app.Activity;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.graphics.drawable.AnimationDrawable;

import android.nfc.NdefMessage;

import android.nfc.NdefRecord;

import android.nfc.NfcAdapter;

import android.os.Bundle;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.ImageButton;

import android.widget.ImageView;

import android.widget.TextView;

 

/*

 * 클래스 명 : NFC_WAIT

 * 클래스 기능 : 애니메이션 기능, 버튼클릭시 버튼이미지 교체 기능, NFC통신 발신 기능

 * 클래스 설명 : 이 클래스는 frame 애니메이션을 진행하고

 *                NFC단말기에 접촉시 텍스트 데이터를 전송하는 클래스이다.

 */

 

public class NFC_WAIT extends Activity {

 

       /*

        * 위젯 관련 변수

        */

       private TextView           text; //텍스트 뷰 변수

      

       /*

        * NFC 통신 관련 변수

        */

       private NfcAdapter         nfcAdapter;

       private NdefMessage         mNdeMessage; //NFC 전송 메시지

      

     

      

       /*

        * 액티비티 화면을 만들고 xml 파일을 호출하는 메소드이다.

        * 이 함수내에 사용하는 위젯을 선언한다.

        * 메소드의 소스 형식과 순서는 다음과 같다.

        * 1. 위젯 지정

        * 2. NFC 단말기 정보 가져오기

        * 3. NdefMessage 타입 mNdeMessage 변수에 NFC 단말기에 보낼 정보를 넣는다.

        */ 


       @Override

       public void onCreate(Bundle savedInstanceState) {

           super.onCreate(savedInstanceState);

           setContentView(R.layout.nfc_wait);

           // TODO Auto-generated method stub

          

           /*

            * 1. 위젯 지정

            */

           text = (TextView)findViewById(R.id.text);                                                              // 텍스트뷰

          

      

         

          

           /*

            * 2. NFC 단말기 정보 가져오기

            */

           nfcAdapter = NfcAdapter.getDefaultAdapter(this); // nfc를 지원하지않는 단말기에서는 null을 반환.

          

           if(nfcAdapter != null)

           {

              text.setText("NFC 단말기를 접촉해주세요"+nfcAdapter+"");

           }

           else

           {

              text.setText("NFC 기능이 꺼져있습니다. 켜주세요"+nfcAdapter+"");

           }

          

           /*

            * 3. NdefMessage 타입 mNdeMessage 변수에 NFC 단말기에 보낼 정보를 넣는다.

            */

           mNdeMessage=new NdefMessage(

                     new NdefRecord[]{

                                  createNewTextRecord("이름 : 홍길동", Locale.ENGLISH, true),                        //텍스트 데이터

                                  createNewTextRecord("전화번호 : 010-1234-5678", Locale.ENGLISH, true),    //텍스트 데이터

                                  createNewTextRecord("자격증번호 : 123456", Locale.ENGLISH, true),           //텍스트 데이터

                                  createNewTextRecord("전화번호 : 111-2222-3333", Locale.ENGLISH, true),    //텍스트 데이터

                                  createNewTextRecord("된다!! : 우왕!!", Locale.ENGLISH, true),                  //텍스트 데이터

                                 

                     }

           );

          

       }

      

      

       /*

        * 액티비티 화면이 나오기 전에 실행되는 메소드이다.

        * onCreate 에서 정한 mNdeMessage 의 데이터를 NFC 단말기에 전송한다.

        */

       @Override

       protected void onResume() {

 

           super.onResume();

        

           if (nfcAdapter != null) {

             

              nfcAdapter.<s>enableForegroundNdefPush</s>(this, mNdeMessage);

             

           }

          

       }

 

       /*

        * 액티비티 화면이 종료되면 NFC 데이터 전송을 중단하기 위해 실행되는 메소드이다.

        */

       @Override

       protected void onPause() {

            

             super.onPause();

            

             if (nfcAdapter != null) {

                

                    nfcAdapter.<s>disableForegroundNdefPush</s>(this);

                   

             }

 

       }

      

       /*

        * 텍스트 형식의 데이터를 mNdeMessage 변수에 넣을 수 있도록 변환해 주는 메소드이다.

        */

       public static NdefRecord createNewTextRecord(String text, Locale locale, boolean encodelnUtf8){

            

             byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

            

             Charset utfEncoding = encodelnUtf8 ? Charset.forName("UTF-8"):Charset.forName("UTF-16");

            

             byte[] textBytes = text.getBytes(utfEncoding);

            

             int utfBit = encodelnUtf8 ? 0:(1<<7);

             char status = (char)(utfBit + langBytes.length);

             byte[] data = new byte[1 + langBytes.length + textBytes.length];

             data[0] = (byte)status;

             System.arraycopy(langBytes, 0, data, 1, langBytes.length);

             System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

            

             return new NdefRecord(NdefRecord.TNF_WELL_KNOWN,NdefRecord.RTD_TEXT, new byte[0], data);

       }

      

}
