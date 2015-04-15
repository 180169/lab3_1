/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientDataBuilder;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
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

        RequestItem requestItem = new RequestItemBuilder().build();
        ClientData clientData = new ClientDataBuilder().build();
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

        RequestItem requestItem = new RequestItemBuilder().build();
        ClientData clientData = new ClientDataBuilder().build();
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

        ClientData clientData = new ClientDataBuilder().build();
        InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);

        BookKeeper bookKeeper = new BookKeeper(mockInvoiceFactory);

        //When
        when(mockTaxPolicy.calculateTax((ProductType) any(), (Money) any())).thenReturn(new Tax(new Money(1), ""));
        when(mockInvoiceFactory.create(clientData)).thenReturn(new Invoice(Id.generate(), clientData));
        Invoice invoice = bookKeeper.issuance(invoiceRequest, mockTaxPolicy);
        //Then

        Assert.assertThat(invoice.getItems().size(), is(0));
    }

    @Test
    public void issuance_requestForInvoiceWithZeroElements_callTaxPolicyCalculateZeroTimes() {
        //Given
        InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
        TaxPolicy mockTaxPolicy = mock(TaxPolicy.class);

        ClientData clientData = new ClientDataBuilder().build();
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
