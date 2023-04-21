package com.brielage.uitleendienst.responses;

import com.brielage.uitleendienst.models.ContactMagazijn;
import com.brielage.uitleendienst.models.Magazijn;
import com.brielage.uitleendienst.models.Persoon;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class JsonContactMagazijnResponse
        extends JsonResponse {
    @JsonInclude (JsonInclude.Include.NON_NULL)
    private String   id;
    @JsonInclude (JsonInclude.Include.NON_NULL)
    private Persoon  persoon;
    @JsonInclude (JsonInclude.Include.NON_NULL)
    private Magazijn magazijn;
    @JsonInclude (JsonInclude.Include.NON_NULL)
    private String   opmerking;
    @JsonInclude (JsonInclude.Include.NON_NULL)
    private List     contactMagazijns;

    public JsonContactMagazijnResponse (
            final boolean success,
            ContactMagazijn contactMagazijn) {
        super(success);
        this.id        = contactMagazijn.getId();
        this.persoon   = contactMagazijn.getPersoon();
        this.magazijn  = contactMagazijn.getMagazijn();
        this.opmerking = contactMagazijn.getOpmerking();
    }

    public JsonContactMagazijnResponse (
            final boolean success,
            final List contactMagazijns) {
        super(success);
        this.contactMagazijns = contactMagazijns;
    }

    public String getId ()                                        {return id;}

    public void setId (final String id)                           {this.id = id;}

    public Persoon getPersoon ()                                  {return persoon;}

    public void setPersoon (final Persoon persoon)                {this.persoon = persoon;}

    public Magazijn getMagazijn ()                                {return magazijn;}

    public void setMagazijn (final Magazijn magazijn)             {this.magazijn = magazijn;}

    public String getOpmerking ()                                 {return opmerking;}

    public void setOpmerking (final String opmerking)             {this.opmerking = opmerking;}

    public List getContactMagazijns ()                            {return contactMagazijns;}

    public void setContactMagazijns (final List contactMagazijns) {this.contactMagazijns = contactMagazijns;}
}
