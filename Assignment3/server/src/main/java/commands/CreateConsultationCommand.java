package commands;

import mappers.ConsultationMapper;
import mappers.DataSourceException;
import mappers.DoctorMapper;
import mappers.PatientMapper;
import model.Consultation;
import model.Doctor;
import model.Patient;

import java.sql.Timestamp;

public class CreateConsultationCommand implements Command {
	private String pnc, idDoctor, time;

	public CreateConsultationCommand(String pnc, String idDoctor, String time) {
		super();
		this.pnc = pnc;
		this.idDoctor = idDoctor;
		this.time = time;
	}

	@Override
	public Object execute() {
		try {
			Patient patient = PatientMapper.getInstance().getPatient(Integer.parseInt(pnc));
			Doctor doctor = DoctorMapper.getInstance().getDoctor(Integer.parseInt(idDoctor));
			Timestamp date = Timestamp.valueOf(time);
			Consultation consult = new Consultation();
			consult.setDoctor(doctor);
			consult.setPatient(patient);
			consult.setTime(date);
			ConsultationMapper.getInstance().create(consult);

			return "ConsultCreated";
		} catch (NumberFormatException | DataSourceException e) {
			e.printStackTrace();
		}

		return "ConsultNotCreated";
	}

}
