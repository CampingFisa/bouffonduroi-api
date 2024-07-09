package com.camping_fisa.bouffonduroiapi.entities.questions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "theme")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_id")
    private Long themeId;

    @Column(name = "theme_name")
    @JsonProperty("themeName")
    private String themeName;

    @Column(name = "theme_description")
    private String themeDescription;

    @Column(name = "is_main")
    private boolean isMain;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "parent_theme_id")
    private Theme themeParent;

    @OneToMany(mappedBy = "themeParent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Theme> themeChildren;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private List<Category> categories;

    public Theme(Long themeId, String themeName, String themeDescription, boolean isMain, Theme themeParent,List<Category> categories, List<Theme> themeChildren) {
        this.themeId = themeId;
        this.themeName = themeName;
        this.themeDescription = themeDescription;
        this.isMain = isMain;
        this.themeParent = themeParent;
        this.themeChildren = themeChildren;
        this.categories = categories;
    }

    public Theme() { }

}
