package com.example.lofm.githubfollowers;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.lofm.githubfollowers.model.GHUser;
import com.example.lofm.githubfollowers.presenter.DetailPresenter;
import com.example.lofm.githubfollowers.rest.VolleySingleton;
import com.example.lofm.githubfollowers.ui.CircledNetworkImageView;
import com.example.lofm.githubfollowers.utils.ImageDownloader;

public class DetailActivity extends AppCompatActivity implements DetailPresenter.DetailListener, View.OnClickListener {

    public static final String GH_USER_KEY = "com.example.lofm.githubfollowers.ghUserKey";
    private GHUser ghUser;
    private ImageLoader imageLoader;
    private CircledNetworkImageView userImageView;
    private NetworkImageView userBkgImage;
    private TextView locationTextView, emailTextView, followersTextView, followingTextView, reposTextView, loginTextView, nameTextView;
    private DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
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
        userImageView.setOnClickListener(this);
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
        userImageView = (CircledNetworkImageView) findViewById(R.id.userImageView);
        userBkgImage = (NetworkImageView) findViewById(R.id.userBkgImage);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        followersTextView = (TextView) findViewById(R.id.followersTextView);
        followingTextView = (TextView) findViewById(R.id.followingTextView);
        reposTextView = (TextView) findViewById(R.id.reposTextView);
        loginTextView = (TextView) findViewById(R.id.loginTextView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
    }

    @Override
    public void onSuccess(GHUser ghUser) {
        this.ghUser = ghUser;
        locationTextView.setText(this.ghUser.getLocation());
        emailTextView.setText(this.ghUser.getEmail());
        followersTextView.setText(String.valueOf(this.ghUser.getFollowers()));
        followingTextView.setText(String.valueOf(this.ghUser.getFollowing()));
        reposTextView.setText(String.valueOf(this.ghUser.getPublic_repos()));
        loginTextView.setText(this.ghUser.getLogin());
        nameTextView.setText(this.ghUser.getName());
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // set an exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Explode());
        }
    }

    @Override
    public void onClick(View v) {
        //TODO: Download image from URL
        ImageDownloader imageDownloader = (ImageDownloader) new ImageDownloader(this).execute(ghUser.getAvatar_url());
    }

}
