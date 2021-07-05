package com.example.aislechallenge.util;


import android.content.Context;
import android.widget.ImageView;

import com.example.aislechallenge.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 *  ImageLoaderUtil is used to load images using Picasso image loading library.
 */
@Singleton
public class ImageLoaderUtil {

    @Inject
    public ImageLoaderUtil() {

    }

    public void loadImage(ImageView imageView, String avatarUrl, boolean isBlur) {

        Context context = imageView.getContext();
        if (isBlur) {
            BlurTransformation transformation1 = new BlurTransformation(context);
            Picasso.with(context)
                    .load(avatarUrl)
                    .transform(transformation1)
                    .transform(transformation1)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            //..image loaded from cache
                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(imageView.getContext())
                                    .load(avatarUrl)
                                    .transform(transformation1)
                                    .transform(transformation1)
                                    .error(R.mipmap.profile1_1)
                                    .into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            //... image loaded from online
                                        }

                                        @Override
                                        public void onError() {
                                        }
                                    });
                        }

                    });
        } else {
            Picasso.with(context)
                    .load(avatarUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            //..image loaded from cache
                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(imageView.getContext())
                                    .load(avatarUrl)
                                    .error(R.mipmap.profile1_1)
                                    .into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            //... image loaded from online
                                        }

                                        @Override
                                        public void onError() {
                                        }
                                    });
                        }

                    });
        }
    }
}