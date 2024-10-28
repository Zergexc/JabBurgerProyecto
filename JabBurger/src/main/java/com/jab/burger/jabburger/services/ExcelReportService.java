package com.jab.burger.jabburger.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.jab.burger.jabburger.models.User;

@Service
public class ExcelReportService {
    
    public ByteArrayInputStream exportUsuariosToExcel(List<User> usuarios) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Usuarios");
            
            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nombre");
            headerRow.createCell(2).setCellValue("Apellido");
            headerRow.createCell(3).setCellValue("Email");
            headerRow.createCell(4).setCellValue("DNI");
            headerRow.createCell(5).setCellValue("Celular");
            headerRow.createCell(6).setCellValue("Rol");
            
            int rowIdx = 1;
            for (User usuario : usuarios) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(usuario.getId());
                row.createCell(1).setCellValue(usuario.getNombre());
                row.createCell(2).setCellValue(usuario.getApellido());
                row.createCell(3).setCellValue(usuario.getEmail());
                row.createCell(4).setCellValue(usuario.getDni());
                row.createCell(5).setCellValue(usuario.getCelular());
                row.createCell(6).setCellValue(usuario.getRol());
            }
            
            // Auto ajustar columnas
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el Excel: " + e.getMessage());
        }
    }
}
