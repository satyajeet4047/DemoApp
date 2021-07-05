package com.example.aislechallenge.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetProfileResponse {
    @SerializedName("invites")
    public Invites invites;
    @SerializedName("likes")
    public Likes likes;


   public class GeneralInformation {
        @SerializedName("first_name")
        public String first_name;
        @SerializedName("gender")
        public String gender;
        @SerializedName("age")
        public String age;
    }

    public class Photo {
        @SerializedName("photo")
        public String photo;
        @SerializedName("photo_id")
        public int photo_id;
        @SerializedName("selected")
        public boolean selected;
        @SerializedName("status")
        public String status;
    }

    public class Profile {
        @SerializedName("general_information")
        public GeneralInformation general_information;
        @SerializedName("photos")
        public ArrayList<Photo> photos;
        @SerializedName("avatar")
        public String avatar;
    }

    public class Invites {
        @SerializedName("profiles")
        public ArrayList<Profile> profiles;
        @SerializedName("totalPages")
        public int totalPages;
        @SerializedName("pending_invitations_count")
        public int pending_invitations_count;
    }

    public class Likes {
        @SerializedName("profiles")
        public ArrayList<LikedProfile> profiles;
        @SerializedName("can_see_profile")
        public boolean can_see_profile;
        @SerializedName("likes_received_count")
        public int likes_received_count;


    }

    public class LikedProfile {
        @SerializedName("first_name")
        public String firstName;
        @SerializedName("avatar")
        public String avatar;
    }
}



