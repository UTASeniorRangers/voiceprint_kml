package com.varvet.barcodereadersample;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.CardView;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.octoprint.api.model.OctoPrintFileInformation;
import java.util.List;


public class ListAdapterWithRecyclerView extends RecyclerView.Adapter<ListAdapterWithRecyclerView.ItemViewHolder> {

    private static final String TAG = ListAdapterWithRecyclerView.class.getSimpleName();

    private List<OctoPrintFileInformation> ModalsList;
    private Context context;



    public ListAdapterWithRecyclerView(Context context, List<OctoPrintFileInformation> modals) {
        this.ModalsList = modals;
        this.context = context;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_file_row_item,parent,false);

        return new ItemViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        holder.textViewFileName.setText(this.ModalsList.get(position).getName());


        if(Double.valueOf(this.ModalsList.get(position).getSize()/1024.0) >= 1024.0)
        {
            holder.textViewFileSize.setText(String.format("%.2f", this.ModalsList.get(position).getSize()/1048576.0)+"MB");
        }
        else {
            holder.textViewFileSize.setText(String.format("%.2f", this.ModalsList.get(position).getSize()/1024.0)+"KB");
        }

        DateTimeFormatter fmt = ISODateTimeFormat.date();
        String date = fmt.print(this.ModalsList.get(position).getDate());
        holder.textViewFileUploaded.setText(date);

    }


    @Override
    public int getItemCount() {
        return ModalsList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewFileName;
        public TextView textViewFileUploaded;
        public TextView textViewFileSize;

        public ItemViewHolder(View view) {
            super(view);
            textViewFileName = (TextView) view.findViewById(R.id.fileName);
            textViewFileUploaded = (TextView) view.findViewById(R.id.fileUploaded);
            textViewFileSize = (TextView) view.findViewById(R.id.fileSize);
        }
    }


}
