package com.brielage.uitleendienst.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable (tableName = "contactHuurder")
public class ContactHuurder {
    private String      id;
    private Persoon     persoon;
    private Organisatie organisatie;
    private String      opmerking;

    public ContactHuurder () {}

    public ContactHuurder (
            Persoon persoon,
            Organisatie organisatie,
            String opmerking) {
        this.persoon     = persoon;
        this.organisatie = organisatie;
        this.opmerking   = opmerking;
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId () {
        return id;
    }

    @DynamoDBAttribute
    public void setId (String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public Persoon getPersoon () {
        return persoon;
    }

    @DynamoDBAttribute
    public void setPersoon (Persoon persoon) {
        this.persoon = persoon;
    }

    @DynamoDBAttribute
    public Organisatie getOrganisatie () {
        return organisatie;
    }

    @DynamoDBAttribute
    public void setOrganisatie (Organisatie organisatie) {
        this.organisatie = organisatie;
    }

    @DynamoDBAttribute
    public String getOpmerking () {
        return opmerking;
    }

    @DynamoDBAttribute
    public void setOpmerking (String opmerking) {
        this.opmerking = opmerking;
    }

    @Override
    public String toString () {
        return "ContactHuurder{" +
                "id='" + id + '\'' +
                ", persoon=" + persoon +
                ", organisatie=" + organisatie +
                ", opmerking='" + opmerking + '\'' +
                '}';
    }
}
