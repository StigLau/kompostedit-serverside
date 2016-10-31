package no.lau.kompo.editor.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Stig@Lau.no
 */
public class Segment {

    //Index is the internal # in the segmentDB
    private Long index;

    public String id;


    @NotEmpty(message = "Start is required.")
    public Long start;
    @NotEmpty(message = "End is required.")
    public Long end;

    public Segment() {

    }

    public Segment(String id, long start, long end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public Long getIndex() {
        return index;
    }
    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getStart() {
        return start;
    }

    public Long getEnd() {
        return end;
    }

    public String getId() {
        return id;
    }
}
