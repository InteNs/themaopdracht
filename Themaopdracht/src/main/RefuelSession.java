package main;
import java.time.LocalDate;


public class RefuelSession {
	public RefuelSession(int amountTanked, Product typeTanked, Invoice receipt, Stock stock){
		stock.useProduct(typeTanked, amountTanked);
		double price = amountTanked * typeTanked.getSellPrice();
		//receipt.addItem(price, typeTanked.getName(),""+amountTanked);
		if(receipt.getCustomer() != null)receipt.getCustomer().setLastVisit(LocalDate.now());
	}
}

