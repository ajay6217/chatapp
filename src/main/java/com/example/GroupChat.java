package com.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adapter.ChatAdapter;
import com.example.whatsapp.MainActivity;
import com.example.whatsapp.R;
import com.example.whatsapp.databinding.ActivityGroupChatBinding;
import com.example.whatsapp.models.Messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChat extends AppCompatActivity {
    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupChat.this, MainActivity.class);
                startActivity(intent);

            }
        });


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<Messages> messages = new ArrayList<>();
        final String userid = FirebaseAuth.getInstance().getUid();
        binding.userNaam.setText("Friends");
        final ChatAdapter adapter = new ChatAdapter(messages, this);
        binding.chatrec.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatrec.setLayoutManager(layoutManager);
        database.getReference().child("Group Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot dataSnapshot:
                 snapshot.getChildren()){
                    Messages model=dataSnapshot.getValue(Messages.class);
                    messages.add(model);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mess=binding.etmsg.getText().toString();
                //
                if(mess.isEmpty()){
                    return;
                }
                final Messages model=new Messages(userid,mess);
                model.setTimestamp(new Date().getTime());
                binding.etmsg.setText("");
                database.getReference().child("Group Chat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
            }
        });
    }


}