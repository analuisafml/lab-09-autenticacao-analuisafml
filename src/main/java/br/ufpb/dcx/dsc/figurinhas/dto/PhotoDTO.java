package br.ufpb.dcx.dsc.figurinhas.dto;

public class PhotoDTO {

    private Long photoId;
    private String url;

    public PhotoDTO() {
    }

    public PhotoDTO(String url) {
        this.url = url;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
