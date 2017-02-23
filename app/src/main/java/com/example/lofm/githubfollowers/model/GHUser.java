package com.example.lofm.githubfollowers.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LOFM on 22/02/2017.
 */

public class GHUser implements Parcelable {

    private String login;
    private String avatar_url;
    private String name;
    private String location;
    private String email;
    private String followers_url;
    private int public_repos;
    private int followers;
    private int following;

    protected GHUser(Parcel in) {
        login = in.readString();
        avatar_url = in.readString();
        name = in.readString();
        location = in.readString();
        email = in.readString();
        followers_url = in.readString();
        public_repos = in.readInt();
        followers = in.readInt();
        following = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(avatar_url);
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(email);
        dest.writeString(followers_url);
        dest.writeInt(public_repos);
        dest.writeInt(followers);
        dest.writeInt(following);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GHUser> CREATOR = new Creator<GHUser>() {
        @Override
        public GHUser createFromParcel(Parcel in) {
            return new GHUser(in);
        }

        @Override
        public GHUser[] newArray(int size) {
            return new GHUser[size];
        }
    };

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }
}
