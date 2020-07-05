package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public void listObjects( Map<Integer,ArtObject> map) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				map.put(res.getInt("object_id"), artObj);
			}
			conn.close();
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
public List<ArtObject> listVertex(Map<Integer,ArtObject> map) {
		
		String sql = "select distinct object_id " + 
				"from objects ";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				result.add(map.get(res.getInt("object_id")));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


public List<Adiacenza> listEdge(Map<Integer,ArtObject> map) {
	
	String sql = "select  o1.object_id,o2.object_id, count(distinct o1.object_id) as tot " + 
			"from exhibition_objects as o1,exhibition_objects as o2 " + 
			"where o1.object_id > o2.object_id " + 
			"and o1.exhibition_id = o2.exhibition_id " + 
			"group by o1.object_id,o2.object_id ";
	List<Adiacenza> result = new ArrayList<>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		ResultSet res = st.executeQuery();
		while (res.next()) {
			int id1=res.getInt("o1.object_id");
			int id2=res.getInt("o2.object_id");
			int peso =res.getInt("tot");
			result.add(new Adiacenza(map.get(id1), map.get(id2), peso));
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
}

	
}
