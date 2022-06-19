package com.dreamteam.teamprediction.fordreamteamcricket.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.dreamteam.teamprediction.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    int RC_SIGN_IN = 1000;
    FirebaseAnalytics mFirebaseAnalytics;

    // Client ID
    // 331702306061-7mi307ec0hfldlp8rb9tclqtmgfqs75e.apps.googleusercontent.com

    // Client Secret
    // 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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Google SignIn
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc = GoogleSignIn.getClient(this, gso);

        //        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account!= null){
//            navigateToNextActivity();
//        }

//        // test link span
//        TextView tv =  findViewById(R.id.terms_text);
//        Spannable span = Spannable.Factory.getInstance().newSpannable(
//                "By continuing, you agree to Loan Guide's\n Terms of Service and acknowledge that you've read our Privacy Policy");
//        span.setSpan(new ClickableSpan() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SignUpActivity.this,PrivacyPolicyActivity.class);
//                intent.putExtra("key","terms");
//                startActivity(intent);
//            }
//        }, 42, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        // All the rest will have the same spannable.
//        ClickableSpan cs = new ClickableSpan() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SignUpActivity.this,PrivacyPolicyActivity.class);
//                intent.putExtra("key","policy");
//                startActivity(intent);
//            }
//        };
//
//        // set the "test " spannable.
//        span.setSpan(cs, 96, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        tv.setText(span);
//
//        tv.setMovementMethod(LinkMovementMethod.getInstance());
//
//        binding.withGoogle.setOnClickListener(view -> {
//            signIn();
//        });
    }
//    private void signIn() {
//        Intent signInIntent = gsc.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//
//            try {
//                task.getResult(ApiException.class);
//                navigateToNextActivity();
//            } catch (ApiException e) {
//                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }
//    private void navigateToNextActivity() {
//        finish();
//        Intent intent = new Intent(SignUpActivity.this, WelcomeActivity.class);
//        startActivity(intent);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Google Sign in");
//        mFirebaseAnalytics.logEvent("Clicked_On_Google_SignIn", bundle);
//    }
}