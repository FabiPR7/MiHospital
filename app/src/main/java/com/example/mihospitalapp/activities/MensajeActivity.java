package com.example.mihospitalapp.activities;

import android.os.Bundle;
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
import com.example.mihospitalapp.modelo.Mensaje;
import com.example.mihospitalapp.modelo.MensajeRepository;
import com.example.mihospitalapp.modelo.Personal;
import com.example.mihospitalapp.modelo.UsuarioRepository;

public class MensajeActivity extends AppCompatActivity {

    private GestorBD gestorBD;
    private RecyclerView recycler;
    private EditText contenido;
    private TextView nombreMensaje;
    private MensajeRepository mr;
    private UsuarioRepository ur;
    private Mensaje[] mensajes = new Mensaje[0];
    private String contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mensaje);
        recycler = findViewById(R.id.recyclerMensaje);
        contenido = findViewById(R.id.mensajeTxt);
        nombreMensaje = findViewById(R.id.nombreMensaje);
        mr = new MensajeRepository();
        ur = new UsuarioRepository();
        gestorBD = new GestorBD(this);
       Bundle bundle = getIntent().getExtras();
        contacto = bundle.getString("codigo");
        System.out.println(contacto);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        cargarMensajes();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void cargarMensajes(){
        if (contacto.contains("-")){
            mr.recibirMensajes(gestorBD,contacto, ur.obtenerCodigo(gestorBD),success -> {
                if (success){
                    System.out.println("FUNCIONAN LOS MENSAJES");
                    mensajes = mr.getMensajesRecibidos();
                    System.out.println(mensajes.length);
                    recycler.setAdapter(new miAdapter());
                }
                else{
                    System.out.println("NO FUNCIONO");
                }
            });
        }else{
            mr.recibirMensajes(gestorBD,contacto, contacto,success -> {
                if (success){
                    System.out.println("FUNCIONAN LOS MENSAJES");
                    mensajes = mr.getMensajesRecibidos();
                    System.out.println(mensajes.length);
                    recycler.setAdapter(new miAdapter());
                }
                else{
                    System.out.println("NO FUNCIONO");
                }
            });
        }
    }

    private class miAdapter extends RecyclerView.Adapter<miAdapter.miAdapterHolder> {


        public miAdapter(){

        }
        @NonNull
        @Override
        public miAdapter.miAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new miAdapter.miAdapterHolder(getLayoutInflater().inflate(R.layout.recycler_mensajes,parent,false));
        }
        @Override
        public void onBindViewHolder(@NonNull miAdapter.miAdapterHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return mensajes.length;
        }

        private class miAdapterHolder extends RecyclerView.ViewHolder{

            TextView nombre, fechaHora, contenido;

            public miAdapterHolder(@NonNull View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.nombreMensaje);
                fechaHora = itemView.findViewById(R.id.horaMensaje);
                contenido = itemView.findViewById(R.id.textoMensaje);
            }

            public void imprimir(int position) {
                nombre.setText(mensajes[position].getAutor());
                fechaHora.setText(mensajes[position].getFecha_Hora());
                contenido  .setText(mensajes[position].getContenido());
            }
        }
    }
}