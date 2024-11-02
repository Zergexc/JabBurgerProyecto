package com.jab.burger.jabburger.services;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import com.jab.burger.jabburger.models.User;

/**
 * Servicio para la generación de reportes en formato Excel.
 * Proporciona funcionalidades para exportar datos de usuarios a archivos Excel.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Service
public class ExcelReportService {
    
    /**
     * Exporta una lista de usuarios a un archivo Excel.
     *
     * @param usuarios Lista de usuarios a exportar
     * @return ByteArrayInputStream Stream con el contenido del archivo Excel
     * @throws RuntimeException si hay error al generar el Excel
     */
    public ByteArrayInputStream exportUsuariosToExcel(List<User> usuarios) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Usuarios");
            
            // Agregar logo
            try {
                InputStream logoStream = getClass().getResourceAsStream("/static/images/logo.jpg");
                if (logoStream != null) {
                    byte[] bytes = IOUtils.toByteArray(logoStream);
                    int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
                    CreationHelper helper = workbook.getCreationHelper();
                    Drawing<?> drawing = sheet.createDrawingPatriarch();
                    ClientAnchor anchor = helper.createClientAnchor();
                    
                    // Centrar el logo con más ancho
                    anchor.setCol1(2);  // Columna C
                    anchor.setRow1(0);  // Primera fila
                    anchor.setCol2(5);  // Aumentado a 3 columnas de ancho
                    anchor.setRow2(4);  // Mantener 4 filas de alto
                    
                    Picture picture = drawing.createPicture(anchor, pictureIdx);
                    picture.resize(1.0); // Mantener tamaño completo
                    
                    logoStream.close();
                    
                    // Ajustar altura de las filas para el logo
                    for(int i = 0; i < 4; i++) {
                        Row row = sheet.createRow(i);
                        row.setHeight((short) 1500); // Mantener altura
                    }
                } else {
                    System.err.println("No se pudo encontrar el logo en la ruta especificada");
                }
            } catch (IOException e) {
                System.err.println("Error al cargar el logo: " + e.getMessage());
                e.printStackTrace();
            }

            // Estilos para el título
            XSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            
            XSSFFont titleFont = workbook.createFont();
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);

            // Estilos para los encabezados
            XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            
            XSSFFont headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Estilos para las celdas de datos
            XSSFCellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setAlignment(HorizontalAlignment.CENTER);

            // Ajustar anchos de columna
            sheet.setColumnWidth(0, 2500);  // ID
            sheet.setColumnWidth(1, 6000);  // NOMBRE
            sheet.setColumnWidth(2, 6000);  // APELLIDO
            sheet.setColumnWidth(3, 10000); // EMAIL
            sheet.setColumnWidth(4, 5000);  // DNI
            sheet.setColumnWidth(5, 5000);  // CELULAR

            // Crear título con más espacio después del logo
            Row titleRow = sheet.createRow(5);  // Movido una fila más abajo por el logo más grande
            titleRow.setHeight((short) 800);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("REPORTE DE USUARIOS - JABBURGER");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));

            // Crear encabezados con más espacio
            String[] headers = {"ID", "NOMBRE", "APELLIDO", "EMAIL", "DNI", "CELULAR"};
            Row headerRow = sheet.createRow(7);  // Movido dos filas más abajo
            headerRow.setHeight((short) 500);
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Agregar datos
            int rowIdx = 8;  // Comenzar los datos una fila después de los headers
            for (User usuario : usuarios) {
                Row row = sheet.createRow(rowIdx++);
                
                Cell[] cells = new Cell[6];
                for (int i = 0; i < cells.length; i++) {
                    cells[i] = row.createCell(i);
                    cells[i].setCellStyle(dataStyle);
                }
                
                cells[0].setCellValue(usuario.getId());
                cells[1].setCellValue(usuario.getNombre());
                cells[2].setCellValue(usuario.getApellido());
                cells[3].setCellValue(usuario.getEmail());
                cells[4].setCellValue(usuario.getDni());
                cells[5].setCellValue(usuario.getCelular());
            }

            // Agregar pie de página
            Row footerRow = sheet.createRow(rowIdx + 2);
            Cell footerCell = footerRow.createCell(0);
            footerCell.setCellValue("Reporte generado el: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
            
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el Excel: " + e.getMessage());
        }
    }
}