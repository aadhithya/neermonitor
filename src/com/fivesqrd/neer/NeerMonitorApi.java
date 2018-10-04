package com.fivesqrd.neer;

import javax.ws.rs.*;
import org.json.*;
//import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;



import java.sql.*;
import java.text.SimpleDateFormat;

@Path("/neer")
public class NeerMonitorApi {
	
	
	@Path("/addDevice")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDevice(NeerSense neerSense) {
		
		//SaveDeviceToDB
		return null;
		
	}
	
	@Path("/listDevices")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listDevices(User user){
		
		//Get userId from the request, look up DB for the user's devices.
		return null;
	}
	
	@Path("/setUpdate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setUpdate(@QueryParam("deviceId") String deviceId, @QueryParam("temp") float temp, @QueryParam("counter") float counter){
		
		
		//Get userId from the request, look up DB for the user's devices.
		try {
			String mDate = getDate();
			System.out.println(mDate);
			Connection con = DBHandler.getConnection();
			Statement st = con.createStatement();
			String query = "INSERT INTO `device_usage`(`device_id`,`start_time` ,`counter`, `temp`) VALUES ('"+deviceId+"','"+mDate+"',"+counter+","+temp+")";
			st.execute(query);
			ResponseResult rr = new ResponseResult("Success","Update successful.");
			System.out.println(rr.getStatus()+","+rr.getMessage());
			return Response.ok().entity(rr).header("Access-Control-Allow-Origin", "*")
					//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
					.allow("OPTIONS").build();
		}catch(Exception e) {
			ResponseResult rr = new ResponseResult("Error",e.getMessage());
			System.out.println(rr.getStatus()+","+rr.getMessage());
			return Response.ok().entity(rr).header("Access-Control-Allow-Origin", "*")
					//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
					.allow("OPTIONS").build();
		}
		
		
	}
	
