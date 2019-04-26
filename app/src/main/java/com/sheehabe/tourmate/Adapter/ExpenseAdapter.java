package com.sheehabe.tourmate.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.sheehabe.tourmate.R;
import com.sheehabe.tourmate.UpdateActivity;
import com.sheehabe.tourmate.pojo.ExpenseInfo;

import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private static Context context;
    private ExpenseAdapter.OnItemClickListener mListener;
    //private ArrayList<ExpenseInfo> arrayList = new ArrayList<>();


    private List<ExpenseInfo> expenseInfo;

    public ExpenseAdapter(Context context, List<ExpenseInfo> expenseInfo) {
        this.context = context;
        this.expenseInfo = expenseInfo;
    }

    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from( viewGroup.getContext() );
        View view = inflater.inflate( R.layout.expense_item_view, viewGroup, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final ExpenseAdapter.ViewHolder viewHolder, final int i) {

        final ExpenseInfo currentUser = expenseInfo.get( i );

        // viewHolder.expenseTypeTV.setText( currentUser.getTypeName() + currentUser.getExpenseID() );

        viewHolder.expenseTypeTV.setText( currentUser.getTypeName() );
        viewHolder.expenseTimeTV.setText( currentUser.getTime() );
        viewHolder.expenseDateTV.setText( currentUser.getDate() );
        viewHolder.takaTV.setText( String.valueOf( currentUser.getCost() ) );
        // Glide.with( context ).load( currentImage.getImageURL() ).into( holder.imageViewIV );


        viewHolder.popUpIV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( context, "Click", Toast.LENGTH_SHORT ).show();
                PopupMenu popupMenu = new PopupMenu( context, viewHolder.popUpIV );
                popupMenu.getMenuInflater().inflate( R.menu.popup_menu, popupMenu.getMenu() );
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.delete_menu:

                                Log.d( TAG, "Delete clicked" );

                                expenseInfo.remove( i );
//                                main problem here
                                // helper.deleteInfo(expenses.getId());
                                notifyDataSetChanged();

                                return true;

                            case R.id.update_menu:
                                Log.d( TAG, "Update clicked" );

                                Intent intent = new Intent( context, UpdateActivity.class );
                                intent.putExtra( "expenseID", expenseInfo.get( i ).getExpenseID() );
                                intent.putExtra( "TypeName", expenseInfo.get( i ).getTypeName() );
                                intent.putExtra( "Date", expenseInfo.get( i ).getDate() );
                                intent.putExtra( "Cost", expenseInfo.get( i ).getCost() );
                                intent.putExtra( "Time", expenseInfo.get( i ).getTime() );
                                context.startActivity( intent );
                                return true;
                        }

                        return false;
                    }
                } );
            }
        } );
    }


    @Override
    public int getItemCount() {
        return expenseInfo.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView expenseTypeTV, expenseDateTV, takaTV, expenseTimeTV;
        private ImageView popUpIV;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );


            expenseTypeTV = itemView.findViewById( R.id.expenseTypeTV );
            expenseDateTV = itemView.findViewById( R.id.expenseDateTV );
            expenseTimeTV = itemView.findViewById( R.id.expenseTimeTV );
            takaTV = itemView.findViewById( R.id.takaTV );
            popUpIV = itemView.findViewById( R.id.popUp );
            itemView.setOnClickListener( this );
            itemView.setOnCreateContextMenuListener( this );


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

    public void setOnItemClickListener(ExpenseAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}

