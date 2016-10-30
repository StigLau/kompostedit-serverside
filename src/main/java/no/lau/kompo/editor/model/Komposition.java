package no.lau.kompo.editor.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Calendar;

/**
 * @author Stig@Lau.no
 */
public class Komposition {
    public Long id;

    @NotEmpty(message = "Summary is required.")
    private String reference;

    //private Config created = Calendar.getInstance();

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
}
