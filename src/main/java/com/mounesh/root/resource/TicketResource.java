package com.mounesh.root.resource;

import com.mounesh.root.Dao.TicketDao;
import com.mounesh.root.exception.model.TicketTypeNotFoundException;
import com.mounesh.root.model.Ticket;
import com.mounesh.root.model.TicketWrapper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/tickets")
@CrossOrigin
public class TicketResource {

    @Autowired
    TicketDao service;

    private final Map<String, Integer> COLUMNS_AND_INDEX = new HashMap();
    private static final String ASSIGNEE = "Assignee";
    private static final String REPORTER = "Reporter";
    private static final String KEY = "Key";
    private static final String RESOLVED = "Resolved";
    private static final String SUMMARY = "Summary";


    {
        COLUMNS_AND_INDEX.put(KEY, 0);
        COLUMNS_AND_INDEX.put(SUMMARY, 0);
        COLUMNS_AND_INDEX.put(ASSIGNEE, 0);
        COLUMNS_AND_INDEX.put(REPORTER, 0);
        COLUMNS_AND_INDEX.put(RESOLVED, 0);
    }

    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable("id") String id, HttpServletRequest request){
        System.out.println("request uri ="+request.getRequestURI());
        return service.getTicket(id);
    }

    @GetMapping
    public List<Ticket> getAllTickets(@RequestParam("type") String ticketType){
        if(ticketType == null){
            throw new TicketTypeNotFoundException("parameter type is missing");
        }
        return  service.getAllTickets(ticketType);
    }

    @PostMapping
    public Ticket addTicket(@RequestBody Ticket model){
        return service.addTicket(model);
    }


    @PutMapping
    public Ticket updateTicket(@RequestBody Ticket ticket){
        return service.updateTicket(ticket);
    }

    @DeleteMapping("/{id}")
    public Ticket deleteTicket(@PathVariable("id") String id){
        return service.deleteTicket(id);
    }

    @PostMapping("/upload")
    public TicketWrapper mapExcelDataToDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
        TicketWrapper model = new TicketWrapper();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        boolean isValidTicket = false;
        System.out.println("upload total number of rows*******************" + worksheet.getPhysicalNumberOfRows());
        if (worksheet.getPhysicalNumberOfRows() > 4 /* check if at least one ticket exist */) {

            XSSFRow header = worksheet.getRow(3); //get header row
            for (int index = 0; index < header.getPhysicalNumberOfCells(); index++) {
                if (COLUMNS_AND_INDEX.containsKey(header.getCell(index).getStringCellValue())) {
                    COLUMNS_AND_INDEX.put(header.getCell(index).getStringCellValue(), index);
                }
            }

            System.out.println(COLUMNS_AND_INDEX);

            for (int i = 4; i < worksheet.getPhysicalNumberOfRows() - 1; i++) {
                Ticket ticket = new Ticket();
                XSSFRow row = worksheet.getRow(i);
                String ticketId = row.getCell(COLUMNS_AND_INDEX.get(KEY)).getStringCellValue();
                if (ticketId != null) {
                    ticket.setId(ticketId);
                    ticket.setTicketType((ticketId.startsWith("PSUP") || ticketId.startsWith("PD")) ? "psup" : "tms");
                }

                ticket.setReporter(row.getCell(COLUMNS_AND_INDEX.get(REPORTER)).getStringCellValue());
                ticket.setName(row.getCell(COLUMNS_AND_INDEX.get(SUMMARY)).getStringCellValue());
                ticket.setAssignee(row.getCell(COLUMNS_AND_INDEX.get(ASSIGNEE)).getStringCellValue());

                SimpleDateFormat reqFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = row.getCell(COLUMNS_AND_INDEX.get(RESOLVED)).getDateCellValue();
                ticket.setResolutionDate(reqFormat.format(date));

                isValidTicket = (ticket.getId() != null && ticket.getName() != null && ticket.getTicketType() != null
                        && ticket.getReporter() != null && ticket.getAssignee() != null && ticket.getResolutionDate() != null);

                if(isValidTicket){
                   model.getValidTickets().add(ticket);
                }else{
                    model.getInValidTickets().add(ticket);
                }
                model.getTotalTickets().add(ticket);
            }
        }
        System.out.println("total number of valid tickets "+model.getValidTickets().size());
        return  service.bulkInsert(model);
    }
}
