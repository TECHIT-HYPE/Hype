package com.ll.hype.domain.order.order.controller;


import com.ll.hype.domain.order.buy.dto.request.CreateBuyRequest;
import com.ll.hype.domain.order.buy.dto.response.BuyResponse;
import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.order.order.dto.OrderRequest;
import com.ll.hype.domain.order.order.dto.response.OrderResponse;
import com.ll.hype.domain.order.order.service.OrderService;
import com.ll.hype.domain.order.sale.dto.response.SaleResponse;
import com.ll.hype.domain.order.sale.service.SaleService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
@Controller
public class OrderController {
    private final OrderService orderService;
    private final BuyService buyService;
    private final SaleService saleService;

    @Value("${api.key}")
    private String API_KEY;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String paymentCreate() throws Exception {
        return "domain/order/order/order_payment";
    }

    /**
     * 인증성공처리
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/payment/success")
    public String paymentSuccess() throws Exception {
        return "domain/order/order/success";
    }

    /**
     * 인증 실패 처리
     *
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/payment/fail")
    public String paymentFail(HttpServletRequest request, Model model) throws Exception {
        String failCode = request.getParameter("code");
        String failMessage = request.getParameter("message");

        // TODO
        // 실패 시 로직 구현

        model.addAttribute("code", failCode);
        model.addAttribute("message", failMessage);
        return "domain/order/order/fail";
    }

    /**
     * 결제 성공 후 2차 확인
     *
     * @param jsonBody
     * @return
     * @throws Exception
     */
    @PostMapping("/confirm")
    public ResponseEntity<JSONObject> paymentConfirm(@RequestBody String jsonBody) throws Exception {
        log.info("[OrderController.confirm] paymentConfirm start");
        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        orderService.checkAmount(orderId, amount);

        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // TODO: 개발자센터에 로그인해서 내 결제위젯 연동 키 > 시크릿 키를 입력하세요. 시크릿 키는 외부에 공개되면 안돼요.
        // @docs https://docs.tosspayments.com/reference/using-api/api-keys
        String widgetSecretKey = API_KEY;

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        // @docs https://docs.tosspayments.com/reference/using-api/authorization#%EC%9D%B8%EC%A6%9D
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

        // 결제 승인 API를 호출하세요.
        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        // @docs https://docs.tosspayments.com/guides/payment-widget/integration#3-결제-승인하기
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200 ? true : false;
        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        if (!isSuccess) {
            throw new IllegalArgumentException("결제 실패");
        }

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ResponseEntity.status(code).body(jsonObject);
    }

    @PostMapping("/complete")
    public String paymentComplete(@RequestParam String tossId,
                                  Model model) {
        log.info("[OrderController.paymentComplete] 진입 : " + tossId);
        orderService.setPaymentComplete(tossId);
        return "redirect:/mypage/order/trading/buy";
    }


    /**
     * 결제
     */
    @PostMapping("/payment")
    public String createBuyNow(@RequestParam("id") Long id,
                               @AuthenticationPrincipal UserPrincipal user,
                               Model model) {
        OrderResponse order = orderService.findOrder(id, user.getMember());
        model.addAttribute("order", order);
        return "domain/order/order/order_payment";
    }

    /**
     * 구매 확정
     */
    @PutMapping("/trad/complete")
    public String tradComplete(@RequestParam("id") Long id,
                               @AuthenticationPrincipal UserPrincipal user) {
        orderService.orderComplete(id, user.getMember());
        return "redirect:/mypage/order/trading/buy";
    }
}
