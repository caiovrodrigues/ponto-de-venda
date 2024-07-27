package com.caio.pdv.web;

import net.sf.jasperreports.engine.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jasper")
public class JasperController {

    @GetMapping
    public void generateReport() throws FileNotFoundException, JRException {
        JasperReport mainReport = JasperCompileManager.compileReport(new FileInputStream("sr/main/resources/templates/docente.jrxml"));

        List<Users> users = List.of(new Users("caio", "123"), new Users("josh", "1234"));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("UsersList", users);

        JasperPrint report = JasperFillManager.fillReport(mainReport, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfFile(report, "src/main/resources/report.pdf");

    }

    record Users(String username, String password){}

}
