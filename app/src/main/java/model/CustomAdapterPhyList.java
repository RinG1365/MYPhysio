package model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myphysio.R;

import java.util.List;

public class CustomAdapterPhyList extends RecyclerView.Adapter<CustomAdapterPhyList.ViewHolder>
{

    List<Physio> listPhysio;

    public CustomAdapterPhyList(List<Physio> physio)
    {
        this.listPhysio = physio;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_phy,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Physio physio = listPhysio.get(position);
        holder.txtGetPhyID.setText(physio.getStaffid());
        holder.txtGetPhyName.setText(physio.getPhyName());
        holder.txtGetPhyAge.setText(physio.getPhyAge());
        holder.txtGetPhyGen.setText(physio.getPhyGender());
        holder.txtGetPhyPos.setText(physio.getPhyPos());
        holder.txtGetPhyHos.setText(physio.getPhyHos());
        holder.getPhyRat.setRating(physio.getPhyRate());
    }

    @Override
    public int getItemCount(){
        return listPhysio.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtGetPhyID, txtGetPhyName, txtGetPhyAge,txtGetPhyGen, txtGetPhyPos, txtGetPhyHos;
        RatingBar getPhyRat;

        public ViewHolder(View itemView)
        {
            super(itemView);

            txtGetPhyID = itemView.findViewById(R.id.txtGetPhyID);
            txtGetPhyName = itemView.findViewById(R.id.txtGetPhyName);
            txtGetPhyAge = itemView.findViewById(R.id.txtGetPhyAge);
            txtGetPhyGen = itemView.findViewById(R.id.txtGetPhyGen);
            txtGetPhyPos = itemView.findViewById(R.id.txtGetPhyPos);
            txtGetPhyHos = itemView.findViewById(R.id.txtGetPhyHos);
            getPhyRat = itemView.findViewById(R.id.getPhyRat);
        }

    }
}
