/**
 * David Man 111940002 RO3
 * The Simulator class that simulates packets being sent through the network
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Simulator {
	//Integer for maximum packets being sent to the Dispatcher router
	public static final int MAX_PACKETS = 3;
	//Integer for the total service time of the simulation
	static int totalServiceTime;
	//Integer for the total packets arrived in the simulation
	static int totalPacketsArrived;
	//Integer for the total packets dropped in the simulation
	static int packetsDropped;
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		System.out.println("Starting simulator...");

		String s = "y";
		//Finds user input for all parameters and runs the simulation
		while (s.equals("Y") || s.equals("y")) {
			try {
				int numIntRouters = 0;
				double arrivalProb = 0;
				int maxBufferSize = 0;
				int minPacketSize = 0;
				int maxPacketSize = 0;
				int bandwidth = 0;		
				int duration = 0;

				System.out.print(
				  "\nEnter the number of Intermediate routers: ");
				numIntRouters = input.nextInt();
				while (numIntRouters < 0) {
					System.out.println("Enter a positive integer");
					System.out.print(
							  "\nEnter the number of Intermediate routers: ");
							numIntRouters = input.nextInt();
				}

				System.out.print(
				  "\nEnter the arrival probability of a packet: ");
				arrivalProb = input.nextDouble();
				while (arrivalProb > 1 || arrivalProb < 0) {
					System.out.println(
					  "\nError : Enter a probablity between 0 and 1");
					System.out.print(
					  "\nEnter the arrival probability of a packet: ");
					arrivalProb = input.nextDouble();
				}
				
				System.out.print(
				  "\nEnter the maximum buffer size of a router: ");
				maxBufferSize = input.nextInt();
				while (maxBufferSize < 0) {
					System.out.println("Enter a positive integer");
					System.out.print(
					  "\nEnter the maximum buffer size of a router: ");
					maxBufferSize = input.nextInt();
				}
				
				System.out.print(
				  "\nEnter the minimum size of a packet: ");
				minPacketSize = input.nextInt();
				while (minPacketSize < 0) {
					System.out.println("Enter a positive integer");
					System.out.print(
					  "\nEnter the minimum size of a packet: ");
					  minPacketSize = input.nextInt();
				}
				
				System.out.print(
				  "\nEnter the maximum size of a packet: ");
				maxPacketSize = input.nextInt();
				while (maxPacketSize < 0) {
					System.out.println("Enter a positive integer");
					System.out.print(
					  "\nEnter the maximum size of a packet: ");
					  maxPacketSize = input.nextInt();
				}
		
				System.out.print(
				  "\nEnter the bandwidth size: ");
				bandwidth = input.nextInt();		
				while (bandwidth < 0) {
					System.out.println("Enter a positive integer");
					System.out.print(
					  "\nEnter the bandwidth size: ");
					  bandwidth = input.nextInt();		
				}
				
				System.out.print(
				  "\nEnter the simulation duration: ");
				duration = input.nextInt();
				while (duration < 0) {
					System.out.println("Enter a positive integer");
					System.out.print(
					  "\nEnter the simulation duration: ");
					  duration = input.nextInt();
				}

				Router dispatcher = new Router();
				Collection<Router> routers = null;
				
				Simulator simulation = new Simulator();
			
				simulation.simulate(numIntRouters, arrivalProb,
				  maxBufferSize, minPacketSize, maxPacketSize,
				  bandwidth, duration, dispatcher, routers);
				
				totalServiceTime = 0;
				totalPacketsArrived = 0;
				packetsDropped = 0;

				System.out.print(
				  "\nDo you want to try another simulation? (y/n): ");
				  s = input.next();
				while (!(s.equals("y") || s.equals("n") ||
						  s.equals("Y") || s.equals("N"))) {
					System.out.println("Invalid Input: Enter 'y' or 'n'");
					System.out.print(
					  "\nDo you want to try another simulation? (y/n): ");
					  s = input.next();
				}
			}
			catch (Exception e) {
				System.out.println("Error : Invalid Input");
				break;
			}
		}
		
		System.out.print("\nProgram terminating successfully...");
	}
	
	/**
	 * Simulate method that simulates sending packets through a network
	 * @param numIntRouters
	 *   Integer that represents the number of Intermediate routers
	 * @param arrivalProb
	 *   Double variable that represents the probability of a packet arriving
	 *   at the Dispatcher router
	 * @param maxBufferSize
	 *   Integer that represents the maximum buffer size of the Intermediate
	 *   routers
	 * @param minPacketSize
	 *   Integer that represents the minimum size of each packet
	 * @param maxPacketSize
	 *   Integer that represents the maximum size of each packet
	 * @param bandwidth
	 *   Integer that represents the bandwidth of the amount of packets that
	 *   can be sent to the Destination router at one time
	 * @param duration
	 *   Integer that represents the duration of the simulation
	 * @param dispatcher
	 *   Router class object that represents the Dispatcher router
	 * @return
	 */
	public double simulate(int numIntRouters, double arrivalProb,
	  int maxBufferSize, int minPacketSize, int maxPacketSize,
	  int bandwidth, int duration, Router dispatcher,
	  Collection<Router> routers) {
		ArrayList<Router> IntermediateRouters = new ArrayList<Router>();
		
		for (int i = 0; i < numIntRouters; i++) {
			Router r = new Router();
			IntermediateRouters.add(r);
		}

		int d = 1;
		while (d <= duration) {
			System.out.println("\nTime: " + d);
			
			//Decrease timeToDest
			for (int i = 0; i < numIntRouters; i++) {
				if (IntermediateRouters.get(i).getRouterBuffer().size()
				  == 0) {
					continue;
				}
				else if (IntermediateRouters.get(i).getRouterBuffer().
				  get(0).getTimeToDest() > 0) {
					IntermediateRouters.get(i).getRouterBuffer().
					  get(0).setTimeToDest(
					  IntermediateRouters.get(i).
					  getRouterBuffer().
					  get(0).
					  getTimeToDest() - 1);
				}
			}
			
			//Creates the packets and adds them to dispatcher
			int noPackets = 0;
			for (int i = 0; i < MAX_PACKETS; i++) {
				if (Math.random() < arrivalProb) {
					int packetSize = randInt(minPacketSize, maxPacketSize);
					Packet p = new Packet(
					  Packet.packetCount,
					  packetSize,
					  d,
					  packetSize / 100);
					
					p.setPacketCount(p.getPacketCount() + 1);

					dispatcher.enqueue(p);
					
					System.out.println(
					  "Packet " + p.getId() +
					  " arrives at dispatcher with size " +
					  p.getPacketSize() + ".");
				}
				else {
					noPackets++;
				}
				
				if (noPackets == MAX_PACKETS)
					System.out.println("No packets arrived.");
			}
			
			//Sends the packets from dispatcher to Intermediate routers
			for (int i = 0; i < MAX_PACKETS; i++) {
				if (dispatcher.size() > 0) {
					if (IntermediateRoutersAreFull(
					  IntermediateRouters, maxBufferSize)) {
						System.out.println("Network is congested. Packet " +
						  dispatcher.getRouterBuffer().get(0).getId() +
						  " is dropped.");
						dispatcher.dequeue();
						packetsDropped++;
					}
					else {
						Packet temp = dispatcher.dequeue();
						int index = Router.sendPacketTo(IntermediateRouters);
						IntermediateRouters.get(index).enqueue(temp);
						System.out.println("Packet " + temp.getId() + 
						  " sent to Router " + (index + 1) + ".");
					}
				}
			}

			//Sends packets to Destination router
			for (int i = 0; i < bandwidth; i++) {
				reachDestination(IntermediateRouters);
			}
			
			//Prints out all Intermediate routers and packets in routers
			for (int i = 1; i <= numIntRouters; i++) {
				System.out.println("R" + i + ": " +
				  IntermediateRouters.get(i - 1).toString());
			}

			d++;
		}
		
		double averageServiceTime = totalServiceTime /
		  (float) totalPacketsArrived;
		
		System.out.println("\nSimulation ending..." + 
		  "\nTotal service time: " + totalServiceTime +
		  "\nTotal packets served: " + totalPacketsArrived);
		System.out.print(String.format("%s%.2f",
		  "Average service time per packet: ", averageServiceTime));
		System.out.println("\nTotal packets dropped: " + packetsDropped);
		return averageServiceTime;
	}
	
	/**
	 * randInt method that finds a random size of the packet
	 * @param minVal
	 *   Integer that represents the minimum size of the packet
	 * @param maxVal
	 *   Integer that represents the maximum size of the packet
	 * @return
	 *   Returns the size of the packet
	 */
	private int randInt(int minVal, int maxVal) {
		return (int)(Math.random() * ((maxVal - minVal) + 1) + minVal);
	}
	
	/**
	 * reachDestination method that finds if the packet has reached the
	 *   Destination router
	 * @param routers
	 *   ArrayList representing the Intermediate routers
	 * @return
	 *   Updated Intermediate router ArrayList with packets that haven't
	 *   reached the Destination router yet
	 */
	public ArrayList<Router> reachDestination(ArrayList<Router> routers){
		for (int i = 0; i < routers.size(); i++) {
			for (int j = 0; j < routers.get(i).size(); j++) {
				if (routers.get(i).getRouterBuffer().get(j).getTimeToDest()
				  == 0) {
					System.out.println("Packet " + 
					  routers.get(i).getRouterBuffer().get(j).getId() +
					  " has successfully reached its destination: +" + 
					  routers.get(i).getRouterBuffer().get(j).getPacketSize()
					  / 100);
					totalServiceTime += (routers.get(i).getRouterBuffer().
					  get(j).getPacketSize() / 100);
					totalPacketsArrived++;
					routers.get(i).dequeue();
					return routers;
				}
			}
		}
		
		return routers;
	}
	
	/**
	 * IntermediateRoutersAreFull method that checks if all the Intermediate
	 *   routers are full
	 * @param routers
	 *   ArrayList representing the Intermediate routers
	 * @param maxBufferSize
	 *   Integer that represents the maximum buffer size of the Intermediate
	 *   routers
	 * @return
	 *   Returns true if the all the Intermediate routers are full, else false
	 */
	public boolean IntermediateRoutersAreFull(ArrayList<Router> routers,
	  int maxBufferSize) {
		int count = 0;

		for (int i = 0; i < routers.size(); i++) {
			if (routers.get(i).size() == maxBufferSize)
				count++;
		}
		
		if (count == routers.size())
			return true;
		
		return false;
	}
}