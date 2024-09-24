package com.bruno.bloc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bruno.R;
import com.bruno.room.db.AppDatabase;
import com.bruno.room.db.Note;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private List<Note> note;
    ImageView fondoblock;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloc);

        fondoblock = findViewById(R.id.llBloc);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
        AppDatabase appDatabase = ((RoomApplication) getApplication()).appDatabase;
        if (isFirstTime) {
            // Ejecutar el código solo la primera vez


            Note note1 = new Note();
            note1.title = "prueba";
            note1.body = "prueba";

            Note note2 = new Note();
            note2.title = "prueba2";
            note2.body = "prueba2";

            Note note3 = new Note();
            note3.title = "prueba3";
            note3.body = "prueba3";

            Note note4 = new Note();
            note4.title = "prueba4";
            note4.body = "prueba4";

            Action navigateToMainActivityAction = new Action() {
                @Override
                public void run() throws Throwable {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            };
            // Insertar las notas en la base de datos
            appDatabase.notesDao().insertNote(note1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note3)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note4)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note3)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note4)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note3)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            appDatabase.notesDao().insertNote(note4)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            // Actualizar SharedPreferences para indicar que ya no es la primera vez
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        appDatabase.notesDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) throws Throwable {
                        note = notes;

                        RecyclerView recyclerView = findViewById(R.id.recyclerview);
                        recyclerView.setAdapter(new NoteAdapter(note, new NoteAdapter.NoteClickListener() {
                            @Override
                            public void onNoteEdit(int position) {
                                Note nota = note.get(position);
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, EditNoteActivity.class);
                                intent.putExtra(EditNoteActivity.NOTE_ID_KEY, nota.noteId);

                                startActivity(intent);
                            }

                            @Override
                            public void onNoteDelete(int position) {
                                Note nota = note.get(position);

                                appDatabase.notesDao().deleteNote(nota)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action() {
                                            @Override
                                            public void run() throws Throwable {
                                                note.remove(position);
                                                recyclerView.getAdapter().notifyItemRemoved(position);
                                            }
                                        });
                            }

                            @Override
                            public void onNoteCheck(int position) {
                                Note nota = note.get(position);
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, CheckNoteActivity .class);
                                intent.putExtra(EditNoteActivity.NOTE_ID_KEY, nota.noteId);

                                startActivity(intent);
                            }
                        }));
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }
                });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_new_note:
                // Abre la actividad de crear una nueva nota
                startActivity(new Intent(MainActivity.this, EditNoteActivity.class));
                return true;
            case R.id.action_manage_tags:
                // Abre la actividad de gestionar etiquetas
                startActivity(new Intent(MainActivity.this, TagActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}