package com.example.company.Service.ServiceImpl;

import com.example.company.Model.Company;
import com.example.company.Model.Employee;
import com.example.company.Model.SaveRequest.SaveEmployeeRequest;
import com.example.company.Repository.CompanyRepository;
import com.example.company.Repository.EmployeeRepository;
import com.example.company.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Otpservice otpservice;

    @Override
    public Object saveOrUpdateEmployee(SaveEmployeeRequest saveEmployeeRequest, Long companyId) throws Exception {
        if (employeeRepository.existsById(saveEmployeeRequest.getEmployeeId())) {
            Employee employee = employeeRepository.findById(saveEmployeeRequest.getEmployeeId()).get();
            employee.setEmployeeName(saveEmployeeRequest.getEmployeeName());
            employee.setEmployeeEmail(saveEmployeeRequest.getEmployeeEmail());
            employee.setEmployeeDesignation(saveEmployeeRequest.getEmployeeDesignation());
            employee.setEmployeeMobileNo(saveEmployeeRequest.getEmployeeMobileNo());
            employee.setEmployeeCode(saveEmployeeRequest.getEmployeeCode());
            employee.setEmployeeIsDeleted(false);
            employee.setEmployeeIsActive(true);
            Company company;
            if (companyRepository.existsByCompanyIdAndCompanyIsDeleted(companyId, false)) {
                company = companyRepository.findByCompanyIdAndCompanyIsDeleted(companyId, false);
                employee.setCompany(company);
            } else {
                throw new Exception("company not found");
            }
            String employeeCode = this.generateEmployeeCode(company.getCompanyName());
            employee.setEmployeeCode(employeeCode);
            employeeRepository.save(employee);
            return "updated sucessfully";
        } else {
            Employee employee = new Employee();
            employee.setEmployeeName(saveEmployeeRequest.getEmployeeName());
            employee.setEmployeeEmail(saveEmployeeRequest.getEmployeeEmail());
            employee.setEmployeeDesignation(saveEmployeeRequest.getEmployeeDesignation());
            employee.setEmployeeMobileNo(saveEmployeeRequest.getEmployeeMobileNo());
            employee.setEmployeeIsDeleted(false);
            employee.setEmployeeIsActive(true);
            Company company;
            if (companyRepository.existsByCompanyIdAndCompanyIsDeleted(companyId, false)) {
                company = companyRepository.findByCompanyIdAndCompanyIsDeleted(companyId, false);
                employee.setCompany(company);
            } else {
                throw new Exception("company not found");
            }
            String employeeCode = this.generateEmployeeCode(company.getCompanyName());
            employee.setEmployeeCode(employeeCode);
            employeeRepository.save(employee);
            int otp = otpservice.generateOtp(saveEmployeeRequest.getEmployeeEmail());
            int serverOtp = otpservice.getOtp(saveEmployeeRequest.getEmployeeEmail());
            otpservice.clearOTP(saveEmployeeRequest.getEmployeeEmail());
            this.sendEmail("suryawanshipooja.sp@gmail.com", "Application", "Hi pooja your otp is " + otp);

            return "save sucessfully";

        }
    }


    public String generateEmployeeCode(String companyName) {
        String employeeCode = companyName.replaceAll(" ", "").substring(0, 3) + "00";
        return employeeCode;

    }

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        javaMailSender.send(mailMessage);

    }




    @Override
    public Object getEmployeeById(Long emploeeId) throws Exception {
        if (employeeRepository.existsById(emploeeId)){
           Employee employee = employeeRepository.findById(emploeeId).get();
            return employee;
        }else {
            throw new Exception("employee not found");
        }
    }

    @Override
    public Object deleteEmployeeById(Long employeeId) throws Exception {
        if(employeeRepository.existsById(employeeId)){
            Employee employee = employeeRepository.findById(employeeId).get();
            employee.setEmployeeIsDeleted(false);
            employeeRepository.save(employee);
            return "deleted sucessfully";
        }else {
            throw new Exception("employee not found");
        }
    }

    @Override
    public Object changeEmployeeStatus(Long employeeId) throws Exception {
        if (employeeRepository.existsById(employeeId)) {
            Employee employee = employeeRepository.findById(employeeId).get() ;
                if (employee.getEmployeeIsActive()) {
                    employee.setEmployeeIsActive(true);
                    employeeRepository.save(employee);
                    return "employee inactive";
                } else {
                    employee.setEmployeeIsActive(false);
                    return "employee active";
                }
        }else {
            throw new Exception("employee not found");
        }
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> employees= employeeRepository.getAllEmployee();
        return  employees;
    }


}
