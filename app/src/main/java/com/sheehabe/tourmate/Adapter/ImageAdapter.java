package com.sheehabe.tourmate.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sheehabe.tourmate.R;
import com.sheehabe.tourmate.pojo.ImageInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by AndroidJSon.com on 6/18/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private ImageAdapter.OnItemClickListener mListener;

    Context context;
    List<ImageInfo> imagelist;

    public ImageAdapter(Context context, List<ImageInfo> imagelist) {

        this.imagelist = imagelist;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.row_model, parent, false );

        ViewHolder viewHolder = new ViewHolder( view );

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageInfo currentImage = imagelist.get( position );

        holder.nameTV.setText( currentImage.getImageName() );
        holder.locationTV.setText(currentImage.getLocation() );

        //Loading image from Glide library.
        Glide.with( context ).load( currentImage.getImageURL() ).into( holder.imageViewIV );
        //Toast.makeText( context, ""+currentImage.getImageURL(), Toast.LENGTH_SHORT ).show();

    }

    @Override
    public int getItemCount() {

        return imagelist.size();
    }

    class ViewHolder extends RecyclerView .ViewHolder implements View.OnClickListener,
    View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener  {


        public TextView nameTV, locationTV;
        public ImageView imageViewIV;

        public ViewHolder(View itemView) {
            super( itemView );
            nameTV = itemView.findViewById( R.id.nameTV );
            locationTV = itemView.findViewById( R.id.locationTV );
            imageViewIV = itemView.findViewById( R.id.imageViewIV );
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick( position );
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle( "Select Action" );
            MenuItem editItem = menu.add( Menu.NONE, 1, 1, "Edit" );
            MenuItem deleteItem = menu.add( Menu.NONE, 2, 2, "Delete" );
            editItem.setOnMenuItemClickListener( this );
            deleteItem.setOnMenuItemClickListener( this );
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            mListener.onEditItemClick( position );
                            return true;
                        case 2:
                            mListener.onDeleteItemClick( position );
                            return true;
                    }
                }
            }
            return false;
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onEditItemClick(int position);

        void onDeleteItemClick(int position);
    }

    public void setOnItemClickListener(ImageAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    private String getDateToday() {
        DateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd" );
        Date date = new Date();
        String today = dateFormat.format( date );
        return today;
    }
}
