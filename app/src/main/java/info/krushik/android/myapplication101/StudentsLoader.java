package info.krushik.android.myapplication101;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

class StudentsLoader extends AsyncTaskLoader<List<Student>> {

    private Context mContext;
    private List<Student> Students;

    public StudentsLoader(Context context) {
        super(context);

        this.mContext = context;
    }

    @Override
    public List<Student> loadInBackground() {
        List<Student> students = new ArrayList<>();

        Uri uri = Uri.parse("content://info.krushik.android.myapplication10/students");

        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()){
                Student student = new Student();

                student.id = cursor.getLong(cursor.getColumnIndex("_id"));
                student.FirstName = cursor.getString(cursor.getColumnIndex("FirstName"));
                student.LastName = cursor.getString(cursor.getColumnIndex("FirstName"));
                student.Age = cursor.getLong(cursor.getColumnIndex("Age"));

                students.add(student);
                cursor.moveToFirst();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();

            }
        }
        return students;
    }

    @Override
    public void deliverResult(List<Student> data) {
        if (isReset()) {
            return;
        }

        Students = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (Students != null) {
            deliverResult(Students);
        }

        if (takeContentChanged() || Students == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();

        if (Students != null) {
            Students = null;
        }
    }
}

