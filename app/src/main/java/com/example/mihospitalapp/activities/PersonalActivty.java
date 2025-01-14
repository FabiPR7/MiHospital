package com.example.mihospitalapp.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mihospitalapp.R;
import com.example.mihospitalapp.modelo.GestorBD;
import com.example.mihospitalapp.modelo.Personal;
import com.example.mihospitalapp.modelo.PersonalRepository;

public class PersonalActivty extends AppCompatActivity {
   private  RecyclerView recyclerView;
   private PersonalRepository pr;
   private Personal[] personal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal);
        pr = new PersonalRepository();
        GestorBD gestorBD = new GestorBD(this);
        personal = pr.allPersonal(gestorBD);
        recyclerView = findViewById(R.id.recyclerPersonal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new miAdapter());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private class miAdapter extends RecyclerView.Adapter<miAdapter.miAdapterHolder> {

        @NonNull
        @Override
        public miAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new miAdapterHolder(getLayoutInflater().inflate(R.layout.recycler_personal,parent,false));
        }
        @Override
        public void onBindViewHolder(@NonNull miAdapterHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return personal.length;
        }

        private class miAdapterHolder extends RecyclerView.ViewHolder{
            ImageView estado,barra;
            TextView nombre;
            public miAdapterHolder(@NonNull View itemView) {
                super(itemView);
               barra = itemView.findViewById(R.id.barraPersonalRecycler);
               estado = itemView.findViewById(R.id.estadoPersonalRecyler);
               nombre = itemView.findViewById(R.id.nombrePersonalRecycler);
            }

            public void imprimir(int position) {
                barra.setImageResource(R.drawable.barrapersonalreclyer);
                estado.setImageResource(R.drawable.personalactivo);
                nombre.setText(personal[position].getNombre());
            }
        }
    }
}