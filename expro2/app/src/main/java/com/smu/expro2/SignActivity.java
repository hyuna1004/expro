package com.smu.expro2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignActivity extends AppCompatActivity {

    EditText edit_id_sign;
    EditText edit_password_sign;
    EditText edit_passwordcheck_sign;
    Button button_ok;

    private FirebaseAuth mAuth;
    String TAG = "SignActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        edit_id_sign= (EditText)findViewById(R.id.id_edit_sign);
        edit_password_sign = (EditText)findViewById(R.id.password_edit_sign);
        edit_passwordcheck_sign = (EditText)findViewById(R.id.password_check_edit);
        button_ok = (Button)findViewById(R.id.ok_button);
        mAuth = FirebaseAuth.getInstance();


        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign(edit_id_sign.getText().toString(),edit_password_sign.getText().toString(),edit_passwordcheck_sign.getText().toString());
            }
        });




    }

    //생명주기 시작
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
 //★       updateUI(currentUser);
    }


    //계정만들기
    public void sign(final String email, final String password, final String passwordcheck) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
//★                            updateUI(user);
                            Toast.makeText(SignActivity.this, "환영합니다.",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(SignActivity.this,MainActivity.class);
                            startActivity(in);
                        }

                        else{
                            // If sign in fails, display a message to the user.
                             if(!(email.contains("@"))) {
                                Toast.makeText(SignActivity.this, "이메일 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                             }
                             else if(password.length()<6){
                                Toast.makeText(SignActivity.this, "비밀번호가 6자리 이상이 아닙니다.",Toast.LENGTH_SHORT).show();
                            }
                            else if(!(password.equals(passwordcheck))){
                                 Toast.makeText(SignActivity.this,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                             }


                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//★                            updateUI(null);
                        }

                        // ...
                    }
                });

    }






}
