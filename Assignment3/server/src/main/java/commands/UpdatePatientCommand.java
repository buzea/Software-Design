package commands;

import mappers.PatientMapper;
import model.Patient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdatePatientCommand implements Command {
	@SuppressWarnings("unused")
	private String           pncString;
	@SuppressWarnings("unused")
	private String           name;
	@SuppressWarnings("unused")
	private String           birthdate;
	@SuppressWarnings("unused")
	private String           address;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");


	public UpdatePatientCommand(String staffUsername, String pncString, String name, String birthdate, String address) {
		super();
		String staffUsername1 = staffUsername;
		this.pncString = pncString;
		this.name = name;
		this.birthdate = birthdate;
		this.address = address;
	}

	@Override
	public Object execute() {

		try {
			Patient patient = new Patient();
			int pnc = Integer.parseInt(pncString);
			patient.setPnc(pnc);
			patient.setName(name);
			Date date = df.parse(birthdate);
			patient.setBirtdate(date);
			patient.setAddress(address);
			PatientMapper.getInstance().update(patient);
			return "PatientUpdated";

		} catch (Exception e) {
			return "PatientNotUpdated";
		}


	}

}
