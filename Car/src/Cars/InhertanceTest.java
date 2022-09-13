package Cars;

class GasolineCar{
	private int gasolineGauge;
	public int getGasolineGauge() {
		return gasolineGauge;
	}
	public void setGasolineGauge(int gasolineGauge) {
		this.gasolineGauge = gasolineGauge;
	}
	GasolineCar(int gasolineGauge){
		this.gasolineGauge = gasolineGauge;
	}
}

class ElectricCar extends GasolineCar{
	private int electricGauge;
	ElectricCar(int gasolineCar,int electricGauge){
		super(gasolineCar);
		this.electricGauge = electricGauge;
	}
	public int getElectricGauge() {
		return electricGauge;
	}
	public void setElectricGauge(int electricGauge) {
		this.electricGauge = electricGauge;
	}
}

class WaterCar extends ElectricCar{
	private int waterGauge;
	public int getWaterGauge() {
		return waterGauge;
	}

	public void setWaterGauge(int waterGauge) {
		this.waterGauge = waterGauge;
	}

	WaterCar(int gasolineCar, int electricGauge, int waterGauge){
		super(gasolineCar, electricGauge);
		this.waterGauge = waterGauge;
	}
	
	public void showCurrentGauge() {
		System.out.println("가솔린 양:" +super.getGasolineGauge());
		System.out.println("전기 양:" +super.getElectricGauge());
		System.out.println("물 양:" +this.getWaterGauge());
		
	}
}



public class InhertanceTest {

	public static void main(String[] args) {
		WaterCar car = new WaterCar(5,3,2);
		car.showCurrentGauge();
	}

}
