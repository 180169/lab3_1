/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.bottega.ecommerce.sales.domain.invoicing;

import java.util.Date;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

/**
 *
 * @author Godzio
 */
public class BookKeeperTest {

    @Test
    public void issuance_requestForInvoiceWithOneElement_invoiceWithOneElement() {
        //Given
        InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
        TaxPolicy mockTaxPolicy = mock(TaxPolicy.class);

        ProductData productData = new ProductData(Id.generate(), new Money(1), "makowiec", ProductType.FOOD, new Date());
        RequestItem requestItem = new RequestItem(productData, 1, new Money(7));
        ClientData clientData = new ClientData(Id.generate(), "godzio");
        InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
        invoiceRequest.add(requestItem);

        BookKeeper bookKeeper = new BookKeeper(mockInvoiceFactory);

        //When
        when(mockTaxPolicy.calculateTax((ProductType) any(), (Money) any())).thenReturn(new Tax(new Money(1), ""));
        when(mockInvoiceFactory.create(clientData)).thenReturn(new Invoice(Id.generate(), clientData));
        Invoice invoice = bookKeeper.issuance(invoiceRequest, mockTaxPolicy);
        //Then
        Assert.assertThat(invoice.getItems().size(), is(1));
    }

    @Test
    public void issuance_requestForInvoiceWithTwoElements_callTaxPolicyCalculateTaxTwoTimes() {
        //Given
        InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
        TaxPolicy mockTaxPolicy = mock(TaxPolicy.class);

        ProductData productData = new ProductData(Id.generate(), new Money(1), "makowiec", ProductType.FOOD, new Date());
        RequestItem requestItem = new RequestItem(productData, 1, new Money(7));
        ClientData clientData = new ClientData(Id.generate(), "godzio");
        InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);

        BookKeeper bookKeeper = new BookKeeper(mockInvoiceFactory);

        //When
        when(mockTaxPolicy.calculateTax((ProductType) any(), (Money) any())).thenReturn(new Tax(new Money(1), ""));
        when(mockInvoiceFactory.create(clientData)).thenReturn(new Invoice(Id.generate(), clientData));
        bookKeeper.issuance(invoiceRequest, mockTaxPolicy);
        //Then

        verify(mockTaxPolicy, times(2)).calculateTax((ProductType) any(), (Money) any());
    }

    @Test
    public void issuance_requestForInvoiceWithZeroElements_invoiceWithZeroElement() {
        //Given
        InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
        TaxPolicy mockTaxPolicy = mock(TaxPolicy.class);

        ClientData clientData = new ClientData(Id.generate(), "godzio");
        InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);

        BookKeeper bookKeeper = new BookKeeper(mockInvoiceFactory);

        //When
        when(mockTaxPolicy.calculateTax((ProductType) any(), (Money) any())).thenReturn(new Tax(new Money(1), ""));
        when(mockInvoiceFactory.create(clientData)).thenReturn(new Invoice(Id.generate(), clientData));
        Invoice invoice = bookKeeper.issuance(invoiceRequest, mockTaxPolicy);
        //Then

        Assert.assertThat(invoice.getItems().size(), is(1));
    }

    @Test
    public void issuance_requestForInvoiceWithZeroElements_callTaxPolicyCalculateZeroTimes() {
        //Given
        InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
        TaxPolicy mockTaxPolicy = mock(TaxPolicy.class);

        ClientData clientData = new ClientData(Id.generate(), "godzio");
        InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);

        BookKeeper bookKeeper = new BookKeeper(mockInvoiceFactory);

        //When
        when(mockTaxPolicy.calculateTax((ProductType) any(), (Money) any())).thenReturn(new Tax(new Money(1), ""));
        when(mockInvoiceFactory.create(clientData)).thenReturn(new Invoice(Id.generate(), clientData));
        bookKeeper.issuance(invoiceRequest, mockTaxPolicy);
        //Then

        verify(mockTaxPolicy, times(0)).calculateTax((ProductType) any(), (Money) any());
    }
}
