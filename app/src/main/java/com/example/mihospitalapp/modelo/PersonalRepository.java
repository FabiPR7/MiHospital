package com.example.mihospitalapp.modelo;

import androidx.annotation.NonNull;

import com.example.mihospitalapp.activities.PersonalActivty;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class PersonalRepository {
    Personal[] personalArray = new Personal[0];
    ArrayList<Personal> personals = new ArrayList<>();
    public PersonalRepository(){

    }
    public void procesoAllPersonal(GestorBD gestorBD){
        String codigo = "H001";
        gestorBD.getmDatabase().
                child("personal").
                orderByChild("codigo").
                startAt(codigo).
                addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            System.out.println("No se encontraron datos para el código: " + codigo);
                            return;
                        }
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Personal personal = dataSnapshot.getValue(Personal.class);
                            personals.add(personal);
                            System.out.println(personal.toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("ERROR MI REY");
                    }
                });
    }

    public Personal[] allPersonal(GestorBD gestorBD){
       personalArray = new Personal[personals.size()];
       int count = 0;
        for (Personal personal : personals){
           personalArray[count] = personal;
           count++;
       }
        return  personalArray;
    }
}
