package com.example.idsapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.idsapp.R;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {


    EditText textName, textEmail, textPassword, textAge;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        textAge = findViewById(R.id.textAge);

        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    private void registrar() {
        final String name = textName.getText().toString().trim();
        final String email = textEmail.getText().toString().trim();
        final String password = textPassword.getText().toString().trim();
        final String age = textAge.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        if(name.isEmpty()){
            textName.setError("Not valid name");
            return;
        } else if (email.isEmpty()){
            textEmail.setError("Not valid email");
            return;
        } else if (password.isEmpty()){
            textPassword.setError("Not valid password");
            return;
        } else if (age.isEmpty()){
            textAge.setError("Not valid password");
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("¡Registro exitoso!")) {
                        Toast.makeText(RegistroActivity.this, "¡Registro realizado!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(RegistroActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegistroActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("age", age);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(RegistroActivity.this);
            requestQueue.add(request);
        }
    }

    private boolean isEmailValid(String textEmail) {
        if (textEmail == null) {
            return false;
        }
        if (textEmail.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(textEmail).matches();
        } else {
            return !textEmail.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String textPassword) {
        return textPassword != null && textPassword.trim().length() > 5;
    }
}