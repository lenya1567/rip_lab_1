package ru.lepeshka.laba1.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String path;

    @JsonIgnore
    private String link;

    @JsonIgnore
    private boolean isDir;

    private Timestamp created;

    @JsonIgnore
    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    public File() {
    }

    public File(String name, boolean isDir) {
        this.name = name;
        this.path = "/";
        this.isDir = isDir;
        this.files = new ArrayList<File>();
        this.created = new Timestamp(System.currentTimeMillis());
    }

    public File(String name, String path, boolean isDir) {
        this.name = name;
        this.path = path;
        this.isDir = isDir;
        this.files = new ArrayList<File>();
        this.created = new Timestamp(System.currentTimeMillis());
    }

    public void setParent(File file) {
        this.file = file;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getLink() {
        return link;
    }

    @JsonIgnore
    public String getFullPath() {
        return name + path;
    }

    public boolean getIsDir() {
        return isDir;
    }

    public List<File> getFiles() {
        return files;
    }

    public Timestamp getCreated() {
        return created;
    }
}
