package com.capr.pe.beans;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Gantz on 5/07/14.
 */
public class Local_DTO implements Parcelable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private transient JSONObject jsonObject;
    private int mData;

    public Local_DTO(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Parcelable.Creator<Local_DTO> CREATOR = new Parcelable.Creator<Local_DTO>() {
        public Local_DTO createFromParcel(Parcel in) {
            return new Local_DTO(in);
        }

        public Local_DTO[] newArray(int size) {
            return new Local_DTO[size];
        }
    };

    private Local_DTO(Parcel in) {
        mData = in.readInt();
    }
}
