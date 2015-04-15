/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductDataBuilder;
import pl.com.bottega.ecommerce.sharedkernel.Money;

/**
 *
 * @author Godzio
 */
public class RequestItemBuilder {

    private ProductData productData = new ProductDataBuilder().build();
    private int quantity = 0;
    private Money totalCost = new Money( 0 );

    public void setProductData( ProductData productData ) {
        this.productData = productData;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public void setTotalCost( Money totalCost ) {
        this.totalCost = totalCost;
    }

    public RequestItem build() {
        return new RequestItem( productData, quantity, totalCost );
    }
}
