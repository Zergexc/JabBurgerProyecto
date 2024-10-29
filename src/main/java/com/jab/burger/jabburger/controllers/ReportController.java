package com.jab.burger.jabburger.controllers;

import com.jab.burger.jabburger.models.User;
import com.jab.burger.jabburger.services.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controlador que maneja la generación de reportes en formato Excel.
 * Permite exportar información de usuarios a archivos Excel.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Controller
@RequestMapping("/admin/reportes")
public class ReportController {

    @Autowired
    private UserService userService;

    /**
     * Genera y exporta un reporte Excel con la información de todos los usuarios.
     * Crea un archivo Excel con columnas para ID, Nombre, Email y Celular.
     *
     * @param response Respuesta HTTP para escribir el archivo Excel
     * @throws IOException si hay error al escribir el archivo
     */
    @GetMapping("/usuarios")
    public void exportarUsuarios(HttpServletResponse response) throws IOException {
        List<User> usuarios = userService.findAllUsers();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");

        // Crear el encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Celular");

        // Llenar los datos
        int rowNum = 1;
        for (User user : usuarios) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getNombre());
            row.createCell(2).setCellValue(user.getEmail());
            row.createCell(3).setCellValue(user.getCelular());
        }

        // Configurar la respuesta HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.xlsx");

        // Escribir el libro de trabajo en la respuesta
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
