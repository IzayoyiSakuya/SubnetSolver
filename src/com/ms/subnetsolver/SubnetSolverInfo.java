package com.ms.subnetsolver;

import java.io.Serializable;

public class SubnetSolverInfo implements Serializable {
	private static final long serialVersionUID = -7060210544600464481L;
	private int    net_address_int;
	private String net_address_string;
	private int    net_mask_int; // 192.168.77.10/24
	private String net_mask_string;
	private boolean subnet_first;
	private int    subnet_capacity;
	private int    machine_capacity;
	
	
	public boolean isSubnet_first() {
		return subnet_first;
	}

	public void setSubnet_first(boolean subnet_first) {
		this.subnet_first = subnet_first;
	}

	public int getSubnet_capacity() {
		return subnet_capacity;
	}

	public void setSubnet_capacity(int subnet_capacity) {
		this.subnet_capacity = subnet_capacity;
	}

	public int getMachine_capacity() {
		return machine_capacity;
	}

	public void setMachine_capacity(int machine_capacity) {
		this.machine_capacity = machine_capacity;
	}

	public SubnetSolverInfo(int net_address, int net_mask){
		this.setNet_address_int(net_address);
		this.setNet_mask_int(net_mask);	
	}
	
	public String toString(){
		String host = SubnetSolverInfo.ip_int_to_string(net_address_int);
		String mask = String.valueOf(net_mask_int);
		return host + "/" + mask;
	}
	
	public static boolean checkAddrValidate(String net_addr){
		
		String[] ips = net_addr.split("\\.");
		if(ips.length != 4){
			// ERROR!
			return false;
		}
		
		for(int i = 0; i < 4; i++){
			int ip_sec = Integer.parseInt(ips[i]);
			if (ip_sec > 255)
				return false;
			if (ip_sec < 0)
				return false;
		}
		
		return true;
	}
	
	
	static public SubnetSolverInfo[] calculate_subnet_by_net_count(
			SubnetSolverInfo source,
			int subnet_count
			)
	{
		int net_mask_int = source.getNet_mask_int();
		int mask_in_binary = SubnetSolverInfo.bit_to_mask(net_mask_int);
		int net_addr = source.getNet_address_int();
		net_addr = net_addr & mask_in_binary;
		
		int subnet_bit = SubnetSolverInfo.get_capacity(subnet_count);
		int new_subnet_mask = net_mask_int + subnet_bit;
		
		SubnetSolverInfo[] infos = new SubnetSolverInfo[subnet_count];
		
		for(int i = 1; i <= subnet_count; i++){
			int subnet_no = i;
			int subnet_gateway_addr = subnet_no << ((32-net_mask_int) - subnet_bit);
			subnet_gateway_addr = subnet_gateway_addr | net_addr;
			SubnetSolverInfo info = new SubnetSolverInfo(subnet_gateway_addr, new_subnet_mask);
			infos[i-1] = info; 
		}
		
		return infos;
		
	}
	
	static public SubnetSolverInfo[] calculate_subnet_by_host_count(
			SubnetSolverInfo source,
			int host_count
			)
	{	
		int host_bit = SubnetSolverInfo.get_capacity(host_count);
		int subnet_bit = (32 - source.getNet_mask_int()) - host_bit;
		return SubnetSolverInfo.calculate_subnet_by_net_count(source, 1 << subnet_bit);	
	}
	
	
	static public int get_capacity(int required){
		int result = 0;
		for(int i = 0; i < 32; i++){
			int cap = 1 << i;
			if(cap >= required){
				result = i;
				break;
			}
		}
		return result;		
	}
	
	static public String int_to_binary(int num){
		
		String result = "";
		for(int i = 0; i < 32; i++){
			int mask = 1 << (32 - i -1);
			int bit = num | mask;
			if(bit > 0){
				result = result + "1";
			}
			else{
				result = result + "0";
			}
		}
		return result;
	}
	
	static public int bit_to_mask(int bit){
		
		int result = 0;
		
		if(bit >= 32){
			result =  0xffffffff;
		}
		else{
			for(int i = 0 ; i < bit; i++){
				int mask = 1;
				mask = mask << (32 - i - 1);
				result = result | mask;
			}
			
		}
		return result;	
	}
	
	 public static byte[] intToBytes(int ipInt) {
	        byte[] ipAddr = new byte[4];
	        ipAddr[0] = (byte) ((ipInt >>> 24) & 0xFF);
	        ipAddr[1] = (byte) ((ipInt >>> 16) & 0xFF);
	        ipAddr[2] = (byte) ((ipInt >>> 8) & 0xFF);
	        ipAddr[3] = (byte) (ipInt & 0xFF);
	        return ipAddr;
	    }
	 
	  public static int bytesToInt(byte[] bytes) {
	        int addr = bytes[3] & 0xFF;
	        addr |= ((bytes[2] << 8) & 0xFF00);
	        addr |= ((bytes[1] << 16) & 0xFF0000);
	        addr |= ((bytes[0] << 24) & 0xFF000000);
	        return addr;
	    }
	
	static public String ip_int_to_string(int ip){
		int[] masks = new int[4];
		masks[0] = 0xff000000;
		masks[1] = 0xff0000;
		masks[2] = 0xff00;
		masks[3] = 0xff;
		
		
		int[] ip_sections = new int[4];
		for(int i = 0; i < 4; i++){
			int masked = ip & masks[i];
			masked = masked >>> ((4-i-1)*8);
			ip_sections[i] = masked;
		}
		
		String result = "";
		for(int i = 0; i < 4; i++){
			result = result + String.valueOf(ip_sections[i]);
			if(i < 3){
				result = result + ".";
			}
		}
		return result;
	}
	
	static public int ip_string_to_int(String ip){
		
		String[] ips = ip.split("\\.");
		if(ips.length != 4){
			// ERROR!
			return 0;
		}
		
		int result = 0;
		
		for(int i = 0; i < 4; i++){
			int ip_sec = Integer.parseInt(ips[i]);
			ip_sec = ip_sec << ((4-i-1) * 8);
			result = result | ip_sec;
		}
		return result;
	}
	
	public int getNet_address_int() {
		return net_address_int;
	}
	public void setNet_address_int(int net_address_int) {
		this.net_address_int = net_address_int;
	}
	public String getNet_address_string() {
		return net_address_string;
	}
	public void setNet_address_string(String net_address_string) {
		this.net_address_string = net_address_string;
	}
	public int getNet_mask_int() {
		return net_mask_int;
	}
	public void setNet_mask_int(int net_mask_int) {
		this.net_mask_int = net_mask_int;
	}
	public String getNet_mask_string() {
		return net_mask_string;
	}
	public void setNet_mask_string(String net_mask_string) {
		this.net_mask_string = net_mask_string;
	}
	
	
}
