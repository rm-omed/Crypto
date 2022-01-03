package com.rh.cesarschats;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chat_Room_V extends AppCompatActivity {

    private Button btn_send_msg;
    private EditText input_msg,input_cle,input_pw;
    private ListView chat_conversation;
    private String cryptmsg;

    ArrayList<Msg> list ;
    private String user_name,room_name;
    private DatabaseReference root ;
    private String temp_key;
    ListView listMsg;
    private  MsgListAdapter adapter;
    private String pw;
    private CryptageC c;
    private CryptageV v;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_v);

        listMsg=(ListView) findViewById(R.id.listMsg2);


        list= new ArrayList<Msg>();


        //list=new
        v =new CryptageV();
        //c =new CryptageC();


        btn_send_msg = (Button) findViewById(R.id.btn_send2);
        input_msg = (EditText) findViewById(R.id.msg_input2);


        input_pw = (EditText) findViewById(R.id.motpass);
        chat_conversation = (ListView) findViewById(R.id.listMsg2);

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        setTitle(" Room - "+room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);


        listMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                //int p= ;
                String msgbefore=  list.get(i).getMsg();
                // System.out.println(msgbefore);
                decrypt_msg( msgbefore);
            }
        });

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                String cryptedmsg=CryptageV.crypt(input_msg.getText().toString(),input_pw.getText().toString());
                map2.put("msg",cryptedmsg);

                message_root.updateChildren(map2);
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String chat_msg,chat_user_name;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

            Msg msgs = new Msg(chat_user_name,chat_msg);

            list.add(msgs);

            // chat_conversation.append(chat_user_name +" : "+chat_msg +" \n");
        }
        adapter = new MsgListAdapter(getApplicationContext(), R.layout.adapter_view_layout, list);
        listMsg.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }
    private void showdecryptmsg (String s ){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Decrypted MSG : "+ s);

        builder.setNegativeButton("Done.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });

        builder.show();
    }


    private void decrypt_msg(String msgbefore) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Decrypting MSG : ");

        final EditText input_cle = new EditText(this);
        input_cle.setHint("Password...");
        input_cle.setInputType(InputType.TYPE_CLASS_TEXT);

        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        // lay.addView(output_field);

        lay.addView(input_cle);

        builder.setView(lay);

        builder.setPositiveButton("Decrypt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                pw=input_cle.getText().toString();


                String msgafter= CryptageV.decrypt(msgbefore,pw);
                // Toast.makeText(getApplicationContext(), msgafter, Toast.LENGTH_SHORT).show();
                showdecryptmsg(msgafter);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });

        builder.show();
    }

}

