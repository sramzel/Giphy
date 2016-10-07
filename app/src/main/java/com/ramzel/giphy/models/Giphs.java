package com.ramzel.giphy.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class Giphs implements Parcelable {

    @Nullable public Giph fixed_height;
    @Nullable public Giph fixed_height_still;
    @Nullable public Giph fixed_height_downsampled;
    @Nullable public Giph fixed_width;
    @Nullable public Giph fixed_width_still;
    @Nullable public Giph fixed_width_downsampled;
    @Nullable public Giph fixed_height_small;
    @Nullable public Giph fixed_height_small_still;
    @Nullable public Giph fixed_width_small;
    @Nullable public Giph fixed_width_small_still;
    @Nullable public Giph downsized;
    @Nullable public Giph downsized_still;
    @Nullable public Giph downsized_large;
    @Nullable public Giph original;
    @Nullable public Giph original_still;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.fixed_height, flags);
        dest.writeParcelable(this.fixed_height_still, flags);
        dest.writeParcelable(this.fixed_height_downsampled, flags);
        dest.writeParcelable(this.fixed_width, flags);
        dest.writeParcelable(this.fixed_width_still, flags);
        dest.writeParcelable(this.fixed_width_downsampled, flags);
        dest.writeParcelable(this.fixed_height_small, flags);
        dest.writeParcelable(this.fixed_height_small_still, flags);
        dest.writeParcelable(this.fixed_width_small, flags);
        dest.writeParcelable(this.fixed_width_small_still, flags);
        dest.writeParcelable(this.downsized, flags);
        dest.writeParcelable(this.downsized_still, flags);
        dest.writeParcelable(this.downsized_large, flags);
        dest.writeParcelable(this.original, flags);
        dest.writeParcelable(this.original_still, flags);
    }

    public Giphs() {
    }

    protected Giphs(Parcel in) {
        this.fixed_height = in.readParcelable(Giph.class.getClassLoader());
        this.fixed_height_still = in.readParcelable(Giph.class.getClassLoader());
        this.fixed_height_downsampled = in.readParcelable(Giph.class.getClassLoader());
        this.fixed_width = in.readParcelable(Giph.class.getClassLoader());
        this.fixed_width_still = in.readParcelable(Giph.class.getClassLoader());
        this.fixed_width_downsampled = in.readParcelable(Giph.class.getClassLoader());
        this.fixed_height_small = in.readParcelable(Giph.class.getClassLoader());
        this.fixed_height_small_still = in.readParcelable(Giph.class.getClassLoader());
        this.fixed_width_small = in.readParcelable(Giph.class.getClassLoader());
        this.fixed_width_small_still = in.readParcelable(Giph.class.getClassLoader());
        this.downsized = in.readParcelable(Giph.class.getClassLoader());
        this.downsized_still = in.readParcelable(Giph.class.getClassLoader());
        this.downsized_large = in.readParcelable(Giph.class.getClassLoader());
        this.original = in.readParcelable(Giph.class.getClassLoader());
        this.original_still = in.readParcelable(Giph.class.getClassLoader());
    }

    public static final Parcelable.Creator<Giphs> CREATOR = new Parcelable.Creator<Giphs>() {
        @Override
        public Giphs createFromParcel(Parcel source) {
            return new Giphs(source);
        }

        @Override
        public Giphs[] newArray(int size) {
            return new Giphs[size];
        }
    };
}
