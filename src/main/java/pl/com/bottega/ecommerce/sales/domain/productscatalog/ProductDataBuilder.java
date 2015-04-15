/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.bottega.ecommerce.sales.domain.productscatalog;

import java.util.Date;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sharedkernel.Money;

/**
 *
 * @author Godzio
 */
public class ProductDataBuilder {

    private Id productID = Id.generate();
    private Money price = new Money( 0 );
    private String name = "someName";
    private Date snapshotDate = new Date();
    private ProductType type = ProductType.STANDARD;

    public void setProductID( Id productID ) {
        this.productID = productID;
    }

    public void setPrice( Money price ) {
        this.price = price;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setSnapshotDate( Date snapshotDate ) {
        this.snapshotDate = snapshotDate;
    }

    public void setType( ProductType type ) {
        this.type = type;
    }

    public ProductData build() {
        return new ProductData( productID, price, name, type, snapshotDate );
    }
}
