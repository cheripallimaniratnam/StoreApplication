package com.store.application.scheduler;

import com.store.application.model.Sale;
import com.store.application.model.UserLogin;
import com.store.application.repository.UserRepository;
import com.store.application.service.EmailService;
import com.store.application.service.SaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DailyReportScheduler {

    @Autowired
    private SaleService saleService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 50 23 * * ?")
    public void sendDailyReport() throws Exception {

        LocalDate today = LocalDate.now();

        List<Sale> sales = saleService.getSalesByDate(today);

        double revenue = sales.stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();

        int orders = sales.size();

        int quantity = sales.stream()
                .mapToInt(Sale::getQuantity)
                .sum();

        String html = buildHtmlReport(today, sales, revenue, orders, quantity);

        List<UserLogin> admins = userRepository.findByRole("ADMIN");

        for (UserLogin admin : admins) {

            emailService.sendHtmlEmail(
                    admin.getEmail(),
                    "Daily Sales Report",
                    html
            );
        }
    }

    private String buildHtmlReport(LocalDate date,
                                   List<Sale> sales,
                                   double revenue,
                                   int orders,
                                   int quantity){

        StringBuilder tableRows = new StringBuilder();

        for(Sale s : sales){

            tableRows.append("<tr>")
                    .append("<td>").append(s.getBrand().getName()).append("</td>")
                    .append("<td>").append(s.getCustomer().getName()).append("</td>")
                    .append("<td>").append(s.getQuantity()).append("</td>")
                    .append("<td>").append(s.getTotalAmount()).append("</td>")
                    .append("</tr>");
        }

        String html =
                "<html>" +
                        "<body style='font-family:Arial; background:#f8f9fc; padding:20px;'>" +

                        "<h2 style='color:#4e73df;'>📊 Daily Sales Report</h2>" +

                        "<p><b>Date:</b> " + date + "</p>" +

                        "<h3>Summary</h3>" +
                        "<ul>" +
                        "<li><b>Total Orders:</b> " + orders + "</li>" +
                        "<li><b>Total Quantity Sold:</b> " + quantity + "</li>" +
                        "<li><b>Total Revenue:</b> ₹" + revenue + "</li>" +
                        "</ul>" +

                        "<h3>Sales Details</h3>" +

                        "<table border='1' cellpadding='8' cellspacing='0' style='border-collapse:collapse;'>" +
                        "<tr style='background:#4e73df;color:white;'>" +
                        "<th>Brand</th>" +
                        "<th>Customer</th>" +
                        "<th>Quantity</th>" +
                        "<th>Total</th>" +
                        "</tr>" +

                        tableRows.toString() +

                        "</table>" +

                        "<br><p style='color:#777;'>Auto generated sales report</p>" +

                        "</body>" +
                        "</html>";

        return html;
    }
}