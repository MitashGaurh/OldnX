package com.oldandx.oldnx.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.oldandx.oldnx.R;
import com.oldandx.oldnx.utils.ActivityUtils;
import com.oldandx.oldnx.view.navigation.NavigationActivity;
import com.oldandx.oldnx.viewmodel.LinkAccountViewModel;
import com.oldandx.oldnx.vo.AppConstants;

import java.util.Arrays;

import timber.log.Timber;

public class LinkAccountActivity extends AppCompatActivity {

    private static final String TAG = "LinkAccountActivity";

    private LinkAccountViewModel mLinkAccountViewModel;

    private CallbackManager mCallbackManager;

    private final String[] LOGIN_READ_PERMISSIONS = new String[]{AppConstants.LinkAccountConstants.PUBLIC_PROFILE
            , AppConstants.LinkAccountConstants.EMAIL
            , AppConstants.LinkAccountConstants.USER_BIRTHDAY
            , AppConstants.LinkAccountConstants.USER_FRIENDS};

    private static final String KEY_FIELDS = "fields";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_account);

        mLinkAccountViewModel = ActivityUtils.obtainViewModel(this, LinkAccountViewModel.class);

        mCallbackManager = CallbackManager.Factory.create();

        if (null == savedInstanceState) {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager()
                    , LinkAccountFragment.newInstance()
                    , R.id.link_account_container
                    , false
                    , LinkAccountFragment.class.getSimpleName());
        }

        subscribeToViewEvent();
    }

    private void subscribeToViewEvent() {
        mLinkAccountViewModel.getContinueWithFaceBookEvent().observe(this
                , aVoid -> performFacebookLogin());

        mLinkAccountViewModel.getLoginNowEvent().observe(this
                , aVoid -> startActivity(new Intent(this, NavigationActivity.class)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void performFacebookLogin() {
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        (object, response) -> {
                            Timber.tag(TAG).d("Success in fetching user details.");
                        });
                Bundle parameters = new Bundle();
                parameters.putString(KEY_FIELDS, AppConstants.LinkAccountConstants.GRAPH_REQUEST_FIELDS_STRING);
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList(LOGIN_READ_PERMISSIONS));
    }
}
