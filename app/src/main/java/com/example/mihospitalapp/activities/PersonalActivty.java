package com.example.mihospitalapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.mihospitalapp.modelo.HospitalRepository;
import com.example.mihospitalapp.modelo.Personal;
import com.example.mihospitalapp.modelo.PersonalRepository;
import com.example.mihospitalapp.modelo.UsuarioRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

public class PersonalActivty extends AppCompatActivity {

   private RecyclerView recyclerView;
   private PersonalRepository pr;
   private UsuarioRepository ur;
   private HospitalRepository hr;
   private Personal[] personal;
   private ArrayList<Personal> ordenados;
   private GestorBD gestorBD;
   private EditText buscador;
   private ImageView mensajeButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal);
        gestorBD = new GestorBD(this);
        pr = new PersonalRepository();
        ur = new UsuarioRepository();
        hr = new HospitalRepository();
        personal = new Personal[0];
        ordenados = new ArrayList<>();
        buscador = findViewById(R.id.buscadorTxt);
        mensajeButon = findViewById(R.id.mensajeGrupal);
        mensajeButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMensaje(hr.codigoHospitalMysqlite(gestorBD));
            }
        });
       recyclerView = findViewById(R.id.recyclerPersonal);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(linearLayoutManager);
       obtenerPersonalAll();
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                buscar();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void activityMensaje(String codigo ){
        Intent i = new Intent(PersonalActivty.this, MensajeActivity.class);
        i.putExtra("codigo",codigo);
        startActivity(i);
    }
    public void buscar(){
        String buscar = buscador.getText().toString().toLowerCase();
        HashSet<Personal> personalBuscado = new HashSet<>();
        if (!buscar.isEmpty()){
            for (Personal personal1 : personal){
                if (personal1.getNombre().toLowerCase().startsWith(buscar)||personal1.getApellido().toLowerCase().startsWith(buscar))
                     personalBuscado.add(personal1);
            }
            recyclerView.setAdapter(new miAdapter(personalBuscado.toArray(new Personal[0])));
        }
        else{
           ejecutarReciclerview();
        }
    }
    public void ejecutarReciclerview(){
        recyclerView.setAdapter(new miAdapter(personal));
    }

    public void obtenerPersonalAll(){
        pr.allPersonal(gestorBD,hr.obtenerCodigoHospital(ur.obtenerCodigo(gestorBD)) ,success -> {
            if (success) {
                ordenados = new ArrayList<>();
                personal = pr.getPersonals().toArray(new Personal[0]);
                for(Personal persona : personal){
                    if (persona.getEstado().equalsIgnoreCase("activo")) {
                        ordenados.add(persona);
                    }
                }
                for(Personal persona : personal){
                    if (persona.getEstado().equalsIgnoreCase("no activo")) {
                        ordenados.add(persona);
                    }
                }
                personal = ordenados.toArray(new Personal[0]);
                ejecutarReciclerview();
            } else {
                System.out.println("Error o datos no encontrados.");
            }
        });
    }

    private class miAdapter extends RecyclerView.Adapter<miAdapter.miAdapterHolder> {

        private Personal[] personalAdapter;

        public miAdapter(Personal[] personalAdapter){
            this.personalAdapter = personalAdapter;
        }
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
            return personalAdapter.length;
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
               if (personalAdapter[position].getEstado().equalsIgnoreCase("activo")){
                   estado.setImageResource(R.drawable.personalactivo);
               }
                else{
                   estado.setImageResource(R.drawable.personalnoactivo);
               }
                nombre.setText(personalAdapter[position].getNombre()+" "+personalAdapter[position].getApellido());
            }
        }
    }
}