package com.fereshte.event_reminder.event.ui.main;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fereshte.event_reminder.data.model.EventItem;
import com.fereshte.event_reminder.R;
import com.fereshte.event_reminder.databinding.ItemEventLayoutBinding;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final Context context;
    private final List<EventItem> itemList;
    public OnItemClickListener itemClickListener;

    public EventsAdapter(Context context, List<EventItem> itemList, OnItemClickListener itemClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemEventLayoutBinding binding = ItemEventLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemBinding.eventTitleTxv.setText(itemList.get(position).getEventTitle());
        holder.itemBinding.eventDayOfWeekTxv.setText(itemList.get(position).getEventWeekDay());
        holder.itemBinding.eventTimeTxv.setText(itemList.get(position).getEventTime());
        holder.bind(itemList.get(position), itemClickListener);
        setEventSelectedColor(position, holder.itemBinding.eventColorIv);
        setEventIconVisibility(itemList.get(position).getEventOccasion(), holder.itemBinding.iconEventIcoTxv);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemEventLayoutBinding itemBinding;
        public ViewHolder(@NonNull ItemEventLayoutBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        private void bind(EventItem item, OnItemClickListener listener){
            itemView.setOnClickListener(v -> listener.itemClick(item));
        }
    }

    private void setEventIconVisibility(String occasion, View icon) {
        if (!occasion.equals(""))
            icon.setVisibility(View.VISIBLE);
        else icon.setVisibility(View.GONE);
    }

    private void setEventSelectedColor(int position, View imageView) {
        int[] colorPalette = context.getResources().getIntArray(R.array.colorPalette);
        int color = colorPalette[itemList.get(position).getEventColor()];
        imageView.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    public interface OnItemClickListener{
        void itemClick(EventItem eventItem);
    }
}

