package com.example.lofm.githubfollowers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.lofm.githubfollowers.model.GHUser;
import com.example.lofm.githubfollowers.presenter.DetailPresenter;
import com.example.lofm.githubfollowers.rest.VolleySingleton;

public class DetailActivity extends AppCompatActivity implements DetailPresenter.DetailListener {

    public static final String GH_USER_KEY = "com.example.lofm.githubfollowers.ghUserKey";
    private GHUser ghUser;
    private ImageLoader imageLoader;
    private NetworkImageView userImageView, userBkgImage;
    private TextView locationTextView, emailTextView;
    private DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Hide bar
        getSupportActionBar().hide();
        //Create presenter
        presenter = new DetailPresenter(this);
        //Get GHUser object sent from previous activity
        Bundle bundle = getIntent().getExtras();
        ghUser = bundle.getParcelable(GH_USER_KEY);
        //Init views
        initViews();
        //Set images url
        imageLoader = VolleySingleton.getInstance(this).getImageLoader();
        userImageView.setImageUrl(ghUser.getAvatar_url(), imageLoader);
        userBkgImage.setImageUrl(ghUser.getAvatar_url(), imageLoader);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.registerListener(this);
        presenter.getFollowerDetails(ghUser.getLogin());
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unRegisterListener();
    }

    private void initViews() {
        userImageView = (NetworkImageView) findViewById(R.id.userImageView);
        userBkgImage = (NetworkImageView) findViewById(R.id.userBkgImage);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
    }

    @Override
    public void onSuccess(GHUser ghUser) {
        this.ghUser = ghUser;
        locationTextView.setText(this.ghUser.getLocation());
        emailTextView.setText(this.ghUser.getEmail());
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
