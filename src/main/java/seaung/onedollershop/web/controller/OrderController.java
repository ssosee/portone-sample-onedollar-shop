package seaung.onedollershop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seaung.onedollershop.domain.entity.Member;
import seaung.onedollershop.domain.entity.Order;
import seaung.onedollershop.domain.service.MemberService;
import seaung.onedollershop.domain.service.OrderService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/order")
    public String order(@RequestParam(name = "message", required = false) String message,
                        @RequestParam(name = "orderUid", required = false) String id,
                        Model model) {

        model.addAttribute("message", message);
        model.addAttribute("orderUid", id);

        return "order";
    }

    @PostMapping("/order")
    public String autoOrder(RedirectAttributes redirectAttributes) {
        Member member = memberService.autoRegister();
        Order order = orderService.autoOrder(member);

        String message = "주문 실패";
        if(order != null) {
            message = "주문 성공";
        }

        redirectAttributes.addAttribute("message", message);
        redirectAttributes.addAttribute("orderUid", order.getOrderUid());

        return "redirect:/order";
    }
}
