package com.bruno.bloc;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bruno.R;
import com.bruno.room.db.AppDatabase;
import com.bruno.room.db.Tag;
import com.bruno.room.db.TagWithNotes;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TagActivity extends AppCompatActivity {
    private final List<TagWithNotes> _tags = new ArrayList<>();
    private  List <Tag> etiquetas = new ArrayList<>();

    private CompositeDisposable disposable = new CompositeDisposable();


    public static final String TAG_ID_KEY = "TAG_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Gestionar tags");



        setContentView(R.layout.tag_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppDatabase appDatabase = ((RoomApplication) getApplication()).appDatabase;
        appDatabase.tagsDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Tag>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onSuccess(List<Tag> tags) {
                        etiquetas = tags;
                        RecyclerView recyclerView = findViewById(R.id.recyclerviewTags);
                        TagAdapter tagAdapter = new TagAdapter(_tags, new TagAdapter.TagClickListener() {
                            @Override
                            public void onTagSave(int position) {

                            }

                            @Override
                            public void onTagDelete(int position) {
                                TagWithNotes tagWithNotes = _tags.get(position);
                                Tag tag = tagWithNotes.tag;

                                appDatabase.noteWithTagsDao().deleteTagAndCrossReferences(tag)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action() {
                                            @Override
                                            public void run() throws Throwable {
                                                _tags.remove(position);
                                                recyclerView.getAdapter().notifyItemRemoved(position);
                                            }
                                        });
                            }
                        });
                        recyclerView.setLayoutManager(new LinearLayoutManager(TagActivity.this));
                        recyclerView.setAdapter(tagAdapter);
                        appDatabase.tagsDao().getTagsWithNotes().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((tagsWithNotes -> {
                            _tags.clear();
                            _tags.addAll(tagsWithNotes);
                            tagAdapter.notifyDataSetChanged();

                        }));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }


                });
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
            AlertDialog.Builder builder = new AlertDialog.Builder(TagActivity.this);
            builder.setTitle("Nueva etiqueta:");
            final EditText input = new EditText(TagActivity.this);
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

                        tag.tag = texto;

                        Completable completable = tag.tagId > 0 ? appDatabase.tagsDao().updateTag(tag) : appDatabase.tagsDao().insertTag(tag);
                        RecyclerView recyclerView = findViewById(R.id.recyclerviewTags);
                        completable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    if (!etiquetas.contains(tag)) {
                                        etiquetas.add(tag);
                                    }
                                    TagAdapter tagAdapter = (TagAdapter) recyclerView.getAdapter();
                                    appDatabase.tagsDao().getTagsWithNotes().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((tagsWithNotes -> {
                                        _tags.clear();
                                        _tags.addAll(tagsWithNotes);
                                        tagAdapter.notifyDataSetChanged();

                                    }));
                                });
                    };
                    int tagId = getIntent().getIntExtra(TAG_ID_KEY, 0);
                    if (tagId > 0) {
                        disposable.add(
                                appDatabase.tagsDao().find(tagId)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(tagConsumer, throwable -> {
                                            Toast.makeText(TagActivity.this, "Error loading tag", Toast.LENGTH_SHORT).show();
                                            throwable.printStackTrace();
                                        })
                        );
                    } else {
                        try {
                            tagConsumer.accept(TagWithNotes.Empty().tag);
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
}
