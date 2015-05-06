package jUnit;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.mappers.ConsultationMapper;
import server.mappers.DataSourceException;
import server.mappers.DoctorMapper;
import server.mappers.PatientMapper;
import server.mappers.StaffMapper;
import client.model.Consultation;
import client.model.Doctor;
import client.model.Patient;
import client.model.Staff;

public class TestCases {
	SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

	//@Test
	public void patientMapperTest()  {
		try{
		PatientMapper mapper = PatientMapper.getInstance();
		System.out.println(mapper.getAll());
		Patient patient = new Patient();
		patient.setPnc(3);
		patient.setAddress("Cluj");
		patient.setBirtdate(new Date());
		patient.setName("Ildiko");
		mapper.create(patient);
		System.out.println(mapper.getAll());
		mapper.delete(3);
		System.out.println(mapper.getAll());
		
		System.out.println("\n");
		}catch(Exception e){
			e.printStackTrace();
			fail("Error");
		}
	}
	
	//@Test
	public void consultMapperTest() throws DataSourceException, ParseException{
		ConsultationMapper consultationMapper = ConsultationMapper.getInstance();
		PatientMapper patientMapper = PatientMapper.getInstance();
		DoctorMapper doctorMapper = DoctorMapper.getInstance();
		
		System.out.println(consultationMapper.getAll());
		
		Patient patient = patientMapper.getPatient(1);
		patient.setConsultations(consultationMapper.getAll(patient));
		System.out.println(patient.getConsultations());
		
		Doctor doctor = doctorMapper.getDoctor(1);
		doctor.setConsultations(consultationMapper.getAll(doctor));
		System.out.println(doctor.getConsultations());
		
		Consultation consultation = new Consultation();
		consultation.setDoctor(doctor);
		consultation.setPatient(patient);
		Timestamp timestamp = Timestamp.valueOf("2015-02-14 12:30:00");
		consultation.setTime(timestamp);
		consultationMapper.create(consultation);
		
		System.out.println(consultationMapper.getAll());
		for(Consultation c:consultationMapper.getAll()){
			if(c.equals(consultation))
				consultationMapper.delete(c.getIdconsultation());
		}
		
		System.out.println("\n");
		
	}
	
	//@Test
	public void staffTest(){
		try{
		StaffMapper mapper= StaffMapper.getInstance();
		System.out.println(mapper.getAll());
		System.out.println(mapper.getStaff("admin"));
		System.out.println(mapper.getStaff(2));
		
		Staff secretary = new Staff();
		secretary.setUsername("Fried");
		secretary.setRole(Staff.Type.SECRETARY);
		secretary.setPassword("Kill everybody!");
		mapper.create(secretary);
		System.out.println(mapper.getAll());
		
		secretary=mapper.getStaff("Fried");
		secretary.setPassword("Silence! I Kill you"); //source: https://www.youtube.com/watch?v=qfResyFrqlM
		mapper.update(secretary);
		System.out.println(mapper.getAll());
		
		
		mapper.delete(secretary.getId());
		System.out.println(mapper.getAll());
		
		}catch (DataSourceException e){
			e.printStackTrace();
			fail("Error");
		}
	}
	
	//@Test
	public void doctorTest() throws DataSourceException{
		DoctorMapper doctorMapper = DoctorMapper.getInstance();
		
		System.out.println(doctorMapper.getDoctor("dobrescul"));
		System.out.println(doctorMapper.getDoctor(1));
		System.out.println(doctorMapper.getAll());
		
		Doctor doctor = new Doctor();
		doctor.setName("Ciomu");
		doctor.setPassword("tai tot");
		doctor.setRating(1);
		doctor.setUsername("ciomu");
		doctorMapper.create(doctor);
		System.out.println(doctorMapper.getAll());
		
		doctor=doctorMapper.getDoctor("ciomu");
		doctor.setRating(0);
		doctorMapper.update(doctor);
		System.out.println(doctorMapper.getAll());
		
		doctorMapper.delete(doctor.getIdaccount());
		System.out.println(doctorMapper.getAll());
		
		
		
	}
	


}