	@Path("/getUpdate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUpdate(@QueryParam("deviceId") String deviceId, @QueryParam("checkLimit") boolean check_limit){
		ResponseResult rr;
		System.out.println(deviceId);
		ResponseResult res=null;
		//Get userId from the request, look up DB for the user's devices.
		try {
			GetUpdateObj msg = null;
			double left=0.0;
			Connection con = DBHandler.getConnection();
			Statement st = con.createStatement();
			String query = "SELECT * FROM `device_usage` WHERE device_id='"+deviceId+"' ORDER BY start_time DESC";
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				if(check_limit) {
					left = checkLimit(deviceId);
				}
				msg = new GetUpdateObj(rs.getString(1),rs.getDouble(4),rs.getDouble(3), left);
			}else {
				throw new Exception();
			}
			return Response.ok().entity(msg).header("Access-Control-Allow-Origin", "*")
					//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
					.allow("OPTIONS").build();
			}catch(Exception e) {
				 res = new ResponseResult("Error",e.getMessage());
			}
		
		
		return Response.ok().entity(res).header("Access-Control-Allow-Origin", "*")
				//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
				.allow("OPTIONS").build();
	}
	
	@Path("/hello")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response setTemp(){
		return Response.ok().entity("<h1> Hello, World!</h1>").header("Access-Control-Allow-Origin", "*")
				//	.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
					.allow("OPTIONS").build(); 
	}
	
	@Path("/addUser")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(@QueryParam("uname") String username, @QueryParam("uid") String uid) {
		ResponseResult res = null;
		try {
		String query = "INSERT INTO `user_table`(`user_id`, `user_name`) VALUES ('"+uid+"','"+username+"' )";
		Connection con = DBHandler.getConnection();
		Statement st = con.createStatement();
		res = new ResponseResult("Success", "User "+username+" added.");
		}catch(Exception e) {
			res = new ResponseResult("Error", e.getMessage());
		}
		return Response.ok().entity(res).header("Access-Control-Allow-Origin", "*")
				//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
				.allow("OPTIONS").build();
	}
	
	@Path("/getTimedDeviceUsage")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTimedDeviceUsage(@QueryParam("deviceId") String deviceId, @QueryParam("fdate") String fdate, @QueryParam("tdate") String tdate, @QueryParam("pq") int pq) {
		
		ResponseResult res= null;
		double daily_total = pgetTimedDeviceUsage(deviceId, fdate, tdate);
		System.out.println(daily_total);
		if (daily_total >=0) {
			TimedConsumption tc = new TimedConsumption(fdate,tdate,deviceId,daily_total);
			System.out.println(tc.getDeviceid());
			return Response.ok().entity(tc).header("Access-Control-Allow-Origin", "*")
					//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
					.allow("OPTIONS").build();
		}else {
			res = new ResponseResult("Error","Operation Failed");
			return Response.ok().entity(res).header("Access-Control-Allow-Origin", "*")
					//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
					.allow("OPTIONS").build();
		}
		
	}
 
	@Path("/getDevices")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDevices(@QueryParam("userId") String userid) {
		ArrayList device_list = new ArrayList<Device>();
		ArrayList device_list_tot = new ArrayList<Device>();
		double total_usage = 0;
		String date = getDate();

		try {
			Connection con = DBHandler.getConnection();
			Statement st = con.createStatement();
			String query = "SELECT device_id,device_name,device_limit FROM `devices` WHERE user_id='"+userid+"'";
			System.out.println(query);
			ResultSet rrs = st.executeQuery(query);
			JSONObject device_list_json = new JSONObject();
			while(rrs.next()) {
				JSONObject device = new JSONObject();
				String device_id= rrs.getString(1);
				String device_name = rrs.getString(2);
				device.put("name", device_name);
				String start_date=null,end_date=null;
				double limit=0.0, total_per_device=0.0;
				String query1 = "SELECT `start_date`, `end_date`, `device_limit` FROM `devices` WHERE device_id='"+device_id+"'";
				Statement st1 = con.createStatement();
				ResultSet rs1 = st1.executeQuery(query1);
				if(rs1.next()) {
					start_date = rs1.getString(1);
					end_date = rs1.getString(2);
					limit = rs1.getDouble(3);
					System.out.println(start_date+","+end_date+","+limit);
				}
				String query2 = "SELECT SUM(counter) FROM `device_usage` WHERE device_id='"+device_id+"' AND start_time='"+date+"'";
				System.out.println(query2);
				Statement st3 = con.createStatement();
				ResultSet rs2 = st3.executeQuery(query2);
				if(rs2.next()) {
					total_per_device = rs2.getDouble(1);
				}
				device.put("total", total_per_device);
				device.put("limit", limit);
				device_list_json.put(device_id,device);
				device_list.add(new Device(device_name,device_id,total_per_device,start_date,end_date,limit));
				}

			return Response.ok().entity(device_list_json.toString()).header("Access-Control-Allow-Origin", "*")
					//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
					.allow("OPTIONS").build();
		}catch(Exception e) {
			e.printStackTrace();
			ResponseResult res = new ResponseResult("Error",e.getMessage());
			return Response.ok().entity(res).header("Access-Control-Allow-Origin", "*")
					//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
					.allow("OPTIONS").build();
		}
	}
	@Path("/getTotalConsumption")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTotalConsumption(@QueryParam("userId") String userid, @QueryParam("date") String date) {
		ArrayList device_list = new ArrayList<Device>();
		double total_usage = 0;
		ResponseResult res = null;
		try {
			Connection con = DBHandler.getConnection();
			Statement st = con.createStatement();
			String query = "SELECT device_id,device_name FROM `devices` WHERE user_id='"+userid+"'";
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				device_list.add(new Device(rs.getString(2),rs.getString(1)));
			}
			System.out.println(device_list.size());
			for(int i=0; i<device_list.size();i++) {
				Device d = (Device)device_list.get(i);
				System.out.println(d.getName()+","+d.getId());
				query = "SELECT SUM(counter) FROM `device_usage` WHERE device_id='"+d.getId()+"' AND start_time='"+date+"'";
				rs = st.executeQuery(query);
				rs.next();
				total_usage += rs.getDouble(1);
			}
			System.out.println(total_usage);
			res = new ResponseResult("Success",""+total_usage);
			System.out.println("Here...");
		}catch(Exception e) {
			res = new ResponseResult("Error",e.getMessage());	
		}
		return Response.ok().entity(res).header("Access-Control-Allow-Origin", "*")
				//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
				.allow("OPTIONS").build();
	}
	
	@Path("/setDeviceLimit")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  Response setLimit(@QueryParam("deviceId") String deviceid, @QueryParam("limit") double limit, @QueryParam("fdate") String fdate, @QueryParam("tdate") String tdate) {
		
		ResponseResult res = null;
		try {
			Connection con = DBHandler.getConnection();
			Statement st = con.createStatement();
			String query = "UPDATE `devices` SET `start_date`='"+fdate+"',`end_date`='"+tdate+"',`device_limit`="+limit+" WHERE devie_id='"+deviceid+"'";
			st.execute(query);
			
			res = new ResponseResult("Success", "Limit added.");
			
		}catch(Exception e) {
			res = new ResponseResult("Error", e.getMessage());
		}
		return Response.ok().entity(res).header("Access-Control-Allow-Origin", "*")
				//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.header("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type")
				.allow("OPTIONS").build();
	}

	private void sendNotification() {
		
	}
	
	private double checkLimit(String deviceid) {
		
		double rem = -1.0;
		try {
			Connection con = DBHandler.getConnection();
			Statement st = con.createStatement();
			String query = "SELECT `start_date`, `end_date`, `device_limit` FROM `devices` WHERE device_id='"+deviceid+"'";
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				String fdate = rs.getString(1);
				String tdate = rs.getString(2);
				double limit = rs.getDouble(3);
				rem = limit - pgetTimedDeviceUsage(deviceid,fdate,tdate);
			}
			return rem;
		}catch(Exception e){
			return rem;
		}		
	}
	
	private double pgetTimedDeviceUsage(String deviceid, String fdate, String tdate) {
		double daily_total = -1.0;
		try {
			Connection con = DBHandler.getConnection();
			Statement st = con.createStatement();
			String query = "SELECT SUM(counter) FROM `device_usage` WHERE device_id='"+deviceid+"' AND start_time BETWEEN '"+fdate+"' AND '"+tdate+"'";
			ResultSet rs = st.executeQuery(query);
			rs.next();
			daily_total = rs.getDouble(1);
			return daily_total;
		}catch(Exception e) {
			return daily_total;
		}
		
	}

	private String getDate() {
		Date date = new Date();
		String mDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		return mDate;
	}
}

