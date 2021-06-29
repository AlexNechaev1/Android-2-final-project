package com.example.android_2_final_project.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.models.ChatModel;
import com.example.android_2_final_project.models.UserModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class ChatListRecyclerViewAdapter extends FirebaseRecyclerAdapter<String, ChatListRecyclerViewAdapter.ChatViewHolder > {

    public interface ChatListAdapterListener{
        void OnCellClicked(String contactID);
    }

    private ChatListAdapterListener listener;
    private final Context mContext;

    public ChatListRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<String> options, Context context, ChatListAdapterListener listener) {
        super(options);
        mContext = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat_instance, parent, false);

        return new ChatViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull  ChatListRecyclerViewAdapter.ChatViewHolder holder, int position, @NonNull String string) {

         String contactID = getRef(position).getKey();
        FirebaseDatabase.getInstance().getReference().child("users").child(contactID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){

                    UserModel user = Objects.requireNonNull(task.getResult()).getValue(UserModel.class);
                    Glide.with(mContext)
                            .load(Objects.requireNonNull(user).getProfileImage())
                            .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(50)))
                            .into(holder.profileIv);

                    holder.contactName.setText(user.getUsername());
                }
            }
        });
//        holder.contactName.setText(string);
//        Glide.with(mContext).load(model.getContact().getProfileImage()).into(holder.profileIv);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{

        private ImageView profileIv;
        private TextView contactName;

        public ChatViewHolder(@NonNull  View itemView) {
            super(itemView);

            profileIv = itemView.findViewById(R.id.profile_img);
            contactName = itemView.findViewById(R.id.profile_username_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String contactID = getRef(getAdapterPosition()).getKey();
                    listener.OnCellClicked(contactID);
                }
            });
        }
    }

}
