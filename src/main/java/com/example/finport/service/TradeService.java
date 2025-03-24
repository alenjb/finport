package com.example.finport.service;

import com.example.finport.entity.Agreement;
import com.example.finport.entity.Request;
import com.example.finport.entity.Stock;
import com.example.finport.entity.User;
import com.example.finport.repository.AgreementRepository;
import com.example.finport.repository.RequestRepository;
import com.example.finport.repository.StockRepository;
import com.example.finport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final UserRepository userRepo;
    private final StockRepository stockRepo;
    private final RequestRepository requestRepo;
    private final AgreementRepository agreementRepo;

    public void placeBuyRequest(Long userId, Long stockId, int quantity, double price) {
        User user = userRepo.findById(userId).orElseThrow();
        Stock stock = stockRepo.findById(stockId).orElseThrow();

        Request request = new Request(user, stock, "BUY", quantity, price, false, LocalDateTime.now());
        requestRepo.save(request);

        matchOrders(request);
    }

    public void placeSellRequest(Long userId, Long stockId, int quantity, double price) {
        User user = userRepo.findById(userId).orElseThrow();
        Stock stock = stockRepo.findById(stockId).orElseThrow();

        Request request = new Request(user, stock, "SELL", quantity, price, false, LocalDateTime.now());
        requestRepo.save(request);

        matchOrders(request);
    }

    private void matchOrders(Request newRequest) {
        List<Request> candidates = newRequest.getType().equals("BUY")
                ? requestRepo.findByTypeAndMatchedFalseOrderByPriceAscCreatedAtAsc("SELL")
                : requestRepo.findByTypeAndMatchedFalseOrderByPriceDescCreatedAtAsc("BUY");

        for (Request existing : candidates) {
            if (!existing.getStock().getId().equals(newRequest.getStock().getId())) continue;
            if (existing.getQuantity() != newRequest.getQuantity()) continue;

            boolean match = newRequest.getType().equals("BUY")
                    ? newRequest.getPrice() >= existing.getPrice()
                    : newRequest.getPrice() <= existing.getPrice();

            if (match) {
                Request buy = newRequest.getType().equals("BUY") ? newRequest : existing;
                Request sell = newRequest.getType().equals("SELL") ? newRequest : existing;

                Agreement agreement = new Agreement();
                agreement.setBuyer(buy.getUser());
                agreement.setSeller(sell.getUser());
                agreement.setStock(buy.getStock());
                agreement.setPrice(sell.getPrice());
                agreement.setQuantity(buy.getQuantity());
                agreement.setCreatedAt(LocalDateTime.now());
                agreementRepo.save(agreement);

                buy.setMatched(true);
                sell.setMatched(true);
                requestRepo.saveAll(List.of(buy, sell));

                Stock stock = buy.getStock();
                double matchedPrice = sell.getPrice();
                stock.setCurrentPrice(matchedPrice);

                // 🔁 상한가/하한가 갱신
                if (stock.getUpperLimitPrice() == 0 || matchedPrice > stock.getUpperLimitPrice()) {
                    stock.setUpperLimitPrice(matchedPrice);
                }
                if (stock.getLowerLimitPrice() == 0 || matchedPrice < stock.getLowerLimitPrice()) {
                    stock.setLowerLimitPrice(matchedPrice);
                }
                stockRepo.save(stock);

                // 📊 자산 및 주식 반영
                double totalPrice = matchedPrice * buy.getQuantity();

                User buyer = buy.getUser();
                buyer.setAsset(buyer.getAsset() - totalPrice);
                buyer.addStock(stock.getId(), buy.getQuantity());

                User seller = sell.getUser();
                seller.setAsset(seller.getAsset() + totalPrice);
                seller.removeStock(stock.getId(), sell.getQuantity());

                userRepo.saveAll(List.of(buyer, seller));
                break;
            }
        }
    }

    public List<Stock> searchStockByName(String name) {
        return stockRepo.findByNameContaining(name).stream().toList();
    }

    public List<Agreement> getUserAgreements(Long userId) {
        return agreementRepo.findByBuyerIdOrSellerId(userId, userId);
    }

    public List<Agreement> getAllAgreements() {
        return agreementRepo.findAllByOrderByCreatedAtDesc();
    }

    public List<Request> getUserPendingRequests(Long userId) {
        return requestRepo.findByUserIdAndMatchedFalse(userId);
    }

    public List<Request> getAllPendingRequests() {
        return requestRepo.findByMatchedFalse();
    }
}