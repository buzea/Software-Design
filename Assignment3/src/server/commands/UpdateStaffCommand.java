package server.commands;

import server.mappers.DataSourceException;
import server.mappers.StaffMapper;
import client.model.Staff;

public class UpdateStaffCommand implements Command {
	private String id,username,password,type;
	
	public UpdateStaffCommand(String id, String username, String password, String type) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
	}

	@Override
	public Object execute() {
		try {
			Staff staff = new Staff();
			staff.setId(Integer.parseInt(id));
			staff.setUsername(username);
			staff.setPassword(password);
			if(type.equalsIgnoreCase("admin")){
				staff.setRole(Staff.Type.ADMIN);
			}else{
				staff.setRole(Staff.Type.SECRETARY);
			}
			StaffMapper.getInstance().update(staff);
			return "accountUpdated";
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		
		return "accountNotUpdated";
	}

}
