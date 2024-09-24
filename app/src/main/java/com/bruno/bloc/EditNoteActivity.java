package com.bruno.bloc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bruno.R;
import com.bruno.room.db.AppDatabase;
import com.bruno.room.db.NoteWithTags;
import com.bruno.room.db.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditNoteActivity extends AppCompatActivity {

    public static final String NOTE_ID_KEY = "NOTE_ID";
    public static final String TAG_ID_KEY = "TAG_ID";

    private List<Tag> tagList = new ArrayList<>();
    private ChipGroup chipGroup;
    private CompositeDisposable disposable = new CompositeDisposable();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppDatabase appDatabase = ((RoomApplication) getApplication()).appDatabase;

        ChipGroup chipGroup = findViewById(R.id.tag_chip_group);
        Log.d("chigroup", chipGroup.toString());
        loadTags();
        int noteId = getIntent().getIntExtra(NOTE_ID_KEY, 0);




        Consumer<NoteWithTags> noteConsumer = new Consumer<NoteWithTags>() {
            @Override
            public void accept(NoteWithTags noteWithTags) {

                EditText editNoteNameText = findViewById(R.id.edit_note_title);
                editNoteNameText.setText(noteWithTags.note.title);
                EditText editNoteBodyText = findViewById(R.id.edit_note_body);
                editNoteBodyText.setText(noteWithTags.note.body);
                for (Tag tag : noteWithTags.tags) {
                    for (int i = 0; i < chipGroup.getChildCount(); i++) {
                        Chip chip = (Chip) chipGroup.getChildAt(i);
                        if (chip.getText().toString().equals(tag.tag)) {
                            chip.setChecked(true);
                        }
                    }
                }

                findViewById(R.id.save_note_button).setOnClickListener(view -> {
                    noteWithTags.note.title = editNoteNameText.getText().toString();
                    noteWithTags.note.body = editNoteBodyText.getText().toString();

                    // Obtener las etiquetas seleccionadas


                    List<Tag> tagsToPersist = new ArrayList<>();
                    for (int i = 0; i < tagList.size(); i++) {
                        if (((Chip) chipGroup.getChildAt(i)).isChecked()) {
                            tagsToPersist.add(tagList.get(i));
                        }
                    }
                    noteWithTags.tags = tagsToPersist;


                    Action navigateToMainActivityAction = new Action() {
                        @Override
                        public void run() throws Throwable {
                            Intent intent = new Intent();
                            intent.setClass(EditNoteActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    };
                    if (noteWithTags.note.noteId > 0) {
                        appDatabase.noteWithTagsDao().updateNoteWithTags(noteWithTags)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(navigateToMainActivityAction);
                    } else {
                        appDatabase.noteWithTagsDao().insertNoteWithTags(noteWithTags)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(navigateToMainActivityAction);
                    }
                });
            }
        };



        if (noteId > 0) {
            appDatabase.notesDao().findWithTags(noteId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(noteConsumer);
        } else {
            try {
                noteConsumer.accept(NoteWithTags.Empty());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Aquí es donde se realiza la acción cuando se hace clic en el botón de retroceso
            onBackPressed();
            return true;
        }
        if (id == R.id.createTag) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditNoteActivity.this);
            builder.setTitle("Nueva etiqueta:");
            final EditText input = new EditText(EditNoteActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Acción a realizar al hacer clic en el botón "Aceptar"
                    String texto = input.getText().toString();
                    // Aquí puedes usar el texto ingresado en la caja de texto
                    //1. crear un Tag y poner el texto
                    Tag etiqueta = new Tag();
                    etiqueta.tag = texto;
                    //2. guardarlo en bd
                    AppDatabase appDatabase = ((RoomApplication) getApplication()).appDatabase;
                    Consumer<Tag> tagConsumer = tag -> {
                        ChipGroup chipGroup = findViewById(R.id.tag_chip_group);
                        tag.tag = texto;

                        Completable completable = tag.tagId > 0 ? appDatabase.tagsDao().updateTag(tag) : appDatabase.tagsDao().insertTag(tag);

                        completable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Chip chip = new Chip(EditNoteActivity.this);
                                    chip.setText(tag.tag);
                                    chip.setCloseIconVisible(true);

                                    chipGroup.addView(chip);
                                    tagList.add(tag);

                                });
                    };
                    int tagId = getIntent().getIntExtra(TAG_ID_KEY, 0);
                    if (tagId > 0) {
                        disposable.add(
                                appDatabase.tagsDao().find(tagId)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(tagConsumer, throwable -> {
                                            Toast.makeText(EditNoteActivity.this, "Error loading tag", Toast.LENGTH_SHORT).show();
                                            throwable.printStackTrace();
                                        })
                        );
                    } else {
                        try {
                            tagConsumer.accept(new Tag());
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }


            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadTags() {
        ChipGroup chipGroup = findViewById(R.id.tag_chip_group);
        AppDatabase appDatabase = ((RoomApplication) getApplication()).appDatabase;
        disposable.add(appDatabase.tagsDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tags -> {
                    Log.d("tagList", tags.toString());
                    tagList.clear();
                    tagList.addAll(tags);

                 //   chipGroup.removeAllViews();
                    for (Tag tag : tagList) {
                        Chip chip = new Chip(EditNoteActivity.this);
                        chip.setText(tag.tag);
                        chip.setClickable(true);

                  ;
                        chip.setCheckable(true);
                        chipGroup.addView(chip);
                        chip.setCloseIconVisible(true);
                        chip.setOnCloseIconClickListener(v -> {
                            chipGroup.removeView(v);
                            tagList.remove(tag);
                            appDatabase.tagsDao().deleteTag(tag)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        Toast.makeText(EditNoteActivity.this, "Tag deleted", Toast.LENGTH_SHORT).show();
                                    }, throwable -> {
                                        Toast.makeText(EditNoteActivity.this, "Error deleting tag", Toast.LENGTH_SHORT).show();
                                        throwable.printStackTrace();
                                    });
                        });
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

}
