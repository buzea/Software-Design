package commands;

import mappers.DoctorMapper;
import mappers.StaffMapper;

public class DeleteAccount implements Command {
	private String id, type;

	public DeleteAccount(String id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	@Override
	public Object execute() {
		try {
			int role = Integer.parseInt(type);
			int idInt = Integer.parseInt(id);
			if (role == 2) {
				DoctorMapper.getInstance().delete(idInt);
			} else {
				StaffMapper.getInstance().delete(idInt);
			}
			return "accountDeleted";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "accountNotDeleted";
	}

}
