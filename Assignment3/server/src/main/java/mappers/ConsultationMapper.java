package mappers;

import model.Consultation;
import model.Doctor;
import model.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultationMapper extends Mapper {
	private static ConsultationMapper         instance;
	private        mappers.DoctorMapper       doctorMapper;
	private        mappers.PatientMapper      patientMapper;
	private        Map<Integer, Consultation> buffer;

	private ConsultationMapper() throws mappers.DataSourceException {

		doctorMapper = mappers.DoctorMapper.getInstance();
		patientMapper = mappers.PatientMapper.getInstance();
		buffer = new HashMap<Integer, Consultation>();
	}

	public static ConsultationMapper getInstance() throws mappers.DataSourceException {
		if (instance == null) {
			instance = new ConsultationMapper();
		}
		return instance;

	}

	public void create(Consultation consult) throws mappers.DataSourceException {

		String sql = "INSERT INTO `hospital`.`consultation` (`idPatient`, `idDoctor`, `time`, `observations`, `diagnosis`, `prescription`) VALUES (?, ?, ?, ?, ?, ?);";

		try {
			if (!isFree(consult)) {
				throw new mappers.DataSourceException("Time not available!");
			}
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, consult.getPatient().getPnc());
			statement.setInt(2, consult.getDoctor().getIdaccount());
			// java.sql.Date sqlDate = new
			// java.sql.Date(consult.getTime().getTime());
			statement.setTimestamp(3, consult.getTime());
			statement.setString(4, consult.getObservations());
			statement.setString(5, consult.getDiagnosis());
			statement.setString(6, consult.getPrescription());

			statement.executeUpdate();
			// Can't update buffer due to missing ID. Will update on read.

		} catch (SQLException e) {

			throw new mappers.DataSourceException(e);
		}
	}

	public void update(Consultation consult) throws mappers.DataSourceException {
		String sql = "UPDATE `hospital`.`consultation` SET"
				+ " `idPatient`=?, `idDoctor`=?, `time`=?, `observations`=?, `diagnosis`=?, `prescription`=?"
				+ " WHERE `idconsultation`=?;";

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, consult.getPatient().getPnc());
			statement.setInt(2, consult.getDoctor().getIdaccount());
			// java.sql.Date sqlDate = new
			// java.sql.Date(consult.getTime().getTime());
			statement.setTimestamp(3, consult.getTime());
			statement.setString(4, consult.getObservations());
			statement.setString(5, consult.getDiagnosis());
			statement.setString(6, consult.getPrescription());
			statement.setInt(7, consult.getIdconsultation());

			statement.executeUpdate();
			// Modified Object should be also modified in the buffer
			// (theoretically!!!)
			buffer.put(consult.getIdconsultation(), consult);

		} catch (SQLException e) {

			throw new mappers.DataSourceException(e);
		}
	}

	public void delete(int id) throws mappers.DataSourceException {
		String sql = "DELETE FROM `hospital`.`consultation` WHERE `idconsultation`=?;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			buffer.remove(id);

		} catch (SQLException e) {

			throw new mappers.DataSourceException(e);
		}

	}

	public List<Consultation> getAll() throws mappers.DataSourceException {
		List<Consultation> consultations = new ArrayList<Consultation>();
		String sql = "Select * from consultation";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int idConsult = rs.getInt("idconsultation");
				if (buffer.containsKey(idConsult)) {
					consultations.add(buffer.get(idConsult));
				} else {
					Consultation consultation = new Consultation();
					consultation.setDiagnosis(rs.getString("diagnosis"));
					consultation.setDoctor(doctorMapper.getDoctor(rs.getInt("idDoctor")));
					consultation.setIdconsultation(idConsult);
					consultation.setObservations(rs.getString("observations"));
					consultation.setPatient(patientMapper.getPatient(rs.getInt("idPatient")));
					consultation.setPrescription(rs.getString("prescription"));
					consultation.setTime(rs.getTimestamp("time"));
					buffer.put(idConsult, consultation);
					consultations.add(consultation);
				}
			}

		} catch (SQLException e) {

			throw new mappers.DataSourceException(e);
		}

		return consultations;
	}

	public List<Consultation> getAll(Doctor doctor) throws mappers.DataSourceException {
		List<Consultation> consultations = new ArrayList<Consultation>();
		String sql = "Select * from consultation where idDoctor=" + doctor.getIdaccount();
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int idConsult = rs.getInt("idconsultation");
				if (buffer.containsKey(idConsult)) {
					consultations.add(buffer.get(idConsult));
				} else {
					Consultation consultation = new Consultation();
					consultation.setDiagnosis(rs.getString("diagnosis"));
					consultation.setDoctor(doctor);
					consultation.setIdconsultation(idConsult);
					consultation.setObservations(rs.getString("observations"));
					consultation.setPatient(patientMapper.getPatient(rs.getInt("idPatient")));
					consultation.setPrescription(rs.getString("prescription"));
					consultation.setTime(rs.getTimestamp("time"));
					buffer.put(idConsult, consultation);
					consultations.add(consultation);
				}
			}

		} catch (SQLException e) {

			throw new mappers.DataSourceException(e);
		}

		return consultations;
	}

	public List<Consultation> getAll(Patient patient) throws mappers.DataSourceException {
		List<Consultation> consultations = new ArrayList<Consultation>();
		String sql = "Select * from consultation where idPatient=" + patient.getPnc();
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int idConsult = rs.getInt("idconsultation");
				if (buffer.containsKey(idConsult)) {
					consultations.add(buffer.get(idConsult));
				} else {
					Consultation consultation = new Consultation();
					consultation.setDiagnosis(rs.getString("diagnosis"));
					consultation.setDoctor(doctorMapper.getDoctor(rs.getInt("idDoctor")));
					consultation.setIdconsultation(idConsult);
					consultation.setObservations(rs.getString("observations"));
					consultation.setPatient(patient);
					consultation.setPrescription(rs.getString("prescription"));
					consultation.setTime(rs.getTimestamp("time"));
					buffer.put(idConsult, consultation);
					consultations.add(consultation);
				}
			}

		} catch (SQLException e) {

			throw new mappers.DataSourceException(e);
		}

		return consultations;
	}

	public Consultation getLastConsultation(Doctor doctor) throws mappers.DataSourceException {
		Consultation consultation = null;
		String sql = "SELECT * FROM hospital.consultation where idDoctor=? and time<=NOW() order by time desc LIMIT 1;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, doctor.getIdaccount());
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				int idConsult = rs.getInt("idconsultation");
				if (buffer.containsKey(idConsult)) {
					return (buffer.get(idConsult));
				} else {
					consultation = new Consultation();
					consultation.setDiagnosis(rs.getString("diagnosis"));
					consultation.setDoctor(doctor);
					consultation.setIdconsultation(idConsult);
					consultation.setObservations(rs.getString("observations"));
					consultation.setPatient(patientMapper.getPatient(rs.getInt("idPatient")));
					consultation.setPrescription(rs.getString("prescription"));
					consultation.setTime(rs.getTimestamp("time"));
					buffer.put(idConsult, consultation);
				}
			}

		} catch (SQLException e) {

			throw new mappers.DataSourceException(e);
		}

		return consultation;
	}

	public Consultation getConsultation(int id) throws mappers.DataSourceException {
		Consultation consultation = null;
		String sql = "SELECT * FROM hospital.consultation where idconsultation=? ";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				int idConsult = rs.getInt("idconsultation");
				if (buffer.containsKey(idConsult)) {
					return buffer.get(idConsult);
				} else {
					consultation = new Consultation();
					consultation.setDiagnosis(rs.getString("diagnosis"));
					consultation.setDoctor(doctorMapper.getDoctor(rs.getInt("idDoctor")));
					consultation.setIdconsultation(idConsult);
					consultation.setObservations(rs.getString("observations"));
					consultation.setPatient(patientMapper.getPatient(rs.getInt("idPatient")));
					consultation.setPrescription(rs.getString("prescription"));
					consultation.setTime(rs.getTimestamp("time"));
					buffer.put(idConsult, consultation);
				}
			}

		} catch (SQLException e) {

			throw new mappers.DataSourceException(e);
		}

		return consultation;
	}

	public boolean isFree(Consultation consult) throws SQLException {
		String sql = "select * from consultation where idDoctor = ? and consultation.time between ? and ? ";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, consult.getDoctor().getIdaccount());
		Timestamp start = subtract15Min(consult.getTime());
		preparedStatement.setTimestamp(2, start);
		Timestamp end = add15Min(consult.getTime());
		preparedStatement.setTimestamp(3, end);
		ResultSet rs = preparedStatement.executeQuery();
		return !rs.next();
	}

	private Timestamp add15Min(Timestamp stamp) {
		long current = stamp.getTime();
		long substracted = current + 15 * 60 * 1000;
		return new Timestamp(substracted);
	}

	private Timestamp subtract15Min(Timestamp stamp) {
		long current = stamp.getTime();
		long substracted = current - 15 * 60 * 1000;
		return new Timestamp(substracted);
	}

}
