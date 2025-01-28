package com.example.mihospitalapp.modelo;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MensajeRepository {

    public Mensaje[] mensajesRecibidos;

    public void insertar_mensaje(GestorBD gestor, String contenido, String autor, String receptor, String fehcaHora){
        // Crear un mapa para almacenar los datos del mensaje
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("autor", autor);
        mensaje.put("fecha_hora", fehcaHora);
        mensaje.put("receptor", receptor);
        mensaje.put("contenido", contenido);
        // Generar un ID único para el mensaje (por ejemplo, con un timestamp)
        String mensajeId = gestor.getMensajeReference().push().getKey();
        if (mensajeId != null) {
            // Insertar el mensaje en la base de datos
            gestor.getMensajeReference()
                    .child(mensajeId)
                    .setValue(mensaje)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            System.out.println("Mensaje insertado correctamente.");
                        } else {
                            System.err.println("Error al insertar el mensaje: " + task.getException());
                        }
                    });
        } else {
            System.err.println("No se pudo generar un ID único para el mensaje.");
        }
    }

    public void recibirMensajes(GestorBD gestor, String autor, String receptor, PersonalRepository.OnCompleteListener listener){
        mensajesRecibidos = new Mensaje[0];
        gestor.getMensajeReference().orderByChild("autor").equalTo(autor).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Mensaje> mensajes = new ArrayList<>();
                System.out.println("Leeyendo");
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Mensaje mensaje = childSnapshot.getValue(Mensaje.class);
                    mensajes.add(mensaje);
                }
                // Procesar los mensajes recuperados
                for (Mensaje mensaje : mensajes) {
                    System.out.println("Mensaje recibido: " + mensaje.getContenido());
                }
                mensajesRecibidos = mensajes.toArray(new Mensaje[0]);
                listener.onComplete(true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error al recibir los mensajes: " + error.getMessage());
            listener.onComplete(false);
            }
        });
    }

    public Mensaje[] getMensajesRecibidos() {
        return mensajesRecibidos;
    }
}











