package com.ramzel.giphy;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.ramzel.giphy.models.Datum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailDialog extends DialogFragment {

    public static final String KEY_DATUM = "datum";

    public DetailDialog() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        final Datum datum = getArguments().getParcelable(KEY_DATUM);

        View view = inflater.inflate(R.layout.fragment_detail, container);

        if (datum != null) {
            final ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
            final View shareButton = view.findViewById(R.id.share);

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareGiph(imageView);
                }
            });

            //Style the dialog
            Window window = getDialog().getWindow();
            if (window != null) {
                window.requestFeature(Window.FEATURE_NO_TITLE);
                window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }

            loadGiph(datum, view, imageView, progressBar);
        } else {
            dismiss();
        }

        return view;
    }

    private void loadGiph(
            @NonNull Datum datum,
            @NonNull View view,
            @NonNull final ImageView imageView,
            @NonNull final ProgressBar progressBar) {
        GlideDrawableImageViewTarget imageViewTarget =
                new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(
                            GlideDrawable resource,
                            GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                };
        if (datum.images != null && datum.images.original != null) {
            Glide.with(view.getContext()).load(datum.images.original.url).into(imageViewTarget);
        }
    }

    private void shareGiph(@NonNull ImageView imageView) {
        Uri bmpUri = getLocalBitmapUri(imageView);
        if (bmpUri != null) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("image/gif");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
    }

    @Nullable
    private Uri getLocalBitmapUri(@NonNull ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof GifDrawable) {
            try {
                // Store image to default external storage directory
                File externalStoragePublicDirectory = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(externalStoragePublicDirectory, "shared_gif.gif");
                GifDrawable gifDrawable = ((GifDrawable) imageView.getDrawable());
                FileOutputStream out = new FileOutputStream(file);
                out.write(gifDrawable.getData());
                out.close();
                return Uri.fromFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}