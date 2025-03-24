package org.example.linkshortener.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "original_link")
public class OriginalLink {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url", unique = true, nullable = false)
    @URL
    private String url;

    @OneToMany(mappedBy = "original", fetch = FetchType.LAZY)
    private List<ShortedLink> shortAnalogues;

    @Column(name = "date_of_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreation;

    public OriginalLink() {}
    public OriginalLink(String url) {
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

    public List<ShortedLink> getShortAnalogue() {
        return shortAnalogues;
    }

    public void setShortAnalogue(List<ShortedLink> shortAnalogues) {
        this.shortAnalogues = shortAnalogues;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void addShortAnalogue(ShortedLink link){
        if (this.shortAnalogues.isEmpty()){
            this.shortAnalogues = new ArrayList<>();
        }
        this.shortAnalogues.add(link);
        link.setOriginal(this);
    }
}
