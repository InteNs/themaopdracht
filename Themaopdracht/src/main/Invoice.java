package main;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.ArrayList;


public class Invoice {
    private final LocalDate invoiceDate;
    private double totalPrice;
    private boolean isPayed;
    private Customer customer;

    private final ArrayList<InvoiceItem> items = new ArrayList<>();

    public enum PayMethod {CASH, PIN, CREDIT}

    public Invoice() {
        invoiceDate = LocalDate.now();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void add(InvoiceItem e) {
        totalPrice += e.getTotal();
        items.add(e);
    }

    public ArrayList<InvoiceItem> getItems() {
        return items;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void payNow(PayMethod payMethod) {
        isPayed = true;
    }

    public void bindToCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public String toString() {
        return "invoice";
    }

    public class InvoiceItem extends HBox {
        private final int amount;
        private final double price;
        private final double semiTotalPrice;
        private final Label description = new Label();
        private final Label amountL = new Label();
        private final Label priceL = new Label();
        private final Label totalPriceL = new Label();

        public InvoiceItem(String desc, double price, int amount) {
            this.price = price;
            this.amount = amount;
            this.semiTotalPrice = amount * price;
            description.setText(desc);
            priceL.setText(ATDProgram.convert(price));
            amountL.setText(Integer.toString(amount));
            totalPriceL.setText(ATDProgram.convert(semiTotalPrice));
            setSpacing(5);
            getChildren().addAll(
                    amountL,
                    new Separator(Orientation.VERTICAL),
                    description,
                    new Separator(Orientation.VERTICAL),
                    priceL,
                    new Separator(Orientation.VERTICAL),
                    totalPriceL);
            ((Label) getChildren().get(0)).setPrefWidth(50);
            ((Label) getChildren().get(2)).setPrefWidth(100);
            ((Label) getChildren().get(4)).setPrefWidth(100);
            ((Label) getChildren().get(6)).setPrefWidth(100);
        }

        public double getTotal() {
            return price * amount;
        }

        public void refresh() {
            totalPriceL.setText(Double.toString(price * amount));
        }
    }
}

