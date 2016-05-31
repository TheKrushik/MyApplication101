package info.krushik.android.myapplication101;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Student>> {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<Student>> onCreateLoader(int id, Bundle args) {
        return new StudentsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Student>> loader, List<Student> data) {
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                data
        );
        mListView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<List<Student>> loader) {



    }
}
