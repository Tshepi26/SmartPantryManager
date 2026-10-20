package com.mnisi.smartpantrymanager.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mnisi.smartpantrymanager.R;
import com.mnisi.smartpantrymanager.RecipeDetailActivity;
import com.mnisi.smartpantrymanager.model.Recipe;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> list;

    public RecipeAdapter(List<Recipe> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe r = list.get(position);
        holder.txtName.setText(r.name);
        holder.txtIngredients.setText("Needs: " + r.ingredients + "\nQty: " + r.requiredQuantities + "\nTap to view method");

        // CLICK TO OPEN DETAIL SCREEN
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), RecipeDetailActivity.class);
            i.putExtra("name", r.name);
            i.putExtra("ingredients", r.ingredients);
            i.putExtra("qty", r.requiredQuantities);
            i.putExtra("steps", r.steps);
            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtIngredients;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtRecipeName);
            txtIngredients = itemView.findViewById(R.id.txtRecipeIngredients);
        }
    }
}