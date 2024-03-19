package com.example.swd392.controller;

import com.example.swd392.Response.VNPay.ApiResponse;
import com.example.swd392.config.VnPayConfig;
import com.example.swd392.repository.CartRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/v1/payment")
public class VNPayController {
    @GetMapping("/pay")
    public ResponseEntity<?> getPay(HttpServletRequest req, HttpServletResponse resp,
                                    @RequestParam(name = "amount") long amount) throws ServletException, IOException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        amount = amount*100;
        String bankCode = "NCB";

        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VnPayConfig.getIpAddress(req);

        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("username", username);

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;
//        JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));
        ApiResponse response = new ApiResponse();
        response.setCode("00");
        response.setMessage("success");
        response.setData(paymentUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/results")
    public ResponseEntity<?> getResult(@RequestParam(name = "vnp_Amount") String vnpAmount,
                                       @RequestParam(name = "vnp_BankCode") String vnpBankCode,
                                       @RequestParam(name = "vnp_BankTranNo") String vnpBankTranNo,
                                       @RequestParam(name = "vnp_CardType") String vnpCardType,
                                       @RequestParam(name = "vnp_OrderInfo") String vnpOrderInfo,
                                       @RequestParam(name = "vnp_PayDate") String vnpPayDate,
                                       @RequestParam(name = "vnp_ResponseCode") String vnpResponseCode,
                                       @RequestParam(name = "vnp_TmnCode") String vnpTmnCode,
                                       @RequestParam(name = "vnp_TransactionNo") String vnpTransactionNo,
                                       @RequestParam(name = "vnp_TransactionStatus") String vnpTransactionStatus,
                                       @RequestParam(name = "vnp_TxnRef") String vnpTxnRef,
                                       @RequestParam(name = "vnp_SecureHash") String vnpSecureHash, Model model
    ) {
//        log.info("vnpAmount|{}|vnpBankCode|{}|vnpBankTranNo|{}|vnpCardType|{}|vnpOrderInfo|{}|vnpPayDate|{}|" +
//                        "vnpPayDate|{}|vnpTmnCode|{}|vnpTransactionNo|{}|vnpTransactionStatus|{}|vnpTxnRef|{}|vnpSecureHash|{}",
//                vnpAmount, vnpBankCode, vnpBankTranNo, vnpCardType, vnpOrderInfo, vnpPayDate, vnpPayDate, vnpTmnCode,
//                vnpTransactionNo, vnpTransactionStatus, vnpTxnRef, vnpSecureHash);
        ApiResponse response = new ApiResponse();
        if(vnpTransactionStatus.equals("00")) {
            response.setCode("200");
            response.setMessage("Payment success");
//            repository.updateAllBought(username);
        } else {
            response.setCode("500");
            response.setMessage("Payment processing error");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
