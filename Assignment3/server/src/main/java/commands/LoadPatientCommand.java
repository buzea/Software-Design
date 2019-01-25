package commands;

import mappers.DataSourceException;
import mappers.PatientMapper;

public class LoadPatientCommand implements Command {
	private String pnc;

	public LoadPatientCommand(String pnc) {
		super();
		this.pnc = pnc;
	}


	@Override
	public Object execute() {
		try {
			return PatientMapper.getInstance().getPatient(Integer.parseInt(pnc));
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		return null;
	}

}
