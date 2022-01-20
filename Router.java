/**
 * David Man 111940002 RO3
 * The Router class that creates the routers for the simulation
 */

import java.util.ArrayList;
import java.util.Collection;

public class Router {
	//ArrayList for router buffer
	ArrayList<Packet> routerBuffer = new ArrayList<Packet>();
	//Integer for maximum router buffer size
	int maxBufferSize = 0;
	
	//No argument Router class constructor
	public Router() {
		routerBuffer = new ArrayList<Packet>();
	}
	
	//Argument Router class constructor
	public Router(ArrayList<Packet> routerBuffer) {
		this.setRouterBuffer(routerBuffer);
	}
	
	public ArrayList<Packet> getRouterBuffer(){
		return routerBuffer;
	}
	/**
	 * Router Buffer mutator method
	 * @param routerBuffer
	 *   ArrayList that represents the router buffer
	 */
	public void setRouterBuffer(ArrayList<Packet> routerBuffer) {
		this.routerBuffer = routerBuffer;
	}
	
	/**
	 * Router Enqueue method
	 * @param p
	 *   Packet that gets added to the end of the router buffer
	 */
	public void enqueue(Packet p) {
		routerBuffer.add(p);
	}
	
	/**
	 * Router Dequeue method
	 * @return
	 *   Returns packet that is removed from the front of the router buffer
	 */
	public Packet dequeue() {
		Packet temp = routerBuffer.get(0);
		routerBuffer.remove(0);
		return temp;
	}
	
	/**
	 * Router Peek method
	 * @return
	 *   Returns the packet at the front of the router buffer
	 */
	public Packet peek() {
		return routerBuffer.get(0);
	}
	
	/**
	 * Router Size method
	 * @return
	 *   Returns the size of the router buffer
	 */
	public int size() {
		return routerBuffer.size();
	}
	
	/**
	 * Router isEmpty method
	 * @return
	 *   Returns true if the router buffer is empty else returns false
	 */
	public boolean isEmpty() {
		if (routerBuffer.size() == 0)
			return true;
		return false;
	}
	
	/**
	 * Router toString method
	 */
	public String toString() {
		String s = "{";
		for (int i = 0; i < routerBuffer.size(); i++) {
			if (i == routerBuffer.size() - 1)
				s += routerBuffer.get(i).toString();
			else
				s += routerBuffer.get(i).toString() + ", ";
		}
		
		return s + "}";
	}
	
	/**
	 * sendPacketTo method that sends a packet to Intermediate routers
	 * @param routers
	 *   Collection of Router objects that represents the Intermediate routers
	 * @return
	 *   Returns which Intermediate router the packet is sent to
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int sendPacketTo(Collection routers) {
		ArrayList<Router> list = new ArrayList<Router>(routers);
		int index = 0;
		int temp = list.get(0).size();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getRouterBuffer().size() < temp) {
				temp = list.get(i).getRouterBuffer().size();
				index = i;
			}
		}
		
		return index;
	}
}