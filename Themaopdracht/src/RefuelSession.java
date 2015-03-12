import java.time.LocalDate;


public class RefuelSession {
	public RefuelSession(int amountTanked, Fuel typeTanked, Invoice receipt, Stock stock){
		stock.useFuel(typeTanked.gettSIC(), amountTanked);
		double price = amountTanked * typeTanked.getSellPrice();
		receipt.addItem(price, typeTanked.getType(),""+amountTanked);
		if(receipt.getCustomer() != null)receipt.getCustomer().setLastVisit(LocalDate.now());
	}
}

