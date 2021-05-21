package com.sample.mark9;

public class CommonModel {

    private String path;
    private boolean selected;

    public String getAlbumCoverPath() {
        return albumCoverPath;
    }

    public void setAlbumCoverPath(String albumCoverPath) {
        this.albumCoverPath = albumCoverPath;
    }

    private String albumCoverPath;

    public CommonModel(String path, String albumCoverPath, boolean b) {
        this.path = path;
        this.albumCoverPath=albumCoverPath;
        this.selected=b;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        selected = selected;
    }
}
