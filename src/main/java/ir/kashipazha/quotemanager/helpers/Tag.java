package ir.kashipazha.quotemanager.helpers;

import ir.kashipazha.quotemanager.poem.Verse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// todo generalize this class
@Entity
@Table(name = "tag", uniqueConstraints = {@UniqueConstraint(columnNames = {"tag_pk"})})
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_pk", nullable = false, unique = true, length = 11, updatable = false)
    private Integer ID;

    @Column(name = "data", nullable = false, length = 400)
    private String data;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "tag_catalog", joinColumns = {
			@JoinColumn(name = "tag_pk", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "catalog_pk",
					nullable = false, updatable = false) })
    private List<Verse> catalogs;

    public void addCatalog(Verse v) {
        catalogs.add(v);
    }

    public ArrayList<Verse> getCatalogs(){
        return new ArrayList<>(catalogs);
    }

    public Tag(String data){
        this.data = data;
        this.catalogs = new ArrayList<>();
    }

    public Tag() {
    }

    @Override
    public boolean equals(Object o){
        if( !( o instanceof Tag ))
            return false;
        Tag t = (Tag) o;

        return t.ID.equals(this.ID);
    }

    @Override
    public String toString(){
        return String.format("Tag is: %s", data);
    }

    public Integer getID() {
        return ID;
    }
}
