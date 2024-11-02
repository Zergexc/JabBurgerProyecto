package com.jab.burger.jabburger.controllers;

import com.jab.burger.jabburger.models.User;
import com.jab.burger.jabburger.services.UserService;
import com.jab.burger.jabburger.services.ExcelReportService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Controlador que maneja la generación y descarga de reportes en formato Excel.
 * Este controlador proporciona endpoints para exportar diferentes tipos de datos a Excel.
 */
@Controller
@RequestMapping("/admin/reportes") // Ruta base para todos los endpoints de reportes
public class ReportController {

    // Inyección de dependencias usando @Autowired
    @Autowired
    private UserService userService; // Servicio para obtener datos de usuarios
    
    @Autowired
    private ExcelReportService excelReportService; // Servicio para generar archivos Excel

    /**
     * Endpoint para exportar la lista de usuarios a un archivo Excel.
     * Cuando se accede a /admin/reportes/usuarios, genera y descarga un archivo Excel
     * con la información de todos los usuarios.
     *
     * @param response El objeto HttpServletResponse para enviar el archivo al cliente
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    @GetMapping("/usuarios")
    public void exportarUsuarios(HttpServletResponse response) throws IOException {
        // 1. Obtener la lista completa de usuarios desde la base de datos
        List<User> usuarios = userService.findAllUsers();

        // 2. Generar el archivo Excel usando el servicio especializado
        // El servicio convierte los datos a un formato Excel y los devuelve como un stream
        ByteArrayInputStream excelFile = excelReportService.exportUsuariosToExcel(usuarios);

        // 3. Configurar los headers de la respuesta HTTP
        // Establecer el tipo de contenido para archivo Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // Configurar el nombre del archivo que se descargará
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.xlsx");

        // 4. Escribir el contenido del Excel en la respuesta
        // IOUtils.copy copia el contenido del archivo al stream de salida de la respuesta
        IOUtils.copy(excelFile, response.getOutputStream());
        // No es necesario cerrar los streams ya que Spring los maneja automáticamente
    }
}
