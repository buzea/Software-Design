package commands;

import mappers.ConsultationMapper;
import mappers.DataSourceException;
import mappers.DoctorMapper;
import model.Doctor;
import model.Patient;

public class GetCurrentPatientCommand implements Command {

	private String username;

	public GetCurrentPatientCommand(String string) {
		username = string;
	}

	@Override
	public Object execute() {
		try {
			Doctor doctor = DoctorMapper.getInstance().getDoctor(username);
			Patient patient = ConsultationMapper.getInstance().getLastConsultation(doctor).getPatient();

			patient.setConsultations(ConsultationMapper.getInstance().getAll(patient));

			return patient;
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		return null;
	}

}
