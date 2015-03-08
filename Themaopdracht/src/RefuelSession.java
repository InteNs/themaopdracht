
public class RefuelSession {
	private Stock stock;
	public RefuelSession(int amountTanked, Fuel typeTanked, Invoice receipt){
		stock.useFuel(typeTanked.gettSIC(), amountTanked, receipt);
	}
}

