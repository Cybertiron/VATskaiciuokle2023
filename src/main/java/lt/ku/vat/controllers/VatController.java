package lt.ku.vat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
public class VatController {

    private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.21);

    @GetMapping("/")
    public String form() {
        return "form";
    }

    @PostMapping("/results")
    public String calculate(
            @RequestParam("price") BigDecimal price,
            @RequestParam("quantity") int quantity,
            Model model
    ) {
        BigDecimal unitPriceWithoutVat = price.divide(BigDecimal.ONE.add(VAT_RATE), 2, RoundingMode.HALF_UP);
        BigDecimal unitVat = price.subtract(unitPriceWithoutVat);
        BigDecimal totalPriceWithoutVat = unitPriceWithoutVat.multiply(BigDecimal.valueOf(quantity));
        BigDecimal totalVat = unitVat.multiply(BigDecimal.valueOf(quantity));
        BigDecimal totalPrice = totalPriceWithoutVat.add(totalVat);

        model.addAttribute("unitPriceWithoutVat", unitPriceWithoutVat);
        model.addAttribute("unitVat", unitVat);
        model.addAttribute("price", price);
        model.addAttribute("totalPriceWithoutVat", totalPriceWithoutVat);
        model.addAttribute("totalVat", totalVat);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("quantity", quantity);

        return "results";
    }
}
