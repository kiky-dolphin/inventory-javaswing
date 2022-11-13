package usecase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ActivityModel;

public class ActivityUsecase {
	private Connection conn;
	public ActivityUsecase(Connection conn) {
		this.conn = conn;
	}

	public void request(Integer userId, Integer itemId, Integer qty) throws Exception {
		try {
			PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO items (id_users, id_items, qty) VALUES (?, ?, ?)");
			stmt.setInt(1, userId);
			stmt.setInt(2, itemId);
			stmt.setInt(3, qty);
			stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<ActivityModel> lists() throws Exception {
		List<ActivityModel> activities = new ArrayList<>();
		try {
			Statement stmt = this.conn.createStatement();
			ResultSet results = stmt.executeQuery("SELECT activity.id, activity.qty, items.code AS item_code, requester.username AS request_by_name, approver.username AS approved_by_name,(CASE WHEN activity.qty < 1 THEN 'OUT' ELSE 'IN' END) AS activity_type FROM activity INNER JOIN items ON items.id = activity.id_items LEFT JOIN users AS approver ON approver.id = activity.approved_by LEFT JOIN users AS requester ON requester.id = activity.id_users;");
			while(results.next()) 
			{
				ActivityModel activity = new ActivityModel();
				activity.setId(results.getInt("id"));
				activity.setItemCode(results.getString("item_code"));
				activity.setQty(results.getInt("qty"));
				activity.setActivityType(results.getString("activity_type"));
				activity.setApprovedByName(results.getString("approved_by_name"));
				activity.setRequestByName(results.getString("request_by_name"));
				activities.add(activity);
			}
		} catch(Exception e) {
			throw e;
		}
		return activities;
	}

	public void approve(Integer userId, Integer activityId) throws Exception {
		try {
			PreparedStatement stmt = this.conn.prepareStatement("UPDATE activity SET approved_by = ?, approved_at = ? WHERE id = ?");
			int currentTime  = (int) (new Date().getTime() / 10000);
			stmt.setInt(1, userId);
			stmt.setInt(2, currentTime);
			stmt.setInt(3, activityId);
			stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}	
	}
}
