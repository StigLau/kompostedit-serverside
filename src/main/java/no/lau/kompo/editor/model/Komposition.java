package no.lau.kompo.editor.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stig@Lau.no
 */
public class Komposition {
    public Long id;

    @NotEmpty(message = "Summary is required.")
    private String reference;

    //private Config created = Calendar.getInstance();

    List<Segment> segments = new ArrayList<>();
    /*
     config: Config
  , mediaFile: Mediafile
  , segments: List Segment
     */

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public Segment getSegmentByIndex(Long index) throws Exception {
        for (Segment segment : segments) {
            if(segment.getIndex().equals(index)) {
                return segment;
            }
        }
        throw new Exception("Segment with index" + index + " not found in komposition");
    }

    public void addSegment(Segment newSegment) {
        segments.add(newSegment);
    }

    public void deleteSegmentWithIndex(Long index) {
        for (Segment segment : segments) {
            if(segment.getIndex().equals(index)) {
                segments.remove(segment);
                return;
            }
        }
    }
}
