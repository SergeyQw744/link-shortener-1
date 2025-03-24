package org.example.linkshortener.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

@Entity
@Table(name = "shorted_link")
public class ShortedLink {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url", nullable = false, unique = true)
    @URL
    private String url;

    @ManyToOne
    @JoinColumn(name = "original_link_id", referencedColumnName = "id")
    private OriginalLink original;

    @Column(name = "date_of_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreation;

    @Column(name = "is_active")
    private boolean isActive;

    public ShortedLink() {}
    public ShortedLink(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OriginalLink getOriginal() {
        return original;
    }

    public void setOriginal(OriginalLink original) {
        this.original = original;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
