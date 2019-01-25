package commands;

import mappers.DataSourceException;
import mappers.PatientMapper;

public class DeletePatientCommand implements Command {
	private String pnc;

	public DeletePatientCommand(String pnc) {
		super();
		this.pnc = pnc;
	}

	@Override
	public Object execute() {
		try {
			PatientMapper.getInstance().delete(Integer.parseInt(pnc));
			return "PatientDeleted";
		} catch (DataSourceException e) {
			e.printStackTrace();
			return "PatientNotDeleted";
		}

	}


}
