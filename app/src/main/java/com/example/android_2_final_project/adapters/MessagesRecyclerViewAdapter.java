package com.example.android_2_final_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_2_final_project.R;
import com.example.android_2_final_project.models.MessageModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MessagesRecyclerViewAdapter
        extends FirebaseRecyclerAdapter<MessageModel, MessagesRecyclerViewAdapter.MessagesViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MessagesRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<MessageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessagesViewHolder holder, int position, @NonNull MessageModel message) {
        holder.messageTv.setText(message.getMessage());
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_message, parent, false);

        return new MessagesViewHolder(view);
    }

    public class MessagesViewHolder extends RecyclerView.ViewHolder {

        private TextView messageTv;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);

            messageTv = itemView.findViewById(R.id.message_tv);
        }
    }
}
