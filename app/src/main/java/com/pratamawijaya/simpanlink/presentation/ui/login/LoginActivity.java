package com.pratamawijaya.simpanlink.presentation.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pratamawijaya.simpanlink.R;
import com.pratamawijaya.simpanlink.presentation.ui.home.HomeActivity;

public class LoginActivity extends AppCompatActivity
    implements GoogleApiClient.OnConnectionFailedListener {

  private static final int REQUESTCODE_SIGN_IN = 1;
  private static final String TAG = LoginActivity.class.getSimpleName();

  private GoogleSignInOptions googleSignInOptions;
  private GoogleApiClient googleApiClient;
  private FirebaseAuth firebaseAuth;
  private FirebaseAuth.AuthStateListener authStateListener;
  private DatabaseReference databaseReference;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    databaseReference = FirebaseDatabase.getInstance().getReference();

    setupGoogleApiLogin();
    setupFirebaseSignIn();
  }

  /**
   * setup firebaseAuth instance
   * setup firebaseAuth state listener
   */
  private void setupFirebaseSignIn() {
    firebaseAuth = FirebaseAuth.getInstance();
    authStateListener = new FirebaseAuth.AuthStateListener() {
      @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
          Log.d(TAG, "onAuthStateChanged: signed in " + user.getUid());
          navigateToHome();
        } else {
          Log.d(TAG, "onAuthStateChanged: user logout");
        }
      }
    };
  }

  private void navigateToHome() {
    Log.d(TAG, "navigateToHome: ");
    startActivity(new Intent(this, HomeActivity.class));
    finish();
  }

  @Override protected void onStart() {
    super.onStart();
    firebaseAuth.addAuthStateListener(authStateListener);
    googleApiClient.connect();
  }

  @Override protected void onStop() {
    super.onStop();
    if (authStateListener != null) {
      firebaseAuth.removeAuthStateListener(authStateListener);
    }
    if (googleApiClient != null) {
      googleApiClient.disconnect();
    }
  }

  /**
   * konfigurasi untuk Google Sign In
   */
  private void setupGoogleApiLogin() {
    googleSignInOptions =
        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            getString(R.string.default_web_client_id)).requestEmail().build();

    googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
        .build();
  }

  @OnClick(R.id.btnSignIn) void onSignInClick() {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    startActivityForResult(signInIntent, REQUESTCODE_SIGN_IN);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUESTCODE_SIGN_IN) {
      if (resultCode == RESULT_OK) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        handleSignInResult(result);
      }
    }
  }

  /**
   * handle signinresult dari GoogleSignIn
   */
  private void handleSignInResult(GoogleSignInResult result) {
    Log.i(TAG, "handleSignInResult: isSuccess :" + result.isSuccess());
    if (result.isSuccess()) {
      GoogleSignInAccount acct = result.getSignInAccount();
      firebaseAuthWithGoogle(acct);
    }
  }

  /**
   * handle firebase auth dengan data akun dari GoogleSignIn
   *
   * @param acct GoogleSignIn Data
   */
  private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
    Log.d(TAG, "firebaseAuthWithGoogle: " + acct.getIdToken());
    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            if (!task.isSuccessful()) {
              Log.e(TAG,
                  "onComplete: task not succesfull " + task.getException().getLocalizedMessage());
            }
          }
        });
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Log.e(TAG, "onConnectionFailed: " + connectionResult.getErrorMessage());
  }
}
