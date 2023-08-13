package com.example.hourglass;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    ManualActivity manualActivity;
    ArrayList<Subject> list;
    onClickInterface onclickInterface;

    public SubjectAdapter(ArrayList<Subject> list, onClickInterface onclickInterface) {
        this.list = list;
        this.onclickInterface = onclickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_manual, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Get the Subject based on the current position
        Subject currentItem = list.get(position);

        manualActivity = new ManualActivity();

        // we face problem with the holder.cardView.setCardBackgroundColor()
        if (currentItem.isClicked()) {
            holder.arrowDown.setVisibility(View.VISIBLE);
//            holder.itemView.setActivated(true);
            holder.subject_numberOfPlayers.setActivated(true);
            holder.subject_withOrNotPause.setActivated(true);
//            holder.cardView.setCardBackgroundColor(manualActivity.getResources().getColor(R.color.primary_fifth));
//            holder.cardView.setCardBackgroundColor(R.color.secondary_sixth);
        } else {
//            holder.itemView.setActivated(false);
            holder.arrowDown.setVisibility(View.INVISIBLE);
            holder.subject_numberOfPlayers.setActivated(false);
            holder.subject_withOrNotPause.setActivated(false);
//            holder.cardView.setCardBackgroundColor(R.color.secondary_first);
//            holder.cardView.setCardBackgroundColor(manualActivity.getResources().getColor(R.color.secondary_first));
//                        holder.cardView.setCardBackgroundColor(R.color.secondary_seventh);

        }

        // Setting views with the corresponding data
        if (position == 0) {
            ImageView imageViewGeneral = holder.subject_general;
            imageViewGeneral.setImageResource(currentItem.getImageIDgeneral());
        }

        ImageView imageView1 = holder.subject_numberOfPlayers;
        imageView1.setImageResource(currentItem.getImageID1());

        ImageView imageView2 = holder.subject_withOrNotPause;
        imageView2.setImageResource(currentItem.getImageID2());

        TextView textView = holder.subjectName;
        textView.setText(currentItem.getName());


//        holder.itemView.findViewById(R.id.view_manual_arrowDown).setVisibility(View.INVISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                for (Subject subjects : list) {
                    subjects.setClicked(false);
                }

//                v.findViewById(R.id.view_manual_arrowDown).setVisibility(View.VISIBLE);
                currentItem.setClicked(true);
                holder.itemView.setPressed(true);

                notifyDataSetChanged();
                onclickInterface.setClick(position);
            }
        });


    }

//    @SuppressLint("ResourceAsColor")
//    public void ifIsNotNull(){
//        if(cardView != null){
//            cardView.setCardBackgroundColor(R.color.secondary_seventh);}
//        if(view != null){
//            view.setVisibility(View.VISIBLE);
//        }
//    }


    // Indicating how long your data is
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView subject_general;
        public ImageView subject_numberOfPlayers;
        public ImageView subject_withOrNotPause;
        public TextView subjectName;
        public View arrowDown;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject_general = itemView.findViewById(R.id.subject_image_general);
            subject_numberOfPlayers = itemView.findViewById(R.id.subject_image_numberOfPlayers);
            subject_withOrNotPause = itemView.findViewById(R.id.subject_image_withOrNotPause);
            subjectName = itemView.findViewById(R.id.subject_name);
            arrowDown = itemView.findViewById(R.id.view_manual_arrowDown);
            cardView = itemView.findViewById(R.id.cardview_manualActivity);
        }
    }
}
