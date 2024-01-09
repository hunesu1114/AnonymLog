package BoardAdv.AnonymLog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortfolioController {

    @GetMapping("/portfolio")
    public String portfolioPage() {

        return "portfolio/portfolio";
    }
}
