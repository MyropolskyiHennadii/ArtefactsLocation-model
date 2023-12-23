package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

//Events of Artefact
@Entity
@Table(name = "artefacts_events")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtefactsEvent implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id_events_artefacts;
    @Column
    private String event;
    @Column
    private String event_begin;
    @Column
    private String event_end;
    @Column
    @JsonIgnore
    private String modified;//date-time of last modification
    @Column
    @JsonIgnore
    private String created;//date-time of creation
    @Column
    @JsonIgnore
    private String reviewed;//date-time of last review


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    //for comparing objects created with id_events_artefacts = 0
    @Transient
    @JsonIgnore
    private int id_temporary;

    public ArtefactsEvent() {
    }

    public ArtefactsEvent(String event, String event_begin, String event_end, Artefact artefact) {
        this.event = event;
        this.event_begin = event_begin;
        this.event_end = event_end;
        this.artefact = artefact;
    }

    public int getId() {
        return id_events_artefacts;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Artefact getArtefact() {
        return artefact;
    }

    public void setArtefact(Artefact artefact) {
        this.artefact = artefact;
    }

    public String getEvent_begin() {
        return event_begin;
    }

    public void setEvent_begin(String event_begin) {
        this.event_begin = event_begin;
    }

    public String getEvent_end() {
        return event_end;
    }

    public void setEvent_end(String event_end) {
        this.event_end = event_end;
    }

    @Override
    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String getReviewed() {
        return reviewed;
    }

    public void setReviewed(String reviewed) {
        this.reviewed = reviewed;
    }

    public void setId_events_artefacts(int id_events_artefacts) {
        this.id_events_artefacts = id_events_artefacts;
    }

    @Override
    public String toString() {
        return "ArtefactsEvent{" +
                "id_events_artefacts=" + id_events_artefacts +
                ", event='" + event + '\'' +
                ", event_begin=" + event_begin +
                ", event_end=" + event_end +
                ", artefact=" + artefact +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtefactsEvent that = (ArtefactsEvent) o;

        /*one of them is already written to the database (id!=0), another one can be not*/
        if(getId()*that.getId() == 0 && (getId() + that.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + that.getId() == 0){
            return getEvent().equals(that.getEvent())
                    && getEvent_begin().equals(that.getEvent_begin())
                    && getEvent_end().equals(that.getEvent_end())
                    && getArtefact().equals(that.getArtefact());
        }

        /*both are written in database*/
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
