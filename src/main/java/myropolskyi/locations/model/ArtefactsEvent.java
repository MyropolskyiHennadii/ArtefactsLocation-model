package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

//Events of Artefact
@Entity
@Table(name = "artefacts_events")
public class ArtefactsEvent implements LocationsJsonRepresentable {

    private static int counter;//for comparing objects created with id_events_artefacts = 0

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_events_artefacts;
    @Column
    private String event;
    @Column
    private String event_begin;
    @Column
    private String event_end;
    @Column
    @JsonIgnore
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    @JsonIgnore
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference(value = "artefacts_events")//important to prevent infinite loop of references
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
        this.updated = 1;//always for new exemplar (for database exchange)

        //for comparing objects created with id_events_artefacts = 0
        this.id_temporary = counter;
        counter++;
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

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
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

    public int getId_temporary() {
        return id_temporary;
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

        return getArtefact() == that.getArtefact() && getEvent().equals(that.getEvent()) && getEvent_begin().equals(that.getEvent_begin()) && getEvent_end().equals(that.getEvent_end());
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
