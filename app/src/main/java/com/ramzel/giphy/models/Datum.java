
package com.ramzel.giphy.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class Datum implements Parcelable {

    @Nullable public String type;
    @Nullable public String id;
    @Nullable public String slug;
    @Nullable public String url;
    @Nullable public String bitly_gif_url;
    @Nullable public String bitly_url;
    @Nullable public String embed_url;
    @Nullable public String username;
    @Nullable public String source;
    @Nullable public String rating;
    @Nullable public String caption;
    @Nullable public String content_url;
    @Nullable public String source_tld;
    @Nullable public String source_post_url;
    @Nullable public String import_datetime;
    @Nullable public String trending_datetime;
    @Nullable public Giphs images;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.id);
        dest.writeString(this.slug);
        dest.writeString(this.url);
        dest.writeString(this.bitly_gif_url);
        dest.writeString(this.bitly_url);
        dest.writeString(this.embed_url);
        dest.writeString(this.username);
        dest.writeString(this.source);
        dest.writeString(this.rating);
        dest.writeString(this.caption);
        dest.writeString(this.content_url);
        dest.writeString(this.source_tld);
        dest.writeString(this.source_post_url);
        dest.writeString(this.import_datetime);
        dest.writeString(this.trending_datetime);
        dest.writeParcelable(this.images, flags);
    }

    public Datum() {
    }

    protected Datum(Parcel in) {
        this.type = in.readString();
        this.id = in.readString();
        this.slug = in.readString();
        this.url = in.readString();
        this.bitly_gif_url = in.readString();
        this.bitly_url = in.readString();
        this.embed_url = in.readString();
        this.username = in.readString();
        this.source = in.readString();
        this.rating = in.readString();
        this.caption = in.readString();
        this.content_url = in.readString();
        this.source_tld = in.readString();
        this.source_post_url = in.readString();
        this.import_datetime = in.readString();
        this.trending_datetime = in.readString();
        this.images = in.readParcelable(Giphs.class.getClassLoader());
    }

    public static final Parcelable.Creator<Datum> CREATOR = new Parcelable.Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel source) {
            return new Datum(source);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };
}
