package com.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adapter.ChatAdapter;
import com.example.whatsapp.MainActivity;
import com.example.whatsapp.R;
import com.example.whatsapp.databinding.ActivityChatdetailBinding;
import com.example.whatsapp.models.Messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatdetail extends AppCompatActivity {
    ActivityChatdetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatdetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        final String senderId= auth.getUid();
        String recId=getIntent().getStringExtra("userId");
        String username=getIntent().getStringExtra("userName");
        String propic=getIntent().getStringExtra("profpic");
        binding.userNaam.setText(username);
        Picasso.get().load(propic).placeholder(R.drawable.circleimg).into(binding.profileimg);
        binding.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(chatdetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
        final ArrayList<Messages> messages=new ArrayList<>();

        final ChatAdapter chatAdapter=new ChatAdapter(messages,this,recId);
        binding.chatrec.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        final String senderroom=senderId+recId;
        final String recevierRoom=recId+senderId;
        database.getReference().child("chats").child(senderroom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messages.clear();
                        for (DataSnapshot snapshot1:snapshot.getChildren()
                             ) {Messages model=snapshot1.getValue(Messages.class);
                            model.setMessageid(snapshot1.getKey());
                            messages.add(model);

                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.chatrec.setLayoutManager(linearLayoutManager);
        binding.sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etmsg.getText().toString().isEmpty()){
                    return;
                }
               String msg = binding.etmsg.getText().toString();
               final Messages messages1=new Messages(senderId,msg);
               messages1.setTimestamp(new Date().getTime());
               binding.etmsg.setText("");
               database.getReference().child("chats")
                       .child(senderroom).push().setValue(messages1).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       database.getReference().child("chats").child(recevierRoom).push().setValue(messages1).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {

                           }
                       });

                   }
               });
            }
        });

    }
}