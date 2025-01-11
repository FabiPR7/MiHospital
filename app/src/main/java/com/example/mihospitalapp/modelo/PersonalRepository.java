package com.example.mihospitalapp.modelo;

import com.example.mihospitalapp.activities.PersonalActivty;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class PersonalRepository {

    public Personal[] allPersonal(){
        ArrayList<Personal> personals = new ArrayList<>();

            Personal[] personalArray = personals.toArray(new Personal[0]);
        return  personalArray;
    }
}
