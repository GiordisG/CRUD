package com.example.almacenamiento5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_editar, btn_cancelar, btn_insertar, btn_eliminar, btn_Id;
    EditText txt_id, txt_nombre, txt_cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_nombre = findViewById(R.id.txtNombre);
        txt_cedula = findViewById(R.id.txtCedula);
        txt_id = findViewById(R.id.txtID);
        btn_insertar = findViewById(R.id.buttonInsertar);
        btn_eliminar = findViewById(R.id.buttonEliminar);
        btn_editar = findViewById(R.id.buttonEditar);
        btn_cancelar = findViewById(R.id.buttonCancelar);
        btn_Id = findViewById(R.id.buttonBuscarId);
    }
    public void insertar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,  "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo = txt_id.getText().toString();
        String nombre = txt_nombre.getText().toString();
        String cedula = txt_cedula.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("id", codigo);
        registro.put("nombre", nombre);
        registro.put("cedula",cedula);
        db.insert("persona", null, registro);
        db.close();
        txt_id.setText("");
        txt_nombre.setText("");
        txt_cedula.setText("");
        Toast.makeText(this, "Datos agregados correctamente", Toast.LENGTH_LONG).show();
    }
    public void editar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo = txt_id.getText().toString();
        String nombre = txt_nombre.getText().toString();
        String cedula = txt_cedula.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("id", codigo);
        registro.put("nombre", nombre);
        registro.put("cedula", cedula);
        int i = db.update("persona", registro, "id="+ codigo, null);
        db.close();
        if (i == 1){
            Toast t = Toast.makeText(this, "Los datos se insertaron correctamente", Toast.LENGTH_LONG);
            t.show();

        }
        else{
            Toast t = Toast.makeText(this, "Los datos no existen", Toast.LENGTH_LONG);
            t.show();
        }
    }
    public void buscarId(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo = txt_id.getText().toString();
        Cursor fila = db.rawQuery("SELECT nombre, cedula FROM persona WHERE id = "+codigo, null);
        if (fila.moveToFirst()){
            txt_nombre.setText(fila.getString(0));
            txt_cedula.setText(fila.getString(1));
        }
        else{
            Toast.makeText(this, "No existe un dato con dicho ID", Toast.LENGTH_LONG).show();
            db.close();
        }
    }
    public void  eliminar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo = txt_id.getText().toString();
        int i = db.delete("persona", "id="+codigo, null);
        db.close();
        txt_id.setText("");
        txt_nombre.setText("");
        txt_cedula.setText("");
        if(i == 1){
            Toast.makeText(this, "Datos eliminados correctamente", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Los datos no fueron eliminados", Toast.LENGTH_LONG).show();
        }
    }
    public void cancelar(View v){
        txt_id.setText("");
        txt_nombre.setText("");
        txt_cedula.setText("");
    }
}