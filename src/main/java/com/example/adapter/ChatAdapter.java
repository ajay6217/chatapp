package com.example.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.R;
import com.example.whatsapp.models.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    ArrayList<Messages> messages;
    Context context;
    String recid;
    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<Messages> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    public ChatAdapter(ArrayList<Messages> messages, Context context, String recid) {
        this.messages = messages;
        this.context = context;
        this.recid = recid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.sender,parent,false);
            return new SenderViewHolder(view);
        }

        else{
            View view= LayoutInflater.from(context).inflate(R.layout.samplerec,parent,false);
            return new RecieverViewHolder(view);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else{
            return RECEIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages message=messages.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("Delete").setMessage("Are you sure you want to delete the message")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String sender =FirebaseAuth.getInstance().getUid()+recid;
                                database.getReference().child("chats").child(sender).child(message.getMessageid())
                                        .setValue(null);

                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                }).show();
                return false;
            }
        });
        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).sendermsg.setText(message.getMessage());
        }
        else{
            ((RecieverViewHolder)holder).recivermsg.setText(message.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView recivermsg,rectime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recivermsg=itemView.findViewById(R.id.reciver);
            rectime=itemView.findViewById(R.id.timer);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView sendermsg,times;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermsg=itemView.findViewById(R.id.send);
            times=itemView.findViewById(R.id.times);

        }
    }
}
