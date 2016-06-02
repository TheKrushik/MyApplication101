package info.krushik.android.myapplication101;

import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Student>> {//объявляем LoaderCallbacks
    //реализация интерфейса LoaderCallbacks(можно писать в любом классе и обратится в этом классе)

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView); //находим ListView
        getSupportLoaderManager().initLoader(0, null, this); //SupportLoaderManager()-поддержка старых версий v4
    }

    //Создание Loader
    @Override
    public Loader<List<Student>> onCreateLoader(int id, Bundle args) { //(id-какой лоадер нкжно создать(БД, сеть, файл),
        return new StudentsLoader(this);
    }

    //Получение ответа Loader(возвращаются студенты)
    @Override
    public void onLoadFinished(Loader<List<Student>> loader, List<Student> data) {
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(//список ArrayAdapter
                this, //контекст
                android.R.layout.simple_list_item_1, //разметка
                android.R.id.text1, //текст
                data //массив
        );
        mListView.setAdapter(adapter); //заполняем ListView данными adapter
    }

    //если Loader резетится, то мы можем на это прореагировать
    @Override
    public void onLoaderReset(Loader<List<Student>> loader) {

    }

    class SaveTask extends AsyncTask<Student, Void, Long>{

    }
}
