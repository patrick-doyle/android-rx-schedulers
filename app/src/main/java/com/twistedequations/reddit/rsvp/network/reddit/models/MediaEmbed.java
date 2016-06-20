
package com.twistedequations.reddit.rsvp.network.reddit.models;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MediaEmbed implements Parcelable {


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
  }

  public MediaEmbed() {
  }

  protected MediaEmbed(Parcel in) {
  }

  public static final Parcelable.Creator<MediaEmbed> CREATOR = new Parcelable.Creator<MediaEmbed>() {
    @Override
    public MediaEmbed createFromParcel(Parcel source) {
      return new MediaEmbed(source);
    }

    @Override
    public MediaEmbed[] newArray(int size) {
      return new MediaEmbed[size];
    }
  };
}
