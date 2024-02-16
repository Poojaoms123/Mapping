package com.example.company.Service.ServiceImpl;

import com.amazonaws.util.IOUtils;
import com.example.company.Model.Company;
import com.example.company.Model.Employee;
import com.example.company.Model.SaveRequest.SaveEmployeeRequest;
import com.example.company.Repository.CompanyRepository;
import com.example.company.Repository.EmployeeRepository;
import com.example.company.Service.EmployeeService;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    private TemplateEngine templateEngine;


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


            String htmlConent = "<html><body><h1>Hi This Is Html Template</h1></body></html>";
            Context context=new Context();
            context.setVariable("name",employee.getEmployeeName());
            String process = templateEngine.process("emai-template.html", context);
            File file = File.createTempFile("template",".pdf");
            InputStream inputStream=new FileInputStream(file);
            FileInputStream fileOutputStream=new FileInputStream(file);
            MultipartFile multipartFile=new MockMultipartFile("converted",file.getName(),".pdf", IOUtils.toByteArray(fileOutputStream));
            HtmlConverter.convertToPdf(process,new FileOutputStream(file));
            this.sendEmailWithAttachment("suryawanshipooja.sp@gmail.com","Application","hello",multipartFile);
            this.sendEmailWithHtmlTemplate("suryawanshipooja.sp@gmail.com","Application",process);
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
    public void sendEmailWithHtmlTemplate(String toEmail, String subject, String body) {

        MimeMessage mimeMessage =javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
           e.printStackTrace();
        }
    }

    public void sendEmailWithAttachment(String toEmail, String subject, String body, MultipartFile attachment) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

      //      FileSystemResource file = new FileSystemResource(new File(attachment));
            helper.addAttachment(attachment.getOriginalFilename(),new ByteArrayResource(attachment.getBytes()));
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
           e.printStackTrace();
        }
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
        List<Employee> employees = employeeRepository.getAllEmployee();
        return  employees;
    }


}
