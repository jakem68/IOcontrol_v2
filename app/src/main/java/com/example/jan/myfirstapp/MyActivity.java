package com.example.jan.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

//import android.widget.Toast;
//import java.io.OutputStream;


public class MyActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    public final static String EXTRA_MESSAGE = "com.example.jan.myfirstapp.MESSAGE";
    private static final int serverPort = 44332;
    private static final String serverIP = "192.168.4.1";
    int _nrButtons = 12;
    ToggleButton[] _myToggleButtons = new ToggleButton[_nrButtons];
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        new Thread(new ClientThread()).start();

        for(int i=0; i<_nrButtons; i++) {
            //look for the "ID" of switch i
            String toggleButtonID = "toggleButton" + (i+1);

            //get the "resource ID" of switch i
            int resID = getResources().getIdentifier(toggleButtonID, "id", getPackageName());

            Log.d("TAG", Float.toString(resID));

            //myToggleButton for ToggleButton i
            _myToggleButtons[i] = (ToggleButton) findViewById(resID);
            _myToggleButtons[i].setTextOff("Led "+(i+1));
            _myToggleButtons[i].setTextOn("Led "+(i+1));
            _myToggleButtons[i].setChecked(false);
            _myToggleButtons[i].setOnCheckedChangeListener(this);

        }
//        SwitchCompat switchCompat1 = (SwitchCompat) findViewById(R.id.switch1);
//        switchCompat1.setOnCheckedChangeListener(onCheckedChanged());
//        SwitchCompat switchCompat2 = (SwitchCompat) findViewById(R.id.switch2);
//        switchCompat2.setOnCheckedChangeListener(onCheckedChanged());
//        SwitchCompat switchCompat3 = (SwitchCompat) findViewById(R.id.switch3);
//        switchCompat3.setOnCheckedChangeListener(onCheckedChanged());
//        SwitchCompat switchCompat4 = (SwitchCompat) findViewById(R.id.switch4);
//        switchCompat4.setOnCheckedChangeListener(onCheckedChanged());
    }

    /** Called when the user clicks the Send button */
    public void storeMessage (View view) throws IOException {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = "";
        message = editText.getText().toString();

        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);

        sendMessage(message);

    }


    public void sendMessage (String message) throws IOException {

        new Thread(new ClientThread()).start();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        out.println(message);

        try
        {
            Thread.sleep(500);
        }
        catch (Exception e){}

        out.flush();
        out.close();
    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int resID;
        String isCheckedValue;
        if (isChecked){
            isCheckedValue = "1";
        }
        else {
            isCheckedValue = "0";
        }
////////////////TODO: Try to make a fancy button
//        http://angrytools.com/android/button/

        for (int i=0; i<_nrButtons; i++) {
            ToggleButton myToggleButton = _myToggleButtons[i];
            String toggleButtonID = "toggleButton" + (i+1);
            //get the "resource ID" of switch i
            resID = getResources().getIdentifier(toggleButtonID, "id", getPackageName());
            if (buttonView.getId()==resID){
                //createMessage
                String message = (Integer.toString(i+1)+","+isCheckedValue);
                    Log.d("TAG", message);
                try {
                    sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


//    http://stackoverflow.com/questions/15732307/how-to-tell-which-button-was-clicked-in-onclick
//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        switch(v.getId())
//        {
//            case R.id.button1 :
//                Toast.makeText(MainActivity.this,"button1", 1000).show();
//                break;
//            case R.id.button2 :
//                Toast.makeText(MainActivity.this,"button2", 1000).show();
//                break;
//            case R.id.button3 :
//                Toast.makeText(MainActivity.this,"button3", 1000).show();
//                break;
//
//
//        }
//
//    }





//
/*
//    http://stackoverflow.com/questions/15732307/how-to-tell-which-button-was-clicked-in-onclick
///////////////////TO BE REWORKED!!!!!!!!!!!!!!!!!!!!!!!!
    //called when a toggle switch changes state
    private CompoundButton.OnCheckedChangeListener onCheckedChanged() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.switch1:
                        if(isChecked){
                            try {
                                sendMessage("1,1");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("MyApp","LED 1 aan");
                        }
                        else {
                            try {
                                sendMessage("1,0");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("MyApp","LED 1 uit");
                        }
                        break;
                    case R.id.switch2:
                        if(isChecked){
                            try {
                                sendMessage("2,1");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("MyApp","LED 2 aan");
                        }
                        else {
                            try {
                                sendMessage("2,0");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("MyApp","LED 2 uit");
                        }
                        break;
                    case R.id.switch3:
                        if(isChecked){
                            try {
                                sendMessage("3,1");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("MyApp","LED 3 aan");
                        }
                        else {
                            try {
                                sendMessage("3,0");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("MyApp","LED 3 uit");
                        }
                        break;
                    case R.id.switch4:
                        //DO something?????  setButtonState(isChecked);
                        if(isChecked){
                            try {
                                sendMessage("4,1");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("MyApp","LED 4 aan");
                        }
                        else {
                            try {
                                sendMessage("4,0");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("MyApp","LED 4 uit");
                        }
                        break;
                }
            }
        };
    }
*/

//

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(serverIP);

                socket = new Socket(serverAddr, serverPort);

            } catch (UnknownHostException e1){
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}

