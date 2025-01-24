package com.example.mihospitalapp.modelo;

import androidx.annotation.NonNull;

import com.example.mihospitalapp.activities.PersonalActivty;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class PersonalRepository {
    ArrayList<Personal> personals = new ArrayList<>();

    public PersonalRepository(){
    }

    public void allPersonal(GestorBD gestorBD, String codigo, OnCompleteListener listener){
        gestorBD.getmDatabase().
                child("personal").
                orderByChild("codigo").
                startAt(codigo).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            System.out.println("No se encontraron datos para el código: " + codigo);
                            listener.onComplete(false);
                            return;
                        }
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Personal personal = dataSnapshot.getValue(Personal.class);
                            personals.add(personal);
                        }
                       listener.onComplete(true);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("ERROR MI REY");
                    }
                });
    }

    public interface OnCompleteListener {
        void onComplete(boolean success);
    }

    public ArrayList<Personal> getPersonals() {
        return personals;
    }
}
