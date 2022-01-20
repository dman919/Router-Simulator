/**
 * David Man 111940002 RO3
 * The Packet class that generates the packets for the routers
 */

public class Packet {
	//Counter variable for amount of packets sent
	static int packetCount = 1;
	int id;
	int packetSize;
	int timeArrive;
	int timeToDest;
	
	//No argument packet class constructor
	public Packet() {
		id = 0;
		packetSize = 0;
		timeArrive = 0;
		timeToDest = 0;
	}
	
	//Argument packet class constructor
	public Packet(int id, int packetSize,
	  int timeArrive, int timeToDest) {
		this.setId(id);
		this.setPacketSize(packetSize);
		this.setTimeArrive(timeArrive);
		this.setTimeToDest(timeToDest);
	}
	
	public int getPacketCount() {
		return packetCount;
	}
	/**
	 * Packet count mutator method
	 * @param packetCount
	 *   Integer that counts packets sent
	 */
	public void setPacketCount(int packetCount) {
		Packet.packetCount = packetCount;
	}
	
	public int getId() {
		return id;
	}
	/**
	 * Packet Id mutator method
	 * @param id
	 *   Integer that represents the Id of the packet
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPacketSize() {
		return packetSize;
	}
	/**
	 * Packet size mutator method
	 * @param packetSize
	 *   Integer that represents the size of the packet
	 */
	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}
	
	public int getTimeArrive() {
		return timeArrive;
	}
	/**
	 * Packet time arrival mutator method
	 * @param timeArrive
	 *   Integer that represents the time of arrival of the packet
	 */
	public void setTimeArrive(int timeArrive) {
		this.timeArrive = timeArrive;
	}
	
	public int getTimeToDest() {
		return timeToDest;
	}
	/**
	 * Packet time to dest mutator method
	 * @param timeToDest
	 *   Integer that represents the time to dest of the packet
	 */
	public void setTimeToDest(int timeToDest) {
		this.timeToDest = timeToDest;
	}
	
	/**
	 * Packet toString method
	 */
	public String toString() {
		return "[" + getId() + ", " + getTimeArrive() + ", "
		  + getTimeToDest() + "]";
	}
}
