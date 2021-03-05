package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class ReferralActivity extends AppCompatActivity {
    String invitationalUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        findViewById(R.id.referLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLink(Uri.parse("http://opinverse.com/user_referral/?user_id="+CurrentUser.user.user_id))
                        .setDomainUriPrefix("https://opinverse.page.link")
                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.amit.opinverse").setMinimumVersion(125).build())
                        .buildShortDynamicLink()
                        .addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() {
                            @Override
                            public void onSuccess(ShortDynamicLink shortDynamicLink) {
                                invitationalUrl = shortDynamicLink.getShortLink().toString();
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Welcome to Opin Verse. Referral User ID : "+ CurrentUser.user.user_id+"\n"+"Download it from here: "+invitationalUrl);
                                startActivity(Intent.createChooser(intent, "Share via"));
                            }
                        });
            }
        });


    }
}