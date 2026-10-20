package com.mnisi.smartpantrymanager.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mnisi.smartpantrymanager.R;
import com.mnisi.smartpantrymanager.database.PantryDatabase;
import com.mnisi.smartpantrymanager.model.PantryItem;
import java.util.List;
import java.util.concurrent.Executors;
// RecyclerView adapter for pantry list - manages item display
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {
    private List<PantryItem> list;
    private PantryDatabase db;

    public PantryAdapter(List<PantryItem> list, PantryDatabase db) {
        this.list = list;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PantryItem item = list.get(position);
        holder.txtName.setText(item.getName() + " - " + item.getQuantity() + " " + item.getUnit());

        // LONG PRESS = UPDATE / DELETE - This gives you CRUD
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle(item.getName())
                    .setMessage("Choose action")
                    .setItems(new String[]{"Edit (Update)", "Delete"}, (dialog, which) -> {
                        if (which == 0) {
                            // ===== UPDATE =====
                            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_add_item, null);
                            EditText edtName = dialogView.findViewById(R.id.edtName);
                            EditText edtQty = dialogView.findViewById(R.id.edtQuantity);
                            EditText edtUnit = dialogView.findViewById(R.id.edtUnit);

                            edtName.setText(item.getName());
                            edtQty.setText(String.valueOf(item.getQuantity()));
                            edtUnit.setText(item.getUnit());

                            new AlertDialog.Builder(v.getContext())
                                    .setTitle("Update Item")
                                    .setView(dialogView)
                                    .setPositiveButton("Update", (a, b) -> {
                                        String name = edtName.getText().toString().trim();
                                        String qtyStr = edtQty.getText().toString().trim();
                                        String unit = edtUnit.getText().toString().trim();

                                        if (name.isEmpty()) {
                                            Toast.makeText(v.getContext(), "Name required", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (qtyStr.isEmpty()) {
                                            Toast.makeText(v.getContext(), "Quantity required", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        try {
                                            double newQty = Double.parseDouble(qtyStr);
                                            if (newQty <= 0) throw new NumberFormatException();

                                            item.setName(name);
                                            item.setQuantity(newQty);
                                            item.setUnit(unit);

                                            Executors.newSingleThreadExecutor().execute(() -> {
                                                db.pantryDao().update(item);
                                            });
                                            notifyItemChanged(holder.getAdapterPosition());
                                            Toast.makeText(v.getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                        } catch (NumberFormatException e) {
                                            Toast.makeText(v.getContext(), "Enter valid number > 0", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();
                        } else {
                            // ===== DELETE =====
                            new AlertDialog.Builder(v.getContext())
                                    .setTitle("Delete?")
                                    .setMessage("Delete " + item.getName() + "?")
                                    .setPositiveButton("Delete", (a,b) -> {
                                        int pos = holder.getAdapterPosition();
                                        PantryItem toDelete = list.get(pos);
                                        Executors.newSingleThreadExecutor().execute(() -> {
                                            db.pantryDao().delete(toDelete);
                                        });
                                        list.remove(pos);
                                        notifyItemRemoved(pos);
                                        Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();
                        }
                    }).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtPantryName);
        }
    }
}