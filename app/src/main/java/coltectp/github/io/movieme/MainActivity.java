package coltectp.github.io.movieme;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.view.View;

import coltectp.github.io.movieme.provider.MovieContract;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_MOVIE = 1;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instancia o recycler view utilizado (lista dos filmes) //
        final RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));

        // Instancia o floating button //
        FloatingActionButton btn = findViewById(R.id.fab);

        mMovieAdapter = new MovieAdapter();
        list.setAdapter(mMovieAdapter);

        // No clique do botão
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                                        RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Inicia o assincronismo
        // getSupportLoaderManager().initLoader(LOADER_MOVIE,null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_MOVIE:
                String[] projection =  {
                    MovieContract.MovieEntry.COLUMN_ID,
                    MovieContract.MovieEntry.COLUMN_AGE_GROUP,
                    MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
                    MovieContract.MovieEntry.COLUMN_GENRE
                };

                return new CursorLoader(this, MovieContract.MovieEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_MOVIE:
                mMovieAdapter.setMovie(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_MOVIE:
                mMovieAdapter.setMovie(null);
                break;
        }
    }
}
