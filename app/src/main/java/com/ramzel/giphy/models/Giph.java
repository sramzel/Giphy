package com.ramzel.giphy.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class Giph implements Parcelable {

    @Nullable public String url;
    public int width;
    public int height;
    @Nullable public String size;
    @Nullable public String frames;
    @Nullable public String mp4;
    @Nullable public String mp4_size;
    @Nullable public String webp;
    @Nullable public String webp_size;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.size);
        dest.writeString(this.frames);
        dest.writeString(this.mp4);
        dest.writeString(this.mp4_size);
        dest.writeString(this.webp);
        dest.writeString(this.webp_size);
    }

    public Giph() {
    }

    protected Giph(Parcel in) {
        this.url = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.size = in.readString();
        this.frames = in.readString();
        this.mp4 = in.readString();
        this.mp4_size = in.readString();
        this.webp = in.readString();
        this.webp_size = in.readString();
    }

    public static final Parcelable.Creator<Giph> CREATOR = new Parcelable.Creator<Giph>() {
        @Override
        public Giph createFromParcel(Parcel source) {
            return new Giph(source);
        }

        @Override
        public Giph[] newArray(int size) {
            return new Giph[size];
        }
    };
}
