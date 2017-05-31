package eu.dev.test.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "sortedResults" )
public class SortedResult implements Serializable {

    private static final long serialVersionUID = -5864186071045968631L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "sortResultId", length = 11 )
    private int sortResultId;

    @NotNull
    @Column( columnDefinition = "LONGTEXT" )
    private String orgNumbers;

    @NotNull
    @Column( columnDefinition = "LONGTEXT" )
    private String sorNumbers;

    @NotNull
    private int changes;

    @NotNull
    private long millis;

    @NotNull
    private long created;

    public SortedResult() {

    }

    public SortedResult( String orgNumbers, String sorNumbers, int changes, long millis ) {
        this.orgNumbers = orgNumbers;
        this.sorNumbers = sorNumbers;
        this.changes = changes;
        this.millis = millis;
        this.created = System.currentTimeMillis();
    }

    public String getOrgNumbers() {
        return orgNumbers;
    }

    public void setOrgNumbers( String orgNumbers ) {
        this.orgNumbers = orgNumbers;
    }

    public String getSorNumbers() {
        return sorNumbers;
    }

    public void setSorNumbers( String sorNumbers ) {
        this.sorNumbers = sorNumbers;
    }

    public long getMillis() {
        return millis;
    }

    public void setMillis( long millis ) {
        this.millis = millis;
    }

    public long getCreated() {
        return created;
    }

    public int getChanges() {
        return changes;
    }

    public void setChanges( int changes ) {
        this.changes = changes;
    }

}
