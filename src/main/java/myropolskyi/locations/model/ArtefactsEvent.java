package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Artefact's event (for instance, the date of construction or destruction)
 */
@Entity
@Table(name = "artefacts_events")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class ArtefactsEvent implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id_events_artefacts", nullable = false)
    private int id;
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

    public ArtefactsEvent(String event, String event_begin, String event_end, Artefact artefact) {
        this.event = event;
        this.event_begin = event_begin;
        this.event_end = event_end;
        this.artefact = artefact;
    }

    @Override
    public String toString() {
        return "ArtefactsEvent{" +
                "id_events_artefacts=" + id +
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
