package com.example.cakrawalatravelpartner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cardview_bookinglist extends RecyclerView.Adapter<cardview_bookinglist.viewholder>  {
    private Context context;
    private Activity activity;
    private ArrayList book_id, from, to, passenger, date, status;
    cardview_bookinglist(Activity activity, Context context, ArrayList book_id, ArrayList from, ArrayList to, ArrayList passenger, ArrayList date, ArrayList status){
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.from = from;
        this.to = to;
        this.passenger = passenger;
        this.date = date;
        this.status = status;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView list_bookid, list_from, list_to, list_passenger, list_date, list_status;
        LinearLayout mainlayout;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            list_bookid = itemView.findViewById(R.id.list_bookid);
            list_from = itemView.findViewById(R.id.list_from);
            list_to = itemView.findViewById(R.id.list_to);
            list_passenger = itemView.findViewById(R.id.list_passenger);
            list_date = itemView.findViewById(R.id.list_date);
            list_status = itemView.findViewById(R.id.list_payment);
            mainlayout = itemView.findViewById(R.id.mainlayout);
        }
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_cardview_bookinglist, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        holder.list_bookid.setText(String.valueOf(book_id.get(position)));
        holder.list_from.setText(String.valueOf(from.get(position)));
        holder.list_to.setText(String.valueOf(to.get(position)));
        holder.list_passenger.setText(String.valueOf(passenger.get(position)));
        holder.list_date.setText(String.valueOf(date.get(position)));
        holder.list_status.setText(String.valueOf(status.get(position)));
        holder.mainlayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,pay_ticket.class);
                intent.putExtra("book_id", String.valueOf(book_id.get(position)));
                intent.putExtra("book_from", String.valueOf(from.get(position)));
                intent.putExtra("book_to", String.valueOf(to.get(position)));
                intent.putExtra("passenger", String.valueOf(passenger.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }
}
