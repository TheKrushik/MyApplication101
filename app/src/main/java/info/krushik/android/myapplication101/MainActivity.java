package info.krushik.android.myapplication101;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Student>> {//объявляем LoaderCallbacks
    //реализация интерфейса LoaderCallbacks(можно писать в любом классе и обратится в этом классе)

    private ListView mListView;
    private SaveTask mSaveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView); //находим ListView
        getSupportLoaderManager().initLoader(0, null, this); //SupportLoaderManager()-поддержка старых версий v4
    }

    public void OnClick(View v){
        mSaveTask = new SaveTask();
        mSaveTask.execute(new Student("Ivan", "Ivanov", 22));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mSaveTask !=null){
            mSaveTask.cancel(true);
        }
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

    //AsyncTask для сохранения студента(однозадачные)
    class SaveTask extends AsyncTask<Student, Void, Long>{//(принимает студентов, ,возвращает long(id"шку))
        private ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(MainActivity.this);//говорим пользователю что чтото делается
            mDialog.setMessage("Saving...");
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected Long doInBackground(Student... params) {//doInBackground добавляет по умолчанию
            long id = 0;
            Student student = params[0];

            try {
                Uri uri = Uri.parse("content://info.krushik.android.myapplication10/students");//объявляем новый Uri, убеждаемся что он правильный

                ContentValues values = new ContentValues();

                values.put("FirstName", student.FirstName);
                values.put("LastName", student.LastName);
                values.put("Age", student.Age);

                getContentResolver().insert(uri, values);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return id;
        }

        //передаем на интерфейс ответ что мы сохранили студента и его id"шка вот такая
        @Override
        protected void onPostExecute(Long aLong) {
            if (mDialog != null) {//если mDialog существует
                mDialog.dismiss();//мы его закрываем
            }

            getSupportLoaderManager().restartLoader(0, null, MainActivity.this);//перечитуем заново наших студентов
        }
    }
}
