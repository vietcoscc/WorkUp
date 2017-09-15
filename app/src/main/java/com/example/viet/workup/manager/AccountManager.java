package com.example.viet.workup.manager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.ui.main.MainActivity;
import com.example.viet.workup.utils.CalendarUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static com.example.viet.workup.utils.AccountUtils.buildProgressDialog;
import static com.example.viet.workup.utils.AccountUtils.isValidEmail;
import static com.example.viet.workup.utils.AccountUtils.isValidPassword;
import static com.example.viet.workup.utils.ApplicationUtils.isNetworkConnectionAvailable;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.userAccountRef;

/**
 * Created by viet on 15/08/2017.
 */

public class AccountManager {
    private static final String TAG = "AccountManager";
    private static AccountManager sInstance;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static AccountManager getsInstance() {
        if (sInstance == null) {
            sInstance = new AccountManager();
            return sInstance;
        } else {
            return sInstance;
        }
    }

    public void signOut() {
        mAuth.signOut();
    }

    public void signIn(String userName, String password, final Context context) {
        if (!isNetworkConnectionAvailable(context)) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userName.isEmpty() || password.isEmpty()) {
            Log.i(TAG, "User name or password must not empty ");
            Toast.makeText(context, "User name or password must not empty ", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signOut();
        final ProgressDialog progressDialog = buildProgressDialog(context, "Signing in ... ");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    String displayName = null;
                    String photoUrl = null;
                    if (user.getPhotoUrl() == null) {
                        photoUrl = "";
                    }
                    if (user.getDisplayName() == null) {
                        displayName = "";
                    }
                    UserInfo userInfo = new UserInfo(displayName,
                            user.getEmail(),
                            CalendarUtils.getCurrentDate(),
                            photoUrl,
                            user.getUid());
                    userAccountRef(userInfo.getUid()).setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "isSuccessful");
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                            progressDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "isNotSuccessful", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "isNotSuccessful");
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(context, "isNotSuccessful", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "isNotSuccessful");
                    progressDialog.dismiss();
                }
            }
        });
    }

    public void signUp(final String userName, String password, final Context context) {
        if (!isNetworkConnectionAvailable(context)) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userName.isEmpty() || password.isEmpty()) {
            Log.i(TAG, "User name or password must not empty ");
            Toast.makeText(context, "User name or password must not empty ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidEmail(userName)) {
            Log.i(TAG, "User name must be an email ");
            Toast.makeText(context, "User name must be an email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidPassword(password)) {
            Log.i(TAG, " Password is invalid ");
            Toast.makeText(context, "Password is invalid ", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progressDialog = buildProgressDialog(context, "Signing up ... ");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "isSuccessful");
                    Activity activity = (Activity) context;
                    activity.finish();
                } else {
                    Toast.makeText(context, "isNotSuccessful", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "isNotSuccessful");
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }
        });
    }

    public void firebaseAuthWithGoogle(final GoogleSignInAccount acct, final Context context) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        if (!isNetworkConnectionAvailable(context)) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if (acct == null) {
            Toast.makeText(context, "GoogleSignInAccount is null", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signOut();
        final ProgressDialog progressDialog = buildProgressDialog(context, "Siging in ... ");
        progressDialog.show();
        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = task.getResult().getUser();
                            UserInfo userInfo = new UserInfo(user.getDisplayName(),
                                    user.getEmail(),
                                    CalendarUtils.getCurrentDate(),
                                    user.getPhotoUrl().toString(),
                                    user.getUid());
                            userAccountRef(user.getUid()).setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "signInWithCredential:success");
                                    Toast.makeText(context, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(intent);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                                    progressDialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(context, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            progressDialog.dismiss();
                        }

                    }
                });
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
}
