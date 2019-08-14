package com.example.musica.models;

import com.example.musica.models.Album;
import com.example.musica.models.Artist;
import com.google.gson.annotations.Expose;
import org.parceler.Parcel;
import com.google.gson.annotations.SerializedName;

@Parcel
public class Song {
    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("readable")
    @Expose
    Boolean readable;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("title_short")
    @Expose
    String titleShort;
    @SerializedName("title_version")
    @Expose
    String titleVersion;
    @SerializedName("link")
    @Expose
    String link;
    @SerializedName("duration")
    @Expose
    String duration;
    @SerializedName("rank")
    @Expose
    String rank;
    @SerializedName("explicit_lyrics")
    @Expose
    Boolean explicitLyrics;
    @SerializedName("explicit_content_lyrics")
    @Expose
    Integer explicitContentLyrics;
    @SerializedName("explicit_content_cover")
    @Expose
    Integer explicitContentCover;
    @SerializedName("preview")
    @Expose
    String preview;
    @SerializedName("artist")
    @Expose
    Artist artist;
    @SerializedName("album")
    @Expose
    Album album;
    @SerializedName("type")
    @Expose
    String type;
    private String pushId;

    public Song(){

    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getReadable() {
        return readable;
    }

    public void setReadable(Boolean readable) {
        this.readable = readable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleShort() {
        return titleShort;
    }

    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }

    public String getTitleVersion() {
        return titleVersion;
    }

    public void setTitleVersion(String titleVersion) {
        this.titleVersion = titleVersion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Boolean getExplicitLyrics() {
        return explicitLyrics;
    }

    public void setExplicitLyrics(Boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    public Integer getExplicitContentLyrics() {
        return explicitContentLyrics;
    }

    public void setExplicitContentLyrics(Integer explicitContentLyrics) {
        this.explicitContentLyrics = explicitContentLyrics;
    }

    public Integer getExplicitContentCover() {
        return explicitContentCover;
    }

    public void setExplicitContentCover(Integer explicitContentCover) {
        this.explicitContentCover = explicitContentCover;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
