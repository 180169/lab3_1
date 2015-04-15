/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage;

/**
 *
 * @author Godzio
 */
public class ClientDataBuilder {

    private Id id = Id.generate();
    private String name = "";

    public void setId( Id id ) {
        this.id = id;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public ClientData build() {
        return new ClientData( this.id, name );
    }
}
