
public class RefuelSession {
	public RefuelSession(int amountTanked, Fuel typeTanked, Invoice receipt, Stock stock){
		stock.useFuel(typeTanked.gettSIC(), amountTanked, receipt);
	}
}

