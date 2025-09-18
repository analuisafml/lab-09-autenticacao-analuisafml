package br.ufpb.dcx.dsc.figurinhas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "url")
    private String url;

    @OneToOne(mappedBy = "photo")
    private User user;

    public Photo() {
    }

    public Photo(String url) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
