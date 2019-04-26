package com.sheehabe.tourmate.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;
import com.sheehabe.tourmate.BudgetActivity;
import com.sheehabe.tourmate.ImageActivity;
import com.sheehabe.tourmate.Memory.ItemsActivity;
import com.sheehabe.tourmate.Memory.TripActivity;
import com.sheehabe.tourmate.databinding.TourItemViewBinding;
import com.sheehabe.tourmate.pojo.TourInfo;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {
    private TourItemViewBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private TourAdapter adapter;
    //final MediaPlayer mediaPlayer = (MediaPlayer) MediaPlayer.create(this, R.raw.clicksound );
    private OnItemClickListener mListener;
    private Context context;
    private List<TourInfo> tourlist;

    public TourAdapter(Context context, List<TourInfo> tourlist) {
        this.context = context;
        this.tourlist = tourlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from( viewGroup.getContext() );
        TourItemViewBinding tourItemViewBinding = TourItemViewBinding.inflate( layoutInflater,viewGroup,false ) ;

        return new ViewHolder( tourItemViewBinding );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TourInfo CurrentUser= tourlist.get( i );

        viewHolder.bidning.tourNameTV.setText(CurrentUser.getTourName());
        viewHolder.bidning.tourLocationTV.setText(CurrentUser.getTourLocation());
        viewHolder.bidning.tourBudgetTV.setText(CurrentUser.getTourBudget());
        viewHolder.bidning.tourStartDateTV.setText(CurrentUser.getTourStartDate());
        viewHolder.bidning.tourEndDateTV.setText(CurrentUser.getTourEndDate());

        viewHolder.bidning.expenseTV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( context, BudgetActivity.class );
                intent.putExtra( "tourId",CurrentUser.getTourID() );
                context.startActivity( intent );

            }
        } );
        viewHolder.bidning.memoriesTV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( context, ImageActivity.class );
                intent.putExtra( "tourId",CurrentUser.getTourID() );
                context.startActivity( intent );

            }
        } );
        viewHolder.bidning.gallaryTV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( context, ItemsActivity.class );
                intent.putExtra( "tourId",CurrentUser.getTourID() );
                context.startActivity( intent );

            }
        } );
        viewHolder.bidning.walletTV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( context, TripActivity.class );
                intent.putExtra( "tourId",CurrentUser.getTourID() );
                context.startActivity( intent );

            }
        } );




    }

    @Override
    public int getItemCount() {
        return tourlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        private TourItemViewBinding bidning;
        public ViewHolder(TourItemViewBinding tourItemViewBinding) {
            super(tourItemViewBinding.getRoot());
            bidning=tourItemViewBinding;
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
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
    }
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onEditItemClick(int position);

        void onDeleteItemClick(int position);
    }

    public void setOnItemClickListener(TourAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

}
