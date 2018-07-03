package com.dev.geomaster;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.MyImageViewHolder> {

    List<ClientModel> clients;
    private Context context;
    private OnclickRecyclerListener recyclerListener;

    // private ArrayList<ClientModel> clients;

    
    public ClientAdapter(Context context, List<ClientModel> clients, OnclickRecyclerListener listener) {
        this.context = context;
        this.recyclerListener = listener;

        this.clients = clients;
       
    }


    @Override
    public ClientAdapter.MyImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        return new ClientAdapter.MyImageViewHolder(itemView, recyclerListener);

    }

    @Override
    public void onBindViewHolder(ClientAdapter.MyImageViewHolder holder, int position) {
        final ClientModel image = clients.get(position);



        holder.name.setText(image.getName());
        holder.location.setText("Lat : "+image.getLatitude() + " Lon : "+image.getLongitude());

        String longV = ""+image.getTime();
        long millisecond = Long.parseLong(longV);
        // or you already have long value of date, use this instead of milliseconds variable.
        String dateString = DateFormat.format("MM/dd/yyyy hh:mm:ss", new Date(millisecond)).toString();
        holder.time.setText(dateString);


    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public void setData(List<ClientModel> clients) {
        this.clients.clear();
        if (clients != null) {
            this.clients.addAll(clients);
        }
        notifyDataSetChanged();
    }

    public void setDataChange(int position, ClientModel image) {
        //clients.get(position).setCaption(image.getCaption());
        notifyItemChanged(position);

    }

    public class MyImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, location, time;
        private WeakReference<OnclickRecyclerListener> listenerWeakReference;


        public MyImageViewHolder(View itemView, OnclickRecyclerListener listener) {
            super(itemView);
            listenerWeakReference = new WeakReference<>(listener);


            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            time = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            listenerWeakReference.get().onClickListener(getAdapterPosition());
        }
    }
}
