
public class ProductSupplier {
	private String name,phone, adress, postal, place;

	public ProductSupplier(String name, String phone, String adress, String postal, String place) {
		this.name = name;
		this.phone = phone;
		this.adress = adress;
		this.postal = postal;
		this.place = place;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "ProductSupplier [name=" + name + ", phone=" + phone
				+ ", adress=" + adress + ", postal=" + postal + ", place="
				+ place + "]";
	}
}
